package com.example.ebookstorebackend.user;

import java.util.List;

public interface UserDao {
    boolean isUserExist(String username);

    boolean isVerified(String username, String password);

    boolean isAdmin(String username);

    boolean isUser(String username);

    void addUser(String username, String password, UserPrivacyEntity.Role role);

    UserPublicEntity getUser(String username);

    UserPublicEntity getUser(Long id);

    void removeUser(String username);

    void updateUser(UserPublicEntity newUser, String username);

    void setRole(String username, String role);

    void changePassword(String username, String password);

    List<UserPublicEntity> getUsers();

    List<UserPublicEntity> searchUsers(String keyword);

    boolean changeUserStatus(String username, UserPublicEntity.Status status);
}
