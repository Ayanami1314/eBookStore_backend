package com.example.ebookstorebackend.book;

import java.util.List;

public class BookDTO {
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
