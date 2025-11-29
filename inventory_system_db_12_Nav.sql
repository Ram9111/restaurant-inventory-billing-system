-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: restaurant_inventory
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ingredients_master`
--

DROP TABLE IF EXISTS `ingredients_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredients_master` (
  `ingredient_id` bigint NOT NULL AUTO_INCREMENT,
  `ingredient_code` varchar(50) NOT NULL,
  `ingredient_name` varchar(100) NOT NULL,
  `ingredient_description` varchar(255) DEFAULT NULL,
  `category` varchar(100) DEFAULT NULL,
  `uom` varchar(50) DEFAULT NULL,
  `smaller_unit` varchar(20) NOT NULL,
  `base_unit_value` decimal(38,2) NOT NULL,
  `current_stock_subunit` decimal(38,2) DEFAULT NULL,
  `cost` decimal(10,2) DEFAULT NULL,
  `notes` varchar(500) DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `active_flag` int DEFAULT '1',
  `enable_flag` int DEFAULT '1',
  PRIMARY KEY (`ingredient_id`),
  UNIQUE KEY `ingredient_code_UNIQUE` (`ingredient_code`),
  KEY `fk_ingredient_created_by` (`created_by`),
  KEY `fk_ingredient_updated_by` (`updated_by`),
  CONSTRAINT `fk_ingredient_created_by` FOREIGN KEY (`created_by`) REFERENCES `user` (`User_Id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_ingredient_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `user` (`User_Id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredients_master`
--

LOCK TABLES `ingredients_master` WRITE;
/*!40000 ALTER TABLE `ingredients_master` DISABLE KEYS */;
INSERT INTO `ingredients_master` VALUES (11,'ING00','Tomato (Organic)','Organic fresh tomatoes sourced locally',NULL,'KG','gram',1000.00,10000.00,NULL,NULL,1,'2025-11-05 15:27:15',NULL,'2025-11-10 15:36:23',1,1),(13,'ING002','Tomat2','Fresh red tomatoes used for sauces and salads2',NULL,'KG','gram',1000.00,0.00,NULL,NULL,1,'2025-11-05 15:31:55',NULL,'2025-11-05 16:57:25',0,0),(14,'ING003','Tomat3','Fresh red tomatoes used for sauces and salads2',NULL,'KG','gram',1000.00,0.00,NULL,NULL,1,'2025-11-05 16:10:43',NULL,'2025-11-05 16:56:47',0,0),(15,'ING004','Tomat4','Fresh red tomatoes used for sauces and salads2',NULL,'KG','gram',1000.00,0.00,NULL,NULL,1,'2025-11-05 16:47:58',NULL,NULL,1,1),(16,'ING005','Tomat5','2Fresh red tomatoes used for sauces and salads2',NULL,'Litter','ml',1500.00,0.00,NULL,NULL,1,'2025-11-06 20:16:46',NULL,NULL,1,1),(17,'Item1','Item1','Use in recepy Item1',NULL,'Litter','ml',1000.00,0.00,NULL,NULL,1,'2025-11-07 15:32:12',NULL,NULL,1,1),(19,'Item2','Item2','Use in recepy Item2',NULL,'Litter','ml',1000.00,0.00,NULL,NULL,1,'2025-11-07 15:32:32',NULL,NULL,1,1),(20,'Item3','Item3','Use in recepy Item3',NULL,'Litter','ml',1000.00,0.00,NULL,NULL,1,'2025-11-07 16:10:34',NULL,NULL,1,1),(21,'Item4','Item4','Use in recepy Item4',NULL,'Litter','ml',1000.00,0.00,NULL,NULL,1,'2025-11-07 16:10:46',NULL,NULL,1,1),(23,'Item5','Tomat55','Fresh red tomatoes used for sauces and salads2',NULL,'KG','ml',1000.00,0.00,NULL,NULL,1,'2025-11-07 18:46:26',NULL,'2025-11-07 18:47:01',1,1),(24,'Item6','Item6','Use in recepy Item6',NULL,'Litter','ml',1000.00,0.00,NULL,NULL,1,'2025-11-09 10:17:33',NULL,NULL,1,1),(25,'T1','T55','Fresh red tomatoes used for sauces and salads2',NULL,'KG','ml',1000.00,0.00,NULL,NULL,1,'2025-11-09 10:26:51',NULL,'2025-11-09 10:28:53',1,1),(26,'Test1','Test1','Test1',NULL,'Litter','ml',1000.00,50000.00,NULL,NULL,1,'2025-11-10 15:55:36',NULL,'2025-11-10 16:07:07',1,1),(27,'Milk','Milk','Milk',NULL,'Litter','ml',1000.00,9900.00,NULL,NULL,1,'2025-11-12 21:55:23',NULL,'2025-11-12 22:01:54',1,1),(28,'Powder','Powder','Powder',NULL,'KG','gram',1000.00,950.00,NULL,NULL,1,'2025-11-12 21:57:27',NULL,'2025-11-12 22:02:15',1,1),(29,'Sugger','Sugger','Sugger',NULL,'KG','gram',1000.00,950.00,NULL,NULL,1,'2025-11-13 22:57:02',NULL,'2025-11-13 23:03:01',1,1),(30,'Bread','Bread','Bread',NULL,'KG','gram',1000.00,9800.00,NULL,NULL,1,'2025-11-13 22:58:35',NULL,'2025-11-13 23:01:31',1,1),(31,'Paneer','Paneer','Paneer',NULL,'KG','gram',1000.00,1900.00,NULL,NULL,1,'2025-11-13 22:58:51',NULL,'2025-11-13 23:01:54',1,1),(32,'Chees','Chees','Chees',NULL,'KG','gram',1000.00,900.00,NULL,NULL,1,'2025-11-13 22:59:13',NULL,'2025-11-13 23:02:13',1,1),(33,'Corn','Corn','Fresh Corm used for Create Recipe',NULL,'KG','gram',1000.00,0.00,NULL,NULL,1,'2025-11-14 10:17:52',NULL,'2025-11-14 10:23:23',1,1);
/*!40000 ALTER TABLE `ingredients_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_detail` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `order_no` varchar(255) NOT NULL,
  `table_no` varchar(255) DEFAULT NULL,
  `order_type` varchar(255) DEFAULT NULL,
  `order_date` datetime(6) DEFAULT NULL,
  `customer_name` varchar(255) DEFAULT NULL,
  `customer_phone` varchar(255) DEFAULT NULL,
  `total_amount` decimal(38,2) DEFAULT NULL,
  `discount` decimal(38,2) DEFAULT NULL,
  `tax_amount` decimal(38,2) DEFAULT NULL,
  `grand_total` decimal(38,2) DEFAULT NULL,
  `payment_mode` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `active_flag` int DEFAULT NULL,
  `enable_flag` int DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_by` int DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `UKr55n7ieoh0nx1dvsisurugqic` (`order_no`),
  KEY `FKr7v04k9bwa5fqtj1oqt3khv2y` (`created_by`),
  KEY `FK3q3drebcwu17ct57adxcfvmpn` (`modified_by`),
  CONSTRAINT `FK3q3drebcwu17ct57adxcfvmpn` FOREIGN KEY (`modified_by`) REFERENCES `user` (`User_Id`),
  CONSTRAINT `FKr7v04k9bwa5fqtj1oqt3khv2y` FOREIGN KEY (`created_by`) REFERENCES `user` (`User_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (1,'ORD-001','T1','Dine-In','2025-11-12 19:30:00.000000','Ram Choudhary','9876543210',200.00,10.00,9.00,199.00,'Cash','No sugar in cold coffee',0,0,NULL,NULL,NULL,NULL),(3,'ORD-002','T1','Dine-In','2025-11-12 19:30:00.000000','Ram Choudhary','9876543210',200.00,10.00,9.00,199.00,'Cash','sugar in Hot coffee',1,1,NULL,NULL,NULL,NULL),(4,'ORD-003','T1','Dine-In','2025-11-12 19:30:00.000000','Moni Choudhary','9876543210',200.00,10.00,9.00,199.00,'Cash','sugar in Hot coffee',0,0,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail_xref`
--

DROP TABLE IF EXISTS `order_detail_xref`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_detail_xref` (
  `order_detail_xref_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `recipe_id` bigint NOT NULL,
  `recipe_name` varchar(255) DEFAULT NULL,
  `quantity` decimal(38,2) DEFAULT NULL,
  `selling_price` decimal(38,2) DEFAULT NULL,
  `active_flag` int DEFAULT NULL,
  `enable_flag` int DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` int DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`order_detail_xref_id`),
  KEY `fk_orderxref_order` (`order_id`),
  KEY `fk_orderxref_recipe` (`recipe_id`),
  KEY `fk_orderxref_created_by` (`created_by`),
  KEY `fk_orderxref_modified_by` (`modified_by`),
  CONSTRAINT `fk_orderxref_created_by` FOREIGN KEY (`created_by`) REFERENCES `user` (`User_Id`),
  CONSTRAINT `fk_orderxref_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `user` (`User_Id`),
  CONSTRAINT `fk_orderxref_orderDetail` FOREIGN KEY (`order_id`) REFERENCES `order_detail` (`order_id`),
  CONSTRAINT `fk_orderxref_recipe` FOREIGN KEY (`recipe_id`) REFERENCES `recipe_master` (`recipe_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail_xref`
--

LOCK TABLES `order_detail_xref` WRITE;
/*!40000 ALTER TABLE `order_detail_xref` DISABLE KEYS */;
INSERT INTO `order_detail_xref` VALUES (1,1,7,'Cold Coffee',2.00,220.00,0,0,NULL,NULL,NULL,NULL),(2,3,8,'Hot Coffee',1.00,100.00,1,1,NULL,NULL,NULL,NULL),(3,3,9,'Sandwich',1.00,200.00,1,1,NULL,NULL,NULL,NULL),(4,4,8,'Hot Coffee',2.00,100.00,0,0,NULL,NULL,NULL,NULL),(5,4,9,'Sandwich',3.00,200.00,0,0,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `order_detail_xref` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `recipe_ingredient_view`
--

DROP TABLE IF EXISTS `recipe_ingredient_view`;
/*!50001 DROP VIEW IF EXISTS `recipe_ingredient_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `recipe_ingredient_view` AS SELECT 
 1 AS `recipe_xref_id`,
 1 AS `recipe_id`,
 1 AS `recipe_code`,
 1 AS `recipe_name`,
 1 AS `recipe_type`,
 1 AS `ingredient_id`,
 1 AS `ingredient_name`,
 1 AS `uom`,
 1 AS `smaller_unit`,
 1 AS `base_unit_value`,
 1 AS `quantity_value`,
 1 AS `remarks`,
 1 AS `created_by_id`,
 1 AS `created_by_name`,
 1 AS `created_date`,
 1 AS `modified_by_id`,
 1 AS `modified_by_name`,
 1 AS `modified_date`,
 1 AS `active_flag`,
 1 AS `enable_flag`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `recipe_master`
--

DROP TABLE IF EXISTS `recipe_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipe_master` (
  `recipe_id` bigint NOT NULL AUTO_INCREMENT,
  `recipe_code` varchar(50) NOT NULL,
  `recipe_name` varchar(100) NOT NULL,
  `recipe_type` varchar(30) DEFAULT NULL,
  `description` text,
  `total_cost` decimal(10,2) DEFAULT NULL,
  `selling_price` decimal(10,2) DEFAULT NULL,
  `preparation_time` int DEFAULT NULL,
  `active_flag` int NOT NULL DEFAULT '1',
  `enable_flag` int NOT NULL DEFAULT '1',
  `created_by` int DEFAULT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_by` int DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`recipe_id`),
  UNIQUE KEY `recipe_code` (`recipe_code`),
  KEY `fk_recipe_created_by` (`created_by`),
  KEY `fk_recipe_modified_by` (`modified_by`),
  CONSTRAINT `fk_recipe_created_by` FOREIGN KEY (`created_by`) REFERENCES `user` (`User_Id`),
  CONSTRAINT `fk_recipe_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `user` (`User_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe_master`
--

LOCK TABLES `recipe_master` WRITE;
/*!40000 ALTER TABLE `recipe_master` DISABLE KEYS */;
INSERT INTO `recipe_master` VALUES (4,'RCP005','Dal Fry Special','VEG','Updated Dal Fry Special',NULL,250.00,30,1,1,1,'2025-11-07 18:49:12',1,'2025-11-09 00:07:26'),(5,'RCP004','Dal','VEG','Dal creamy Chole gravy',NULL,220.00,25,0,0,1,'2025-11-09 00:04:07',NULL,'2025-11-09 00:09:27'),(6,'Dummy','Dummy','VEG','Dummy Dummy Dummy',NULL,220.00,25,1,1,1,'2025-11-10 15:57:25',NULL,NULL),(7,'Cold Coffee','Cold Coffee','VEG','Cold Coffee',NULL,220.00,25,1,1,1,'2025-11-12 22:00:53',NULL,NULL),(8,'Hot Coffee','Hot Coffee','VEG','Hot Coffee',NULL,100.00,10,1,1,1,'2025-11-13 23:10:33',NULL,NULL),(9,'Sandwich','Sandwich','VEG','Sandwich',NULL,200.00,10,1,1,1,'2025-11-13 23:12:18',NULL,NULL);
/*!40000 ALTER TABLE `recipe_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipe_master_ingredient_xref`
--

DROP TABLE IF EXISTS `recipe_master_ingredient_xref`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipe_master_ingredient_xref` (
  `recipe_xref_id` bigint NOT NULL AUTO_INCREMENT,
  `recipe_id` bigint NOT NULL,
  `ingredient_id` bigint NOT NULL,
  `uom` varchar(50) DEFAULT NULL,
  `smaller_unit` varchar(20) DEFAULT NULL,
  `base_unit_value` decimal(10,2) DEFAULT NULL,
  `quantity_value` decimal(10,2) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `active_flag` int DEFAULT NULL,
  `enable_flag` int DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` int DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`recipe_xref_id`),
  KEY `fk_recipe_xref_recipe` (`recipe_id`),
  KEY `fk_recipe_xref_ingredient` (`ingredient_id`),
  KEY `fk_recipe_xref_created_by` (`created_by`),
  KEY `fk_recipe_xref_modified_by` (`modified_by`),
  CONSTRAINT `fk_recipe_xref_created_by` FOREIGN KEY (`created_by`) REFERENCES `user` (`User_Id`),
  CONSTRAINT `fk_recipe_xref_ingredient` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredients_master` (`ingredient_id`),
  CONSTRAINT `fk_recipe_xref_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `user` (`User_Id`),
  CONSTRAINT `fk_recipe_xref_recipe` FOREIGN KEY (`recipe_id`) REFERENCES `recipe_master` (`recipe_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe_master_ingredient_xref`
--

LOCK TABLES `recipe_master_ingredient_xref` WRITE;
/*!40000 ALTER TABLE `recipe_master_ingredient_xref` DISABLE KEYS */;
INSERT INTO `recipe_master_ingredient_xref` VALUES (13,5,19,'AAA','Chole',200.00,200.00,NULL,0,0,1,'2025-11-09 00:04:07',NULL,'2025-11-09 00:09:27'),(14,5,20,'BBB','Chole',50.00,50.00,NULL,0,0,1,'2025-11-09 00:04:07',NULL,'2025-11-09 00:09:27'),(15,4,19,'CCC','Bhature',250.00,250.00,'Updated quantity',1,1,NULL,NULL,1,'2025-11-09 00:07:26'),(16,4,20,'DDD','ml',60.00,60.00,'Updated for taste',1,1,NULL,NULL,1,'2025-11-09 00:07:26'),(17,6,26,'AAA','Chole',200.00,200.00,NULL,1,1,1,'2025-11-10 15:57:25',NULL,NULL),(18,7,27,'Litter','ml',1000.00,100.00,NULL,1,1,1,'2025-11-12 22:00:53',NULL,NULL),(19,7,28,'KG','gram',1000.00,20.00,NULL,1,1,1,'2025-11-12 22:00:53',NULL,NULL),(20,8,27,'Litter','ml',1000.00,100.00,NULL,1,1,1,'2025-11-13 23:10:33',NULL,NULL),(21,8,28,'KG','gram',1000.00,50.00,NULL,1,1,1,'2025-11-13 23:10:33',NULL,NULL),(22,8,29,'KG','gram',1000.00,50.00,NULL,1,1,1,'2025-11-13 23:10:33',NULL,NULL),(23,9,30,'KG','gram',1000.00,200.00,NULL,1,1,1,'2025-11-13 23:12:18',NULL,NULL),(24,9,31,'KG','gram',1000.00,100.00,NULL,1,1,1,'2025-11-13 23:12:18',NULL,NULL),(25,9,32,'KG','gram',1000.00,100.00,NULL,1,1,1,'2025-11-13 23:12:18',NULL,NULL);
/*!40000 ALTER TABLE `recipe_master_ingredient_xref` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_in`
--

DROP TABLE IF EXISTS `stock_in`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock_in` (
  `stock_in_id` bigint NOT NULL AUTO_INCREMENT,
  `ingredient_id` bigint NOT NULL,
  `stock_no` varchar(255) NOT NULL,
  `stock_in_date` datetime NOT NULL,
  `supplier_name` varchar(255) DEFAULT NULL,
  `qty_main` decimal(38,2) NOT NULL,
  `qty_sub` decimal(38,2) NOT NULL,
  `previous_stock_sub` decimal(38,2) NOT NULL,
  `updated_stock_sub` decimal(38,2) NOT NULL,
  `cost_per_unit` decimal(38,2) DEFAULT NULL,
  `total_cost` decimal(38,2) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` int DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `active_flag` int NOT NULL DEFAULT '1',
  `enable_flag` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`stock_in_id`),
  KEY `fk_stockin_ingredient` (`ingredient_id`),
  KEY `fk_stockin_created_by` (`created_by`),
  KEY `fk_stockin_modified_by` (`modified_by`),
  CONSTRAINT `fk_stockin_created_by` FOREIGN KEY (`created_by`) REFERENCES `user` (`User_Id`),
  CONSTRAINT `fk_stockin_ingredient` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredients_master` (`ingredient_id`),
  CONSTRAINT `fk_stockin_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `user` (`User_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_in`
--

LOCK TABLES `stock_in` WRITE;
/*!40000 ALTER TABLE `stock_in` DISABLE KEYS */;
INSERT INTO `stock_in` VALUES (1,11,'STK-1762761446263','2025-11-09 10:30:00','Fresh Foods Pvt Ltd',5.00,5000.00,0.00,5000.00,120.50,602.50,'Morning delivery',1,'2025-11-10 13:27:26',NULL,NULL,1,1),(2,11,'STK-1762764463382','2025-11-09 10:30:00','Fresh Foods Pvt Ltd',5.00,5000.00,5000.00,10000.00,120.50,602.50,'Morning delivery',1,'2025-11-10 14:17:43',NULL,'2025-11-10 15:36:23',1,1),(3,26,'STK-1762770764652','2025-11-09 10:30:00','Dummy Foods Pvt Ltd',50.00,50000.00,0.00,50000.00,10.00,500.00,'Morning delivery',1,'2025-11-10 16:02:45',NULL,NULL,1,1),(4,26,'STK-1762770964264','2025-11-09 10:30:00','Dummy Foods Pvt Ltd',20.00,20000.00,50000.00,70000.00,10.00,200.00,'Morning delivery',1,'2025-11-10 16:06:04',NULL,'2025-11-10 16:07:07',0,0),(5,27,'STK-1762965113684','2025-11-09 10:30:00','Dummy Foods Pvt Ltd',10.00,10000.00,0.00,10000.00,10.00,100.00,'Morning delivery',1,'2025-11-12 22:01:54',NULL,NULL,1,1),(6,28,'STK-1762965134599','2025-11-09 10:30:00','Dummy Foods Pvt Ltd',1.00,1000.00,0.00,1000.00,100.00,100.00,'Morning delivery',1,'2025-11-12 22:02:15',NULL,NULL,1,1),(7,30,'STK-1763055090998','2025-11-09 10:30:00','Dummy Foods Pvt Ltd',10.00,10000.00,0.00,10000.00,100.00,1000.00,'Morning delivery',1,'2025-11-13 23:01:31',NULL,NULL,1,1),(8,31,'STK-1763055114002','2025-11-09 10:30:00','Dummy Foods Pvt Ltd',2.00,2000.00,0.00,2000.00,100.00,200.00,'Morning delivery',1,'2025-11-13 23:01:54',NULL,NULL,1,1),(9,32,'STK-1763055133140','2025-11-09 10:30:00','Dummy Foods Pvt Ltd',1.00,1000.00,0.00,1000.00,100.00,100.00,'Morning delivery',1,'2025-11-13 23:02:13',NULL,NULL,1,1),(10,29,'STK-1763055181227','2025-11-09 10:30:00','Dummy Foods Pvt Ltd',1.00,1000.00,0.00,1000.00,100.00,100.00,'Morning delivery',1,'2025-11-13 23:03:01',NULL,NULL,1,1);
/*!40000 ALTER TABLE `stock_in` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `User_Id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `Password` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `designation` varchar(255) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `User_Type` int DEFAULT NULL,
  `user_email` varchar(255) DEFAULT NULL,
  `UserRole_Id` int DEFAULT NULL,
  `Create_Date` datetime DEFAULT NULL,
  `Modify_Date` datetime DEFAULT NULL,
  `Create_User_Id` int DEFAULT NULL,
  `Modify_User_Id` int DEFAULT NULL,
  `Active_Flag` int DEFAULT '1',
  `Enable_Flag` int DEFAULT NULL,
  `user_role_id` int DEFAULT NULL,
  PRIMARY KEY (`User_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'ram','test123','Ram Choudhary','Developer','IT',1,'ramc@test.com',NULL,'2025-10-31 19:53:01',NULL,NULL,NULL,1,1,2),(2,'ram2','test123','Ram Choudhary','Developer','IT',1,'ramc@test.com',NULL,'2025-10-31 19:53:20',NULL,NULL,NULL,1,1,2),(3,'ramtest','$2a$10$izCtK7oNj0NJS26OTB0FVO0CEUs.VlR5EX.gAO2QDNC/u0HaTDKaC','Ram Choudhary','Developer','IT',NULL,NULL,NULL,'2025-11-01 13:29:29',NULL,NULL,NULL,1,1,NULL),(4,'ramtest2','$2a$10$rwdEzWXGn2W9CTXue6qjN.kjwYzyVwZdDZx60cee77hVVopFZM.WG','Ram Choudhary','Developer','IT',NULL,NULL,NULL,'2025-11-01 13:58:49',NULL,NULL,NULL,1,1,NULL),(5,'ram6','$2a$10$OULlFJEZ471.L0fxFcgwR.7lV1Yg/QyX7eGnTg8iAjiIqgc3d8JFi','Ram Choudhary','Developer','IT',1,'ramc@test.com',NULL,'2025-11-03 01:44:42',NULL,NULL,NULL,1,1,2),(6,'ram7','$2a$10$mYOI.jLvOUMVCyKaHcQZpuKJL1VXr9ZHvmmTuuXHKx0tfZPuVNngy','Ram Choudhary','Developer','IT',1,'ramc@test.com',NULL,'2025-11-03 11:20:59',NULL,NULL,NULL,1,1,2),(7,'ram8','$2a$10$7853wsIuT4oKqJHm84DiLuxY/0A55RLdInA6nigBiRWQLmUARa02S','Ram Choudhary','Developer','IT',1,'ramc@test.com',NULL,'2025-11-05 14:26:12',NULL,NULL,NULL,1,1,2),(8,'ram8','$2a$10$dtGx2ZQrwfB0kAWpNSb.heR1FhrNVFz5vrpqQZiS3dJKOzFnrmtHq','Ram Choudhary','Developer','IT',1,'ramc@test.com',NULL,'2025-11-05 14:26:19',NULL,NULL,NULL,1,1,2),(9,'ram2','$2a$10$m.qC4X.EygkgwJ6UBYvY5OT7hGsOhQ2TKzXVipejwCW.5YvDDHyc6','Ram Choudhary','Developer','IT',1,'ramc@test.com',NULL,'2025-11-05 14:43:57',NULL,NULL,NULL,1,1,2),(10,'ram2','$2a$10$HbASlVIXAq4rtjb3bfO3BO3oXHJgL2poSsbcVmhXd6FHh5knspq9u','Ram Choudhary','Developer','IT',1,'ramc@test.com',NULL,'2025-11-05 14:44:00',NULL,NULL,NULL,1,1,2),(11,'ram2','$2a$10$gOBGlCkH64r2lLVpperKKOxb4ICvjKIcTdwKkmfW/5zpgut5AWaA.','Ram Choudhary','Developer','IT',1,'ramc@test.com',NULL,'2025-11-05 14:44:04',NULL,NULL,NULL,1,1,2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'restaurant_inventory'
--

--
-- Dumping routines for database 'restaurant_inventory'
--

--
-- Final view structure for view `recipe_ingredient_view`
--

/*!50001 DROP VIEW IF EXISTS `recipe_ingredient_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `recipe_ingredient_view` AS select `rmx`.`recipe_xref_id` AS `recipe_xref_id`,`rm`.`recipe_id` AS `recipe_id`,`rm`.`recipe_code` AS `recipe_code`,`rm`.`recipe_name` AS `recipe_name`,`rm`.`recipe_type` AS `recipe_type`,`im`.`ingredient_id` AS `ingredient_id`,`im`.`ingredient_name` AS `ingredient_name`,`rmx`.`uom` AS `uom`,`rmx`.`smaller_unit` AS `smaller_unit`,`rmx`.`base_unit_value` AS `base_unit_value`,`rmx`.`quantity_value` AS `quantity_value`,`rmx`.`remarks` AS `remarks`,`cb`.`User_Id` AS `created_by_id`,`cb`.`user_name` AS `created_by_name`,`rmx`.`created_date` AS `created_date`,`mb`.`User_Id` AS `modified_by_id`,`mb`.`user_name` AS `modified_by_name`,`rmx`.`modified_date` AS `modified_date`,`rmx`.`active_flag` AS `active_flag`,`rmx`.`enable_flag` AS `enable_flag` from ((((`recipe_master_ingredient_xref` `rmx` join `recipe_master` `rm` on((`rmx`.`recipe_id` = `rm`.`recipe_id`))) join `ingredients_master` `im` on((`rmx`.`ingredient_id` = `im`.`ingredient_id`))) left join `user` `cb` on((`rmx`.`created_by` = `cb`.`User_Id`))) left join `user` `mb` on((`rmx`.`modified_by` = `mb`.`User_Id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-14 12:44:40
