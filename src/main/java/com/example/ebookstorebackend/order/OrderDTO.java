package com.example.ebookstorebackend.order;

import lombok.Data;

import java.util.List;

public class OrderDTO {
    @Data
    static public class OrderPost {
        public String receiver;
        public String address;
        public String tel;
        public List<Integer> itemIds; // cartItemIds
    }
}
