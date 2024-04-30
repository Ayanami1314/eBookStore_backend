package com.example.ebookstorebackend.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;
    @GetMapping("/books")
    public List<BookEntity> getBooks(){
      return bookService.getBooks();
    }
    @GetMapping("/book/{id}")
    public BookEntity getBook(@PathVariable Long id){
        return bookService.getBook(id);
    }

    @PutMapping("/book/{id}")
    public void replaceBook(@RequestBody BookEntity newBookEntity, @PathVariable Long id){
           bookService.replaceBook(newBookEntity, id);
    }
    @PostMapping("/books")
    public void addBook(@RequestBody BookEntity newBookEntity){
        bookService.addBook(newBookEntity);
    }
    @DeleteMapping("/book/{id}")
    public void removeBook(@PathVariable Long id){
        bookService.removeBook(id);
    }

    @GetMapping("/books/sorted")
    public Page<BookEntity> sortedBooks(@RequestParam String sortBy, @RequestParam String direction, @RequestParam int pageNo, @RequestParam int size){
        return bookService.sortedBooks(sortBy, direction, pageNo, size);
    }
}
