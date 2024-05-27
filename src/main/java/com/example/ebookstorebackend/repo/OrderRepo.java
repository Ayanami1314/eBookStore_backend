package com.example.ebookstorebackend.repo;

import com.example.ebookstorebackend.entity.OrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.sql.Timestamp;
import java.util.List;

public interface OrderRepo extends ListCrudRepository<OrderEntity, Long> {
    List<OrderEntity> findAll();

    @Query("SELECT o FROM OrderEntity o WHERE o.createdAt BETWEEN ?1 AND ?2")
    List<OrderEntity> findByOrderTimeBetween(Timestamp start, Timestamp end);

    @Query("SELECT o FROM OrderEntity o WHERE o.createdAt > ?1")
    List<OrderEntity> findByTimeAfter(Timestamp start);

    @Query("SELECT o FROM OrderEntity o WHERE o.createdAt < ?1")
    List<OrderEntity> findByTimeBefore(Timestamp end);

    @Query("SELECT o FROM OrderEntity o WHERE o.createdAt BETWEEN ?1 AND ?2 AND o.user.id = ?3")
    List<OrderEntity> findByOrderTimeBetweenAndUserName(Timestamp start, Timestamp end, Long userId);

    @Query("SELECT o FROM OrderEntity o WHERE o.createdAt > ?1 AND o.user.id = ?2")
    List<OrderEntity> findByTimeAfterAndUserName(Timestamp start, Long userId);

    @Query("SELECT o FROM OrderEntity o WHERE o.createdAt < ?1 AND o.user.id = ?2")
    List<OrderEntity> findByTimeBeforeAndUserName(Timestamp end, Long userId);
}
