package com.restaurant.inventorysystem.dto;

import java.util.List;

/**
 * Author: Ram Choudhary
 * Date: 07-Nov-2025
 * Description: Composite request containing Recipe master + its ingredient xrefs.
 * Since: 07-Nov-2025
 */
public class RecipeRequestDTO {

    private RecipeMasterDTO recipeMasterDTO;
    private List<RecipeIngredientXrefDTO> recipeIngredientXrefDTO;

    public RecipeMasterDTO getRecipeMasterDTO() {
        return recipeMasterDTO;
    }

    public void setRecipeMasterDTO(RecipeMasterDTO recipeMasterDTO) {
        this.recipeMasterDTO = recipeMasterDTO;
    }

    public List<RecipeIngredientXrefDTO> getRecipeIngredientXrefDTO() {
        return recipeIngredientXrefDTO;
    }

    public void setRecipeIngredientXrefDTO(List<RecipeIngredientXrefDTO> recipeIngredientXrefDTO) {
        this.recipeIngredientXrefDTO = recipeIngredientXrefDTO;
    }


}
