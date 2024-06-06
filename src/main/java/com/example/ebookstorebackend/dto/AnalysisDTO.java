package com.example.ebookstorebackend.dto;

import com.example.ebookstorebackend.entity.BookEntity;
import com.example.ebookstorebackend.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

public class AnalysisDTO {
    @Data
    @AllArgsConstructor
    public static class UserAnalysis {
        private int totalcost;
        private UserEntity user;
    }

    @Data
    @AllArgsConstructor
    public static class BookAnalysis {
        private BookEntity book;
        @JsonProperty("total_sales")
        private int totalSales;
        @JsonProperty("total_price")
        private int totalPrice;
    }

    @Data
    @AllArgsConstructor
    public static class SaleAndPrice {
        private final int sale;
        private final int price;
    }

}
