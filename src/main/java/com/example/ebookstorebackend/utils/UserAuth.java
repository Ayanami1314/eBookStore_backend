package com.example.ebookstorebackend.utils;

import com.example.ebookstorebackend.entity.UserPublicEntity;
import jakarta.servlet.http.HttpSession;

public class UserAuth {
    public static boolean hasLogin(HttpSession session) {
        UserPublicEntity user = (UserPublicEntity) session.getAttribute("user");
        return user != null;
    }
}
