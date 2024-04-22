package org.example.ebookstorebackend.book;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity // HINT: @Entity 是一个 JPA 注释，用于使该对象准备好存储在基于 JPA 的数据存储中
public class Book {
    @Id @GeneratedValue
    Long id;  // 指示它是主键并由 JPA 提供程序自动填充
    String title;
    String description;
    String author;
    double price;
    String cover; // 图像资源的url
    int sales;
    String isbn;
    public Book(){
        this.author = "test";
        this.description = "test";
        this.price = 0;
        this.sales = 0;
        this.isbn = "test";
        this.cover = "test";
    }
    public Book(String title, String author, String description, double price, String cover, String isbn){
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.sales = 0;
        this.isbn = isbn;
        this.cover = cover;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }
    public void setProps(Book other){
        this.sales = other.sales;
        this.author = other.author;
        this.price = other.price;
        this.isbn = other.isbn;
        this.title = other.title;
        this.description = other.description;
    }

    @Override
    public String toString() {
        return "Book:\nid: " + id + "\n" +
                "title: " + title + "\n" +
                "author: " + author + "\n" +
                "description: " + description + "\n" +
                "price: " + price + "\n" +
                "sales: " + sales + "\n" +
                "isbn: " + isbn + "\n" +
                "cover: " + cover + "\n";
    }

    // getter and setters
}
