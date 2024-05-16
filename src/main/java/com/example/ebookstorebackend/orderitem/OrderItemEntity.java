package com.example.ebookstorebackend.orderitem;

import com.example.ebookstorebackend.book.BookEntity;
import com.example.ebookstorebackend.cart.CartEntity;
import com.example.ebookstorebackend.order.OrderEntity;
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
@Table(name = "OrderItems")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private BookEntity book;

    @Column(name = "number")
    @JsonProperty("number")
    private int quantity;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonIgnore
    private OrderEntity order; // HINT: add jsonignore to avoid recursive call

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    @JsonIgnore
    private CartEntity cart;

    @Override
    public String toString() {
        return "OrderItemEntity{" +
                "id=" + id +
                ", book=" + book.getTitle() +
                ", quantity=" + quantity +
                '}';
    }

    public OrderItemEntity(BookEntity book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public OrderItemEntity(BookEntity book, int quantity, OrderEntity order) {
        this.book = book;
        this.quantity = quantity;
        this.order = order;
    }

    @Override
    public int hashCode() {
        return Hash.IDHashCode(id, "OrderItem" + book.getTitle());
    }

    public int getCost() {
        if (book == null)
            return 0;
        return book.getPrice() * quantity;
    }
}
