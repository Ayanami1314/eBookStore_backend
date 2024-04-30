package com.example.ebookstorebackend.book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;



public interface BookRepo extends JpaRepository<BookEntity, Long>, PagingAndSortingRepository<BookEntity, Long>, JpaSpecificationExecutor<BookEntity> {
}
