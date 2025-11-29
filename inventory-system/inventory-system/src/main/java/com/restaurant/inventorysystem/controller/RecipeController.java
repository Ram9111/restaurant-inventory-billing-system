package com.restaurant.inventorysystem.controller;

import com.restaurant.inventorysystem.dto.RecipeMasterDTO;
import com.restaurant.inventorysystem.dto.RecipeRequestDTO;
import com.restaurant.inventorysystem.dto.RecipeResponseDTO;
import com.restaurant.inventorysystem.entity.RecipeMaster;
import com.restaurant.inventorysystem.service.RecipeService;
import com.restaurant.inventorysystem.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: Ram Choudhary
 * Date: 07-Nov-2025
 * Description: REST Controller for managing Recipe aggregate (master + xrefs).
 * Endpoints: Add, Update, Fetch All, Fetch One, Delete.
 * Since: 07-Nov-2025
 */
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService) { this.recipeService = recipeService; }

    /**
     * Add a new recipe with its ingredient xrefs.
     * @param request Composite request containing master and xrefs.
     * @return ApiResponse with saved master entity.
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addRecipe(@RequestBody RecipeRequestDTO request) {
        RecipeMaster saved = recipeService.saveRecipe(request);
        return ResponseEntity.ok(new ApiResponse(200, "Recipe saved successfully", saved));
    }

    /**
     * Update an existing recipe and fully replace its xrefs.
     * @param id Recipe ID to update.
     * @param request Composite request with updated master and xrefs.
     * @return ApiResponse with updated master entity.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateRecipe(@PathVariable Integer id,
                                                    @RequestBody RecipeRequestDTO request) {
        RecipeMaster updated = recipeService.updateRecipe(id, request);
        return ResponseEntity.ok(new ApiResponse(200, "Recipe updated successfully", updated));
    }


    /**
     * Fetch all recipes (master only DTOs).
     * @return ApiResponse with list of recipes.
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllRecipes() {
        List<RecipeMasterDTO> list = recipeService.getAllRecipes();
        return ResponseEntity.ok(new ApiResponse(200, "Recipes fetched successfully", list));
    }

    /**
     * Fetch a single recipe by ID (master DTO).
     * @param id Recipe ID
     * @return ApiResponse with recipe DTO.
     */
//    @GetMapping("/{id}")
//    public ResponseEntity<ApiResponse> getRecipeById(@PathVariable Long id) {
//        RecipeMasterDTO dto = recipeService.getRecipeById(id);
//        return ResponseEntity.ok(new ApiResponse(200, "Recipe fetched successfully", dto));
//    }
    /**
     * Fetch a recipe with its ingredient xrefs.
     *
     * @param id The recipe ID.
     * @return RecipeResponseDTO containing master + xrefs.
     * @since 07-Nov-2025
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getRecipeById(@PathVariable Integer id) {
        RecipeResponseDTO recipe = recipeService.getRecipeWithXrefs(id);
        return ResponseEntity.ok(
                new ApiResponse(200, "Recipe fetched successfully", recipe)
        );
    }

    /**
     * Delete a recipe by ID (cascades xrefs delete strategy in service).
     * @param id Recipe ID
     * @return ApiResponse with null data.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteRecipe(@PathVariable Integer id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok(new ApiResponse(200, "Recipe deleted successfully", null));
    }
}
