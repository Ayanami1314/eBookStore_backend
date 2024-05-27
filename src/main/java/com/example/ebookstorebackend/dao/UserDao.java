package com.example.ebookstorebackend.dao;

import com.example.ebookstorebackend.entity.UserEntity;

import java.util.List;

public interface UserDao {
    boolean isUserExist(String username);

    boolean isVerified(String username, String password);

    boolean isAdmin(String username);

    boolean isUser(String username);

    void addUser(String username, String password, UserEntity.Role role);

    UserEntity getUser(String username);

    UserEntity getUser(Long id);

    void removeUser(String username);

    void updateUser(UserEntity newUser, String username);

    void setRole(String username, String role);

    void changePassword(String username, String oldpassword, String password) throws Exception;

    List<UserEntity> getUsers();

    List<UserEntity> searchUsers(String keyword);

    boolean changeUserStatus(String username, UserEntity.status status);
}
