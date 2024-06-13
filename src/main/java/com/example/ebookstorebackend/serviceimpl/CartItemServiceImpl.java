package com.example.ebookstorebackend.serviceimpl;


import com.example.ebookstorebackend.dao.CartItemDao;
import com.example.ebookstorebackend.entity.CartItemEntity;
import com.example.ebookstorebackend.entity.OrderItemEntity;
import com.example.ebookstorebackend.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemDao cartItemDao;

    @Override
    public void addCartItem(CartItemEntity CartItem) {
        cartItemDao.addCartItem(CartItem);
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemDao.deleteCartItem(id);
    }

    @Override
    public void updateCartItem(CartItemEntity CartItem) {
        cartItemDao.updateCartItem(CartItem);
    }

    @Override
    public CartItemEntity getCartItem(Long id) {
        return cartItemDao.getCartItem(id);
    }

    @Override
    public List<CartItemEntity> getCartItems() {
        return cartItemDao.getCartItems();
    }

    @Override
    public OrderItemEntity convertToOrderItem(CartItemEntity cartItem) {
        return cartItemDao.convertToOrderItem(cartItem);
    }

    @Override
    public List<OrderItemEntity> convertToOrderItems(List<CartItemEntity> cartItems) {
        return cartItems.stream().map(this::convertToOrderItem).collect(Collectors.toList());
    }
}
