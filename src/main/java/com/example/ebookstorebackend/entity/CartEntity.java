package com.example.ebookstorebackend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Carts")
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"user", "cartItems"})
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // cart不应该影响user
    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private UserEntity user;

    // cart应该影响存在cart内的items
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cart")
    private List<CartItemEntity> cartItems;


    public CartEntity(UserEntity user) {
        this.user = user;
        this.cartItems = new ArrayList<>();
    }

}
