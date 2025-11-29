package com.restaurant.inventorysystem.exception;

/**
 * Custom exception for database-related errors.
 * This helps separate DB issues from other types of application errors.
 */
public class DatabaseException extends RuntimeException {

    private static final long serialVersionUID = 1L; // for serialization consistency

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
