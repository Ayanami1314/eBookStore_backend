package com.example.ebookstorebackend.serviceimpl;


import com.example.ebookstorebackend.dao.CartDao;
import com.example.ebookstorebackend.entity.CartEntity;
import com.example.ebookstorebackend.entity.CartItemEntity;
import com.example.ebookstorebackend.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDao cartDao;

    @Override
    public CartEntity getCart(HttpSession session) {
        return cartDao.getCart(session);
    }

    @Override
    public List<CartItemEntity> getCartItems(HttpSession session) {
        return cartDao.getCartItems(session);
    }

    @Override
    public CartEntity createCart(HttpSession session) {
        return cartDao.createCart(session);
    }

    @Override
    public void clearCart(HttpSession session) {
        CartEntity cart = getCart(session);
        if (cart == null) {
            System.out.println("Cannot clear cart: cart does not exist");
            return;
        }
        cart.getCartItems().clear();
        cartDao.save(cart);
    }

    @Override
    public CartItemEntity getCartItem(Long cartItemId, HttpSession session) {
        return cartDao.getCartItem(cartItemId, session);
    }

    @Override
    public boolean addCartItem(CartItemEntity cartItem, HttpSession session) {
        return cartDao.addCartItem(cartItem, session);
    }


    @Override
    public void save(CartEntity cart) {
        cartDao.save(cart);
    }

    @Override
    public boolean removeCartItem(Long cartItemId, HttpSession session) {
        return cartDao.removeCartItem(cartItemId, session);
    }

    @Override
    public boolean updateCartItem(Long cartItemId, int quantity, HttpSession session) {
        return cartDao.updateCartItem(cartItemId, quantity, session);
    }


}
