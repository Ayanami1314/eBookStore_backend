package com.example.ebookstorebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OrderItems")
@ToString(exclude = {"order"})
@EqualsAndHashCode(exclude = "order")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 只有更新book操作会影响到OrderItem，orderItem不会反向影响book，保持级联影响单向, 所以没有MERGE
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private BookEntity book;

    @Column(name = "number")
    @JsonProperty("number")
    private int quantity;

    @Column(name = "book_price")
    private int book_price;

    // 同理，orderItem(的删除)不应该反向影响order，保持级联影响单向
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonIgnore
    private OrderEntity order; // HINT: add jsonignore to avoid recursive call


    public OrderItemEntity(BookEntity book, int quantity) {
        this.book = book;
        this.quantity = quantity;
        this.book_price = book.getPrice();
    }

    public OrderItemEntity(BookEntity book, int quantity, OrderEntity order) {
        this.book = book;
        this.quantity = quantity;
        this.order = order;
    }

    public int getCost() {
//        if (book == null)
//            return 0;
//        return book.getPrice() * quantity;
        return book_price * quantity;
    }
}
