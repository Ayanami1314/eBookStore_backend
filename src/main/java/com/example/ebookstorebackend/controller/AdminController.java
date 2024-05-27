package com.example.ebookstorebackend.controller;

import com.example.ebookstorebackend.dto.AdminDTO;
import com.example.ebookstorebackend.dto.CommonResponse;
import com.example.ebookstorebackend.entity.BookEntity;
import com.example.ebookstorebackend.entity.OrderEntity;
import com.example.ebookstorebackend.entity.OrderItemEntity;
import com.example.ebookstorebackend.entity.UserEntity;
import com.example.ebookstorebackend.service.BookService;
import com.example.ebookstorebackend.service.OrderService;
import com.example.ebookstorebackend.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.ebookstorebackend.utils.Time.isValidRange;

@RestController
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private OrderService orderService;

    // HINT: 权限验证和拦截在interceptor包中, 对/api/admin/**路径进行拦截

    @DeleteMapping("/api/admin/book/{id}")
    public CommonResponse<Object> removeBook(@PathVariable Long id) {
        bookService.removeBook(id);
        var res = new CommonResponse<>();
        res.ok = true;
        res.message = "Book removed";
        res.data = new Object();
        return res;
    }

    @GetMapping("/api/admin/users")
    public List<UserEntity> getUsers(@RequestParam(required = false) String keyword) {
        if (keyword == null) {
            return userService.getAllUsers();
        }
        return userService.searchUsers(keyword);
    }

    @GetMapping("/api/admin/analysis/users")
    public List<AdminDTO.UserAnalysis> getUserAnalysis(@RequestParam(required = false) String start, @RequestParam(required = false) String end, @RequestParam(name = "keyword") String user_keyword) {
        List<UserEntity> allUsers = userService.searchUsers(user_keyword);
        List<AdminDTO.UserAnalysis> response = new ArrayList<>();
        for (UserEntity user : allUsers) {
            List<Integer> orderCosts = orderService.searchOnesOrdersByTimeRange(start, end, "", user.getId()).stream()
                    .map(OrderEntity::getTotalCost)
                    .toList();
            int totalCost = orderCosts.stream().reduce(0, Integer::sum);
            response.add(new AdminDTO.UserAnalysis(totalCost, user));
        }
        // HINT: 降序排序
        response.sort((a, b) -> b.getTotalcost() - a.getTotalcost());
        return response;
    }

    public static class BookAnalysis {
        public BookEntity book;
        @JsonProperty("total_sales")
        public int totalSales;
        @JsonProperty("total_price")
        public double totalPrice;
    }

    @GetMapping("/api/admin/analysis/books")
    public List<BookAnalysis> getBookAnalysis(@RequestParam(required = false) String start, @RequestParam(required = false) String end, @RequestParam String keyword) {
        if (!isValidRange(start, end))
            return new ArrayList<>();
        List<OrderEntity> orders = orderService.searchOrdersByTimeRange(start, end, keyword);
        List<BookAnalysis> bookAnalysis = new ArrayList<>();
        for (OrderEntity order : orders) {
            for (OrderItemEntity orderItem : order.getOrderItems()) {
                BookEntity book = orderItem.getBook();
                int quantity = orderItem.getQuantity();
                double price = book.getPrice();
                boolean found = false;
                for (BookAnalysis analysis : bookAnalysis) {
                    if (analysis.book.getId().equals(book.getId())) {
                        analysis.totalSales += quantity;
                        analysis.totalPrice += quantity * price;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    BookAnalysis analysis = new BookAnalysis();
                    analysis.book = book;
                    analysis.totalSales = quantity;
                    analysis.totalPrice = quantity * price;
                    bookAnalysis.add(analysis);
                }
            }
        }
        return bookAnalysis;
    }

    @PostMapping("/api/admin/book")
    public CommonResponse<Object> addBook(@RequestBody BookEntity newBook) {
        BookEntity data = bookService.addBook(newBook);
        var res = new CommonResponse<>();
        res.ok = true;
        res.message = "Book added";
        res.data = data;
        return res;
    }

    @PutMapping("/api/admin/book/{id}")
    public CommonResponse<Object> replaceBook(@PathVariable Long id, @RequestBody BookEntity newBook) {
        var data = bookService.replaceBook(newBook, id);
        var res = new CommonResponse<>();
        res.ok = true;
        res.message = "Book replaced";
        res.data = data;
        return res;
    }

    @PutMapping("/api/admin/user/changeban/{id}")
    public CommonResponse<Object> changeBan(@PathVariable Long id, @RequestParam boolean ban) {
        var response = new CommonResponse<>();
        var user = userService.getUser(id);
        if (user == null) {
            response.ok = false;
            response.message = "User not found";
            response.data = new Object();
            return response;
        }
        if (userService.changeUserStatus(user.getUsername(), ban ? UserEntity.status.banned : UserEntity.status.normal)) {
            response.ok = true;
            response.message = "Change ban successful";
        } else {
            response.ok = false;
            response.message = "Change ban failed";
        }
        response.data = new Object();
        return response;
    }

    @GetMapping("/api/admin/orders")
    public List<OrderEntity> searchOrders(@RequestParam(required = false) String start, @RequestParam(required = false) String end, @RequestParam String keyword) {
        return orderService.searchOrdersByTimeRange(start, end, keyword);
    }

    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }
}
