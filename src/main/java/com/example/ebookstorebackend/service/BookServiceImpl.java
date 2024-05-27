package com.example.ebookstorebackend.service;

import com.example.ebookstorebackend.dao.BookDao;
import com.example.ebookstorebackend.dto.BookDTO;
import com.example.ebookstorebackend.entity.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    public Page<BookEntity> getBooks(BookDTO.BookSearchParam params) {
        return bookDao.findAllBooks(params);

    }

    @Override
    public BookEntity getBook(Long id) {
        return bookDao.findBook(id);
    }

    @Override
    public BookEntity addBook(BookEntity newBook) {
        return bookDao.addBook(newBook);
    }

    @Override
    public BookEntity replaceBook(BookEntity newBook, Long id) {
        return bookDao.replaceBook(newBook, id);
    }

    @Override
    public void removeBook(Long id) {
        bookDao.removeBook(id);
    }

    @Override
    public Page<BookEntity> sortedBooks(String sortBy, String direction, int pageNo, int size) {
        return bookDao.sortedBooks(sortBy, direction, pageNo, size);
    }

}
