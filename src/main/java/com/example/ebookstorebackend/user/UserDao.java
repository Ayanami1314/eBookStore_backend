package com.example.ebookstorebackend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {
    // TODO: 对于Privacy实体，进行哈希加密处理
    @Autowired
    private UserPublicRepo userPublicRepo;
    @Autowired
    private UserPrivacyRepo userPrivacyRepo;

    public boolean isUserExist(String username) {
        return userPublicRepo.findByUsername(username).orElse(null) != null;
    }

    public boolean isVerified(String username, String password) {
        return userPrivacyRepo.findByUsernameAndPassword(username, password) != null;
    }

    public boolean isAdmin(String username) {
        var user = userPublicRepo.findByUsername(username);
        return user.isPresent() && user.get().isAdministrator();
    }

    public boolean isUser(String username) {
        var user = userPublicRepo.findByUsername(username);
        return user.isPresent() && user.get().isUser();
    }

    public void addUser(String username, String password, UserPrivacyEntity.Role role) {
        var user = new UserPrivacyEntity();
        // TODO: 强hash加密,非明文密码
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        userPrivacyRepo.save(user);
        var userPublic = new UserPublicEntity();
        // 设置mapping
        userPublic.setUserprivacy(user);
        userPublic.setUsername(username);
        user.setUserPublicEntity(userPublic);

        userPublicRepo.save(userPublic); // HINT: 由于cascade, 会自动保存userPrivacy
    }

    public UserPublicEntity getUser(String username) {
        return userPublicRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public void removeUser(String username) {
        userPublicRepo.delete(userPublicRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username)));
    }

    public void updateUser(UserPublicEntity newUser, String username) {
        userPublicRepo.findByUsername(username).map(userPublicEntity -> {
            userPublicEntity.setAll(newUser);
            return userPublicRepo.save(userPublicEntity);
        }).orElseThrow(() -> new UserNotFoundException(username));
    }

    public void setRole(String username, String role) {
        UserPrivacyEntity userPrivacy = userPrivacyRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        userPrivacy.setRole(UserPrivacyEntity.Role.valueOf(role));
        userPrivacyRepo.save(userPrivacy);
    }

    public void changePassword(String username, String password) {
        UserPrivacyEntity userPrivacy = userPrivacyRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        userPrivacy.setPassword(password);
        userPrivacyRepo.save(userPrivacy);
    }

    public List<UserPublicEntity> getUsers() {
        return userPublicRepo.findAll();
    }
}
