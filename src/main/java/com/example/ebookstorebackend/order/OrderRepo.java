package com.example.ebookstorebackend.order;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.sql.Timestamp;
import java.util.List;

public interface OrderRepo extends ListCrudRepository<OrderEntity, Long> {
    public List<OrderEntity> findAll();

    @Query("SELECT o FROM OrderEntity o WHERE o.createdAt BETWEEN ?1 AND ?2")
    List<OrderEntity> findByOrderTimeBetween(Timestamp start, Timestamp end);

    @Query("SELECT o FROM OrderEntity o WHERE o.createdAt > ?1")
    List<OrderEntity> findByTimeAfter(Timestamp start);

    @Query("SELECT o FROM OrderEntity o WHERE o.createdAt < ?1")
    List<OrderEntity> findByTimeBefore(Timestamp end);
}
