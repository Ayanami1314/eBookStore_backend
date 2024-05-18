package com.example.ebookstorebackend.repo;

import com.example.ebookstorebackend.entity.OrderItemEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface OrderItemRepo extends ListCrudRepository<OrderItemEntity, Long> {
}
