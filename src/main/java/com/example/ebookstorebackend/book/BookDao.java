package com.example.ebookstorebackend.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDao {
    @Autowired
    private BookRepo mysqldb;

    public BookEntity findBook(Long id){
        return mysqldb.findById(id).orElseThrow(()->new BookNotFoundException(id));
    }

    public List<BookEntity> findAllBooks(){
        return mysqldb.findAll();
    }
    public void replaceBook(BookEntity newBook, Long id){
        mysqldb.findById(id).map(bookEntity -> {
            bookEntity.setAll(newBook);
            return mysqldb.save(bookEntity);
        }).orElseGet(()->{
            newBook.setId(id);
            return mysqldb.save(newBook);
        });
    }
    public void addBook(BookEntity newBook){
        mysqldb.save(newBook);
    }
    public void removeBook(Long id){
        mysqldb.deleteById(id);
    }

   public Page<BookEntity> sortedBooks(String sortBy, String direction, int pageNo, int size){
        Sort bookSort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(pageNo, size, bookSort);
        return mysqldb.findAll(pageable);
   }



}
