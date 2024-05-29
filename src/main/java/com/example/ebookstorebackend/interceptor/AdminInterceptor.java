package com.example.ebookstorebackend.interceptor;

import com.example.ebookstorebackend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        var user = userService.getCurUser(session);
        if (user != null && user.isAdministrator()) {
            return true;
        }
        response.sendRedirect("/api/error/no-permission");
        return false;
    }
}
