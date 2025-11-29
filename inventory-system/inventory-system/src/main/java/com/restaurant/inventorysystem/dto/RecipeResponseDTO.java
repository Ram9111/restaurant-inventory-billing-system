package com.restaurant.inventorysystem.dto;

import java.util.List;

/**
 * Author: Ram Choudhary
 * Date: 07-Nov-2025
 * Description: Response DTO containing recipe master and xref list.
 * Since: 07-Nov-2025
 */
public class RecipeResponseDTO {

    private RecipeMasterDTO recipeMaster;
    private List<RecipeIngredientXrefDTO> recipeMasterXrefs;

    public List<RecipeIngredientXrefDTO> getRecipeMasterXrefs() {
        return recipeMasterXrefs;
    }

    public void setRecipeMasterXrefs(List<RecipeIngredientXrefDTO> recipeMasterXrefs) {
        this.recipeMasterXrefs = recipeMasterXrefs;
    }


    public RecipeMasterDTO getRecipeMaster() {
        return recipeMaster;
    }

    public void setRecipeMaster(RecipeMasterDTO recipeMaster) {
        this.recipeMaster = recipeMaster;
    }


}
