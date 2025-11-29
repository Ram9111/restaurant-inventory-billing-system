package com.restaurant.inventorysystem.repository;

import com.restaurant.inventorysystem.entity.RecipeIngredientXref;
import com.restaurant.inventorysystem.entity.RecipeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Author: Ram Choudhary
 * Date: 07-Nov-2025
 * Description: Repository for RecipeIngredientXref.
 * Since: 07-Nov-2025
 */
public interface RecipeIngredientXrefRepository extends JpaRepository<RecipeIngredientXref, Integer> {
    List<RecipeIngredientXref> findByRecipe_RecipeId(Integer recipeId);
    void deleteByRecipe_RecipeId(Integer recipeId);
    List<RecipeIngredientXref> findByRecipe_RecipeIdAndActiveFlagAndEnableFlag(Integer recipeId,
                                                                               Integer activeFlag,
                                                                               Integer enableFlag);
    List<RecipeIngredientXref> findByRecipe(RecipeMaster recipe);


}
