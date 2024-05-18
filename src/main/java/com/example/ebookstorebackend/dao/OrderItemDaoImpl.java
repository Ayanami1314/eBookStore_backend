package com.example.ebookstorebackend.dao;

import com.example.ebookstorebackend.entity.OrderItemEntity;
import com.example.ebookstorebackend.repo.OrderItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {
    @Autowired
    private OrderItemRepo orderItemRepository;

    @Override
    public void addOrderItem(OrderItemEntity orderItem) {
        orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }

    @Override
    public void updateOrderItem(OrderItemEntity orderItem) {
        orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItemEntity getOrderItem(Long id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    @Override
    public List<OrderItemEntity> getOrderItems() {
        return orderItemRepository.findAll();
    }

}
