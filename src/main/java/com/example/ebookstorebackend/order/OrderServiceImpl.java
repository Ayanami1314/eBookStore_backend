package com.example.ebookstorebackend.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDaoImpl;

    @Override
    public void createOrder(OrderEntity order) {
        orderDaoImpl.addOrder(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderDaoImpl.deleteOrderById(id);
    }

    @Override
    public void deleteAllOrders() {
        orderDaoImpl.deleteAllOrders();
    }

    @Override
    public OrderEntity getOrder(Long id) {
        return orderDaoImpl.getOrderById(id);
    }

    @Override
    public OrderEntity updateOrder(OrderEntity newOrder) {
        return orderDaoImpl.updateOrder(newOrder);
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return orderDaoImpl.getAllOrders();
    }

    @Override
    public List<OrderEntity> getOrdersByTimeRange(String start, String end) {
        return orderDaoImpl.getOrdersByTimeRange(start, end);
    }

}
