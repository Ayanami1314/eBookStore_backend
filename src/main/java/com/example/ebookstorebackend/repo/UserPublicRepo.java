package com.example.ebookstorebackend.repo;

import com.example.ebookstorebackend.entity.UserPublicEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserPublicRepo extends ListCrudRepository<UserPublicEntity, Long> {
    Optional<UserPublicEntity> findByUsername(String username);

    // 查找名称含有keyword关键字的用户
    List<UserPublicEntity> findByUsernameContaining(String keyword);
}
