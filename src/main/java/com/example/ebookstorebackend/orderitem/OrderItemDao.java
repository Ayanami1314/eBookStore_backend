package com.example.ebookstorebackend.orderitem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderItemDao {
    @Autowired
    private OrderItemRepo orderItemRepository;

    public void addOrderItem(OrderItemEntity orderItem) {
        orderItemRepository.save(orderItem);
    }

    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }

    public void updateOrderItem(OrderItemEntity orderItem) {
        orderItemRepository.save(orderItem);
    }

    public OrderItemEntity getOrderItem(Long id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    public List<OrderItemEntity> getOrderItems() {
        return orderItemRepository.findAll();
    }


}
