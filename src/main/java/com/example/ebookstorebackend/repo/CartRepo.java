package com.example.ebookstorebackend.repo;

import com.example.ebookstorebackend.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CartRepo extends JpaRepository<CartEntity, Long> {
    @Query("SELECT c FROM CartEntity c WHERE c.user.id = :userId")
    CartEntity findByUserId(Long userId);

    @Modifying
    @Transactional
    void removeCartItemById(Long cartItemId);
}
