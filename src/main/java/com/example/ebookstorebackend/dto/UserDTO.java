package com.example.ebookstorebackend.dto;

import com.example.ebookstorebackend.entity.UserEntity;

public class UserDTO {
    public static class UserResInfo {
        public String headImg;
        public String nickname;
        public String email;
        public Long id;
        public String notes;
        public String phone;
        public String address;
        public String firstName;
        public String lastName;
        public String city;
        public String state;
        public int balance;
        public String role;
        public String status;


        public UserResInfo(UserEntity userEntity) {
            this.headImg = userEntity.getUserImage();
            this.nickname = userEntity.getUsername();
            this.email = userEntity.getEmail();
            this.id = userEntity.getId();
            this.notes = userEntity.getNotes();
            this.phone = userEntity.getPhone();
            this.address = userEntity.getAddress();
            this.firstName = userEntity.getFirstName();
            this.lastName = userEntity.getLastName();
            this.city = userEntity.getCity();
            this.state = userEntity.getState();
            this.balance = userEntity.getBalance();
            this.role = userEntity.getRole().toString();
            this.status = userEntity.getStatus().toString();
        }
    }

    public static class LoginRequest {
        public String username;
        public String password;
    }

    static public class PasswordChangeRequest {
        public String oldPassword;
        public String newPassword;
    }

    static public class RegisterRequest {
        public String username;
        public String password;
        public String email;
    }

    static public class UserInformation {
        public String email;
        public String name;
        public String avatar; // 头像
        public String notes; // 个性签名
    }
}
