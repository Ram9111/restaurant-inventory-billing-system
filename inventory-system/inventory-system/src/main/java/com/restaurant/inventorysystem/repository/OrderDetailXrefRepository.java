package com.restaurant.inventorysystem.repository;

import com.restaurant.inventorysystem.entity.OrderDetailXref;
import com.restaurant.inventorysystem.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for OrderDetailXref entity.
 * Handles CRUD operations on order_detail_xref table.
 * Also provides methods to fetch all recipes linked to an order.
 *
 * Author : Ram Choudhary
 * Date   : 12-Nov-2025
 */
@Repository
public interface OrderDetailXrefRepository extends JpaRepository<OrderDetailXref, Integer> {

    /**
     * Find all recipe items (xref) for a specific order.
     *
     * @param orderDetail OrderDetail object
     * @return List of OrderDetailXref entries
     */
    List<OrderDetailXref> findByOrderDetail(OrderDetail orderDetail);
    List<OrderDetailXref> findByOrderDetailOrderId(Integer orderId);
}
