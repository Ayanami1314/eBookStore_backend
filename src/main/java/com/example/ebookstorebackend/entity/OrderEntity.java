package com.example.ebookstorebackend.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Orders")
@ToString(exclude = {"user", "orderItems"})
@EqualsAndHashCode(exclude = {"user", "orderItems"})
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // order不应该影响user
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    // order应该影响存在order内的items
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
    @JsonProperty("items")
    List<OrderItemEntity> orderItems;

    private String receiver;

    private String address;
    private String tel;
    @JsonSerialize(using = com.example.ebookstorebackend.utils.TimestampSerializer.class)
    @CreationTimestamp // Automatically set the time when the object is created
    private Timestamp createdAt;

    public enum Status {
        unpaid, paid, delivered, received
    }

    @Enumerated(EnumType.STRING)
    private Status status;


    public void addOrderItem(OrderItemEntity orderItem) {
        if (orderItems == null)
            orderItems = new java.util.ArrayList<>();
        orderItem.setOrder(this);
        orderItems.add(orderItem);
    }


    public int getTotalCost() {
        // TODO: opt: do this in sql
        return orderItems.stream().mapToInt(OrderItemEntity::getCost).sum();
    }
}
