package com.example.ebookstorebackend.order;


import com.example.ebookstorebackend.CommonResponse;
import com.example.ebookstorebackend.cart.CartService;
import com.example.ebookstorebackend.orderitem.OrderItemService;
import com.example.ebookstorebackend.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    static public class OrderPost {
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
        var currentUser = userService.getCurUser(session);
        newOrder.setUserPublic(currentUser);

        for (int id : orderParam.itemIds) {
            Long idLong = Long.valueOf(id);
            var orderItem = cartService.getCartItem(idLong, session);
            if (orderItem == null) {
                System.out.println("Cannot find that item in cart");
                continue;
            }
            cartService.removeCartItem(idLong, session);
            orderItem.setOrder(newOrder);
            newOrder.addOrderItem(orderItem);
        }
        orderService.createOrder(newOrder);

        System.out.println(newOrder);
        var response = new CommonResponse<>();
        response.data = new Object();
        response.ok = true;
        response.message = "Order created";
        return response;
    }

    @GetMapping("/api/order")
    public List<OrderEntity> getOrders() {
        return orderService.getAllOrders();
    }
}
