package com.example.ebookstorebackend.dao;

import com.example.ebookstorebackend.dto.BookDTO;
import com.example.ebookstorebackend.entity.BookEntity;
import com.example.ebookstorebackend.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private BookRepo mysqldb;

    @Override
    public BookEntity findBook(Long id) {
        BookEntity book = mysqldb.findById(id).orElse(null);
        if (book == null) {
            System.out.println("book is null:" + id);
        }
        return book;
    }


    @Override
    public Page<BookEntity> findAllBooks(BookDTO.BookSearchParam params) {
        if (params.keyword == null || params.keyword.isEmpty()) {
            System.out.println("keyword is null");
            return mysqldb.findAll(PageRequest.of(params.getPageIndex(), params.getPageSize()));
        }
        return mysqldb.findByTitleContaining(params.keyword, PageRequest.of(params.getPageIndex(), params.getPageSize()));
    }

    @Override
    public BookEntity replaceBook(BookEntity newBook, Long id) {
        mysqldb.findById(id).map(bookEntity -> {
            bookEntity = newBook;
            return mysqldb.save(bookEntity);
        }).orElseGet(() -> {
            newBook.setId(id);
            return mysqldb.save(newBook);
        });
        return newBook;
    }

    @Override
    public BookEntity addBook(BookEntity newBook) {
        return mysqldb.save(newBook);
    }

    @Override
    public void removeBook(Long id) {
        mysqldb.deleteById(id);
    }

    @Override
    public Page<BookEntity> sortedBooks(String sortBy, String direction, int pageNo, int size) {
        Sort bookSort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(pageNo, size, bookSort);
        return mysqldb.findAll(pageable);
    }

}
