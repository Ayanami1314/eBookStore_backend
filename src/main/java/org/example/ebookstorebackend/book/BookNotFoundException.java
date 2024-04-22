package org.example.ebookstorebackend.book;

public class BookNotFoundException extends RuntimeException{
    BookNotFoundException(Long id) {
        super("Could not find book " + id);
    }
}
