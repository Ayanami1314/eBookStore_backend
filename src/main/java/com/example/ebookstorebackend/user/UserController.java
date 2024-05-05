package com.example.ebookstorebackend.user;


import com.example.ebookstorebackend.CommonResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    public static class LoginRequest {
        public String username;
        public String password;
    }

    @PostMapping("/api/login")
    public CommonResponse<UserPublicEntity> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        var username = loginRequest.username;
        var password = loginRequest.password;
        CommonResponse<UserPublicEntity> response = new CommonResponse<>();
        try {
            if (userService.isVerified(username, password)) {
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
        var userPublic = userService.getUser(username);
        session.setAttribute("user", userPublic);
        return response;
    }


    public static class UserResInfo {
        public String nickname;
        public int balance;
        public Long id;
    }

    @GetMapping("/api/user/me")
    public UserResInfo me(HttpSession session) {
        UserPublicEntity currentUser = (UserPublicEntity) session.getAttribute("user");
        if (currentUser == null) {
            return null;
        }
        UserResInfo userResInfo = new UserResInfo();
        userResInfo.nickname = currentUser.getUsername();
        userResInfo.balance = currentUser.getBalance().intValue();
        userResInfo.id = currentUser.getId();
        return userResInfo;
    }

    @PutMapping("/api/user/me/password")
    public CommonResponse<Object> changePassword(@RequestBody String newPassword) {
        // TODO: 从token中获取用户名
        String me = "admin";
        return userService.changePassword(me, newPassword);
    }

    // TODO: admin part
}
