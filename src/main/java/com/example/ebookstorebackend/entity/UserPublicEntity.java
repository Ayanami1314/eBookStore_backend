package com.example.ebookstorebackend.entity;


import com.example.ebookstorebackend.utils.Hash;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity
@Table(name = "UserPublics")
@Data
@NoArgsConstructor
public class UserPublicEntity {
    // TODO
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userprivacy_id", referencedColumnName = "id")
    @JsonIgnore
    private UserPrivacyEntity userprivacy;

    @OneToOne(mappedBy = "userPublic", cascade = CascadeType.ALL)
    @JsonIgnore
    private CartEntity cart;
    @Enumerated(EnumType.STRING)
    private Status status = Status.normal;

    public enum Status {
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
        if (userprivacy == null) {
            return false;
        }
        return userprivacy.isAdmin();
    }

    public UserPublicEntity(UserPrivacyEntity userPrivacyEntity) {
        this.userprivacy = userPrivacyEntity;
        this.username = userPrivacyEntity.getUsername();
        this.email = "";
        this.headImg = "default.jpg";
        this.phone = "";
        this.address = "";
        this.firstName = "";
        this.lastName = "";
        this.city = "";
        this.state = "";
        this.balance = new BigDecimal(0);
    }

    public boolean isUser() {
        if (userprivacy == null) {
            return false;
        }
        return userprivacy.isUser();
    }

    public void setAll(UserPublicEntity userPublicEntity) {
        this.email = userPublicEntity.email;
        this.headImg = userPublicEntity.headImg;
        this.phone = userPublicEntity.phone;
        this.username = userPublicEntity.username;
        this.address = userPublicEntity.address;
        this.firstName = userPublicEntity.firstName;
        this.lastName = userPublicEntity.lastName;
        this.city = userPublicEntity.city;
        this.state = userPublicEntity.state;
        this.balance = userPublicEntity.balance;
    }

    @Override
    public String toString() {
        return "UserPublicEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", headImg='" + headImg + '\'' +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public int hashCode() {
        return Hash.IDHashCode(id, "UserPublic" + username);
    }
}
