package com.example.ebookstorebackend.order;


import com.example.ebookstorebackend.CommonResponse;
import com.example.ebookstorebackend.user.UserPublicEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    static class OrderPost {
        public String receiver;
        public String address;
        public String tel;
        public List<Integer> itemIds; // cartItemIds
    }

    @PostMapping("/api/order")
    public CommonResponse<Object> createOrder(@RequestBody OrderPost orderParam, HttpSession session) {
        OrderEntity newOrder = new OrderEntity();
        newOrder.setReceiver(orderParam.receiver);
        newOrder.setAddress(orderParam.address);
        newOrder.setTel(orderParam.tel);
        var currentUser = (UserPublicEntity) session.getAttribute("user");
        newOrder.setUserPublic(currentUser);

        // TODO how to deal with cartItemIds?
        System.out.println(newOrder);
        var response = new CommonResponse<>();
        response.data = new Object();
        response.ok = true;
        response.message = "Order created";
//        System.out.println(response);
        return response;
    }

    @GetMapping("/api/order")
    public List<OrderEntity> getOrders() {
        return orderService.getAllOrders();
    }
}
