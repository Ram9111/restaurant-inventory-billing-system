package com.restaurant.inventorysystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) by exclude run the project without DB
@SpringBootApplication
public class RestaurantInventoryManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantInventoryManagementApplication.class, args);
        System.out.println("I am On");
	}

}
