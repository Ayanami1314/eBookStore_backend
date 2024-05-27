package com.example.ebookstorebackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

    @GetMapping("/api/error/no-permission")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String noPermission() {
        return "You do not have permission to access this page.";
    }

    @GetMapping("/api/error/banned")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String banned() {
        return "The user is banned.";
    }
}