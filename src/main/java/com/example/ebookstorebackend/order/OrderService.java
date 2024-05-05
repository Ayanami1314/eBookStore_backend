package com.example.ebookstorebackend.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    public void createOrder(OrderEntity order) {
        orderDao.addOrder(order);
    }

    public void deleteOrder(Long id) {
        orderDao.deleteOrderById(id);
    }

    public void deleteAllOrders() {
        orderDao.deleteAllOrders();
    }

    public OrderEntity getOrder(Long id) {
        return orderDao.getOrderById(id);
    }

    public OrderEntity updateOrder(OrderEntity newOrder) {
        return orderDao.updateOrder(newOrder);
    }

    public List<OrderEntity> getAllOrders() {
        return orderDao.getAllOrders();
    }

}
