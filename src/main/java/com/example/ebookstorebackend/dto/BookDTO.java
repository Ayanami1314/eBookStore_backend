package com.example.ebookstorebackend.dto;

import com.example.ebookstorebackend.entity.BookEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

public class BookDTO {
    @Data
    @AllArgsConstructor
    public static class BookSearchParam {
        public String keyword;
        int pageIndex;
        int pageSize;
    }

    public static class BooksResponse {
        public List<BookEntity> books;
        public int total;
    }
}
