package com.example.ebookstorebackend.admin;

import com.example.ebookstorebackend.CommonResponse;
import com.example.ebookstorebackend.book.BookEntity;
import com.example.ebookstorebackend.book.BookServiceImpl;
import com.example.ebookstorebackend.cart.CartService;
import com.example.ebookstorebackend.order.OrderEntity;
import com.example.ebookstorebackend.order.OrderServiceImpl;
import com.example.ebookstorebackend.orderitem.OrderItemEntity;
import com.example.ebookstorebackend.orderitem.OrderItemServiceImpl;
import com.example.ebookstorebackend.user.UserPublicEntity;
import com.example.ebookstorebackend.user.UserServiceImpl;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.ebookstorebackend.utils.Time.isValidRange;

@RestController
public class AdminController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private BookServiceImpl bookServiceImpl;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderItemServiceImpl orderItemServiceImpl;
    @Autowired
    private OrderServiceImpl orderServiceImpl;


    @DeleteMapping("/api/admin/book/{id}")
    public CommonResponse<Object> removeBook(@PathVariable Long id) {
        bookServiceImpl.removeBook(id);
        var res = new CommonResponse<>();
        res.ok = true;
        res.message = "Book removed";
        res.data = new Object();
        return res;
    }

    @GetMapping("/api/admin/users")
    public List<UserPublicEntity> getAllUsers() {
        return userServiceImpl.getAllUsers();
    }

    static class BookAnalysis {
        public BookEntity book;
        @JsonProperty("total_sales")
        public int totalSales;
        @JsonProperty("total_price")
        public double totalPrice;
    }

    @GetMapping("/api/admin/analysis/books")
    public List<BookAnalysis> getBookAnalysis(@RequestParam String start, @RequestParam String end) {
        // TODO: implement this
        if (!isValidRange(start, end))
            return new ArrayList<>();
        List<OrderEntity> orders = orderServiceImpl.getOrdersByTimeRange(start, end);
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
        bookServiceImpl.addBook(newBook);
        var res = new CommonResponse<>();
        res.ok = true;
        res.message = "Book added";
        res.data = new Object();
        return res;
    }

    @PutMapping("/api/admin/book/{id}")
    public CommonResponse<Object> replaceBook(@PathVariable Long id, @RequestBody BookEntity newBook) {
        bookServiceImpl.replaceBook(newBook, id);
        var res = new CommonResponse<>();
        res.ok = true;
        res.message = "Book replaced";
        res.data = new Object();
        return res;
    }
}
