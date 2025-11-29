package com.restaurant.inventorysystem.exception;

/**
 * @author Ram
 * @date 2025-11-05
 * @purpose Custom exception used to indicate that a duplicate record
 *           already exists in the system.
 *
 * <p>This exception is typically thrown when attempting to insert or
 * register a resource (such as an ingredient, user, or category)
 * that already exists, violating a unique constraint.</p>
 */
public class DuplicateResourceException extends RuntimeException {

    /**
     * Constructor to create a new DuplicateResourceException with a specific message.
     *
     * @param message A detailed message describing the duplicate resource conflict.
     */
    public DuplicateResourceException(String message) {
        super(message);
    }
}
