package com.example.ebookstorebackend.user;

public class UserNotFoundException extends RuntimeException {
    UserNotFoundException(String username) {
        super("Could not find user " + username);
    }
}
