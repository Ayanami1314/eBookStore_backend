package com.example.ebookstorebackend.cart;

import com.example.ebookstorebackend.CommonResponse;
import com.example.ebookstorebackend.book.BookEntity;
import com.example.ebookstorebackend.book.BookServiceImpl;
import com.example.ebookstorebackend.orderitem.OrderItemEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private BookServiceImpl bookServiceImpl;

    @GetMapping("/api/cart")
    public List<OrderItemEntity> getUserCart(HttpSession session) {
        return cartService.getCartItems(session);
    }

    @PutMapping("/api/cart")
    public CommonResponse<Object> addCartItem(@RequestParam Long bookId, HttpSession session) {
        OrderItemEntity newItem = new OrderItemEntity();
        BookEntity book = bookServiceImpl.getBook(bookId);
        newItem.setBook(book);
        newItem.setQuantity(1);
        boolean success = cartService.addCartItem(newItem, session);
        var response = new CommonResponse<>();
        response.data = new Object();
        response.ok = success;
        response.message = success ? "Item added to cart" : "Failed to add item to cart";
        return response;
    }


    @PutMapping("/api/cart/{id}")
    public CommonResponse<Object> updateItemQuantity(@PathVariable Long id, @RequestParam(name = "number") int quantity, HttpSession session) {
        System.out.println("updateItemQuantity: " + id + " " + quantity);
        if (quantity <= 0) {
            return new CommonResponse<>("Quantity must be greater than 0", new Object(), false);
        }
        boolean success = cartService.updateCartItem(id, quantity, session);
        var response = new CommonResponse<>();
        response.data = new Object();
        response.ok = success;
        response.message = "Item quantity updated" + (success ? "" : " (failed)");
        return response;
    }

    @DeleteMapping("/api/cart/{id}")
    public CommonResponse<Object> removeCartItem(@PathVariable Long id, HttpSession session) {
        boolean success = cartService.removeCartItem(id, session, true);
        var response = new CommonResponse<>();
        response.data = new Object();
        response.ok = success;
        response.message = success ? "Item removed from cart" : "Failed to remove item from cart";
        return response;
    }

}
