package com.restaurant.inventorysystem.exception;

/**
 * @author Ram
 * @date 2025-11-05
 * @purpose Custom exception used to indicate that a requested resource
 *           (e.g., entity, record, or data) could not be found in the system.
 *
 * <p>This exception is typically thrown when a database lookup or
 * service call does not return the expected resource, such as when
 * searching for an ingredient by ID that does not exist.</p>
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor to create a new ResourceNotFoundException with a specific error message.
     *
     * @param message A detailed message explaining which resource was not found.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
