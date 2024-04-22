package org.example.ebookstorebackend.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {
    private final BookRepo bookRepo;
    public BookController(BookRepo bookRepo){
        this.bookRepo = bookRepo;
    }
    @GetMapping("/books")
    public List<Book> getBooks(){
      return bookRepo.findAll();
    }
    @GetMapping("/book/{id}")
    public Book getOneBook(@PathVariable Long id){
        return bookRepo.findById(id).orElseThrow(()->new BookNotFoundException(id));
    }

    // HINT： Jpa get return a Optional<T> and may be null
    // So we use orElseGet or orElseThrow to handle null case
    @PutMapping("/book/{id}")
    public void replaceBook(@RequestBody Book newBook, @PathVariable Long id){
        bookRepo.findById(id).map(book -> {
            book.setProps(newBook);
            return bookRepo.save(book);
        }).orElseGet(()->{
            newBook.setId(id);
            return bookRepo.save(newBook);
        });
    }
    @PostMapping("/books")
    public void addBook(@RequestBody Book newBook){
        bookRepo.save(newBook);
    }
    @DeleteMapping("/book/{id}")
    public void removeBook(@PathVariable Long id){
        bookRepo.deleteById(id);
    }
}
