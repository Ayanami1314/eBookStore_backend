package com.example.ebookstorebackend.controller;


import com.example.ebookstorebackend.dto.AnalysisDTO;
import com.example.ebookstorebackend.dto.CommonResponse;
import com.example.ebookstorebackend.dto.UserDTO;
import com.example.ebookstorebackend.entity.BookEntity;
import com.example.ebookstorebackend.entity.OrderEntity;
import com.example.ebookstorebackend.entity.OrderItemEntity;
import com.example.ebookstorebackend.entity.UserEntity;
import com.example.ebookstorebackend.service.OrderService;
import com.example.ebookstorebackend.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.ebookstorebackend.utils.Time.isValidRange;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @PostMapping("/api/login")
    public CommonResponse<UserEntity> login(@RequestBody UserDTO.LoginRequest loginRequest, HttpSession session) {
        var username = loginRequest.username;
        var password = loginRequest.password;
        CommonResponse<UserEntity> response = new CommonResponse<>();
        try {
            if (userService.isVerified(username, password)) {
                var user = userService.getUser(username);
                if (user.getStatus() == UserEntity.status.banned) {
                    response.ok = false;
                    response.message = "用户已被封禁，如有疑问请联系管理员";
                    response.data = null;
                    return response;
                }
                response.ok = true;
                response.message = "Login successful";
                response.data = userService.getUser(username);
                session.setAttribute("userId", response.data.getId());
                System.out.println("User " + username + ",id:" + response.data.getId() + " logged in");
            } else {
                response.ok = false;
                response.message = "Login failed";
                response.data = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    @GetMapping("/api/user/me")
    public UserDTO.UserResInfo me(HttpSession session) {
        UserEntity currentUser = userService.getCurUser(session);
        if (currentUser == null) {
            return null;
        }
        return new UserDTO.UserResInfo(currentUser);
    }

    @PutMapping("/api/user/me/password")
    public CommonResponse<Object> changePassword(@RequestBody UserDTO.PasswordChangeRequest request, HttpSession session) {
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
        return userService.changePassword(username, request.oldPassword, request.newPassword);
    }

    @PutMapping("/api/logout")
    public CommonResponse<Object> logout(HttpSession session) {
        var response = new CommonResponse<>();
        response.ok = true;
        response.message = "Logout successful";
        response.data = new Object();
        session.removeAttribute("userId");
        return response;
    }

    // 注册普通用户
    @PostMapping("/api/register")
    public CommonResponse<Object> register(@RequestBody UserDTO.RegisterRequest request) {
        boolean success = false;
        try {
            success = userService.addUser(request, "user");
        } catch (Exception e) {
            CommonResponse<Object> response = new CommonResponse<>();
            response.ok = false;
            response.message = "Register failed: " + e.getMessage();
            response.data = new Object();
            return response;
        }
        var response = new CommonResponse<>();
        response.ok = success;
        response.message = success ? "Register successful" : "Register failed";
        response.data = new Object();
        return response;
    }

    @PutMapping("/api/user/me/info")
    public CommonResponse<Object> updateUserInfo(@RequestBody UserDTO.UserInformation userInformation, HttpSession session) {
        UserEntity user = userService.getCurUser(session);
        if (userInformation.name != null && !userInformation.name.equals(user.getUsername())) {
            boolean exist = userService.isUserExist(userInformation.name);
            if (exist) {
                CommonResponse<Object> response = new CommonResponse<>();
                response.ok = false;
                response.message = "Username already exists";
                response.data = new Object();
                return response;
            }
        }
        if (userInformation.avatar != null) {
            String rpath = user.setUserImage(userInformation.avatar);
            if (rpath == null) {
                CommonResponse<Object> response = new CommonResponse<>();
                response.ok = false;
                response.message = "Update user information failed";
                response.data = new Object();
                return response;
            }
            session.setAttribute("avatar", rpath);
        }

        user.setNotes(userInformation.notes);
        user.setEmail(userInformation.email);
        user.setUsername(userInformation.name);
        userService.updateUser(user, user.getUsername());
        CommonResponse<Object> response = new CommonResponse<>();
        response.ok = true;
        response.message = "Update user information successful";
        response.data = new Object();
        return response;
    }

    @PostMapping("/api/user/me/avatar")
    public CommonResponse<Object> updateAvatar(@RequestBody MultipartFile avatar, HttpSession session) {
        UserEntity user = userService.getCurUser(session);
        String rpath = user.setUserImage(avatar);
        CommonResponse<Object> response = new CommonResponse<>();
        if (rpath != null) {
            session.setAttribute("avatar", rpath);
            response.ok = true;
            response.message = "Update user avatar successful";
        } else {
            response.ok = false;
            response.message = "Update user avatar failed";
        }
        response.data = new Object();
        return response;
    }

    @GetMapping("/api/user/me/analysis/books")
    public List<AnalysisDTO.BookAnalysis> getBookAnalysis(@RequestParam(required = false) String start, @RequestParam(required = false) String end, @RequestParam String keyword, HttpSession session) {
        if (!isValidRange(start, end))
            return new ArrayList<>();
        List<OrderEntity> orders = orderService.searchMyOrdersByTimeRange(start, end, keyword, session);
        // analysis for each book in different orders
        Stream<OrderItemEntity> allItemsStream = orders.stream().flatMap(order -> order.getOrderItems().stream());

        Map<BookEntity, AnalysisDTO.SaleAndPrice> bookStats = allItemsStream.collect(Collectors.groupingBy(OrderItemEntity::getBook,
                Collectors.collectingAndThen(Collectors.toList(), items -> new AnalysisDTO.SaleAndPrice(
                        items.stream().mapToInt(OrderItemEntity::getQuantity).sum(),
                        items.stream().mapToInt(OrderItemEntity::getCost).sum()
                ))));
        return bookStats.entrySet().stream().map(entry -> new AnalysisDTO.BookAnalysis(entry.getKey(), entry.getValue().getSale(), entry.getValue().getPrice())).toList();
    }
}
