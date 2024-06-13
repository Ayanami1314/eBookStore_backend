package com.example.ebookstorebackend.serviceimpl;

import com.example.ebookstorebackend.dao.OrderDao;
import com.example.ebookstorebackend.entity.OrderEntity;
import com.example.ebookstorebackend.service.OrderService;
import com.example.ebookstorebackend.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserService userService;

    @Override
    public void createOrder(OrderEntity order) {
        orderDao.addOrder(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderDao.deleteOrderById(id);
    }

    @Override
    public void deleteAllOrders() {
        orderDao.deleteAllOrders();
    }

    @Override
    public OrderEntity getOrder(Long id) {
        return orderDao.getOrderById(id);
    }

    @Override
    public OrderEntity updateOrder(OrderEntity newOrder) {
        return orderDao.updateOrder(newOrder);
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return orderDao.getAllOrders();
    }


    @Override
    public List<OrderEntity> searchOnesOrdersByTimeRange(String start, String end, String keyword, Long userId) {
        return orderDao.searchOnesOrdersByTimeRange(start, end, keyword, userId);
    }

    @Override
    public List<OrderEntity> searchMyOrdersByTimeRange(String start, String end, String keyword, HttpSession session) {
        var user = userService.getCurUser(session);
        if (user == null) {
            System.out.println("Please Login again.");
            return null;
        }
        return orderDao.searchOnesOrdersByTimeRange(start, end, keyword, user.getId());
    }

    @Override
    public List<OrderEntity> searchOrdersByTimeRange(String start, String end, String keyword) {
        if (keyword == null || keyword.isEmpty())
            return orderDao.getOrdersByTimeRange(start, end);
        return orderDao.searchOrdersByTimeRange(start, end, keyword);
    }
}
