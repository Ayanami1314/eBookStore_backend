package com.example.ebookstorebackend.service;

import com.example.ebookstorebackend.dto.CommonResponse;
import com.example.ebookstorebackend.entity.UserPublicEntity;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface UserService {
    boolean isUserExist(String username);

    boolean isVerified(String username, String password);

    UserPublicEntity refreshUser(HttpSession session);

    UserPublicEntity getCurUser(HttpSession session);

    boolean isAdmin(String username);

    boolean isUser(String username);

    void addUser(String username, String password, String role);

    void removeUser(String username);

    void updateUser(UserPublicEntity newUser, String username);

    void setRole(String username, String role);

    CommonResponse<Object> changePassword(String username, String password);

    UserPublicEntity getUser(String username);

    UserPublicEntity getUser(Long id);

    List<UserPublicEntity> getAllUsers();

    List<UserPublicEntity> searchUsers(String keyword);

    boolean changeUserStatus(String username, UserPublicEntity.Status status);
}
