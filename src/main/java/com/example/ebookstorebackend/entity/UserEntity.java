package com.example.ebookstorebackend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import static com.example.ebookstorebackend.utils.FileAndImage.*;


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
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private CartEntity cart;
    @Enumerated(EnumType.STRING)
    private status Status = status.normal;

    public enum status {
        banned, normal
    }

    private String notes;// 个性签名
    private String email;
    //    private String headImg; 存文件,不存数据库
    private String phone;
    @JsonProperty("nickname")
    private String username;
    private String address;
    private String firstName;
    private String lastName;
    private String city;
    private String state; // 州
    private int balance = 0;

    public UserEntity(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public boolean isAdministrator() {
        return Status == status.normal && role == Role.admin;
    }


    public boolean isUser() {
        return Status == status.normal;
    }

    public boolean isBanned() {
        return Status == status.banned;
    }

    public String getUserImagePath() {
        return "/images/users/" + id + ".jpg";
    }

    public String getUserImage() {
        String rpath = getUserImagePath();
        try {
            return readImage(rpath);
        } catch (Exception e) {
            System.out.println("Failed to read image: " + e.getMessage());
            return null;
        }
    }

    // save base64 image to file
    public String setUserImage(String image) {
        String rpath = getUserImagePath();
        try {
            saveImage(image, rpath);
            return rpath;
        } catch (Exception e) {
            System.out.println("Failed to save image: " + e.getMessage());
            return null;
        }
    }

    // save multipart image to file
    public String setUserImage(MultipartFile imagefile) {
        String rpath = getUserImagePath();
        try {
            saveFile(imagefile, rpath);
            return rpath;
        } catch (Exception e) {
            System.out.println("Failed to save image: " + e.getMessage());
            return null;
        }
    }
}
