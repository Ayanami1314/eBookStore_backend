package com.example.ebookstorebackend.order;


import com.example.ebookstorebackend.orderitem.OrderItemEntity;
import com.example.ebookstorebackend.user.UserPublicEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserPublicEntity userPublic;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
    @JsonProperty("items")
    List<OrderItemEntity> orderItems;

    private String receiver;

    private String address;
    private String tel;
    @JsonSerialize(using = com.example.ebookstorebackend.utils.TimestampSerializer.class)
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
                ", orderItemIds=" + (orderItems == null ? "null" : orderItems.stream().map(OrderItemEntity::getId).toString()) +
                ", receiver='" + receiver + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", createdAt=" + timeToString(createdAt) +
                ", status=" + status +
                '}';
    }
}
