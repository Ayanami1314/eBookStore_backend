package com.example.ebookstorebackend.service;

import com.example.ebookstorebackend.dao.OrderItemDao;
import com.example.ebookstorebackend.entity.OrderItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemDao orderItemDao;

    @Override
    public void addOrderItem(OrderItemEntity orderItem) {
        orderItemDao.addOrderItem(orderItem);
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemDao.deleteOrderItem(id);
    }

    @Override
    public void updateOrderItem(OrderItemEntity orderItem) {
        orderItemDao.updateOrderItem(orderItem);
    }

    @Override
    public OrderItemEntity getOrderItem(Long id) {
        return orderItemDao.getOrderItem(id);
    }

    @Override
    public List<OrderItemEntity> getOrderItems() {
        return orderItemDao.getOrderItems();
    }
}
