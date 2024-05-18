package com.example.ebookstorebackend.dao;

import com.example.ebookstorebackend.entity.CartItemEntity;
import com.example.ebookstorebackend.entity.OrderItemEntity;

import java.util.List;

public interface CartItemDao {
    void addCartItem(CartItemEntity CartItem);

    void deleteCartItem(Long id);

    void updateCartItem(CartItemEntity CartItem);

    CartItemEntity getCartItem(Long id);

    List<CartItemEntity> getCartItems();

    OrderItemEntity convertToOrderItem(CartItemEntity cartItem);
}
