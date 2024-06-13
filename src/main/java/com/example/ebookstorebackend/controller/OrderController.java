package com.example.ebookstorebackend.controller;


import com.example.ebookstorebackend.dto.CommonResponse;
import com.example.ebookstorebackend.dto.OrderDTO;
import com.example.ebookstorebackend.entity.BookEntity;
import com.example.ebookstorebackend.entity.CartItemEntity;
import com.example.ebookstorebackend.entity.OrderEntity;
import com.example.ebookstorebackend.entity.OrderItemEntity;
import com.example.ebookstorebackend.service.*;
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
    private CartItemService cartItemService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;


    @PostMapping("/api/order")
    public CommonResponse<Object> createOrder(@RequestBody OrderDTO.OrderPost orderParam, HttpSession session) {
        System.out.println(orderParam);
        OrderEntity newOrder = new OrderEntity();
        newOrder.setReceiver(orderParam.getReceiver());
        newOrder.setAddress(orderParam.getAddress());
        newOrder.setTel(orderParam.getTel());

        var currentUser = userService.getCurUser(session);
        newOrder.setUser(currentUser);
        var response = new CommonResponse<>();
        response.data = new Object();
        // validate
        response.ok = true;
        if (orderParam.getItemIds().isEmpty()) {
            response.ok = false;
            response.message = "No item in order";
            return response;
        }
        List<CartItemEntity> cartItems = orderParam.getItemIds().stream().map(id ->
                cartService.getCartItem((long) id, session)).toList();

        // validate
        for (var cartItem : cartItems) {
            if (cartItem == null || cartItem.getCart() == null) {
                System.out.println("Cannot find that item in cart");
                response.message = "Bad item " + cartItem.getId();
                System.out.println(response.message);
                response.ok = false;
                return response;
            }
            if (cartItem.getQuantity() <= 0) {
                System.out.println("Quantity is less than 1");
                response.message = "Quantity is less than 1";
                System.out.println(response.message);
                response.ok = false;
                return response;
            }
            if (cartItem.getBook().getStorage() < cartItem.getQuantity()) {
                System.out.println("Not enough stock");
                response.message = "库存不足";
                System.out.println(response.message);
                response.ok = false;
                return response;
            }
        }

        System.out.println("validate ok");
        for (int id : orderParam.getItemIds()) {
            Long idLong = (long) id;
            OrderItemEntity orderItem = cartItemService.convertToOrderItem(cartService.getCartItem(idLong, session));
            cartService.removeCartItem(idLong, session);
            newOrder.addOrderItem(orderItem);
        }

        cartItems.forEach(cartItem -> {
            BookEntity newBook = cartItem.getBook();
            newBook.setStorage(newBook.getStorage() - cartItem.getQuantity());
            newBook.setSales(newBook.getSales() + cartItem.getQuantity());
            bookService.replaceBook(newBook, newBook.getId());
        });
        System.out.println(newOrder);
        orderService.createOrder(newOrder);

        System.out.println(newOrder);
        response.ok = true;
        response.message = "Order created";
        return response;
    }

    @GetMapping("/api/order")
    public List<OrderEntity> getOrders(@RequestParam(required = false) String start, @RequestParam(required = false) String end, @RequestParam String keyword, HttpSession session) {
        return orderService.searchMyOrdersByTimeRange(start, end, keyword, session);
    }
}
