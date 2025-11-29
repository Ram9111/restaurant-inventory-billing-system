package com.restaurant.inventorysystem.service;

import com.restaurant.inventorysystem.dto.IngredientDTO;
import com.restaurant.inventorysystem.entity.IngredientsMaster;
import java.util.List;

/**
 * Author: Ram Choudhary
 * Date: 31-Oct-2025
 * Description: Service interface for Ingredients Master operations.
 */
public interface IngredientsService {

    IngredientsMaster addIngredient(IngredientsMaster ingredient);

    IngredientsMaster updateIngredient(Integer id, IngredientsMaster ingredient);
   //public IngredientsMaster updateIngredient(Integer id, IngredientsMaster updatedIngredient);

   // List<IngredientsMaster> getAllIngredients();
   List<IngredientDTO> getAllIngredients();

    //IngredientsMaster getIngredientById(Integer id);
    IngredientDTO getIngredientById(Integer id);

    void deleteIngredient(Integer id);
}
