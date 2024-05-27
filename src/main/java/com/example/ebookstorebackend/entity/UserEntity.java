package com.example.ebookstorebackend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "UserPublics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"cart"})
@EqualsAndHashCode(exclude = "cart")
public class UserEntity {
    // TODO
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // HINT：单向，不能通过user获取到任何Auth相关的信息
    public enum Role {
        user, admin
    }

    @Enumerated(EnumType.STRING)
    private Role role = Role.user;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private CartEntity cart;
    @Enumerated(EnumType.STRING)
    private status Status = status.normal;

    public enum status {
        banned, normal
    }

    private String email;
    private String headImg;
    private String phone;
    @JsonProperty("nickname")
    private String username;
    private String address;
    private String firstName;
    private String lastName;
    private String city;
    private String state; // 州
    @Column(precision = 10, scale = 2)
    private BigDecimal balance;

    public boolean isAdministrator() {
        return Status == status.normal && role == Role.admin;
    }


    public boolean isUser() {
        return Status == status.normal;
    }

    public boolean isBanned() {
        return Status == status.banned;
    }
}
