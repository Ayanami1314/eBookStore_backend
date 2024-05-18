package com.example.ebookstorebackend.controller;

import com.example.ebookstorebackend.dto.BookDTO;
import com.example.ebookstorebackend.entity.BookEntity;
import com.example.ebookstorebackend.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
public class BookController {
    @Autowired
    private BookServiceImpl bookServiceImpl;


    @GetMapping("/api/books")
    public BookDTO.BooksResponse getBooks(@RequestParam String keyword, @RequestParam int pageIndex, @RequestParam int pageSize) {
        // TODO: 是否需要修正前端API? 测试兼容性
        var params = new BookDTO.BookSearchParam(keyword, pageIndex, pageSize);
        var res = new BookDTO.BooksResponse();
        var page = bookServiceImpl.getBooks(params);
        res.books = page.getContent();
        res.total = (int) page.getTotalElements();
        return res;
    }

    @GetMapping("/api/book/{id}")
    public BookEntity getBook(@PathVariable Long id) {
        return bookServiceImpl.getBook(id);
    }

    @PutMapping("/api/book/{id}")
    public void replaceBook(@RequestBody BookEntity newBookEntity, @PathVariable Long id) {
        bookServiceImpl.replaceBook(newBookEntity, id);
    }

    @PostMapping("/api/books")
    public void addBook(@RequestBody BookEntity newBookEntity) {
        bookServiceImpl.addBook(newBookEntity);
    }

    @DeleteMapping("/api/book/{id}")
    public void removeBook(@PathVariable Long id) {
        bookServiceImpl.removeBook(id);
    }

    @GetMapping("/api/books/sorted")
    public Page<BookEntity> sortedBooks(@RequestParam String sortBy, @RequestParam String direction, @RequestParam int pageNo, @RequestParam int size) {
        return bookServiceImpl.sortedBooks(sortBy, direction, pageNo, size);
    }
}
