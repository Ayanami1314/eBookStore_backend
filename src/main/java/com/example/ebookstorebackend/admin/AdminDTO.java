package com.example.ebookstorebackend.admin;

import com.example.ebookstorebackend.user.UserPublicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

public class AdminDTO {
    @Data
    @AllArgsConstructor
    static public class UserAnalysis {
        private int totalcost;
        private UserPublicEntity user;
    }
}
