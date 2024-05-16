package com.example.ebookstorebackend.order;

import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface OrderService {
    void createOrder(OrderEntity order);

    void deleteOrder(Long id);

    void deleteAllOrders();

    OrderEntity getOrder(Long id);

    OrderEntity updateOrder(OrderEntity newOrder);

    List<OrderEntity> getAllOrders();

    // List<OrderEntity> getOrdersByTimeRange(String start, String end);

    // List<OrderEntity> getMyOrdersByTimeRange(String start, String end, HttpSession session);

    // List<OrderEntity> getOnesOrdersByTimeRange(String start, String end, Long userId);
    List<OrderEntity> searchOrdersByTimeRange(String start, String end, String keyword);

    List<OrderEntity> searchOnesOrdersByTimeRange(String start, String end, String keyword, Long userId);

    List<OrderEntity> searchMyOrdersByTimeRange(String start, String end, String keyword, HttpSession session);

}