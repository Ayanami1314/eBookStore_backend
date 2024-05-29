package com.example.ebookstorebackend.utils;

import jakarta.servlet.http.HttpSession;

public class UserAuth {
    public static boolean hasLogin(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        return userId != null;
    }
}
