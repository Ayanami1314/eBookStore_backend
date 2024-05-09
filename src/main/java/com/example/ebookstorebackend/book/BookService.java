package com.example.ebookstorebackend.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private BookDao bookDao;

    public Page<BookEntity> getBooks(BookDTO.BookSearchParam params) {
        return bookDao.findAllBooks(params);

    }

    public BookEntity getBook(Long id) {
        return bookDao.findBook(id);
    }

    public void addBook(BookEntity newBook) {
        bookDao.addBook(newBook);
    }

    public void replaceBook(BookEntity newBook, Long id) {
        bookDao.replaceBook(newBook, id);
    }

    public void removeBook(Long id) {
        bookDao.removeBook(id);
    }

    public Page<BookEntity> sortedBooks(String sortBy, String direction, int pageNo, int size) {
        return bookDao.sortedBooks(sortBy, direction, pageNo, size);
    }

}
