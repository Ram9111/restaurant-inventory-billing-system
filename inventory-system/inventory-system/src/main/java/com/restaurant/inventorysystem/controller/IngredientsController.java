package com.restaurant.inventorysystem.controller;

import com.restaurant.inventorysystem.dto.IngredientDTO;
import com.restaurant.inventorysystem.entity.IngredientsMaster;
import com.restaurant.inventorysystem.service.IngredientsService;
import com.restaurant.inventorysystem.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: Ram Choudhary
 * Date: 02-Nav-2025
 * Description: REST Controller for managing Ingredients Master data.
 * Endpoints: Add, Update, Fetch, Delete.
 */
@RestController
@RequestMapping("/api/ingredients")
public class IngredientsController {

    @Autowired
    private IngredientsService ingredientsService;

    /**
     * Add a new ingredient to the database.
     *
     * @param ingredient The ingredient details to be saved.
     * @return ApiResponse with success message and saved ingredient data.
     * @since 05-Nov-2025
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addIngredient(@RequestBody IngredientsMaster ingredient) {
        IngredientsMaster savedIngredient = ingredientsService.addIngredient(ingredient);
        return ResponseEntity.ok(new ApiResponse(200, "Ingredient saved successfully", savedIngredient));
    }

    /**
     * Update an recipeMaster ingredient by its ID.
     *
     * @param id The ID of the ingredient to update.
     * @param updatedIngredient The updated ingredient details.
     * @return ApiResponse with success message and updated ingredient data (as DTO).
     * @since 05-Nov-2025
     */

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateIngredient(
            @PathVariable Integer id,
            @RequestBody IngredientsMaster updatedIngredient
    ) {
        IngredientsMaster saved = ingredientsService.updateIngredient(id, updatedIngredient);
        IngredientDTO responseDTO = new IngredientDTO(saved);

        return ResponseEntity.ok(
                new ApiResponse(200, "Ingredient updated successfully", responseDTO)
        );
    }

    /**
     * Retrieve all active ingredients from the database.
     *
     * @return ApiResponse containing a list of IngredientDTOs and a success message.
     * @since 05-Nov-2025
     */
        @GetMapping("/all")
        public ResponseEntity<ApiResponse> getAllIngredients() {
            List<IngredientDTO> ingredients = ingredientsService.getAllIngredients();
            return ResponseEntity.ok(
                    new ApiResponse(200, "Ingredients fetched successfully", ingredients)
            );
        }

    /**
     * Fetch a single ingredient by its ID.
     *
     * @param id The ID of the ingredient to retrieve.
     * @return ApiResponse containing the ingredient details if found.
     * @since 05-Nov-2025
     */

    @GetMapping("/{id}")
public ResponseEntity<ApiResponse> getIngredientById(@PathVariable Integer id) {
    IngredientDTO ingredient = ingredientsService.getIngredientById(id);
    return ResponseEntity.ok(
            new ApiResponse(200, "Ingredient fetched successfully", ingredient)
    );
}

    /**
     * Delete an ingredient by its ID.
     *
     * @param id The ID of the ingredient to delete.
     * @return ApiResponse with a success message upon successful deletion.
     * @since 05-Nov-2025
     */
@DeleteMapping("/delete/{id}")
public ResponseEntity<ApiResponse> deleteIngredient(@PathVariable Integer id) {
    ingredientsService.deleteIngredient(id);
    return ResponseEntity.ok(
            new ApiResponse(200, "Ingredient deleted successfully", null)
    );
}

}
