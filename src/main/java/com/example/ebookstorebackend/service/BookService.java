package com.example.ebookstorebackend.service;

import com.example.ebookstorebackend.dto.BookDTO;
import com.example.ebookstorebackend.entity.BookEntity;
import org.springframework.data.domain.Page;

public interface BookService {
    Page<BookEntity> getBooks(BookDTO.BookSearchParam params);

    BookEntity getBook(Long id);

    BookEntity addBook(BookEntity newBook);

    BookEntity replaceBook(BookEntity newBook, Long id);

    void removeBook(Long id);

    Page<BookEntity> sortedBooks(String sortBy, String direction, int pageNo, int size);
}
