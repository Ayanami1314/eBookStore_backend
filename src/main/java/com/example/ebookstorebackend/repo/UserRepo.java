package com.example.ebookstorebackend.repo;

import com.example.ebookstorebackend.entity.UserEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends ListCrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    // 查找名称含有keyword关键字的用户
    List<UserEntity> findByUsernameContaining(String keyword);
    
}
