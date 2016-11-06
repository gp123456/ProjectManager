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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill_material_service`
--

LOCK TABLES `bill_material_service` WRITE;
/*!40000 ALTER TABLE `bill_material_service` DISABLE KEYS */;
INSERT INTO `bill_material_service` VALUES (1,'test10006/MK/2',3,'','test 10006/MK/2'),(2,'test10006/MK/1',2,'','test 10006/MK/1'),(3,'test13617/GP/1',1,'','test 13617/GP/1'),(4,'test13621/GP/1',7,'','test 13621/GP/1'),(5,'test13620/GP/1',6,'','test 13620/GP/1'),(6,'test13619/GP/1',5,'','test 13619/GP/1'),(7,'test13618/GP/1',4,'','test 13618/GP/1'),(8,'test13622/GP/1',8,'','test 13622/GP/1'),(9,'test13623',9,'','test 13623'),(10,'test',10,'','test'),(11,'MGPS-Anodes-to-Italy',11,'',''),(12,'13626/GP/1-ADMIBROS SHIPMANAGEMENT',12,'','test name'),(13,'13627/GP/1-AEGEAN BUNKERING SERVICES INC',13,'',''),(14,'13628/GP/1-AEGEAN BULK',14,'',''),(15,'13628/GP/2-AEGEAN BULK',15,'',''),(16,'13629/GP/2-ALLSEAS MARINE',17,'',''),(17,'13629/GP/1-ALLSEAS MARINE',16,'',''),(18,'13630/GP/2-ALMI MARINE MANAGEMENT S.A.',19,'',''),(19,'13630/GP/1-ALMI MARINE MANAGEMENT S.A.',18,'',''),(20,'13631/GP/1-ALMI MARINE MANAGEMENT S.A.',20,'',''),(21,'13632/GP/1-ADMIBROS SHIPMANAGEMENT',21,'',''),(22,'13635/GP/1-ACTECH',24,'','test 13635/GP/1');
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
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill_material_service_item`
--

LOCK TABLES `bill_material_service_item` WRITE;
/*!40000 ALTER TABLE `bill_material_service_item` DISABLE KEYS */;
INSERT INTO `bill_material_service_item` VALUES (1,2,119,4,394.36,5),(2,2,141,0,0.00,10),(3,2,155,1,544.58,15),(4,3,113,10,296.41,5),(5,3,118,0,0.00,10),(6,3,149,0,794.82,15),(7,4,114,2,212.64,10),(8,4,119,4,394.36,10),(9,4,138,0,0.00,1),(10,5,114,2,212.64,5),(11,5,118,0,0.00,10),(12,5,138,0,0.00,15),(13,6,135,0,238.50,5),(14,6,155,1,544.58,10),(15,6,243,0,304.00,15),(16,7,112,-2,184.82,5),(17,7,119,4,394.36,10),(18,7,134,0,137.70,15),(19,8,113,10,296.41,5),(20,8,116,171,13.49,10),(21,8,135,0,238.50,15),(22,9,119,4,394.36,5),(23,9,134,0,137.70,10),(24,9,149,0,794.82,15),(25,10,141,0,0.00,5),(26,10,119,4,394.36,10),(27,11,113,10,296.41,2),(28,11,119,4,394.36,2),(29,12,113,10,296.41,1),(30,12,135,0,238.50,3),(31,12,226,0,150.00,5),(32,13,121,0,558.22,2),(33,13,149,0,794.82,2),(34,14,155,1,544.58,2),(35,14,184,1,4157.29,4),(36,14,215,1,255.96,5),(37,14,277,1,603.72,4),(38,16,116,171,13.49,20),(39,16,140,35,154.06,10),(40,17,155,1,544.58,1),(41,17,164,2,569.82,1),(42,18,113,10,296.41,5),(43,18,140,35,154.06,10),(44,19,155,1,544.58,1),(45,19,309,3,850.00,1),(47,21,115,0,0.00,4),(48,21,134,0,137.70,5),(49,22,135,0,238.50,5),(50,22,150,0,536.95,10),(51,22,228,0,75.00,15);
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
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'13617/GP','Request for Quotation'),(2,'10006/MK','Request for Quotation'),(3,'13618/GP','Request for Quotation'),(4,'13619/GP','Request for Quotation'),(5,'13620/GP','Request for Quotation'),(6,'13621/GP','Request for Quotation'),(7,'13622/GP','Quotation'),(8,'13623/GP','Request for Quotation'),(9,'13624/GP','Quotation'),(10,'13625/GP','Request for Quotation'),(11,'13626/GP','Quotation'),(12,'13627/GP','Request for Quotation'),(13,'13628/GP','Quotation'),(14,'13629/GP','Quotation'),(15,'13630/GP','Create'),(16,'13631/GP','Create'),(17,'13632/GP','Create'),(18,'13633/GP','Create'),(19,'13634/GP','Create'),(20,'13635/GP','Bill of Materials or Services'),(21,'13636/GP','Create'),(22,'13637/GP','Create'),(23,'13638/GP','Create'),(24,'13639/GP','Create');
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
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_detail`
--

LOCK TABLES `project_detail` WRITE;
/*!40000 ALTER TABLE `project_detail` DISABLE KEYS */;
INSERT INTO `project_detail` VALUES (1,1,'Request for Quotation','SALE',7,'2016-08-10 23:18:58','2016-11-16 00:00:00','MARPO',65,'AEGEAN BULK',160,'13617/GP/1','AFOVOS'),(2,2,'Request for Quotation','SALE',4,'2016-08-10 23:21:17','2016-10-27 00:00:00','WCS LTD',189,'ALPHA TANKERS AND FREIGHTERS',139,'10006/MK/1','ALPHA ACTION'),(3,2,'Request for Quotation','SERVICE',7,'2016-08-15 01:32:27','2017-02-09 00:00:00','WCS HELLAS',189,'ALPHA TANKERS AND FREIGHTERS',139,'10006/MK/2',NULL),(4,3,'Request for Quotation','SALE',7,'2016-08-20 16:53:44','2016-09-19 00:00:00','MARPO',631,'ALLSEAS MARINE',215,'13618/GP/1','BLUE SEAS'),(5,4,'Request for Quotation','SALE',7,'2016-08-20 19:01:24','2016-09-19 00:00:00','WCS LTD',631,'ALLSEAS MARINE',215,'13619/GP/1','ASALI'),(6,5,'Request for Quotation','SALE',7,'2016-08-21 00:42:41','2016-09-20 00:00:00','WCS HELLAS',3995,'ACTECH',340,'13620/GP/1','Test-ACTECH'),(7,6,'Request for Quotation','SALE',7,'2016-08-21 15:28:32','2016-09-20 00:00:00','MTS',3080,'ASTRON MARITIME CO.',203,'13621/GP/1','PHOENIX ALPHA'),(8,7,'Quotation','SALE',7,'2016-08-24 01:55:02','2016-09-23 00:00:00','MARPO',631,'ALLSEAS MARINE',215,'13622/GP/1','BLUE SEAS'),(9,8,'Request for Quotation','SALE',7,'2016-08-25 15:46:31','2016-09-24 00:00:00','WCS LTD',1938,'ADMIBROS SHIPMANAGEMENT',180,'13623/GP/1','KALIA'),(10,9,'Quotation','SALE',7,'2016-08-25 17:48:17','2016-09-24 00:00:00','MARPO',1938,'ADMIBROS SHIPMANAGEMENT',180,'13624/GP/1','KALIA'),(11,10,'Request for Quotation','SALE',7,'2016-08-26 16:36:11','2016-09-25 00:00:00','MTS',1803,'AEGEAN BULK',160,'13625/GP/1','INFINITY'),(12,11,'Quotation','SALE',7,'2016-08-30 01:46:24','2016-09-29 00:00:00','MARPO',1938,'ADMIBROS SHIPMANAGEMENT',180,'13626/GP/1','KALIA'),(13,12,'Request for Quotation','SALE',7,'2016-09-01 17:23:43','2016-10-31 00:00:00','MARPO',2602,'AEGEAN BUNKERING SERVICES INC',342,'13627/GP/1','MYKONOS'),(14,13,'Quotation','SALE',7,'2016-09-28 00:25:20','2016-10-31 00:00:00','MARPO',65,'AEGEAN BULK',160,'13628/GP/1','AFOVOS'),(15,13,'Request for Quotation','SERVICE',7,'2016-09-28 00:25:49','2016-10-31 00:00:00','MTS',65,'AEGEAN BULK',160,'13628/GP/2',NULL),(16,14,'Quotation','SALE',7,'2016-09-28 00:53:20','2016-10-28 00:00:00','WCS HELLAS',631,'ALLSEAS MARINE',215,'13629/GP/1','BLUE SEAS'),(17,14,'Request for Quotation','SERVICE',7,'2016-09-28 01:01:16','2016-12-22 00:00:00','MARPO',108,'ALMI MARINE MANAGEMENT S.A.',247,'13629/GP/2',NULL),(18,15,'Bill of Materials or Services','SALE',7,'2016-09-28 01:20:53','2016-10-28 00:00:00','MARPO',108,'ALMI MARINE MANAGEMENT S.A.',247,'13630/GP/1','AKIBA'),(19,15,'Bill of Materials or Services','SALE',7,'2016-09-28 01:21:23','2018-04-11 00:00:00','WCS LTD',108,'ALMI MARINE MANAGEMENT S.A.',247,'13630/GP/2',NULL),(20,16,'Bill of Materials or Services','SALE',7,'2016-10-06 13:05:25','2016-11-05 00:00:00','MARPO',108,'ALMI MARINE MANAGEMENT S.A.',247,'13631/GP/1','AKIBA'),(21,17,'Bill of Materials or Services','SALE',7,'2016-10-06 13:27:40','2016-11-05 00:00:00','WCS LTD',1938,'ADMIBROS SHIPMANAGEMENT',180,'13632/GP/1','KALIA'),(22,18,'Create','SALE',7,'2016-10-06 13:35:41','2016-11-05 00:00:00','WCS HELLAS',108,'ALMI MARINE MANAGEMENT S.A.',247,'13633/GP/1','AKIBA'),(23,19,'Create','SALE',7,'2016-10-06 13:40:44','2016-11-05 00:00:00','MTS',1938,'ADMIBROS SHIPMANAGEMENT',180,'13634/GP/1','KALIA'),(24,20,'Request for Quotation','SALE',7,'2016-10-06 13:44:29','2016-11-05 00:00:00','MARPO',3995,'ACTECH',340,'13635/GP/1','Test-ACTECH'),(25,21,'Create','SERVICE',7,'2016-10-06 13:47:57','2016-11-05 00:00:00','WCS LTD',108,'ALMI MARINE MANAGEMENT S.A.',247,'13636/GP/1','AKIBA'),(26,22,'Create','SERVICE',7,'2016-10-06 13:51:00','2016-11-05 00:00:00','WCS HELLAS',3995,'ACTECH',340,'13637/GP/1','Test-ACTECH'),(27,23,'Create','SERVICE',7,'2016-10-06 13:51:41','2016-11-05 00:00:00','WCS HELLAS',3995,'ACTECH',340,'13638/GP/1','Test-ACTECH'),(28,24,'Create','SERVICE',7,'2016-10-06 13:52:38','2016-11-05 00:00:00','MTS',189,'ALPHA TANKERS AND FREIGHTERS',139,'13639/GP/1','ALPHA ACTION'),(29,16,'Create','SERVICE',7,'2016-10-06 15:24:16','2016-05-11 00:00:00','WCS LTD',108,'ALMI MARINE MANAGEMENT S.A.',247,'13631/GP/2',NULL),(30,17,'Create','SERVICE',7,'2016-10-06 16:10:23','2018-06-11 00:00:00','WCS HELLAS',1938,'ADMIBROS SHIPMANAGEMENT',180,'13632/GP/2',NULL);
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
  `name` varchar(100) DEFAULT NULL,
  `complete` bit(1) NOT NULL DEFAULT b'0',
  `discard` bit(1) NOT NULL DEFAULT b'1',
  `request_quotation` bigint(20) NOT NULL,
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
  UNIQUE KEY `request_for_quotation_UNIQUE` (`request_quotation`,`location`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quotation`
--

LOCK TABLES `quotation` WRITE;
/*!40000 ALTER TABLE `quotation` DISABLE KEYS */;
INSERT INTO `quotation` VALUES (2,'13629/GP/1-ALLSEAS MARINE','\0','',32,'ALLSEAS MARINE','0447808',5,'3 days from receipt of order','To any place required (if handled by us) to be charged extra at absolute cost','Packed','30 days from the invoicing day','2 weeks from this offer',1,3.86,'Good day Mr. Foteinopoulos ,\n\n\n\nMany thanks for your inquiry.\nWe are pleased to offer for MGPS of vessel as requested.','Kindly note that prices ex China are in USD\n\n\nALL ITEMS OFFERED ARE ASBESTOS-FREE ACCORDING TO SOLAS','For any addittional information you may require please do not hesitate to contact us.\n\n\n\nWith Best Regards\nMarpo Group\nYiannis Vourlidis'),(3,'13629/GP/1-ALLSEAS MARINE','','\0',32,'ALLSEAS MARINE','0447808',4,'3 days from receipt of order','To any place required (if handled by us) to be charged extra at absolute cost','Packed','30 days from the invoicing day','2 weeks from this offer',2,11.14,'Good day Mr. Foteinopoulos ,\n\n\n\nMany thanks for your inquiry.\nWe are pleased to offer for MGPS of vessel as requested.','Kindly note that prices ex China are in USD\n\n\nALL ITEMS OFFERED ARE ASBESTOS-FREE ACCORDING TO SOLAS','For any addittional information you may require please do not hesitate to contact us.\n\n\n\nWith Best Regards\nMarpo Group\nYiannis Vourlidis'),(4,'13628/GP/1-AEGEAN BULK','\0','',30,'AEGEAN BULK','231508',4,'3 day','2 day','Packed','Soon','12 days',1,16.36,'Hello George Patitaki,\n\nSent you the offer of MTS of vessel AFOVOS','',''),(5,'13626/GP/1-ADMIBROS SHIPMANAGEMENT','\0','',29,'ADMIBROS SHIPMANAGEMENT','10223344',5,'test availability','test delivery','test packing','test peyment','test validity',1,98.77,'test welcome','test remark','test note'),(6,'13624/GP/1-ADMIBROS SHIPMANAGEMENT','\0','',20,'ADMIBROS SHIPMANAGEMENT','1',5,'4','5','6','7','',1,28.70,'2','3','8'),(7,'13622/GP/1-ALLSEAS MARINE','\0','',35,'ALLSEAS MARINE','1',5,'8','9','10','11','12',1,104.20,'2','7','13');
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
  `request_quotation_item` bigint(20) NOT NULL,
  `discount` decimal(3,1) NOT NULL DEFAULT '0.0',
  `unit_price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `total` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quotation_item`
--

LOCK TABLES `quotation_item` WRITE;
/*!40000 ALTER TABLE `quotation_item` DISABLE KEYS */;
INSERT INTO `quotation_item` VALUES (3,2,60,2.0,1.00,0.98),(4,2,61,4.0,3.00,2.88),(5,3,60,6.0,5.00,4.70),(6,3,61,8.0,7.00,6.44),(7,2,60,5.0,40.00,35.00),(8,2,61,20.0,100.00,80.00),(9,2,60,10.0,100.00,90.00),(10,3,60,10.0,200.00,180.00),(11,3,60,10.0,200.00,180.00),(12,3,61,35.0,200.00,165.00),(13,4,56,2.0,1.00,1.96),(14,4,57,4.0,3.00,14.40),(15,5,53,2.0,5.00,4.90),(16,5,54,3.0,7.00,20.37),(17,5,55,2.0,15.00,73.50),(18,6,36,5.0,2.00,9.50),(19,6,37,4.0,2.00,19.20),(20,7,68,2.0,1.00,4.90),(21,7,69,4.0,3.00,28.80),(22,7,70,6.0,5.00,70.50);
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
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request_quotation`
--

LOCK TABLES `request_quotation` WRITE;
/*!40000 ALTER TABLE `request_quotation` DISABLE KEYS */;
INSERT INTO `request_quotation` VALUES (5,'','\0',6,'ANTI-CORR ROUL CO.',3,23.75,223.75,100.00,100.00,'test 13619/GP/1','test 13619/GP/1','test13619/GP/1'),(9,'','\0',6,'BOSS AUTOMATION EPE',4,90.00,490.00,150.00,250.00,'test 3345523','test 3345523','test3345523'),(10,'','\0',6,'CP TECH FAR EAST LIMITED',5,71.25,271.25,100.00,100.00,'test 44212','test 44212','13619/GP/1-CP TECH FAR EAST LIMITED'),(11,'','\0',7,'ANTI-CORR ROUL CO.',3,22.50,322.50,100.00,200.00,'test 13681/GP/1 AL100x400','test 13681/GP/1 AL100x400','test13681/GP/1-AL100x400'),(12,'','\0',7,'CHOUDALAKIS DIMITRIOS',4,46.75,496.75,200.00,250.00,'test 13618/GP/1 CU100x400','test 13618/GP/1 CU100x400','test13618/GP/1-CU100x400'),(13,'','\0',7,'CP TECH FAR EAST LIMITED',5,189.00,889.00,300.00,400.00,'test 13618/GP/1 All','test 13618/GP/1 All','test13618/GP/1-All'),(18,'','\0',9,'BOSS AUTOMATION EPE',3,305.00,505.00,103.00,104.00,'test 13623 first','test 13623 second','test13623'),(19,'','\0',9,'CHOUDALAKIS DIMITRIOS',4,283.75,483.75,100.00,102.00,'test 13623','test 13623 111','13623/GP/1-CHOUDALAKIS DIMITRIOS'),(20,'','\0',10,'ANTI-CORR ROUL CO.',3,113.75,413.75,100.00,200.00,'test','test',''),(21,'','\0',11,'CORROSERV_UK',4,28.45,329.45,200.50,100.50,'Please mention shipping cost to livorno','','Corroserv'),(22,'','\0',11,'CCEL',4,19.00,219.00,100.00,100.00,'Please advise shipping cost to livorno Italy\nI LIKE','COPPER ANODES WILL BE 2CM SHORTER','13625/GP/1-CCEL'),(23,'','',12,'CORROCO INTERNATIONAL INDUSTRIAL CO  LTD',3,88.00,288.00,100.00,100.00,'test name','test name','13626/GP/1-CORROCO INTERNATIONAL INDUSTRIAL CO  LTD'),(24,'','',13,'CORROSERV_UK',4,NULL,NULL,NULL,NULL,'dvsdv',NULL,'13627/GP/1-CORROSERV_UK'),(25,'','',13,'CORRPRO COMPANIES EUROPE LTD.',4,NULL,NULL,NULL,NULL,' c c',NULL,'13627/GP/1-CORRPRO COMPANIES EUROPE LTD.'),(26,'','',13,'CATHODIC PROTECTION TECHNOLOGY PTE. LTD.',4,20.09,220.59,100.00,100.50,'vvfgfgf bhvghvbh','fggfg bhvbvbv','13627/GP/1-CATHODIC PROTECTION TECHNOLOGY PTE. LTD.'),(27,'','',13,'CORRPRO BAHRAIN',2,NULL,NULL,NULL,NULL,'test digits',NULL,'13627/GP/1-CORRPRO BAHRAIN'),(28,'','',13,'ANTI-CORR ROUL CO.',3,NULL,NULL,NULL,NULL,'',NULL,'13627/GP/1-ANTI-CORR ROUL CO.'),(29,'','\0',12,'ANTI-CORR ROUL CO.',4,108.95,419.95,100.46,210.55,'get offer from ANTI-CORR ROUL CO.','','13626/GP/1-ANTI-CORR ROUL CO.'),(30,'','\0',14,'CIS ELEKTROTECHNIK GMBH',4,2454.00,2954.00,200.00,300.00,'','','13628/GP/1-CIS ELEKTROTECHNIK GMBH'),(31,'','\0',16,'ALMA SHIPMANAGEMENT TRADING',4,394.00,664.00,150.00,120.00,'','','13629/GP/2-ALMA SHIPMANAGEMENT TRADING'),(32,'','\0',17,'CIS ELEKTROTECHNIK GMBH',4,670.50,1220.50,250.00,300.00,'test 32','test 32','13629/GP/1-CIS ELEKTROTECHNIK GMBH'),(33,'','',8,'none',4,NULL,NULL,NULL,NULL,'Test request for quotation',NULL,'13622/GP/1-none'),(34,'','',8,'none',4,NULL,NULL,NULL,NULL,'',NULL,'13622/GP/1-none'),(35,'','\0',8,'CATHODIC PROTECTION TECHNOLOGY PTE. LTD.',4,2375.00,2675.00,100.00,200.00,'','test of request for quotation','13622/GP/1-CATHODIC PROTECTION TECHNOLOGY PTE. LTD.'),(36,'','',22,'ANTI-CORR ROUL CO.',4,NULL,NULL,NULL,NULL,'test 13635/GP/1',NULL,'13635/GP/1-ANTI-CORR ROUL CO.'),(37,'','',22,'BOSS AUTOMATION EPE',3,NULL,NULL,NULL,NULL,'',NULL,'13635/GP/1-BOSS AUTOMATION EPE'),(38,'','',22,'CATHODIC PROTECTION TECHNOLOGY PTE. LTD.',3,NULL,NULL,NULL,NULL,'',NULL,'13635/GP/1-CATHODIC PROTECTION TECHNOLOGY PTE. LTD.'),(39,'','',22,'CATHODIC PROTECTION TECHNOLOGY PTE. LTD.',4,NULL,NULL,NULL,NULL,'',NULL,'13635/GP/1-CATHODIC PROTECTION TECHNOLOGY PTE. LTD.'),(40,'','',22,'CHOUDALAKIS DIMITRIOS',3,NULL,NULL,NULL,NULL,'',NULL,'13635/GP/1-CHOUDALAKIS DIMITRIOS'),(41,'','',22,'ANTI-CORR ROUL CO.',3,NULL,NULL,NULL,NULL,'',NULL,'13635/GP/1-ANTI-CORR ROUL CO.'),(42,'','',22,'BOSS AUTOMATION EPE',4,NULL,NULL,NULL,NULL,'',NULL,'13635/GP/1-BOSS AUTOMATION EPE'),(43,'','',22,'CCEL',3,NULL,NULL,NULL,NULL,'',NULL,'13635/GP/1-CCEL'),(44,'','',22,'CCEL',4,NULL,NULL,NULL,NULL,'',NULL,'13635/GP/1-CCEL'),(45,'','',22,'CORROCO INTERNATIONAL INDUSTRIAL CO  LTD',4,NULL,NULL,NULL,NULL,'',NULL,'13635/GP/1-CORROCO INTERNATIONAL INDUSTRIAL CO  LTD');
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
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request_quotation_item`
--

LOCK TABLES `request_quotation_item` WRITE;
/*!40000 ALTER TABLE `request_quotation_item` DISABLE KEYS */;
INSERT INTO `request_quotation_item` VALUES (1,NULL,NULL,NULL,NULL,1,1),(2,NULL,NULL,NULL,NULL,2,1),(3,NULL,NULL,NULL,NULL,3,1),(4,1,5.00,5.0,23.75,4,2),(5,1,10.00,10.0,90.00,5,2),(6,1,15.00,15.0,191.25,6,2),(7,NULL,NULL,NULL,NULL,7,3),(8,1,5.50,5.0,26.13,10,4),(9,1,5.00,5.0,23.75,13,5),(10,NULL,NULL,NULL,NULL,13,7),(11,10,10.00,10.0,90.00,14,9),(12,5,5.00,5.0,71.25,15,10),(13,5,5.00,10.0,22.50,16,11),(14,1,5.50,15.0,46.75,17,12),(15,1,3.00,10.0,13.50,16,13),(16,1,6.00,10.0,54.00,17,13),(17,1,9.00,10.0,121.50,18,13),(18,NULL,NULL,NULL,NULL,22,14),(19,NULL,NULL,NULL,NULL,23,14),(20,NULL,NULL,NULL,NULL,24,14),(21,NULL,NULL,NULL,NULL,22,15),(22,NULL,NULL,NULL,NULL,23,15),(23,NULL,NULL,NULL,NULL,24,15),(24,NULL,NULL,NULL,NULL,22,16),(25,NULL,NULL,NULL,NULL,23,16),(26,NULL,NULL,NULL,NULL,24,16),(27,NULL,NULL,NULL,NULL,22,17),(28,NULL,NULL,NULL,NULL,23,17),(29,NULL,NULL,NULL,NULL,24,17),(30,5,5.00,5.0,23.75,22,18),(31,5,10.00,10.0,90.00,23,18),(32,5,15.00,15.0,191.25,24,18),(33,1,10.00,10.0,45.00,22,19),(34,1,5.00,5.0,47.50,23,19),(35,1,15.00,15.0,191.25,24,19),(36,1,5.00,5.0,23.75,25,20),(37,1,10.00,10.0,90.00,26,20),(38,1,5.50,5.0,10.45,27,21),(39,1,10.00,10.0,18.00,28,21),(40,5,5.00,5.0,9.50,27,22),(41,5,5.00,5.0,9.50,28,22),(42,1,5.00,5.0,4.75,29,23),(43,1,10.00,10.0,27.00,30,23),(44,2,15.00,25.0,56.25,31,23),(45,NULL,NULL,NULL,NULL,32,24),(46,NULL,NULL,NULL,NULL,33,24),(47,NULL,NULL,NULL,NULL,32,25),(48,NULL,NULL,NULL,NULL,33,25),(49,1,10.25,2.0,20.09,32,26),(50,NULL,NULL,NULL,NULL,32,27),(51,NULL,NULL,NULL,NULL,33,27),(52,NULL,NULL,NULL,NULL,32,28),(53,1,5.56,3.5,5.37,29,29),(54,1,10.24,2.4,29.99,30,29),(55,1,15.57,5.5,73.60,31,29),(56,5,230.00,10.0,414.00,34,30),(57,5,510.00,20.0,2040.00,36,30),(58,1,10.00,1.0,198.00,38,31),(59,2,20.00,2.0,196.00,39,31),(60,3,320.00,10.0,288.00,40,32),(61,3,450.00,15.0,382.50,41,32),(62,NULL,NULL,NULL,NULL,19,33),(63,NULL,NULL,NULL,NULL,20,33),(64,NULL,NULL,NULL,NULL,21,33),(65,NULL,NULL,NULL,NULL,19,34),(66,NULL,NULL,NULL,NULL,20,34),(67,NULL,NULL,NULL,NULL,21,34),(68,1,10.00,5.0,47.50,19,35),(69,1,20.00,5.0,190.00,20,35),(70,10,150.00,5.0,2137.50,21,35),(71,NULL,NULL,NULL,NULL,49,36),(72,NULL,NULL,NULL,NULL,50,36),(73,NULL,NULL,NULL,NULL,51,36),(74,NULL,NULL,NULL,NULL,49,37),(75,NULL,NULL,NULL,NULL,50,37),(76,NULL,NULL,NULL,NULL,51,37),(77,NULL,NULL,NULL,NULL,49,38),(78,NULL,NULL,NULL,NULL,50,38),(79,NULL,NULL,NULL,NULL,51,38),(80,NULL,NULL,NULL,NULL,49,39),(81,NULL,NULL,NULL,NULL,50,39),(82,NULL,NULL,NULL,NULL,51,39),(83,NULL,NULL,NULL,NULL,49,40),(84,NULL,NULL,NULL,NULL,50,40),(85,NULL,NULL,NULL,NULL,51,40),(86,NULL,NULL,NULL,NULL,49,41),(87,NULL,NULL,NULL,NULL,50,41),(88,NULL,NULL,NULL,NULL,51,41),(89,NULL,NULL,NULL,NULL,49,42),(90,NULL,NULL,NULL,NULL,50,42),(91,NULL,NULL,NULL,NULL,51,42),(92,NULL,NULL,NULL,NULL,49,43),(93,NULL,NULL,NULL,NULL,50,43),(94,NULL,NULL,NULL,NULL,51,43),(95,NULL,NULL,NULL,NULL,49,44),(96,NULL,NULL,NULL,NULL,50,44),(97,NULL,NULL,NULL,NULL,51,44),(98,NULL,NULL,NULL,NULL,49,45),(99,NULL,NULL,NULL,NULL,50,45),(100,NULL,NULL,NULL,NULL,51,45);
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

-- Dump completed on 2016-11-07  0:19:26
