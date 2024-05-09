package com.example.ebookstorebackend.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepo extends JpaRepository<CartEntity, Long> {
    @Query("SELECT c FROM CartEntity c WHERE c.userPublic.id = :userId")
    CartEntity findByUserId(Long userId);
}
