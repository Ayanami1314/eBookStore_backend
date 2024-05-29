package com.example.ebookstorebackend.repo;

import com.example.ebookstorebackend.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartItemRepo extends JpaRepository<CartItemEntity, Long> {
}
