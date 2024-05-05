package com.example.ebookstorebackend.orderitem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    OrderItemDao orderItemDao;

    public void addOrderItem(OrderItemEntity orderItem) {
        orderItemDao.addOrderItem(orderItem);
    }

    public void deleteOrderItem(Long id) {
        orderItemDao.deleteOrderItem(id);
    }

    public void updateOrderItem(OrderItemEntity orderItem) {
        orderItemDao.updateOrderItem(orderItem);
    }

    public OrderItemEntity getOrderItem(Long id) {
        return orderItemDao.getOrderItem(id);
    }

    public List<OrderItemEntity> getOrderItems() {
        return orderItemDao.getOrderItems();
    }
}
