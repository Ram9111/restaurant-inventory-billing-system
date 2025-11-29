package com.restaurant.inventorysystem.service;

import com.restaurant.inventorysystem.dto.RecipeMasterDTO;
import com.restaurant.inventorysystem.dto.RecipeRequestDTO;
import com.restaurant.inventorysystem.dto.RecipeResponseDTO;
import com.restaurant.inventorysystem.entity.RecipeMaster;

import java.util.List;

/**
 * Author: Ram Choudhary
 * Date: 07-Nov-2025
 * Description: Service interface for managing Recipe Master and its Ingredient Xref data.
 * Includes methods for create, update, fetch (single + all), and delete operations.
 * Since: 07-Nov-2025
 */
public interface RecipeService {

    /**
     * Save a new recipe along with its ingredient xref rows.
     *
     * @param request Composite DTO containing master + xref list.
     * @return Saved RecipeMaster entity.
     */
    RecipeMaster saveRecipe(RecipeRequestDTO request);

    /**
     * Update an existing recipe and replace its entire ingredient xref list.
     *
     * @param recipeId ID of the recipe to update.
     * @param request Composite DTO containing updated master + xrefs.
     * @return Updated RecipeMaster entity.
     */
    RecipeMaster updateRecipe(Integer recipeId, RecipeRequestDTO request);

    /**
     * Fetch recipe master + its ingredient xrefs.
     *
     * @param recipeId Recipe ID.
     * @return Combined response DTO containing master + xref list.
     */
    RecipeResponseDTO getRecipeWithXrefs(Integer recipeId);

    /**
     * Fetch only recipe master list (without xrefs).
     *
     * Use this for fast listing.
     *
     * @return List of RecipeMasterDTO.
     */
    List<RecipeMasterDTO> getAllRecipes();

    /**
     * Delete the recipe master and all xref rows.
     *
     * @param recipeId Recipe ID to delete.
     */
    void deleteRecipe(Integer recipeId);
}
