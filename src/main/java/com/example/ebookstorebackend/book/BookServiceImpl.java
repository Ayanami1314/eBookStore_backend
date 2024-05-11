package com.example.ebookstorebackend.book;

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
    public void addBook(BookEntity newBook) {
        bookDao.addBook(newBook);
    }

    @Override
    public void replaceBook(BookEntity newBook, Long id) {
        bookDao.replaceBook(newBook, id);
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
