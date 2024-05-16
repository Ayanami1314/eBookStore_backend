package com.example.ebookstorebackend.order;

import com.example.ebookstorebackend.TimeKeyQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

public class OrderDTO {
    @Data
    static public class OrderPost {
        String receiver;
        String address;
        String tel;
        List<Integer> itemIds; // cartItemIds
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    static public class OrderQuery extends TimeKeyQuery {
        boolean all;
    }
}
