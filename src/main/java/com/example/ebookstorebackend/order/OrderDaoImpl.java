package com.example.ebookstorebackend.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private OrderRepo orderRepo;

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

    @Override
    public List<OrderEntity> getOrdersByTimeRange(String start, String end) {
        Timestamp endTime = null;
        Timestamp startTime = null;
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
}
