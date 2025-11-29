package com.restaurant.inventorysystem.repository;

import com.restaurant.inventorysystem.entity.RecipeMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Author: Ram Choudhary
 * Date: 07-Nov-2025
 * Description: Repository for RecipeMaster.
 * Since: 07-Nov-2025
 */
public interface RecipeMasterRepository extends JpaRepository<RecipeMaster, Integer> {
    Optional<RecipeMaster> findByRecipeCodeIgnoreCase(String recipeCode);
    Optional<RecipeMaster> findByRecipeIdAndActiveFlagAndEnableFlag(
            Integer recipeId, Integer activeFlag, Integer enableFlag
    );
    boolean existsByRecipeCodeIgnoreCase(String recipeCode);
    boolean existsByRecipeCodeIgnoreCaseAndRecipeIdNot(String recipeCode, Integer recipeId);
    List<RecipeMaster> findByActiveFlagAndEnableFlag(Integer activeFlag, Integer enableFlag);

}
