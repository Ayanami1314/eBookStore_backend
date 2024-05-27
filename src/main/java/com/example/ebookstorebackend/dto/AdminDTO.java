package com.example.ebookstorebackend.dto;

import com.example.ebookstorebackend.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

public class AdminDTO {
    @Data
    @AllArgsConstructor
    static public class UserAnalysis {
        private int totalcost;
        private UserEntity user;
    }
}
