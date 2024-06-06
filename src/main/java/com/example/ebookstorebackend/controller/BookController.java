package com.example.ebookstorebackend.controller;

import com.example.ebookstorebackend.dto.BookDTO;
import com.example.ebookstorebackend.entity.BookEntity;
import com.example.ebookstorebackend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
public class BookController {
    @Autowired
    private BookService bookService;


    @GetMapping("/api/books")
    public BookDTO.BooksResponse getBooks(@RequestParam String keyword, @RequestParam int pageIndex, @RequestParam int pageSize) {
        var params = new BookDTO.BookSearchParam(keyword, pageIndex, pageSize);
        var res = new BookDTO.BooksResponse();
        var page = bookService.getBooks(params);
        res.books = page.getContent();
        res.total = (int) page.getTotalElements();
        return res;
    }

    @GetMapping("/api/book/{id}")
    public BookEntity getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @PutMapping("/api/book/{id}")
    public void replaceBook(@RequestBody BookEntity newBookEntity, @PathVariable Long id) {
        bookService.replaceBook(newBookEntity, id);
    }

    @PostMapping("/api/books")
    public void addBook(@RequestBody BookEntity newBookEntity) {
        bookService.addBook(newBookEntity);
    }

    @DeleteMapping("/api/book/{id}")
    public void removeBook(@PathVariable Long id) {
        bookService.removeBook(id);
    }

    @GetMapping("/api/books/sorted")
    public Page<BookEntity> sortedBooks(@RequestParam String sortBy, @RequestParam String direction, @RequestParam int pageNo, @RequestParam int size) {
        return bookService.sortedBooks(sortBy, direction, pageNo, size);
    }
}
