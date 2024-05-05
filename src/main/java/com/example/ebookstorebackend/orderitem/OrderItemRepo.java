package com.example.ebookstorebackend.orderitem;

import org.springframework.data.repository.ListCrudRepository;

public interface OrderItemRepo extends ListCrudRepository<OrderItemEntity, Long> {
}
