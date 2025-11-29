package com.restaurant.inventorysystem.repository;

import com.restaurant.inventorysystem.entity.IngredientsMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author: Ram Choudhary
 * Date: 31-Oct-2025
 * Description: Repository interface for IngredientsMaster entity.
 * Includes query to fetch only active and enabled ingredients.
 */
@Repository
public interface IngredientsRepository extends JpaRepository<IngredientsMaster, Integer> {

    // Fetch only active and enabled records
    @Query("SELECT i FROM IngredientsMaster i WHERE i.activeFlag = 1 AND i.enableFlag = 1")
    List<IngredientsMaster> findAllActiveIngredients();
}
