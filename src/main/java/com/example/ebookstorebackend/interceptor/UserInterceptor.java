package com.example.ebookstorebackend.interceptor;

import com.example.ebookstorebackend.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        var user = (UserEntity) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("/api/login");
            return false;
        }
        if (user.isBanned()) {
            response.sendRedirect("/api/error/banned");
            return false;
        }
        return true;
    }
}
