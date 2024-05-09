package com.example.ebookstorebackend.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    public static class BooksResponse {
        public List<BookEntity> books;
        public int total;
    }

    @GetMapping("/api/books")
    public BooksResponse getBooks() {
        var res = new BooksResponse();
        res.books = bookService.getBooks();
        res.total = res.books.size();
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
