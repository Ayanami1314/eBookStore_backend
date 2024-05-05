package com.example.ebookstorebackend.user;

import com.example.ebookstorebackend.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public boolean isUserExist(String username) {
        return userDao.isUserExist(username);
    }

    public boolean isVerified(String username, String password) {
        return userDao.isVerified(username, password);
    }

    public boolean isAdmin(String username) {
        return userDao.isAdmin(username);
    }

    public boolean isUser(String username) {
        return userDao.isUser(username);
    }

    public void addUser(String username, String password, String role) {
        if (role.equals("admin")) {
            userDao.addUser(username, password, UserPrivacyEntity.Role.admin);
        } else if (role.equals("user")) {
            userDao.addUser(username, password, UserPrivacyEntity.Role.user);
        }
    }

    public void removeUser(String username) {
        userDao.removeUser(username);
    }

    public void updateUser(UserPublicEntity newUser, String username) {
        userDao.updateUser(newUser, username);
    }

    public void setRole(String username, String role) {
        userDao.setRole(username, role);
    }

    public CommonResponse<Object> changePassword(String username, String password) {
        try {
            userDao.changePassword(username, password);
        } catch (Exception e) {
            CommonResponse<Object> response = new CommonResponse<>();
            response.ok = false;
            response.message = "Password change failed";
            return response;
        }
        CommonResponse<Object> response = new CommonResponse<>();
        response.ok = true;
        response.message = "Password changed";
        return response;
    }

    public UserPublicEntity getUser(String username) {
        return userDao.getUser(username);
    }


}
