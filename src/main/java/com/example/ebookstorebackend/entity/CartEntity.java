package com.example.ebookstorebackend.entity;


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

    // cart不应该影响user
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private UserPublicEntity userPublic;

    // cart应该影响存在cart内的items
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cart")
    private List<CartItemEntity> cartItems;


    public CartEntity(UserPublicEntity userPublic) {
        this.userPublic = userPublic;
        this.cartItems = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
