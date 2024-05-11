package com.example.ebookstorebackend.order;

import java.util.List;

public interface OrderService {
    void createOrder(OrderEntity order);

    void deleteOrder(Long id);

    void deleteAllOrders();

    OrderEntity getOrder(Long id);

    OrderEntity updateOrder(OrderEntity newOrder);

    List<OrderEntity> getAllOrders();

    List<OrderEntity> getOrdersByTimeRange(String start, String end);
}