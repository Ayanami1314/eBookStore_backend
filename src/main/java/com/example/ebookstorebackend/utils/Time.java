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

    static public boolean isValidRange(String start, String end) {
        Timestamp endTime = null;
        Timestamp startTime = null;
        try {
            startTime = start == null ? null : Timestamp.valueOf(start);
            endTime = end == null ? null : Timestamp.valueOf(end);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid time format");
            return false;
        }
        return true;
    }
}
