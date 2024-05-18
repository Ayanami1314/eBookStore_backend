package com.example.ebookstorebackend.dao;

import com.example.ebookstorebackend.entity.OrderItemEntity;

import java.util.List;

public interface OrderItemDao {
    void addOrderItem(OrderItemEntity orderItem);

    void deleteOrderItem(Long id);

    void updateOrderItem(OrderItemEntity orderItem);

    OrderItemEntity getOrderItem(Long id);

    List<OrderItemEntity> getOrderItems();
}
