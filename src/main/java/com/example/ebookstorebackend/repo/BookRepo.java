package com.example.ebookstorebackend.repo;

import com.example.ebookstorebackend.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;

import java.util.List;


public interface BookRepo extends JpaRepository<BookEntity, Long>, PagingAndSortingRepository<BookEntity, Long>, JpaSpecificationExecutor<BookEntity> {
    Page<BookEntity> findByTitleContaining(String keyword, Pageable pageable);

    @NonNull
    Page<BookEntity> findAll(@NonNull Pageable pageable);

    List<BookEntity> findByTitleContaining(String keyword);
}
