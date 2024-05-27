package com.example.ebookstorebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "UserPrivacys")
@ToString(exclude = "userEntity")
public class UserAuthEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userpublic_id", referencedColumnName = "id")
    @JsonIgnore
    private UserEntity userEntity;

    @Column(unique = true)
    private String username;
    private String password;

    // HINT: 只有CRUD操作，验证操作通过repo进行，不进行Entity通信，不保存Entity实体
}
