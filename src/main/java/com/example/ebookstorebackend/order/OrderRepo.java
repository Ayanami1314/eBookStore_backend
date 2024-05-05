package com.example.ebookstorebackend.order;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface OrderRepo extends ListCrudRepository<OrderEntity, Long> {
    public List<OrderEntity> findAll();
}
