package com.example.ebookstorebackend.cart;

import com.example.ebookstorebackend.orderitem.OrderItemDao;
import com.example.ebookstorebackend.orderitem.OrderItemEntity;
import com.example.ebookstorebackend.user.UserPublicEntity;
import com.example.ebookstorebackend.user.UserService;
import com.example.ebookstorebackend.utils.UserAuth;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CartDao {
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderItemDao orderItemDao;

    public CartEntity getCart(Long cartId) {
        return cartRepo.findById(cartId).orElse(null);
    }

    public CartEntity getCart(HttpSession session) {
        var user = userService.getCurUser(session);
        if (user == null) {
            System.out.println("Cannot find the cart: current user is null");
            return null;
        }

        return cartRepo.findByUserId(user.getId());
    }

    public List<OrderItemEntity> getCartItems(HttpSession session) {
        var cart = getCart(session);
        if (cart == null) {
            return new ArrayList<>();
        }
        return cart.getOrderItems();
    }

    public void save(CartEntity cart) {
        cartRepo.save(cart);
    }

    public OrderItemEntity getCartItem(Long cartItemId, HttpSession session) {
        var cart = getCart(session);
        if (cart == null) {
            System.out.println("Cannot get item from cart: cart does not exist");
            return null;
        }
        for (OrderItemEntity orderItem : cart.getOrderItems()) {
            if (orderItem.getId().equals(cartItemId)) {
                return orderItem;
            }
        }
        System.out.println("Failed to get item from cart: item does not exist");
        return null;
    }

    public CartEntity createCart(HttpSession session) {
        UserPublicEntity user = userService.getCurUser(session);
        if (user == null) {
            System.out.println("Cannot create cart: current user is null");
            return null;
        }
        CartEntity cart = new CartEntity(user);
        return cartRepo.save(cart);
    }

    public boolean addCartItem(OrderItemEntity orderItem, HttpSession session) {
        boolean hasLogin = UserAuth.hasLogin(session);
        if (!hasLogin) {
            System.out.println("Please Login to add items to cart");
            return false;
        }
        CartEntity cart = getCart(session);
        if (cart == null) {
            cart = createCart(session); // HINT: Create a new cart if the user does not have one
        }
        cart.getOrderItems().add(orderItem);
        cartRepo.save(cart);
        OrderItemEntity newOrderItem = orderItem;
        newOrderItem.setCart(cart);
        orderItemDao.updateOrderItem(orderItem);
        return true;
    }

    public boolean removeCartItem(Long orderItemId, HttpSession session) {
        CartEntity cart = getCart(session);
        if (cart == null) {
            System.out.println("Cannot remove item from cart: cart does not exist");
            return false;
        }
        cart.getOrderItems().removeIf(orderItem -> orderItem.getId().equals(orderItemId));
        cartRepo.save(cart);
        OrderItemEntity originOrderItem = orderItemDao.getOrderItem(orderItemId);
        originOrderItem.setCart(null);
        orderItemDao.updateOrderItem(originOrderItem);
        return true;
    }

    public void updateCartItem(Long orderItemId, int quantity, HttpSession session) {
        CartEntity cart = getCart(session);
        if (cart == null) {
            System.out.println("Cannot update item in cart: cart does not exist");
            return;
        }
        for (OrderItemEntity orderItem : cart.getOrderItems()) {
            if (orderItem.getId().equals(orderItemId)) {
                orderItem.setQuantity(quantity);
                orderItemDao.updateOrderItem(orderItem);
                cartRepo.save(cart);
                return;
            }
        }
        System.out.println("Cannot update item in cart: item does not exist");
    }

}
