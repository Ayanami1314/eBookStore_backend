package com.example.ebookstorebackend.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface BookRepo extends JpaRepository<BookEntity, Long>, PagingAndSortingRepository<BookEntity, Long>, JpaSpecificationExecutor<BookEntity> {
    Page<BookEntity> findByTitleContaining(String keyword, Pageable pageable);

    Page<BookEntity> findAll(Pageable pageable);

    List<BookEntity> findByTitleContaining(String keyword);
}
