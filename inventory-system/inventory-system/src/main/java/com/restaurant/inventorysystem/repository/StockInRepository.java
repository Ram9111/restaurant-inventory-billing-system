package com.restaurant.inventorysystem.repository;

import com.restaurant.inventorysystem.entity.StockIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Stock In module.
 * Provides CRUD operations using Spring Data JPA.
 *
 * @author Ram
 * @date 2025-11-08
 * @purpose Database access for stock_in table
 */
@Repository
public interface StockInRepository extends JpaRepository<StockIn, Integer> {

}
