package com.example.ebookstorebackend.dto;

public class UserDTO {
    public static class UserResInfo {
        public String nickname;
        public int balance;
        public Long id;
    }

    public static class LoginRequest {
        public String username;
        public String password;
    }

}
