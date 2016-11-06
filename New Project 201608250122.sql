-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: projectmanager
-- ------------------------------------------------------
-- Server version	5.7.13-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bill_material_service`
--

DROP TABLE IF EXISTS `bill_material_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bill_material_service` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `project` bigint(20) NOT NULL,
  `complete` bit(1) NOT NULL DEFAULT b'0',
  `note` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill_material_service`
--

LOCK TABLES `bill_material_service` WRITE;
/*!40000 ALTER TABLE `bill_material_service` DISABLE KEYS */;
INSERT INTO `bill_material_service` VALUES (1,'test10006/MK/2',3,'','test 10006/MK/2'),(2,'test10006/MK/1',2,'','test 10006/MK/1'),(3,'test13617/GP/1',1,'','test 13617/GP/1'),(4,'test13621/GP/1',7,'','test 13621/GP/1'),(5,'test13620/GP/1',6,'','test 13620/GP/1'),(6,'test13619/GP/1',5,'','test 13619/GP/1'),(7,'test13618/GP/1',4,'','test 13618/GP/1'),(8,'test13622/GP/1',8,'','test 13622/GP/1');
/*!40000 ALTER TABLE `bill_material_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bill_material_service_item`
--

DROP TABLE IF EXISTS `bill_material_service_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bill_material_service_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bill_material_service` bigint(20) NOT NULL,
  `item` bigint(20) NOT NULL,
  `available` int(11) NOT NULL DEFAULT '0',
  `price` double NOT NULL DEFAULT '0',
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill_material_service_item`
--

LOCK TABLES `bill_material_service_item` WRITE;
/*!40000 ALTER TABLE `bill_material_service_item` DISABLE KEYS */;
INSERT INTO `bill_material_service_item` VALUES (1,2,119,4,394.36,5),(2,2,141,0,0,10),(3,2,155,1,544.58,15),(4,3,113,10,296.41,5),(5,3,118,0,0,10),(6,3,149,0,794.82,15),(7,4,114,2,212.64,10),(8,4,119,4,394.36,10),(9,4,138,0,0,1),(10,5,114,2,212.64,5),(11,5,118,0,0,10),(12,5,138,0,0,15),(13,6,135,0,238.5,5),(14,6,155,1,544.58,10),(15,6,243,0,304,15),(16,7,112,-2,184.82,5),(17,7,119,4,394.36,10),(18,7,134,0,137.7,15),(19,8,113,10,296.41,5),(20,8,116,171,13.49,10),(21,8,135,0,238.5,15);
/*!40000 ALTER TABLE `bill_material_service_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reference` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'13617/GP','Request Quotation'),(2,'10006/MK','Request Quotation'),(3,'13618/GP','Request Quotation'),(4,'13619/GP','Request Quotation'),(5,'13620/GP','Request Quotation'),(6,'13621/GP','Request Quotation'),(7,'13622/GP','Bill of Material or Service');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_detail`
--

DROP TABLE IF EXISTS `project_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project` bigint(20) unsigned NOT NULL,
  `status` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `creator` bigint(20) unsigned NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `expired` datetime NOT NULL,
  `company` varchar(45) NOT NULL,
  `vessel` bigint(20) unsigned NOT NULL,
  `customer` varchar(45) NOT NULL,
  `contact` bigint(20) unsigned NOT NULL,
  `reference` varchar(20) NOT NULL,
  `vesselName` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_detail`
--

LOCK TABLES `project_detail` WRITE;
/*!40000 ALTER TABLE `project_detail` DISABLE KEYS */;
INSERT INTO `project_detail` VALUES (1,1,'Request Quotation','SALE',7,'2016-08-10 23:18:58','2016-11-16 00:00:00','MARPO',65,'AEGEAN BULK',160,'13617/GP/1','AFOVOS'),(2,2,'Request Quotation','SALE',4,'2016-08-10 23:21:17','2016-10-27 00:00:00','WCS LTD',189,'ALPHA TANKERS AND FREIGHTERS',139,'10006/MK/1','ALPHA ACTION'),(3,2,'Bill of Material or Service','SERVICE',7,'2016-08-15 01:32:27','2017-02-09 00:00:00','WCS HELLAS',189,'ALPHA TANKERS AND FREIGHTERS',139,'10006/MK/2',NULL),(4,3,'Request Quotation','SALE',7,'2016-08-20 16:53:44','2016-09-19 00:00:00','MARPO',631,'ALLSEAS MARINE',215,'13618/GP/1','BLUE SEAS'),(5,4,'Request Quotation','SALE',7,'2016-08-20 19:01:24','2016-09-19 00:00:00','WCS LTD',631,'ALLSEAS MARINE',215,'13619/GP/1','ASALI'),(6,5,'Request Quotation','SALE',7,'2016-08-21 00:42:41','2016-09-20 00:00:00','WCS HELLAS',3995,'ACTECH',340,'13620/GP/1','Test-ACTECH'),(7,6,'Request Quotation','SALE',7,'2016-08-21 15:28:32','2016-09-20 00:00:00','MTS',3080,'ASTRON MARITIME CO.',203,'13621/GP/1','PHOENIX ALPHA'),(8,7,'Bill of Material or Service','SALE',7,'2016-08-24 01:55:02','2016-09-23 00:00:00','MARPO',631,'ALLSEAS MARINE',215,'13622/GP/1','BLUE SEAS');
/*!40000 ALTER TABLE `project_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request_quotation`
--

DROP TABLE IF EXISTS `request_quotation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `request_quotation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `complete` bit(1) NOT NULL DEFAULT b'0',
  `bill_material_service` bigint(20) NOT NULL,
  `supplier` varchar(256) NOT NULL,
  `currency` int(11) NOT NULL,
  `material_cost` decimal(11,2) DEFAULT NULL,
  `grand_total` decimal(11,2) DEFAULT NULL,
  `delivery_cost` decimal(11,2) DEFAULT NULL,
  `other_expenses` decimal(11,2) DEFAULT NULL,
  `note` varchar(1024) DEFAULT NULL,
  `supplier_note` varchar(1024) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request_quotation`
--

LOCK TABLES `request_quotation` WRITE;
/*!40000 ALTER TABLE `request_quotation` DISABLE KEYS */;
INSERT INTO `request_quotation` VALUES (1,'\0',2,'ANTI-CORR ROUL CO.',4,NULL,NULL,NULL,NULL,'own test',NULL,NULL),(2,'\0',3,'BOSS AUTOMATION EPE',3,305.00,505.00,100.00,100.00,'test 13617/GP/1\nChange supplier','test 13617/GP/1',NULL),(3,'\0',4,'ANTI-CORR ROUL CO.',3,NULL,NULL,NULL,NULL,'test 13621/GP/1 AL ANODE 120X460',NULL,NULL),(4,'\0',5,'BOSS AUTOMATION EPE',3,26.13,276.13,150.00,100.00,'test 13620/GP/1\ni wand new offer12 45\nee rrfv','test 13620/GP/1\ni can only these prices',''),(5,'',6,'ANTI-CORR ROUL CO.',3,23.75,223.75,100.00,100.00,'test 13619/GP/1','test 13619/GP/1','test13619/GP/1'),(9,'',6,'BOSS AUTOMATION EPE',4,90.00,490.00,150.00,250.00,'test 3345523','test 3345523','test3345523'),(10,'',6,'CP TECH FAR EAST LIMITED',5,209.25,810.25,250.50,350.50,'test 44212','test 44212','test44212'),(11,'',7,'ANTI-CORR ROUL CO.',3,22.50,322.50,100.00,200.00,'test 13681/GP/1 AL100x400','test 13681/GP/1 AL100x400','test13681/GP/1-AL100x400'),(12,'',7,'CHOUDALAKIS DIMITRIOS',4,46.75,496.75,200.00,250.00,'test 13618/GP/1 CU100x400','test 13618/GP/1 CU100x400','test13618/GP/1-CU100x400'),(13,'',7,'CP TECH FAR EAST LIMITED',5,189.00,889.00,300.00,400.00,'test 13618/GP/1 All','test 13618/GP/1 All','test13618/GP/1-All');
/*!40000 ALTER TABLE `request_quotation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request_quotation_item`
--

DROP TABLE IF EXISTS `request_quotation_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `request_quotation_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `availability` int(11) DEFAULT NULL,
  `unit_price` double DEFAULT NULL,
  `discount` int(11) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `bill_material_service_item` bigint(20) NOT NULL,
  `request_quotation` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request_quotation_item`
--

LOCK TABLES `request_quotation_item` WRITE;
/*!40000 ALTER TABLE `request_quotation_item` DISABLE KEYS */;
INSERT INTO `request_quotation_item` VALUES (1,NULL,NULL,NULL,NULL,1,1),(2,NULL,NULL,NULL,NULL,2,1),(3,NULL,NULL,NULL,NULL,3,1),(4,1,5,5,23.75,4,2),(5,1,10,10,90,5,2),(6,1,15,15,191.25,6,2),(7,NULL,NULL,NULL,NULL,7,3),(8,1,5.5,5,26.125,10,4),(9,1,5,5,23.75,13,5),(10,NULL,NULL,NULL,NULL,13,7),(11,10,10,10,90,14,9),(12,5,15.5,10,209.25,15,10),(13,5,5,10,22.5,16,11),(14,1,5.5,15,46.75,17,12),(15,1,3,10,13.5,16,13),(16,1,6,10,54,17,13),(17,1,9,10,121.5,18,13);
/*!40000 ALTER TABLE `request_quotation_item` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-08-25  1:21:09
