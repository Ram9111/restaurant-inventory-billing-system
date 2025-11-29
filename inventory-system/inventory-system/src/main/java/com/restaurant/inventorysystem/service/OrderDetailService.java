package com.restaurant.inventorysystem.service;

import com.restaurant.inventorysystem.dto.OrderDetailDTO;
import com.restaurant.inventorysystem.util.ApiResponse;

/**
 * ------------------------------------------------------------
 * SERVICE INTERFACE: Order Detail
 * ------------------------------------------------------------
 * Defines business operations for order management.
 *
 * Author : Ram Choudhary
 * Date   : 12-Nov-2025
 */
public interface OrderDetailService {

    /**
     * Saves a new order along with its recipe items.
     * Also validates and reduces ingredient stock.
     *
     * @param orderDetailDTO Data Transfer Object for order
     * @return ApiResponse with success/failure info
     */
    ApiResponse saveOrder(OrderDetailDTO orderDetailDTO);
    /**
     * Fetch a specific order by its unique ID.
     *
     * @param id the ID of the order to fetch
     * @return ApiResponse containing the order details or an error message if not found
     */
    ApiResponse getOrderById(Integer id);

    /**
     * Retrieve a list of all orders.
     *
     * @return ApiResponse containing a list of all existing orders
     */
    ApiResponse getAllOrders();

    /**
     * Perform a soft delete on an order by its ID.
     *
     * - Marks the order as inactive instead of physically deleting it.
     * - Automatically reverses the stock used in the order’s ingredients.
     *
     * @param id the ID of the order to delete
     * @return ApiResponse indicating success or failure of the delete operation
     */
    ApiResponse deleteOrder(Integer id);


}
