package com.example.ebookstorebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "Books") // HINT: @Entity 是一个 JPA 注释，用于使该对象准备好存储在基于 JPA 的数据存储中
public class BookEntity {
    // HINT: IDENTITY而不是AUTO，因为AUTO在MySQL中会导致主键重复
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 指示它是主键并由 JPA 提供程序自动填充
    private String title;
    private String description;
    private String author;
    private int price; // 分为单位
    private String cover; // 图像资源的url
    private int sales;
    private String isbn;

    // 不允许book更新时更新原有的订单，订单的价格始终应该是“当时的”书籍价格
//    // HINT: 一个book对应多个orderItem，级联删除，更新，刷新，懒加载
//    @JsonIgnore
//    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<OrderItemEntity> orderItems;
    // book更新时应该更新购物车内的book信息，包括价格等
    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItemEntity> cartItems;

}
