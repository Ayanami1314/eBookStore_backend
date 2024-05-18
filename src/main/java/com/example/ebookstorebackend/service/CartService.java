package com.example.ebookstorebackend.service;


import com.example.ebookstorebackend.dao.CartDao;
import com.example.ebookstorebackend.entity.CartEntity;
import com.example.ebookstorebackend.entity.CartItemEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartDao cartDao;

    public CartEntity getCart(HttpSession session) {
        return cartDao.getCart(session);
    }

    public List<CartItemEntity> getCartItems(HttpSession session) {
        return cartDao.getCartItems(session);
    }

    public CartEntity createCart(HttpSession session) {
        return cartDao.createCart(session);
    }

    public void clearCart(HttpSession session) {
        CartEntity cart = getCart(session);
        if (cart == null) {
            System.out.println("Cannot clear cart: cart does not exist");
            return;
        }
        cart.getCartItems().clear();
        cartDao.save(cart);
    }

    public CartItemEntity getCartItem(Long cartItemId, HttpSession session) {
        return cartDao.getCartItem(cartItemId, session);
    }

    public boolean addCartItem(CartItemEntity cartItem, HttpSession session) {
        return cartDao.addCartItem(cartItem, session);
    }


    public void save(CartEntity cart) {
        cartDao.save(cart);
    }

    public boolean removeCartItem(Long cartItemId, HttpSession session) {
        return cartDao.removeCartItem(cartItemId, session);
    }

    public boolean updateCartItem(Long cartItemId, int quantity, HttpSession session) {
        return cartDao.updateCartItem(cartItemId, quantity, session);
    }


}
