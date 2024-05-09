package com.example.ebookstorebackend.user;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserPublicRepo extends ListCrudRepository<UserPublicEntity, Long> {
    Optional<UserPublicEntity> findByUsername(String username);
}
