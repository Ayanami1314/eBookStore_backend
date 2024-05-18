package com.example.ebookstorebackend.repo;

import com.example.ebookstorebackend.entity.UserPrivacyEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserPrivacyRepo extends CrudRepository<UserPrivacyEntity, Long> {
    UserPrivacyEntity findByUsernameAndPassword(String username, String password);

    // HINT: 使用内置查询避免将整个隐私实体通过网络传输
    @Query("SELECT u.role FROM UserPrivacyEntity u WHERE u.username = :username")
    UserPrivacyEntity.Role findRoleByUsername(@Param("username") String username);

    Optional<UserPrivacyEntity> findByUsername(String username);
}