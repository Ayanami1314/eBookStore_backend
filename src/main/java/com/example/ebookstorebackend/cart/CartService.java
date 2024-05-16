package com.example.ebookstorebackend.cart;


import com.example.ebookstorebackend.orderitem.OrderItemEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartDao cartDao;

    public CartEntity getCart(HttpSession session) {
        return cartDao.getCart(session);
    }

    public List<OrderItemEntity> getCartItems(HttpSession session) {
        return cartDao.getCartItems(session);
    }

    public CartEntity createCart(HttpSession session) {
        return cartDao.createCart(session);
    }

    public void clearCart(HttpSession session) {
        CartEntity cart = getCart(session);
        if (cart == null) {
            System.out.println("Cannot clear cart: cart does not exist");
            return;
        }
        cart.getOrderItems().clear();
        cartDao.save(cart);
    }

    public OrderItemEntity getCartItem(Long cartItemId, HttpSession session) {
        return cartDao.getCartItem(cartItemId, session);
    }

    public boolean addCartItem(OrderItemEntity orderItem, HttpSession session) {
        return cartDao.addCartItem(orderItem, session);
    }


    public void save(CartEntity cart) {
        cartDao.save(cart);
    }

    public boolean removeCartItem(Long cartItemId, HttpSession session, boolean delete) {
        return cartDao.removeCartItem(cartItemId, session, delete);
    }

    public boolean updateCartItem(Long cartItemId, int quantity, HttpSession session) {
        return cartDao.updateCartItem(cartItemId, quantity, session);
    }


}
