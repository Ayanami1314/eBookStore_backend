package com.example.ebookstorebackend.service;

import com.example.ebookstorebackend.entity.CartEntity;
import com.example.ebookstorebackend.entity.CartItemEntity;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface CartService {
    CartEntity getCart(HttpSession session);

    List<CartItemEntity> getCartItems(HttpSession session);

    CartEntity createCart(HttpSession session);

    void clearCart(HttpSession session);

    CartItemEntity getCartItem(Long cartItemId, HttpSession session);

    boolean addCartItem(CartItemEntity cartItem, HttpSession session);

    void save(CartEntity cart);

    boolean removeCartItem(Long cartItemId, HttpSession session);

    boolean updateCartItem(Long cartItemId, int quantity, HttpSession session);
}
