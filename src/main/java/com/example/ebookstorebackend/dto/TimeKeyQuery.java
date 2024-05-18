package com.example.ebookstorebackend.dto;


import lombok.Data;

@Data
public class TimeKeyQuery {
    String start;
    String end;
    String keyword;
}
