package com.example.ebookstorebackend.controller;


import com.example.ebookstorebackend.dto.CommonResponse;
import com.example.ebookstorebackend.dto.UserDTO;
import com.example.ebookstorebackend.entity.UserEntity;
import com.example.ebookstorebackend.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;


    @PostMapping("/api/login")
    public CommonResponse<UserEntity> login(@RequestBody UserDTO.LoginRequest loginRequest, HttpSession session) {
        var username = loginRequest.username;
        var password = loginRequest.password;
        CommonResponse<UserEntity> response = new CommonResponse<>();
        try {
            if (userService.isVerified(username, password)) {
                response.ok = true;
                response.message = "Login successful";
                response.data = userService.getUser(username);
            } else {
                response.ok = false;
                response.message = "Login failed";
                response.data = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        var userPublic = userService.getUser(username);
        session.setAttribute("user", userPublic);
        return response;
    }


    @GetMapping("/api/user/me")
    public UserDTO.UserResInfo me(HttpSession session) {
        UserEntity currentUser = (UserEntity) session.getAttribute("user");
        if (currentUser == null) {
            return null;
        }
        UserDTO.UserResInfo userResInfo = new UserDTO.UserResInfo();
        userResInfo.nickname = currentUser.getUsername();
        userResInfo.balance = currentUser.getBalance().intValue();
        userResInfo.id = currentUser.getId();
        return userResInfo;
    }

    @PutMapping("/api/user/me/password")
    public CommonResponse<Object> changePassword(@RequestBody String oldpassword, @RequestBody String password, HttpSession session) {
        String username = userService.getCurUser(session).getUsername();
        var response = new CommonResponse<>();
        if (username == null) {
            String msg = "You are not logged in";
            System.out.println(msg);
            response.ok = false;
            response.message = msg;
            response.data = new Object();
            return response;
        }
        return userService.changePassword(username, oldpassword, password);
    }

    @PutMapping("/api/logout")
    public CommonResponse<Object> logout(HttpSession session) {
        session.removeAttribute("user");
        var response = new CommonResponse<>();
        response.ok = true;
        response.message = "Logout successful";
        response.data = new Object();
        return response;
    }
}
