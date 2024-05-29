package com.example.ebookstorebackend.service;

import com.example.ebookstorebackend.dto.CommonResponse;
import com.example.ebookstorebackend.dto.UserDTO;
import com.example.ebookstorebackend.entity.UserEntity;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface UserService {
    boolean isUserExist(String username);

    boolean isVerified(String username, String password);

    UserEntity refreshUser(HttpSession session);

    UserEntity getCurUser(HttpSession session);

    boolean isAdmin(String username);

    boolean isUser(String username);

    boolean addUser(UserDTO.RegisterRequest register, String role);

    void removeUser(String username);

    void updateUser(UserEntity newUser, String username);

    void setRole(String username, String role);

    CommonResponse<Object> changePassword(String username, String oldpassword, String password);

    UserEntity getUser(String username);

    UserEntity getUser(Long id);

    List<UserEntity> getAllUsers();

    List<UserEntity> searchUsers(String keyword);

    boolean changeUserStatus(String username, UserEntity.status status);
}
