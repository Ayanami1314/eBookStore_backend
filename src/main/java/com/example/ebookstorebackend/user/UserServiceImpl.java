package com.example.ebookstorebackend.user;

import com.example.ebookstorebackend.CommonResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDaoImpl userDaoImpl;

    @Override
    public boolean isUserExist(String username) {
        return userDaoImpl.isUserExist(username);
    }

    @Override
    public boolean isVerified(String username, String password) {
        return userDaoImpl.isVerified(username, password);
    }

    @Override
    public UserPublicEntity refreshUser(HttpSession session) {
        UserPublicEntity user = (UserPublicEntity) session.getAttribute("user");
        if (user == null) {
            System.out.println("Please Login again.");
            return null;
        }
        UserPublicEntity newUser = userDaoImpl.getUser(user.getUsername());
        session.setAttribute("user", newUser);
        return newUser;
    }

    @Override
    public UserPublicEntity getCurUser(HttpSession session) {
        return refreshUser(session);
    }

    @Override
    public boolean isAdmin(String username) {
        return userDaoImpl.isAdmin(username);
    }

    @Override
    public boolean isUser(String username) {
        return userDaoImpl.isUser(username);
    }

    @Override
    public void addUser(String username, String password, String role) {
        if (role.equals("admin")) {
            userDaoImpl.addUser(username, password, UserPrivacyEntity.Role.admin);
        } else if (role.equals("user")) {
            userDaoImpl.addUser(username, password, UserPrivacyEntity.Role.user);
        }
    }

    @Override
    public void removeUser(String username) {
        userDaoImpl.removeUser(username);
    }

    @Override
    public void updateUser(UserPublicEntity newUser, String username) {
        userDaoImpl.updateUser(newUser, username);
    }

    @Override
    public void setRole(String username, String role) {
        userDaoImpl.setRole(username, role);
    }

    @Override
    public CommonResponse<Object> changePassword(String username, String password) {
        try {
            userDaoImpl.changePassword(username, password);
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
        return userDaoImpl.getUser(username);
    }


    @Override
    public List<UserPublicEntity> getAllUsers() {
        return userDaoImpl.getUsers();
    }
}
