package com.example.ebookstorebackend.service;

import com.example.ebookstorebackend.dao.UserDao;
import com.example.ebookstorebackend.dto.CommonResponse;
import com.example.ebookstorebackend.dto.UserDTO;
import com.example.ebookstorebackend.entity.UserAuthEntity;
import com.example.ebookstorebackend.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public boolean isUserExist(String username) {
        return userDao.isUserExist(username);
    }

    @Override
    public boolean isVerified(String username, String password) {
        return userDao.isVerified(username, password);
    }

    @Override
    public UserEntity refreshUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        System.out.println("cur_userId: " + userId);
        UserEntity user = userDao.getUser(userId);
        if (user == null) {
            System.out.println("Please Login again.");
            return null;
        }
        return user;
    }

    @Override
    public UserEntity getCurUser(HttpSession session) {
        return refreshUser(session);
    }

    @Override
    public boolean isAdmin(String username) {
        return userDao.isAdmin(username);
    }

    @Override
    public boolean isUser(String username) {
        return userDao.isUser(username);
    }

    @Override
    public boolean addUser(UserDTO.RegisterRequest register, String role) {
        var newUser = new UserEntity(register.username, register.email);
        UserAuthEntity newUserAuth = new UserAuthEntity(register.username, register.password, newUser);
        if (role.equals("admin")) {
            return userDao.addUser(newUserAuth);
        } else if (role.equals("user")) {
            return userDao.addUser(newUserAuth);
        }
        return false;
    }

    @Override
    public void removeUser(String username) {
        userDao.removeUser(username);
    }

    @Override
    public void updateUser(UserEntity newUser, String username) {
        userDao.updateUser(newUser, username);
    }

    @Override
    public void setRole(String username, String role) {
        userDao.setRole(username, role);
    }

    @Override
    public CommonResponse<Object> changePassword(String username, String oldpassword, String password) {
        try {
            userDao.changePassword(username, oldpassword, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("username:" + username);
            System.out.println("oldpassword:" + oldpassword);
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

    @Override
    public UserEntity getUser(String username) {
        return userDao.getUser(username);
    }

    @Override
    public UserEntity getUser(Long id) {
        return userDao.getUser(id);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userDao.getUsers();
    }

    @Override
    public List<UserEntity> searchUsers(String keyword) {
        return userDao.searchUsers(keyword);
    }

    @Override
    public boolean changeUserStatus(String username, UserEntity.status status) {

        return userDao.changeUserStatus(username, status);
    }
}
