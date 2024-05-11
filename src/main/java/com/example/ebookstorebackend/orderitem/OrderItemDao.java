package com.example.ebookstorebackend.orderitem;

import java.util.List;

public interface OrderItemDao {
    void addOrderItem(OrderItemEntity orderItem);

    void deleteOrderItem(Long id);

    void updateOrderItem(OrderItemEntity orderItem);

    OrderItemEntity getOrderItem(Long id);

    List<OrderItemEntity> getOrderItems();
}
