package com.example.ebookstorebackend.book;

import org.springframework.data.domain.Page;

public interface BookService {
    Page<BookEntity> getBooks(BookDTO.BookSearchParam params);

    BookEntity getBook(Long id);

    void addBook(BookEntity newBook);

    void replaceBook(BookEntity newBook, Long id);

    void removeBook(Long id);

    Page<BookEntity> sortedBooks(String sortBy, String direction, int pageNo, int size);
}
