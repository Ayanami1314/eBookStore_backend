package com.example.ebookstorebackend.utils;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time {
    static public String timeToString(Timestamp time) {
        if (time == null) {
            return "lack of time";
        }
        LocalDateTime localDateTime = time.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }
}
