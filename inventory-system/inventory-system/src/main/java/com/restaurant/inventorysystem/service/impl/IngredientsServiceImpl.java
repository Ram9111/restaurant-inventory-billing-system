package com.restaurant.inventorysystem.service.impl;

import com.restaurant.inventorysystem.dto.IngredientDTO;
import com.restaurant.inventorysystem.entity.IngredientsMaster;
import com.restaurant.inventorysystem.exception.DatabaseException;
import com.restaurant.inventorysystem.exception.DuplicateResourceException;
import com.restaurant.inventorysystem.exception.InvalidDataException;
import com.restaurant.inventorysystem.exception.ResourceNotFoundException;
import com.restaurant.inventorysystem.repository.IngredientsRepository;
import com.restaurant.inventorysystem.service.IngredientsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: Ram Choudhary
 * Date: 31-Oct-2025
 * Description: Implements business logic for Ingredients Master operations.
 * Uses soft delete (updates flags) instead of physical delete.
 */
@Service
public class IngredientsServiceImpl implements IngredientsService {

    @Autowired
    private IngredientsRepository ingredientsRepository;
    private static final Logger logger = LoggerFactory.getLogger(IngredientsServiceImpl.class);

    /**
     * =====================================================================
     *  Project Name  : Restaurant Inventory System
     *  Method Name   : addIngredient
     *  Description   : Adds a new ingredient record into the system after
     *                  performing necessary data initialization and validations.
     *
     *  Author        : Ram Choudhary
     *  Created Date  : 13-Nov-2025
     *  Version       : 1.0
     * =====================================================================
     */
@Override
public IngredientsMaster addIngredient(IngredientsMaster ingredient) {
    try {
        ingredient.setCreatedDate(LocalDateTime.now());
        ingredient.setActiveFlag(1);
        ingredient.setEnableFlag(1);
        return ingredientsRepository.save(ingredient);
    }
    catch (DataIntegrityViolationException e) {
        // Example: unique constraint failed
        throw new DuplicateResourceException("Ingredient with this name already exists");
    }
    catch (IllegalArgumentException e) {
        throw new InvalidDataException("Invalid data provided while saving ingredient");
    }
    catch (Exception e) {
        throw new RuntimeException("Unexpected error while saving ingredient");
    }
}

    /**
     * =====================================================================
     *  Project Name  : Restaurant Inventory System
     *  Method Name   : updateIngredient
     *  Description   : Updates an existing ingredient record in the database.
     *                  Performs data validation and handles exceptions such as
     *                  missing records, invalid input, and duplicate constraints.
     *
     *  Author        : Ram Choudhary
     *  Created Date  : 13-Nov-2025
     *  Version       : 1.0
     * =====================================================================
     */
@Override
public IngredientsMaster updateIngredient(Integer id, IngredientsMaster ingredient) {
    try {
        // Step 1: Fetch existing ingredient from DB
        // If not found, throw ResourceNotFoundException (handled globally → 404)
        IngredientsMaster existing = ingredientsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with ID: " + id));

        // Step 2: Validate incoming data (you can also add Bean Validation annotations)
        if (ingredient.getIngredientName() == null || ingredient.getIngredientName().trim().isEmpty()) {
            throw new InvalidDataException("Ingredient name cannot be blank");
        }

        // Step 3 Update fields (only valid ones)
        existing.setIngredientName(ingredient.getIngredientName());
        existing.setIngredientDescription(ingredient.getIngredientDescription());
        existing.setCategory(ingredient.getCategory());
        existing.setUom(ingredient.getUom());
        existing.setCost(ingredient.getCost());
        existing.setNotes(ingredient.getNotes());
        existing.setUpdatedDate(LocalDateTime.now());
        existing.setUpdatedBy(ingredient.getUpdatedBy());

        // Step 4: Save updated entity in DB
        // If duplicate key or constraint violation occurs, throw DuplicateResourceException (409)
        return ingredientsRepository.save(existing);
    }
    catch (DataIntegrityViolationException e) {
        // Database constraint violated (e.g., duplicate name)
        throw new DuplicateResourceException("Ingredient with this name already exists");
    }
    catch (InvalidDataException e) {
        // Already thrown above, rethrow so global handler can manage
        throw e;
    }
    catch (ResourceNotFoundException e) {
        // Already handled in findById, rethrow to global handler
        throw e;
    }
    catch (Exception e) {
        // Catch any unexpected runtime errors and wrap them in a generic one
        throw new RuntimeException("Unexpected error while updating ingredient", e);
    }
}
/**
 * =====================================================================
 *  Project Name  : Restaurant Inventory System
 *  Method Name   : getAllIngredients
 *  Description   : Retrieves all active ingredients from the database.
 *                  Converts the entity list to DTOs and handles exceptions
 *                  for empty results, database errors, or unexpected issues.
 *
 *  Author        : Ram Choudhary
 *  Created Date  : 13-Nov-2025
 *  Version       : 1.0
 * =====================================================================
 * **/
    @Override
    public List<IngredientDTO> getAllIngredients() {
        try {
            List<IngredientsMaster> ingredients = ingredientsRepository.findAllActiveIngredients();

            // If no ingredients found, throw a custom exception
            if (ingredients.isEmpty()) {
                throw new ResourceNotFoundException("No active ingredients found in the database");
            }

            // Convert entity list to DTO list
            return ingredients.stream()
                    .map(IngredientDTO::new)
                    .collect(Collectors.toList());

        } catch (DataAccessException ex) {
            // Handles database-related errors (like connection issues)
            logger.error("Database error while fetching ingredients: {}", ex.getMessage());
            throw new DatabaseException("Failed to fetch ingredients due to database error", ex);

        } catch (ResourceNotFoundException ex) {
            // Custom exception for empty or missing data
            logger.warn("No ingredients found: {}", ex.getMessage());
            throw ex;

        } catch (Exception ex) {
            // Catch-all fallback for unexpected errors
            logger.error("Unexpected error while fetching ingredients", ex);
            throw new RuntimeException("Something went wrong while fetching ingredients", ex);
        }
    }


    /**
     * =====================================================================
     *  Project Name  : Restaurant Inventory System
     *  Method Name   : getIngredientById
     *  Description   : Fetches a single ingredient record from the database
     *                  using its ID. Validates active and enabled status,
     *                  converts entity to DTO, and handles all possible
     *                  exceptions gracefully.
     *
     *  Author        : Ram Choudhary
     *  Created Date  : 03-Nov-2025
     *  Version       : 1.0
     * =====================================================================
     */

@Override
public IngredientDTO getIngredientById(Integer id) {
    try {

        // 🔹 Try to find the ingredient
        IngredientsMaster entity = ingredientsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with ID: " + id));

        // 🔹 Check if the ingredient is inactive or disabled
        if (entity.getActiveFlag() == 0 || entity.getEnableFlag() == 0) {
            throw new InvalidDataException("Ingredient is inactive or disabled");
        }

        // 🔹 Convert entity to DTO and return
        return new IngredientDTO(entity);
    }
    catch (ResourceNotFoundException ex) {
        // Known handled exception — rethrow to GlobalExceptionHandler
        logger.warn("Ingredient not found: {}", ex.getMessage());
        throw ex;
    }
    catch (InvalidDataException ex) {
        // Known handled exception — rethrow to GlobalExceptionHandler
        logger.warn("Invalid ingredient state: {}", ex.getMessage());
        throw ex;
    }
    catch (DataAccessException ex) {
        // DB-related issue — wrap and rethrow as custom DatabaseException
        logger.error("Database error while fetching ingredient ID {}: {}", id, ex.getMessage());
        throw new DatabaseException("Failed to fetch ingredient from database", ex);
    }
    catch (Exception ex) {
        // Catch-all for unexpected errors
        logger.error("Unexpected error while fetching ingredient ID {}: {}", id, ex.getMessage(), ex);
        throw new RuntimeException("Unexpected error while fetching ingredient", ex);
    }
}


    /**
     * =====================================================================
     *  Project Name  : Restaurant Inventory System
     *  Method Name   : deleteIngredient
     *  Description   : Performs a soft delete of an ingredient record by
     *                  setting its activeFlag and enableFlag to 0 instead
     *                  of permanently deleting the record from the database.
     *
     *  Author        : Ram Choudhary
     *  Created Date  : 03-Nov-2025
     *  Version       : 1.0
     * =====================================================================
     */
    @Override
    public void deleteIngredient(Integer id) {
        //  Soft delete logic (no physical delete)
        IngredientsMaster ingredient = ingredientsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found with ID: " + id));

        ingredient.setActiveFlag(0);
        ingredient.setEnableFlag(0);
        ingredient.setUpdatedDate(LocalDateTime.now());
        ingredientsRepository.save(ingredient);
    }
}
