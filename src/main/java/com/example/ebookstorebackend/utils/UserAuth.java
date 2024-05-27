package com.example.ebookstorebackend.utils;

import com.example.ebookstorebackend.entity.UserEntity;
import jakarta.servlet.http.HttpSession;

public class UserAuth {
    public static boolean hasLogin(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        return user != null;
    }
}
