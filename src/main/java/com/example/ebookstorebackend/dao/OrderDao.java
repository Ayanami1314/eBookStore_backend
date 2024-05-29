package com.example.ebookstorebackend.dao;

import com.example.ebookstorebackend.entity.OrderEntity;

import java.util.List;

public interface OrderDao {
    void addOrder(OrderEntity order);

    OrderEntity getOrderById(Long id);

    void deleteOrderById(Long id);

    void deleteAllOrders();

    OrderEntity updateOrder(OrderEntity newOrder);

    List<OrderEntity> getAllOrders();

    List<OrderEntity> getOrdersByTimeRange(String start, String end);

    List<OrderEntity> searchOrdersByTimeRange(String start, String end, String keyword);

    List<OrderEntity> getOnesOrdersByTimeRange(String start, String end, Long userId);

    List<OrderEntity> searchOnesOrdersByTimeRange(String start, String end, String keyword, Long userId);
}
