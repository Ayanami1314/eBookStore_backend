package com.example.ebookstorebackend.admin;

import com.example.ebookstorebackend.CommonResponse;
import com.example.ebookstorebackend.book.BookEntity;
import com.example.ebookstorebackend.book.BookService;
import com.example.ebookstorebackend.cart.CartService;
import com.example.ebookstorebackend.order.OrderEntity;
import com.example.ebookstorebackend.order.OrderService;
import com.example.ebookstorebackend.orderitem.OrderItemEntity;
import com.example.ebookstorebackend.orderitem.OrderItemService;
import com.example.ebookstorebackend.user.UserPublicEntity;
import com.example.ebookstorebackend.user.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpSession;
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
    private CartService cartService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;

    public boolean checkAdmin(HttpSession session) {
        var userPublic = userService.getCurUser(session);
        if (userPublic == null) {
            return false;
        }
        Long userId = userPublic.getId();
        return userService.isAdmin(userPublic.getUsername());
    }

    @DeleteMapping("/api/admin/book/{id}")
    public CommonResponse<Object> removeBook(@PathVariable Long id, HttpSession session) {
        if (!checkAdmin(session)) {
            var response = new CommonResponse<>();
            response.ok = false;
            response.message = "You are not an admin";
            response.data = new Object();
            return response;
        }
        bookService.removeBook(id);
        var res = new CommonResponse<>();
        res.ok = true;
        res.message = "Book removed";
        res.data = new Object();
        return res;
    }

    @GetMapping("/api/admin/users")
    public List<UserPublicEntity> getUsers(@RequestParam String keyword, HttpSession session) {
        if (!checkAdmin(session)) {
            return new ArrayList<>();
        }
        return userService.searchUsers(keyword);
    }

    @GetMapping("/api/admin/analysis/users")
    public List<AdminDTO.UserAnalysis> getUserAnalysis(@RequestParam String start, @RequestParam String end, @RequestParam(name = "keyword") String user_keyword, HttpSession session) {
        if (!checkAdmin(session)) {
            return new ArrayList<>();
        }
        List<UserPublicEntity> allUsers = userService.searchUsers(user_keyword);
        List<AdminDTO.UserAnalysis> response = new ArrayList<AdminDTO.UserAnalysis>();
        for (UserPublicEntity user : allUsers) {
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

    static class BookAnalysis {
        public BookEntity book;
        @JsonProperty("total_sales")
        public int totalSales;
        @JsonProperty("total_price")
        public double totalPrice;
    }

    @GetMapping("/api/admin/analysis/books")
    public List<BookAnalysis> getBookAnalysis(@RequestParam String start, @RequestParam String end, @RequestParam String keyword, HttpSession session) {
        if (!checkAdmin(session)) {
            return new ArrayList<>();
        }
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
    public CommonResponse<Object> addBook(@RequestBody BookEntity newBook, HttpSession session) {
        if (!checkAdmin(session)) {
            var response = new CommonResponse<>();
            response.ok = false;
            response.message = "You are not an admin";
            response.data = new Object();
            return response;
        }
        bookService.addBook(newBook);
        var res = new CommonResponse<>();
        res.ok = true;
        res.message = "Book added";
        res.data = new Object();
        return res;
    }

    @PutMapping("/api/admin/book/{id}")
    public CommonResponse<Object> replaceBook(@PathVariable Long id, @RequestBody BookEntity newBook, HttpSession session) {
        if (!checkAdmin(session)) {
            var response = new CommonResponse<>();
            response.ok = false;
            response.message = "You are not an admin";
            response.data = new Object();
            return response;
        }
        bookService.replaceBook(newBook, id);
        var res = new CommonResponse<>();
        res.ok = true;
        res.message = "Book replaced";
        res.data = new Object();
        return res;
    }

    @PutMapping("/api/admin/user/changeban/{id}")
    public CommonResponse<Object> changeBan(@PathVariable Long id, @RequestBody Boolean ban, HttpSession session) {
        if (!checkAdmin(session)) {
            var response = new CommonResponse<>();
            response.ok = false;
            response.message = "You are not an admin";
            response.data = new Object();
            return response;
        }
        var response = new CommonResponse<>();
        var user = userService.getUser(id);
        if (user == null) {
            response.ok = false;
            response.message = "User not found";
            response.data = new Object();
            return response;
        }
        if (userService.changeUserStatus(user.getUsername(), ban ? UserPublicEntity.Status.banned : UserPublicEntity.Status.normal)) {
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
    public List<OrderEntity> searchOrders(@RequestParam String start, @RequestParam String end, @RequestParam String keyword, HttpSession session) {
        if (!checkAdmin(session)) {
            return new ArrayList<>();
        }
        return orderService.searchOrdersByTimeRange(start, end, keyword);
    }

    @GetMapping
    public List<UserPublicEntity> getAllUsers(HttpSession session) {
        if (!checkAdmin(session)) {
            System.out.println("You are not an admin");
            return new ArrayList<>();
        }
        return userService.getAllUsers();
    }
}
