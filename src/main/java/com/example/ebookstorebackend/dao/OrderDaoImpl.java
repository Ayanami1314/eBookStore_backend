package com.example.ebookstorebackend.dao;

import com.example.ebookstorebackend.entity.BookEntity;
import com.example.ebookstorebackend.entity.OrderEntity;
import com.example.ebookstorebackend.entity.OrderItemEntity;
import com.example.ebookstorebackend.repo.BookRepo;
import com.example.ebookstorebackend.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private BookRepo bookRepo;

    @Override
    public void addOrder(OrderEntity order) {
        orderRepo.save(order);
    }

    @Override
    public OrderEntity getOrderById(Long id) {
        return orderRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteOrderById(Long id) {
        orderRepo.deleteById(id);
    }

    @Override
    public void deleteAllOrders() {
        orderRepo.deleteAll();
    }


    @Override
    public OrderEntity updateOrder(OrderEntity newOrder) {
        // HINT: You can use the save method to update an existing entity
        return orderRepo.save(newOrder);
    }


    @Override
    public List<OrderEntity> getAllOrders() {
        return orderRepo.findAll();
    }

    public List<OrderEntity> filterByBookContains(List<OrderEntity> orders, String keyword) {
        List<BookEntity> books = bookRepo.findByTitleContaining(keyword);
        if (books == null || books.isEmpty())
            return new ArrayList<>();
        Set<BookEntity> bookSet = new HashSet<>(books);
        List<OrderEntity> ordersCopy = new ArrayList<>();
        for (OrderEntity order : orders) {
            for (OrderItemEntity item : order.getOrderItems()) {
                if (bookSet.contains(item.getBook())) {
                    ordersCopy.add(order);
                    break;
                }
            }
        }
        return ordersCopy;
    }

    @Override
    public List<OrderEntity> getOrdersByTimeRange(String start, String end) {
        Timestamp endTime;
        Timestamp startTime;
        try {
            startTime = start == null ? null : Timestamp.valueOf(start);
            endTime = end == null ? null : Timestamp.valueOf(end);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid time format");
            return null;
        }
        if (endTime == null && startTime == null)
            return orderRepo.findAll();
        if (endTime == null)
            return orderRepo.findByTimeAfter(startTime);
        if (startTime == null)
            return orderRepo.findByTimeBefore(endTime);
        return orderRepo.findByOrderTimeBetween(startTime, endTime);
    }


    @Override
    public List<OrderEntity> getOnesOrdersByTimeRange(String start, String end, Long userId) {
        Timestamp endTime;
        Timestamp startTime;
        try {
            startTime = start == null ? null : Timestamp.valueOf(start);
            endTime = end == null ? null : Timestamp.valueOf(end);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid time format");
            return null;
        }
        if (endTime == null && startTime == null)
            return orderRepo.findAll();
        if (endTime == null)
            return orderRepo.findByTimeAfterAndUserName(startTime, userId);
        if (startTime == null)
            return orderRepo.findByTimeBeforeAndUserName(endTime, userId);
        return orderRepo.findByOrderTimeBetweenAndUserName(startTime, endTime, userId);
    }

    @Override
    public List<OrderEntity> searchOrdersByTimeRange(String start, String end, String keyword) {
        List<OrderEntity> orders = getOrdersByTimeRange(start, end);
        return filterByBookContains(orders, keyword);
    }

    @Override
    public List<OrderEntity> searchOnesOrdersByTimeRange(String start, String end, String keyword, Long userId) {
        List<OrderEntity> orders = getOnesOrdersByTimeRange(start, end, userId);
        return filterByBookContains(orders, keyword);
    }
}
