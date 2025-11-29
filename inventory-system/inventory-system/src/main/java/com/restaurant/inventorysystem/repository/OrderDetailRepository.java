package com.restaurant.inventorysystem.repository;

import com.restaurant.inventorysystem.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for OrderDetail entity.
 * Handles CRUD operations on order_detail table.
 *
 * Author : Ram Choudhary
 * Date   : 12-Nov-2025
 */
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

}
