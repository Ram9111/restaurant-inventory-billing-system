package com.restaurant.inventorysystem.exception;
/**
 * @author Ram
 * @date 2025-11-05
 * @purpose Custom exception to handle invalid or bad input data scenarios
 *           within the application.
 *
 * <p>This exception is thrown when the user or client sends invalid data
 * that does not meet validation criteria or business rules.
 * Example use cases include missing required fields, invalid formats,
 * or data constraints violations.</p>
 */
public class InvalidDataException extends RuntimeException {

    /**
     * Constructor to create InvalidDataException with a specific error message.
     *
     * @param message A detailed description of the invalid data error.
     */
    public InvalidDataException(String message) {
        super(message);
    }
}
