package com.example.ebookstorebackend.user;

import com.example.ebookstorebackend.CommonResponse;
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
    public UserPublicEntity refreshUser(HttpSession session) {
        UserPublicEntity user = (UserPublicEntity) session.getAttribute("user");
        if (user == null) {
            System.out.println("Please Login again.");
            return null;
        }
        UserPublicEntity newUser = userDao.getUser(user.getUsername());
        session.setAttribute("user", newUser);
        return newUser;
    }

    @Override
    public UserPublicEntity getCurUser(HttpSession session) {
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
    public void addUser(String username, String password, String role) {
        if (role.equals("admin")) {
            userDao.addUser(username, password, UserPrivacyEntity.Role.admin);
        } else if (role.equals("user")) {
            userDao.addUser(username, password, UserPrivacyEntity.Role.user);
        }
    }

    @Override
    public void removeUser(String username) {
        userDao.removeUser(username);
    }

    @Override
    public void updateUser(UserPublicEntity newUser, String username) {
        userDao.updateUser(newUser, username);
    }

    @Override
    public void setRole(String username, String role) {
        userDao.setRole(username, role);
    }

    @Override
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

    @Override
    public UserPublicEntity getUser(String username) {
        return userDao.getUser(username);
    }

    @Override
    public UserPublicEntity getUser(Long id) {
        return userDao.getUser(id);
    }

    @Override
    public List<UserPublicEntity> getAllUsers() {
        return userDao.getUsers();
    }

    @Override
    public List<UserPublicEntity> searchUsers(String keyword) {
        return userDao.searchUsers(keyword);
    }

    @Override
    public boolean changeUserStatus(String username, UserPublicEntity.Status status) {

        return userDao.changeUserStatus(username, status);
    }
}
