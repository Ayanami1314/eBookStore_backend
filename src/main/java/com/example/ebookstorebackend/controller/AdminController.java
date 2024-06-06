package com.example.ebookstorebackend.controller;

import com.example.ebookstorebackend.dto.AnalysisDTO;
import com.example.ebookstorebackend.dto.CommonResponse;
import com.example.ebookstorebackend.entity.BookEntity;
import com.example.ebookstorebackend.entity.OrderEntity;
import com.example.ebookstorebackend.entity.OrderItemEntity;
import com.example.ebookstorebackend.entity.UserEntity;
import com.example.ebookstorebackend.service.BookService;
import com.example.ebookstorebackend.service.OrderService;
import com.example.ebookstorebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private int calculateUserTotalCost(UserEntity user, String start, String end) {
        return orderService.searchOnesOrdersByTimeRange(start, end, "", user.getId()).stream()
                .mapToInt(OrderEntity::getTotalCost)
                .sum();
    }

    @GetMapping("/api/admin/analysis/users")
    public List<AnalysisDTO.UserAnalysis> getUserAnalysis(@RequestParam(required = false) String start,
                                                          @RequestParam(required = false) String end,
                                                          @RequestParam(name = "keyword") String user_keyword) {
        System.out.println("start: " + start + ", end: " + end + ", keyword: " + user_keyword);
        List<UserEntity> allUsers = userService.searchUsers(user_keyword);
        return new ArrayList<>(allUsers.stream().map(user ->
                new AnalysisDTO.UserAnalysis(calculateUserTotalCost(user, start, end), user)
        ).sorted(Comparator.comparingInt(AnalysisDTO.UserAnalysis::getTotalcost).reversed()).toList()); // 降序排序
    }


    @GetMapping("/api/admin/analysis/books")
    public List<AnalysisDTO.BookAnalysis> getBookAnalysis(@RequestParam(required = false) String start, @RequestParam(required = false) String end, @RequestParam String keyword) {
        if (!isValidRange(start, end))
            return new ArrayList<>();
        List<OrderEntity> orders = orderService.searchOrdersByTimeRange(start, end, keyword);
        // analysis for each book in different orders
        Stream<OrderItemEntity> allItemsStream = orders.stream().flatMap(order -> order.getOrderItems().stream());

        Map<BookEntity, AnalysisDTO.SaleAndPrice> bookStats = allItemsStream.collect(Collectors.groupingBy(OrderItemEntity::getBook,
                Collectors.collectingAndThen(Collectors.toList(), items -> new AnalysisDTO.SaleAndPrice(
                        items.stream().mapToInt(OrderItemEntity::getQuantity).sum(),
                        items.stream().mapToInt(OrderItemEntity::getCost).sum()
                ))));
        return bookStats.entrySet().stream().map(entry -> new AnalysisDTO.BookAnalysis(entry.getKey(), entry.getValue().getSale(), entry.getValue().getPrice())).toList();
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
