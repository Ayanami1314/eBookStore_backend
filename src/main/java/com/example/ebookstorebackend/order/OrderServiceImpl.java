package com.example.ebookstorebackend.order;

import com.example.ebookstorebackend.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDaoImpl;
    @Autowired
    private UserService userService;

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
    public List<OrderEntity> searchOnesOrdersByTimeRange(String start, String end, String keyword, Long userId) {
        return orderDaoImpl.searchOnesOrdersByTimeRange(start, end, keyword, userId);
    }

    @Override
    public List<OrderEntity> searchMyOrdersByTimeRange(String start, String end, String keyword, HttpSession session) {
        var user = userService.getCurUser(session);
        if (user == null) {
            System.out.println("Please Login again.");
            return null;
        }
        return orderDaoImpl.searchOnesOrdersByTimeRange(start, end, keyword, user.getId());
    }

    @Override
    public List<OrderEntity> searchOrdersByTimeRange(String start, String end, String keyword) {
        if (keyword == null || keyword.isEmpty())
            return orderDaoImpl.getOrdersByTimeRange(start, end);
        return orderDaoImpl.searchOrdersByTimeRange(start, end, keyword);
    }
}
