package com.example.ebookstorebackend.entity;

import com.example.ebookstorebackend.utils.Hash;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "UserPrivacys")
public class UserPrivacyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "userprivacy", cascade = CascadeType.ALL)
    @JsonIgnore
    private UserPublicEntity userPublicEntity;

    @Column(unique = true)
    private String username;
    private String password;
    // 使用枚举类型来表示用户角色。同时，这个注解会将枚举类型的值映射到字符串再保存到数据库中
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        user, admin
    }

    public boolean isAdmin() {
        return role == Role.admin;
    }

    public boolean isUser() {
        return role == Role.user;
    }

    void setAll(UserPrivacyEntity userPrivacyEntity) {
        this.username = userPrivacyEntity.username;
        this.password = userPrivacyEntity.password;
        this.role = userPrivacyEntity.role;
    }

    boolean isVerified(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    // HINT: should override the toString method to escape the recursive call
    @Override
    public String toString() {
        // HINT: exclude the user public entity to avoid recursive call
        return "UserPrivacyEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public int hashCode() {
        return Hash.IDHashCode(id, "UserPrivacy" + username);
    }
}
