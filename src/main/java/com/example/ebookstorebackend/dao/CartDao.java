package com.example.ebookstorebackend.dao;

import com.example.ebookstorebackend.entity.CartEntity;
import com.example.ebookstorebackend.entity.CartItemEntity;
import com.example.ebookstorebackend.entity.UserEntity;
import com.example.ebookstorebackend.repo.CartRepo;
import com.example.ebookstorebackend.service.UserService;
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
    private CartItemDao cartItemDao;

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

    public List<CartItemEntity> getCartItems(HttpSession session) {
        var cart = getCart(session);
        if (cart == null) {
            return new ArrayList<>();
        }
        return cart.getCartItems();
    }

    public void save(CartEntity cart) {
        cartRepo.save(cart);
    }

    public CartItemEntity getCartItem(Long cartItemId, HttpSession session) {
        var cart = getCart(session);
        if (cart == null) {
            System.out.println("Cannot get item from cart: cart does not exist");
            return null;
        }
        for (CartItemEntity cartItem : cart.getCartItems()) {
            if (cartItem.getId().equals(cartItemId)) {
                return cartItem;
            }
        }
        System.out.println("Failed to get item from cart: item does not exist");
        return null;
    }

    public CartEntity createCart(HttpSession session) {
        UserEntity user = userService.getCurUser(session);
        if (user == null) {
            System.out.println("Cannot create cart: current user is null");
            return null;
        }
        CartEntity cart = new CartEntity(user);
        return cartRepo.save(cart);
    }

    public boolean addCartItem(CartItemEntity cartItem, HttpSession session) {
        boolean hasLogin = UserAuth.hasLogin(session);
        if (!hasLogin) {
            System.out.println("Please Login to add items to cart");
            return false;
        }
        CartEntity cart = getCart(session);
        if (cart == null) {
            cart = createCart(session); // HINT: Create a new cart if the user does not have one
        }
        cartItem.setCart(cart);
        cart.getCartItems().add(cartItem);
        cartRepo.save(cart); // 级联保存
        return true;
    }

    public boolean removeCartItem(Long cartItemId, HttpSession session) {
        CartEntity cart = getCart(session);
        if (cart == null) {
            System.out.println("Cannot remove item from cart: cart does not exist");
            return false;
        }
        try {
            cart.getCartItems().removeIf(cartItem -> cartItem.getId().equals(cartItemId));
            // 同时删除db中的cartItem
            cartItemDao.deleteCartItem(cartItemId);
            cartRepo.save(cart);
            return true;
        } catch (Exception e) {
            System.out.println("Cannot remove item from cart: item does not exist");
            return false;
        }
    }

    public boolean updateCartItem(Long cartItemId, int quantity, HttpSession session) {
        CartEntity cart = getCart(session);
        if (cart == null) {
            System.out.println("Cannot update item in cart: cart does not exist");
            return false;
        }
        for (CartItemEntity cartItem : cart.getCartItems()) {
            if (cartItem.getId().equals(cartItemId)) {
                cartItem.setQuantity(quantity);
                cartItemDao.updateCartItem(cartItem);
                cartRepo.save(cart);
                return true;
            }
        }
        System.out.println("Cannot update item in cart: item does not exist");
        return false;
    }

}
