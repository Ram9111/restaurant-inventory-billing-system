package com.restaurant.inventorysystem.exception;

import com.restaurant.inventorysystem.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * ================================================================
 *  GlobalExceptionHandler
 * ================================================================
 *  This class provides centralized exception handling across
 *  all controllers in the application.
 *
 *  - Annotated with {@link ControllerAdvice}, it intercepts exceptions
 *    thrown from any controller and formats a consistent API response.
 *
 *  1) We create the exception class in exception pakage and extends its parent exception class in it
 *  2) Now create the method in this class to handle that exp globlly
 *  3) use the ExceptionHandler anotation to pass the custom exception class with same pakage.
 *  4) we use ApiResponse for return the exception massage with code.
 *
 *  - It ensures that clients receive a well-structured JSON response
 *    containing:
 *      - status      → HTTP status code (e.g., 400, 500)
 *      - error       → Short description of the error type
 *      - message     → Developer-friendly error message
 *
 *  - Helps maintain cleaner controller code by removing repetitive
 *    try-catch blocks and providing a single point for error formatting.
 *  Author: Ram Choudhary
 *  Created On: {@link LocalDateTime#now()}
 * ================================================================
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles cases where a requested resource (record) is not found in the database.
     * Example: Trying to fetch an ingredient by ID that doesn't exist.
     *
     * HTTP Status: 404 NOT_FOUND
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        logger.error("Resource not found: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(404, ex.getMessage(), null));
    }

    /**
     * Handles cases where a duplicate resource is being created.
     * Example: Trying to insert an ingredient with a name that already exists
     * (violates a unique constraint).
     *
     * HTTP Status: 409 CONFLICT
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse> handleDuplicateResource(DuplicateResourceException ex) {
        logger.error("Duplicate resource: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiResponse(409, ex.getMessage(), null));
    }

    /**
     * Handles cases of invalid or malformed input data.
     * Example: Null values, empty strings, or invalid format fields passed from the client.
     *
     * HTTP Status: 400 BAD_REQUEST
     */
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ApiResponse> handleInvalidData(InvalidDataException ex) {
        logger.error("Invalid data: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(400, ex.getMessage(), null));
    }

    /**
     * Handles low-level database constraint violations from Spring Data JPA.
     * Example: Trying to insert a duplicate key or violating a foreign key constraint.
     *
     * HTTP Status: 409 CONFLICT
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDataIntegrity(DataIntegrityViolationException ex) {
        logger.error("Data integrity violation: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiResponse(409, "Database constraint violation", null));
    }

    /**
     * Fallback handler for any unhandled or unexpected exceptions.
     * This ensures that no internal error details are leaked to the client.
     *
     * HTTP Status: 500 INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
        // Logs full stack trace for developers/admins (appears in server logs)
        logger.error("Unexpected error occurred", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(500, "Something went wrong", null));
    }
    //Handle Database Exception
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ApiResponse> handleDatabaseException(DatabaseException ex) {
        logger.error("Database exception: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(500, ex.getMessage(), null));
    }

}
