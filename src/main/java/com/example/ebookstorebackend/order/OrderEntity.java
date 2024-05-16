package com.example.ebookstorebackend.order;


import com.example.ebookstorebackend.orderitem.OrderItemEntity;
import com.example.ebookstorebackend.user.UserPublicEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

import static com.example.ebookstorebackend.utils.Time.timeToString;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // order不应该影响user
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserPublicEntity userPublic;

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

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", user=" + (userPublic == null ? "null" : userPublic.getUsername()) +
                ", orderItemIds=" + (orderItems == null ? "null" : orderItems.stream().map(OrderItemEntity::getId).toList().toString()) +
                ", receiver='" + receiver + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", createdAt=" + timeToString(createdAt) +
                ", status=" + status +
                '}';
    }

    public void addOrderItem(OrderItemEntity orderItem) {
        if (orderItems == null)
            orderItems = new java.util.ArrayList<>();
        orderItem.setOrder(this);
        orderItem.setCart(null);
        orderItems.add(orderItem);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public int getTotalCost() {
        // TODO: opt: do this in sql
        return orderItems.stream().mapToInt(OrderItemEntity::getCost).sum();
    }
}
