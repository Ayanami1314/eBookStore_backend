package com.example.ebookstorebackend.dao;

import com.example.ebookstorebackend.dto.BookDTO;
import com.example.ebookstorebackend.entity.BookEntity;
import org.springframework.data.domain.Page;

public interface BookDao {
    BookEntity findBook(Long id);

    Page<BookEntity> findAllBooks(BookDTO.BookSearchParam params);

    BookEntity replaceBook(BookEntity newBook, Long id);

    BookEntity addBook(BookEntity newBook);

    void removeBook(Long id);

    Page<BookEntity> sortedBooks(String sortBy, String direction, int pageNo, int size);

    void reduceStock(Long id, int quantity);
}
