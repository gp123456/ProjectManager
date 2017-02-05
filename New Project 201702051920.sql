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
  `rfq_flag` bit(1) NOT NULL DEFAULT b'0',
  `note` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill_material_service`
--

LOCK TABLES `bill_material_service` WRITE;
/*!40000 ALTER TABLE `bill_material_service` DISABLE KEYS */;
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
  `price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill_material_service_item`
--

LOCK TABLES `bill_material_service_item` WRITE;
/*!40000 ALTER TABLE `bill_material_service_item` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'13671/GP','Create'),(2,'13672/GP','Create'),(3,'13673/GP','Create');
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
  `reference` varchar(20) NOT NULL,
  `expired` datetime NOT NULL,
  `expiredCreate` datetime NOT NULL,
  `company` varchar(45) NOT NULL,
  `customer` varchar(45) NOT NULL,
  `vessel` bigint(20) unsigned DEFAULT NULL,
  `vesselName` varchar(1024) DEFAULT NULL,
  `contact` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_detail`
--

LOCK TABLES `project_detail` WRITE;
/*!40000 ALTER TABLE `project_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quotation`
--

DROP TABLE IF EXISTS `quotation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quotation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `complete` bit(1) NOT NULL DEFAULT b'0',
  `discard` bit(1) NOT NULL DEFAULT b'1',
  `bill_material_service` bigint(20) NOT NULL,
  `customer` varchar(100) NOT NULL,
  `customer_reference` varchar(45) NOT NULL,
  `currency` int(11) NOT NULL,
  `availability` varchar(100) DEFAULT '0',
  `delivery` varchar(100) DEFAULT '0',
  `packing` varchar(100) DEFAULT '0',
  `payment` varchar(100) DEFAULT '0',
  `validity` varchar(100) DEFAULT '0',
  `location` int(11) NOT NULL DEFAULT '0',
  `grand_total` decimal(10,2) DEFAULT '0.00',
  `welcome` varchar(1024) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `note` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `request_for_quotation_UNIQUE` (`bill_material_service`,`location`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quotation`
--

LOCK TABLES `quotation` WRITE;
/*!40000 ALTER TABLE `quotation` DISABLE KEYS */;
/*!40000 ALTER TABLE `quotation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quotation_item`
--

DROP TABLE IF EXISTS `quotation_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quotation_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `quotation` bigint(20) NOT NULL,
  `bill_material_service_item` bigint(20) NOT NULL,
  `discount` decimal(3,1) NOT NULL DEFAULT '0.0',
  `unit_price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `total` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quotation_item`
--

LOCK TABLES `quotation_item` WRITE;
/*!40000 ALTER TABLE `quotation_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `quotation_item` ENABLE KEYS */;
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
  `discard` bit(1) NOT NULL DEFAULT b'0',
  `bill_material_service` bigint(20) NOT NULL,
  `supplier` varchar(256) NOT NULL,
  `currency` int(11) NOT NULL,
  `material_cost` decimal(10,2) DEFAULT NULL,
  `grand_total` decimal(10,2) DEFAULT NULL,
  `delivery_cost` decimal(10,2) DEFAULT NULL,
  `other_expenses` decimal(10,2) DEFAULT NULL,
  `note` varchar(1024) DEFAULT NULL,
  `supplier_note` varchar(1024) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request_quotation`
--

LOCK TABLES `request_quotation` WRITE;
/*!40000 ALTER TABLE `request_quotation` DISABLE KEYS */;
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
  `unit_price` decimal(10,2) DEFAULT NULL,
  `discount` decimal(3,1) DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  `bill_material_service_item` bigint(20) NOT NULL,
  `request_quotation` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request_quotation_item`
--

LOCK TABLES `request_quotation_item` WRITE;
/*!40000 ALTER TABLE `request_quotation_item` DISABLE KEYS */;
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

-- Dump completed on 2017-02-05 19:20:19
