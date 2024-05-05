package com.example.ebookstorebackend.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserPublicRepo extends CrudRepository<UserPublicEntity, Long> {
    Optional<UserPublicEntity> findByUsername(String username);
}
