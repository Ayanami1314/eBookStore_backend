package com.example.ebookstorebackend.repo;

import com.example.ebookstorebackend.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepo extends JpaRepository<CartEntity, Long> {
    @Query("SELECT c FROM CartEntity c WHERE c.userPublic.id = :userId")
    CartEntity findByUserId(Long userId);

    void removeCartItemById(Long cartItemId);
}
