package com.example.ebookstorebackend.orderitem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemDaoImpl orderItemDaoImpl;

    @Override
    public void addOrderItem(OrderItemEntity orderItem) {
        orderItemDaoImpl.addOrderItem(orderItem);
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemDaoImpl.deleteOrderItem(id);
    }

    @Override
    public void updateOrderItem(OrderItemEntity orderItem) {
        orderItemDaoImpl.updateOrderItem(orderItem);
    }

    @Override
    public OrderItemEntity getOrderItem(Long id) {
        return orderItemDaoImpl.getOrderItem(id);
    }

    @Override
    public List<OrderItemEntity> getOrderItems() {
        return orderItemDaoImpl.getOrderItems();
    }
}
