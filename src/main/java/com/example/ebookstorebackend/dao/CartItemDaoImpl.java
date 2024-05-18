package com.example.ebookstorebackend.dao;


import com.example.ebookstorebackend.entity.CartItemEntity;
import com.example.ebookstorebackend.entity.OrderItemEntity;
import com.example.ebookstorebackend.repo.CartItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartItemDaoImpl implements CartItemDao {
    @Autowired
    private CartItemRepo cartItemRepo;

    @Override
    public void addCartItem(CartItemEntity CartItem) {
        cartItemRepo.save(CartItem);
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepo.deleteById(id);
    }

    @Override
    public void updateCartItem(CartItemEntity CartItem) {
        cartItemRepo.save(CartItem);
    }

    @Override
    public CartItemEntity getCartItem(Long id) {
        return cartItemRepo.findById(id).orElse(null);
    }

    @Override
    public List<CartItemEntity> getCartItems() {
        return cartItemRepo.findAll();
    }

    @Override
    public OrderItemEntity convertToOrderItem(CartItemEntity cartItem) {
        return new OrderItemEntity(cartItem.getBook(), cartItem.getQuantity());
    }
}
