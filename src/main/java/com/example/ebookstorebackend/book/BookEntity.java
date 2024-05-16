package com.example.ebookstorebackend.book;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    void setAll(BookEntity bookEntity) {
        this.title = bookEntity.title;
        this.description = bookEntity.description;
        this.author = bookEntity.author;
        this.price = bookEntity.price;
        this.cover = bookEntity.cover;
        this.sales = bookEntity.sales;
        this.isbn = bookEntity.isbn;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public boolean equals(BookEntity book) {
        return this.id.equals(book.id);
    }
}
