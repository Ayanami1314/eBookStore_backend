package com.example.ebookstorebackend.book.query;

import lombok.Data;

@Data
public class BookQuery {
    // TODO: implement correct backend query logic
    // HINT: should be the same as the fields in BookEntity
    private String title;
    private String author;
    private String isbn;
}
