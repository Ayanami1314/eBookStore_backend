package com.example.ebookstorebackend.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDao {
    @Autowired
    private OrderRepo orderRepo;

    public OrderEntity addOrder(OrderEntity order) {
        return orderRepo.save(order);
    }

    public OrderEntity getOrderById(Long id) {
        return orderRepo.findById(id).orElse(null);
    }

    public void deleteOrderById(Long id) {
        orderRepo.deleteById(id);
    }

    public void deleteAllOrders() {
        orderRepo.deleteAll();
    }


    public OrderEntity updateOrder(OrderEntity newOrder) {
        // HINT: You can use the save method to update an existing entity
        return orderRepo.save(newOrder);
    }


    public List<OrderEntity> getAllOrders() {
        return orderRepo.findAll();
    }
}
