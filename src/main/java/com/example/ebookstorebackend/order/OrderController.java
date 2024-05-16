package com.example.ebookstorebackend.order;


import com.example.ebookstorebackend.CommonResponse;
import com.example.ebookstorebackend.cart.CartService;
import com.example.ebookstorebackend.orderitem.OrderItemService;
import com.example.ebookstorebackend.user.UserPublicEntity;
import com.example.ebookstorebackend.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/api/order")
    public CommonResponse<Object> createOrder(@RequestBody OrderDTO.OrderPost orderParam, HttpSession session) {
        System.out.println(orderParam);
        OrderEntity newOrder = new OrderEntity();
        newOrder.setReceiver(orderParam.receiver);
        newOrder.setAddress(orderParam.address);
        newOrder.setTel(orderParam.tel);

        var currentUser = userService.getCurUser(session);
        newOrder.setUserPublic(currentUser);
        var response = new CommonResponse<>();
        response.data = new Object();
        // validate
        response.ok = true;
        if (orderParam.itemIds.isEmpty()) {
            response.ok = false;
            response.message = "No item in order";
            return response;
        }
        for (int id : orderParam.itemIds) {
            Long idLong = (long) id;
            var orderItem = cartService.getCartItem(idLong, session);
            if (orderItem == null || orderItem.getCart() == null) {
                System.out.println("Cannot find that item in cart");
                response.message = "Bad itemId " + id;
                System.out.println(response.message);
                response.ok = false;
                return response;
            }
            if (orderItem.getQuantity() <= 0) {
                System.out.println("Quantity is less than 1");
                response.message = "Quantity is less than 1";
                System.out.println(response.message);
                response.ok = false;
                return response;
            }
        }

        System.out.println("validate ok");
        for (int id : orderParam.itemIds) {
            Long idLong = (long) id;
            var orderItem = cartService.getCartItem(idLong, session);
            cartService.removeCartItem(idLong, session, false);
            newOrder.addOrderItem(orderItem);
        }

        System.out.println(newOrder);
        orderService.createOrder(newOrder);

        System.out.println(newOrder);
        response.ok = true;
        response.message = "Order created";
        return response;
    }

    @GetMapping("/api/order")
    public List<OrderEntity> getOrders(@RequestParam(required = false) String start, @RequestParam(required = false) String end, @RequestParam String keyword, HttpSession session) {
        UserPublicEntity cur = userService.getCurUser(session);

        return orderService.searchMyOrdersByTimeRange(start, end, keyword, session);
    }
}
