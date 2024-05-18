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
@Table(name = "OrderItems")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 只有更新book操作会影响到OrderItem，orderItem不会反向影响book，保持级联影响单向
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private BookEntity book;

    @Column(name = "number")
    @JsonProperty("number")
    private int quantity;

    // 同理，orderItem(的删除)不应该反向影响order，保持级联影响单向
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
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
