package com.example.ebookstorebackend.cart;


import com.example.ebookstorebackend.orderitem.OrderItemEntity;
import com.example.ebookstorebackend.user.UserPublicEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Carts")
@NoArgsConstructor
@Data
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private UserPublicEntity userPublic;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cart")
    private List<OrderItemEntity> orderItems;


    public CartEntity(UserPublicEntity userPublic) {
        this.userPublic = userPublic;
        this.orderItems = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
