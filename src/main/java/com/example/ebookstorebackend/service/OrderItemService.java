package com.example.ebookstorebackend.service;

import com.example.ebookstorebackend.entity.OrderItemEntity;

import java.util.List;

public interface OrderItemService {
    void addOrderItem(OrderItemEntity orderItem);

    void deleteOrderItem(Long id);

    void updateOrderItem(OrderItemEntity orderItem);

    OrderItemEntity getOrderItem(Long id);

    List<OrderItemEntity> getOrderItems();
}
