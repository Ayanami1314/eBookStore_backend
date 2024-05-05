package com.example.ebookstorebackend.orderitem;

import com.example.ebookstorebackend.book.BookEntity;
import com.example.ebookstorebackend.order.OrderEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private int quantity;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonIgnore
    private OrderEntity order; // HINT: add jsonignore to avoid recursive call

    @Override
    public String toString() {
        return "OrderItemEntity{" +
                "id=" + id +
                ", book=" + book.getTitle() +
                ", quantity=" + quantity +
                '}';
    }
}
