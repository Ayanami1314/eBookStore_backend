package com.example.ebookstorebackend.entity;

import com.example.ebookstorebackend.utils.Hash;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CartItems")
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 只有更新book操作会影响到cartItem，cartItem不会反向影响book，保持级联影响单向
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private BookEntity book;

    @Column(name = "number")
    @JsonProperty("number")
    private int quantity;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    @JsonIgnore
    private CartEntity cart; // HINT: add jsonignore to avoid recursive call

    @Override
    public String toString() {
        return "cartItemEntity{" +
                "id=" + id +
                ", book=" + book.getTitle() +
                ", quantity=" + quantity +
                '}';
    }

    public CartItemEntity(BookEntity book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public CartItemEntity(BookEntity book, int quantity, CartEntity cart) {
        this.book = book;
        this.quantity = quantity;
        this.cart = cart;
    }

    @Override
    public int hashCode() {
        return Hash.IDHashCode(id, "cartItem" + book.getTitle());
    }

    public int getCost() {
        if (book == null)
            return 0;
        return book.getPrice() * quantity;
    }
}
