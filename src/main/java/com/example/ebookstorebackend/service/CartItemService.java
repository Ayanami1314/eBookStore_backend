package com.example.ebookstorebackend.service;

import com.example.ebookstorebackend.entity.CartItemEntity;
import com.example.ebookstorebackend.entity.OrderItemEntity;

import java.util.List;


public interface CartItemService {
    void addCartItem(CartItemEntity CartItem);

    void deleteCartItem(Long id);

    void updateCartItem(CartItemEntity CartItem);

    CartItemEntity getCartItem(Long id);

    List<CartItemEntity> getCartItems();

    OrderItemEntity convertToOrderItem(CartItemEntity cartItem);

    List<OrderItemEntity> convertToOrderItems(List<CartItemEntity> cartItems);
}
