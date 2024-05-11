package com.example.ebookstorebackend.user;


import com.example.ebookstorebackend.CommonResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
public class UserController {
    @Autowired
    UserServiceImpl userServiceImpl;


    @PostMapping("/api/login")
    public CommonResponse<UserPublicEntity> login(@RequestBody UserDTO.LoginRequest loginRequest, HttpSession session) {
        var username = loginRequest.username;
        var password = loginRequest.password;
        CommonResponse<UserPublicEntity> response = new CommonResponse<>();
        try {
            if (userServiceImpl.isVerified(username, password)) {
                response.ok = true;
                response.message = "Login successful";
                response.data = new UserPublicEntity();
            } else {
                response.ok = false;
                response.message = "Login failed";
                response.data = new UserPublicEntity();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        var userPublic = userServiceImpl.getUser(username);
        session.setAttribute("user", userPublic);
        return response;
    }


    @GetMapping("/api/user/me")
    public UserDTO.UserResInfo me(HttpSession session) {
        UserPublicEntity currentUser = (UserPublicEntity) session.getAttribute("user");
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
    public CommonResponse<Object> changePassword(@RequestBody String password, HttpSession session) {
        String username = userServiceImpl.getCurUser(session).getUsername();
        var response = new CommonResponse<>();
        if (username == null) {
            String msg = "You are not logged in";
            System.out.println(msg);
            response.ok = false;
            response.message = msg;
            response.data = new Object();
            return response;
        }
        return userServiceImpl.changePassword(username, password);
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
    // TODO: admin part
}
