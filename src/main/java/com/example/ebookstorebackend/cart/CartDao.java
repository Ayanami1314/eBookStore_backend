package com.example.ebookstorebackend.cart;

import com.example.ebookstorebackend.orderitem.OrderItemDaoImpl;
import com.example.ebookstorebackend.orderitem.OrderItemEntity;
import com.example.ebookstorebackend.user.UserPublicEntity;
import com.example.ebookstorebackend.user.UserServiceImpl;
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
    private UserServiceImpl userServiceImpl;
    @Autowired
    private OrderItemDaoImpl orderItemDaoImpl;

    public CartEntity getCart(Long cartId) {
        return cartRepo.findById(cartId).orElse(null);
    }

    public CartEntity getCart(HttpSession session) {
        var user = userServiceImpl.getCurUser(session);
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
        UserPublicEntity user = userServiceImpl.getCurUser(session);
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
        orderItem.setOrder(null);
        orderItem.setCart(cart);
        cart.getOrderItems().add(orderItem);
        cartRepo.save(cart); // 级联保存
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
        OrderItemEntity originOrderItem = orderItemDaoImpl.getOrderItem(orderItemId);
        originOrderItem.setCart(null);
        orderItemDaoImpl.updateOrderItem(originOrderItem);
        return true;
    }

    public boolean updateCartItem(Long orderItemId, int quantity, HttpSession session) {
        CartEntity cart = getCart(session);
        if (cart == null) {
            System.out.println("Cannot update item in cart: cart does not exist");
            return false;
        }
        for (OrderItemEntity orderItem : cart.getOrderItems()) {
            if (orderItem.getId().equals(orderItemId)) {
                orderItem.setQuantity(quantity);
                orderItemDaoImpl.updateOrderItem(orderItem);
                cartRepo.save(cart);
                return true;
            }
        }
        System.out.println("Cannot update item in cart: item does not exist");
        return false;
    }

}
