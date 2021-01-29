-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: hospital-db.cowul8deiwyt.ap-south-1.rds.amazonaws.com    Database: hospital
-- ------------------------------------------------------
-- Server version	8.0.20

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '';

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `id` varchar(7) NOT NULL,
  `patient_id` varchar(7) DEFAULT NULL,
  `doctor_id` varchar(7) DEFAULT NULL,
  `date_scheduled` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pat_id_constraint` (`patient_id`),
  KEY `fk_doc_id_constraint` (`doctor_id`),
  CONSTRAINT `fk_doc_id_constraint` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`),
  CONSTRAINT `fk_pat_id_constraint` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES ('AP00001','PA00001','DO00005','2021-01-24 10:00:00'),('AP00002','PA00004','DO00002','2021-01-24 10:15:00'),('AP00003','PA00004','DO00004','2021-01-24 10:30:00'),('AP00004','PA00001','DO00005','2021-01-24 10:45:00'),('AP00005','PA00002','DO00005','2021-01-24 11:00:00'),('AP00006','PA00005','DO00004','2021-01-24 11:15:00'),('AP00007','PA00001','DO00001','2021-01-24 11:30:00'),('AP00008','PA00002','DO00002','2021-01-24 11:45:00'),('AP00009','PA00008','DO00004','2021-01-24 12:00:00'),('AP00010','PA00007','DO00001','2021-01-24 12:15:00'),('AP00011','PA00002','DO00002','2021-01-24 12:30:00'),('AP00012','PA00004','DO00002','2021-01-24 12:45:00'),('AP00013','PA00001','DO00001','2021-01-24 10:00:00'),('AP00014','PA00002','DO00002','2021-01-24 10:15:00'),('AP00015','PA00004','DO00002','2021-01-24 10:30:00'),('AP00016','PA00010','DO00001','2021-01-24 10:30:00'),('AP00017','PA00005','DO00005','2021-01-25 12:00:00'),('AP00018','PA00004','DO00002','2021-01-25 16:15:00'),('AP00019','PA00004','DO00004','2021-01-25 10:30:00'),('AP00020','PA00001','DO00005','2021-01-25 11:45:00'),('AP00021','PA00002','DO00005','2021-01-25 12:00:00'),('AP00022','PA00005','DO00004','2021-01-25 10:15:00'),('AP00023','PA00001','DO00001','2021-01-25 10:30:00'),('AP00024','PA00002','DO00002','2021-01-25 10:45:00'),('AP00025','PA00008','DO00004','2021-01-25 10:00:00'),('AP00026','PA00007','DO00001','2021-01-25 10:15:00'),('AP00027','PA00002','DO00002','2021-01-25 12:30:00'),('AP00028','PA00004','DO00002','2021-01-25 12:45:00'),('AP00029','PA00001','DO00001','2021-01-25 10:00:00'),('AP00030','PA00002','DO00002','2021-01-25 10:15:00'),('AP00031','PA00004','DO00002','2021-01-25 10:30:00'),('AP00032','PA00010','DO00001','2021-01-25 10:30:00'),('AP00033','PA00001','DO00005','2021-01-26 10:00:00'),('AP00034','PA00004','DO00002','2021-01-26 10:15:00'),('AP00035','PA00004','DO00004','2021-01-26 10:30:00'),('AP00036','PA00001','DO00005','2021-01-26 10:45:00'),('AP00037','PA00002','DO00005','2021-01-26 11:00:00'),('AP00038','PA00005','DO00004','2021-01-26 11:15:00'),('AP00039','PA00001','DO00001','2021-01-26 11:30:00'),('AP00040','PA00002','DO00002','2021-01-26 11:45:00'),('AP00041','PA00008','DO00004','2021-01-26 12:00:00'),('AP00042','PA00007','DO00001','2021-01-26 12:15:00'),('AP00043','PA00002','DO00002','2021-01-26 12:30:00'),('AP00044','PA00004','DO00002','2021-01-26 12:45:00'),('AP00045','PA00001','DO00001','2021-01-26 10:00:00'),('AP00046','PA00002','DO00002','2021-01-26 10:15:00'),('AP00047','PA00004','DO00002','2021-01-26 10:30:00'),('AP00048','PA00010','DO00001','2021-01-26 10:30:00'),('AP00049','PA00001','DO00005','2021-01-27 10:00:00'),('AP00050','PA00004','DO00002','2021-01-27 10:15:00'),('AP00051','PA00004','DO00004','2021-01-27 10:30:00'),('AP00052','PA00001','DO00005','2021-01-27 10:45:00'),('AP00054','PA00005','DO00004','2021-01-27 11:15:00'),('AP00055','PA00001','DO00001','2021-01-27 11:30:00'),('AP00056','PA00002','DO00002','2021-01-27 11:45:00'),('AP00057','PA00008','DO00004','2021-01-27 12:00:00'),('AP00058','PA00007','DO00001','2021-01-27 12:15:00'),('AP00059','PA00002','DO00002','2021-01-27 12:30:00'),('AP00060','PA00004','DO00002','2021-01-27 12:45:00'),('AP00061','PA00001','DO00001','2021-01-27 10:00:00'),('AP00062','PA00002','DO00002','2021-01-27 10:15:00'),('AP00063','PA00004','DO00002','2021-01-27 10:30:00'),('AP00064','PA00010','DO00001','2021-01-27 10:30:00'),('AP00065','PA00001','DO00005','2021-01-28 10:00:00'),('AP00066','PA00004','DO00002','2021-01-28 10:15:00'),('AP00067','PA00004','DO00004','2021-01-28 10:30:00'),('AP00068','PA00001','DO00005','2021-01-28 10:45:00'),('AP00069','PA00002','DO00005','2021-01-28 11:00:00'),('AP00070','PA00005','DO00004','2021-01-28 11:15:00'),('AP00071','PA00001','DO00001','2021-01-28 11:30:00'),('AP00072','PA00002','DO00002','2021-01-28 11:45:00'),('AP00073','PA00008','DO00004','2021-01-28 12:00:00'),('AP00074','PA00007','DO00001','2021-01-28 12:15:00'),('AP00075','PA00002','DO00002','2021-01-28 12:30:00'),('AP00076','PA00004','DO00002','2021-01-28 12:45:00'),('AP00077','PA00001','DO00001','2021-01-28 10:00:00'),('AP00078','PA00002','DO00002','2021-01-28 10:15:00'),('AP00079','PA00004','DO00002','2021-01-28 10:30:00'),('AP00080','PA00010','DO00001','2021-01-28 10:30:00'),('AP00081','PA00001','DO00005','2021-01-29 10:00:00'),('AP00082','PA00004','DO00002','2021-01-29 10:15:00'),('AP00083','PA00004','DO00004','2021-01-29 10:30:00'),('AP00084','PA00001','DO00005','2021-01-29 10:45:00'),('AP00085','PA00002','DO00005','2021-01-29 11:00:00'),('AP00086','PA00005','DO00004','2021-01-29 11:15:00'),('AP00087','PA00001','DO00001','2021-01-29 11:30:00'),('AP00088','PA00002','DO00002','2021-01-29 11:45:00'),('AP00089','PA00008','DO00004','2021-01-29 12:00:00'),('AP00090','PA00007','DO00001','2021-01-29 12:15:00'),('AP00091','PA00002','DO00002','2021-01-29 12:30:00'),('AP00092','PA00004','DO00002','2021-01-29 12:45:00'),('AP00093','PA00001','DO00001','2021-01-29 10:00:00'),('AP00094','PA00002','DO00002','2021-01-29 10:15:00'),('AP00095','PA00004','DO00002','2021-01-29 10:30:00'),('AP00096','PA00010','DO00001','2021-01-29 10:30:00'),('AP00097','PA00001','DO00005','2021-01-30 10:00:00'),('AP00098','PA00004','DO00002','2021-01-30 10:15:00'),('AP00099','PA00004','DO00004','2021-01-30 10:30:00'),('AP00100','PA00001','DO00005','2021-01-30 10:45:00'),('AP00101','PA00002','DO00005','2021-01-30 11:00:00'),('AP00102','PA00005','DO00004','2021-01-30 11:15:00'),('AP00103','PA00001','DO00001','2021-01-30 11:30:00'),('AP00104','PA00002','DO00002','2021-01-30 11:45:00'),('AP00105','PA00008','DO00004','2021-01-30 12:00:00'),('AP00106','PA00007','DO00001','2021-01-30 12:15:00'),('AP00107','PA00002','DO00002','2021-01-30 12:30:00'),('AP00108','PA00004','DO00002','2021-01-30 12:45:00'),('AP00109','PA00001','DO00001','2021-01-30 10:00:00'),('AP00110','PA00002','DO00002','2021-01-30 10:15:00'),('AP00111','PA00004','DO00002','2021-01-30 10:30:00'),('AP00112','PA00010','DO00001','2021-01-30 10:30:00'),('AP00114','PA00004','DO00002','2021-01-31 10:15:00'),('AP00118','PA00005','DO00004','2021-01-31 11:15:00'),('AP00119','PA00001','DO00001','2021-01-31 11:30:00'),('AP00120','PA00002','DO00002','2021-01-31 11:45:00'),('AP00121','PA00008','DO00004','2021-01-31 12:00:00'),('AP00122','PA00007','DO00001','2021-01-31 12:15:00'),('AP00123','PA00002','DO00002','2021-01-31 12:30:00'),('AP00124','PA00004','DO00002','2021-01-31 12:45:00'),('AP00125','PA00001','DO00001','2021-01-31 10:00:00'),('AP00126','PA00002','DO00002','2021-01-31 10:15:00'),('AP00132','PA00001','DO00001','2021-01-24 09:00:00'),('AP00133','PA00011','DO00008','2021-01-24 09:00:00'),('AP00134','PA00006','DO00010','2021-01-24 12:00:00'),('AP00135','PA00006','DO00008','2021-01-24 12:30:00'),('AP00136','PA00008','DO00016','2021-01-24 09:30:00'),('AP00137','PA00001','DO00018','2021-01-24 10:45:00');
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`admin`@`%`*/ /*!50003 TRIGGER `appointment_id_seq` BEFORE INSERT ON `appointment` FOR EACH ROW BEGIN
	insert into appointment_seq values (NULL);
	SET NEW.id = concat('AP', lpad(last_insert_id(), 5, '0'));
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Temporary view structure for view `appointment_calendar`
--

DROP TABLE IF EXISTS `appointment_calendar`;
/*!50001 DROP VIEW IF EXISTS `appointment_calendar`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `appointment_calendar` AS SELECT 
 1 AS `doctor_id`,
 1 AS `patients`,
 1 AS `appointments`,
 1 AS `appointment_ids`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `appointment_seq`
--

DROP TABLE IF EXISTS `appointment_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment_seq` (
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment_seq`
--

LOCK TABLES `appointment_seq` WRITE;
/*!40000 ALTER TABLE `appointment_seq` DISABLE KEYS */;
INSERT INTO `appointment_seq` VALUES (1),(2),(3),(4),(5),(6),(7),(8),(9),(10),(11),(12),(13),(14),(15),(16),(17),(18),(19),(20),(21),(22),(23),(24),(25),(26),(27),(28),(29),(30),(31),(32),(33),(34),(35),(36),(37),(38),(39),(40),(41),(42),(43),(44),(45),(46),(47),(48),(49),(50),(51),(52),(53),(54),(55),(56),(57),(58),(59),(60),(61),(62),(63),(64),(65),(66),(67),(68),(69),(70),(71),(72),(73),(74),(75),(76),(77),(78),(79),(80),(81),(82),(83),(84),(85),(86),(87),(88),(89),(90),(91),(92),(93),(94),(95),(96),(97),(98),(99),(100),(101),(102),(103),(104),(105),(106),(107),(108),(109),(110),(111),(112),(113),(114),(115),(116),(117),(118),(119),(120),(121),(122),(123),(124),(125),(126),(127),(128),(129),(130),(131),(132),(133),(134),(135),(136),(137);
/*!40000 ALTER TABLE `appointment_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor` (
  `id` varchar(7) NOT NULL,
  `name` varchar(30) NOT NULL,
  `age` int NOT NULL,
  `gender` varchar(1) NOT NULL,
  `speciality` varchar(20) DEFAULT NULL,
  `contact` varchar(10) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES ('DO00001','adfasdf',43,'O','rlnycuhtrq','5297782692','gmnkkofsikbuyfutxegnmcrywgjsjy'),('DO00002','olccseyexa',35,'M','yrippiaxdc','5388991727','hjayweasyragjhqsbakzglhifylxdo'),('DO00004','vrlzpejlho',75,'M','dtkdhslvus','9086975318','egirtrzliepnfgksawjgsfpzdisurp'),('DO00005','ucgpqawnxp',7,'O','nrknjamqxd','0764930979','qvopscgrpfigslbxsfgvtvpyzjannh'),('DO00006','jbbdmjuhxo',83,'O','ojlhruqsqt','0185577734','oehxckddcchgsdlfshdqshvsobobxd'),('DO00007','nhelkbvjkk',16,'O','pqgfbeyfru','1845897332','mdzoeonxjqrqnlsljvnybuwoecqcms'),('DO00008','nvqzywfuhh',44,'O','vudprgvjqa','4176923618','rwipyxcdcpmhjvqwbbmfklkttijnla'),('DO00009','swetounbvo',86,'M','btbfzvrwhk','9066549368','kgdvnlrmylgigufwymlwucjctjsivc'),('DO00010','ciocirepih',74,'O','kxebjlijrc','8786288778','njwjllwylthkudtaarveyobifnbkin'),('DO00012','ehfnebdcap',5,'F','upywvqpgjd','3367893368','qbenyplrotpkrsjtanolhujbyawodk'),('DO00013','xskuryxyfn',77,'O','ndryrvpeyx','0835962245','qnyimetqceqkhdvbthjghkifzhfpsr'),('DO00014','kklccnfilp',54,'O','zjbfntyhuh','0472169593','aqwrohtcewjvfocdrfedbnkjtkigbp'),('DO00015','knmusvytho',30,'O','vwxfhbdwuk','2996979044','vokqypmdxooyemhwdbgbfvxzoxnxpc'),('DO00016','vbcsqicgro',54,'F','tumsnkiwky','0548892659','wtwjimcekrjzronxwrzqluxxilptek'),('DO00017','xejwybipth',74,'O','rhbepbkejk','8396226874','orfcfikhuieiwrbdduhnydypwgshuo'),('DO00018','hbkpfgcjwx',42,'M','khurijdkrlads','2268897440','okzmhhcobetxlnrdxmeutjpkpemzos'),('DO00019','trwzwfcspq',3,'M','rmxsccroyy','3331985114','ebgovldjetqbrhbdcrbavbiixvjtec'),('DO00020','ovmzbkisxh',69,'M','aiqsbhglee','3835269235','vqvmllmwgsfjybnbvkgvkobgkgllvf'),('DO00021','mwjzbbpbnh',50,'O','gsibbfgygj','7713230248','jfkqcwllkgikmgjiqjflwwytswkcvd'),('DO00022','tairrusvni',70,'F','lvfdrcvmev','4929092476','vprfghlnhuskytxgtstsjqydpredjx'),('DO00023','dpehzbqrjd',65,'M','ldglssdxos','5546965597','gowwowsfxghoyrvhlvoxkylrvvcyio'),('DO00024','fkbiycpvjj',75,'O','myjbeqepcb','8380884562','hzfpdvpxzxfcexkejsxwnkrxypxosv'),('DO00025','jwhjxomskk',63,'F','jddlxhawgg','2135036906','kkbalhgclyktscezkpgfvpoaxygxxp'),('DO00026','cugtffkutu',49,'M','vpriiqdpva','4658538998','yibftrxbzsyrveeovjworxptcumxcc'),('DO00027','fjhicxbofn',55,'F','bnwokrxsda','7249358451','qvrovwqwicedpyesynmuwcqrcqcxow'),('DO00028','rtpomssvhf',57,'F','jujjngbfys','8269632495','vguvpppkvpspswdvfdzikdfrgoxsad'),('DO00029','nlnlkkkobx',68,'M','eplxksctfo','7633941484','xxjungbevlkmkruqtoxmigutfryebc'),('DO00030','qwxwjndgtf',41,'F','pnuquxsviw','3906956555','kemjmogghkezsewnionhwdrgtnfkxs');
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`admin`@`%`*/ /*!50003 TRIGGER `doctor_id_seq` BEFORE INSERT ON `doctor` FOR EACH ROW BEGIN
	insert into doctor_seq values (NULL);
	SET NEW.id = concat('DO', lpad(last_insert_id(), 5, '0'));
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `doctor_seq`
--

DROP TABLE IF EXISTS `doctor_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor_seq` (
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor_seq`
--

LOCK TABLES `doctor_seq` WRITE;
/*!40000 ALTER TABLE `doctor_seq` DISABLE KEYS */;
INSERT INTO `doctor_seq` VALUES (1),(2),(3),(4),(5),(6),(7),(8),(9),(10),(11),(12),(13),(14),(15),(16),(17),(18),(19),(20),(21),(22),(23),(24),(25),(26),(27),(28),(29),(30),(31),(32),(33),(34),(35),(36),(37),(38),(39),(40),(41),(42),(43),(44),(45),(46),(47),(48),(49),(50),(51),(52),(53);
/*!40000 ALTER TABLE `doctor_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `id` varchar(7) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `gender` varchar(1) DEFAULT NULL,
  `contact` varchar(10) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES ('PA00001','john doe',28,'O','1231231231','slpljgiduaxpwhyxvgnfnouqolnins'),('PA00002','abc',28,'O','6320644422','sbkupuftkrghlotbviapwnutodsxkq'),('PA00004','lfhrommieh',36,'M','9758842307','voqtgedpacvpcwgthztdgrvgyqbzco'),('PA00005','owgernsbnw',13,'O','1231231231','dtqghqruodqmjtwyuncjvkvqxdmylg'),('PA00006','wuerkdekia',86,'F','3486362628','jveydopelnthxtktnmavvzyoddsyos'),('PA00007','njyrrvmgki',57,'M','5947003876','xilelcoywwtcxxldxobctpzktmtmqt'),('PA00008','cccmycxojo',49,'F','8505874667','ysbmcjbgoktnmbleibnvekinmveejq'),('PA00010','dfnrlypbyo',80,'F','4438258335','yeekcmifidfaxargzeqnycecfrgino'),('PA00011','hckjtkgwxw',60,'O','9425600886','nkxwglvsyqitjjotyydwyfrfqtfdrj'),('PA00012','zdlactqcag',52,'O','2389690758','vgxxzjoiblxyqlqjtiwldjzofjohqy'),('PA00013','ciwchawunc',88,'F','3747267915','cwtxoopuwfqvpcrtdbcsdcegnyisfc'),('PA00014','cltdlrxavi',77,'M','3766863908','mrkdistmrcexgzienlqbftwcpwqhdq'),('PA00015','ggjytmeuyg',51,'F','7931793327','pwglvdaqkztzrxlnvgogrwtuqosqlv'),('PA00016','tvwcqrhanw',68,'F','7509429256','liukmyqbhhhurkfupwbvgiogbhnaxv'),('PA00017','puhbxysxcp',15,'O','8246935360','notlatshoqyqquqvbqilqnnzmaybvy'),('PA00018','ekimsyewka',11,'M','8645085038','qaxtoyvavnsaovfldnfplguukqcewq'),('PA00019','stysxcqbqa',29,'F','6831827698','btcdrymwjtligpugxonfpiiljjmhcl'),('PA00020','mnupgmjefq',13,'F','6893384797','hwilmclppscgoqlghuoutcedryrgsd'),('PA00021','fsyspcgvfq',70,'M','5080252999','mxtrdvqkvwekbhuqzakyqlsdhrimhe'),('PA00022','tedjwgvrxv',16,'F','6339662800','jozemenoofyjkahxnppepxnxbnpyro'),('PA00023','plelydxtkl',18,'F','4347012490','xdihliugvhnowfjxvhbicllutohuzy'),('PA00025','uximujjerm',51,'O','7839834540','zswctqibomwluthhgmgsfawjntuyet'),('PA00026','quackmuzle',52,'F','4197567638','swohnfjfuhswkqsjiujklmdoclbtlp'),('PA00027','euovrskrdr',8,'F','2683126039','ropomodkjiubrhgyikhfrxptauhhap'),('PA00028','qeqcgttfjk',56,'F','3264954464','wgwdtifpbopwlihfcrefjggcjjkpyb'),('PA00029','wivcvfstnb',67,'F','8447329256','iiymereupmtzfgdxprouooewbhsnzy'),('PA00030','ncrcwqglxm',3,'F','8922670386','yjektcsoeefyovwbhnoxdxbbkilbak'),('PA00031','yxmllkpqrf',47,'O','4884274452','nbmecveynpztuvviedgfdkncxfyjui'),('PA00032','uupzuqhvyr',18,'F','4525932360','wrujbugpvygkmwurcdbhliyhkspfur'),('PA00034','bwxgsrwrbj',79,'M','3889022556','zjnektnxhjdasffzeqqbfkciutyydo'),('PA00036','urngjakfxq',77,'M','1826950034','qsgehcfmqpywhosjresdwkeuesnosm'),('PA00037','auhwscceow',12,'M','2505474522','dwuhvgpuoinixpqhprtencvyvhvbdv'),('PA00038','dnwhpovmbj',24,'O','6674456089','oegxhumxnbndfospshkebpurjbohog'),('PA00039','ntygdpblui',59,'M','5256382597','xjrahihuuwdcwynqnuzhudfxotjwqm'),('PA00040','tlixjsiukb',87,'O','6413525819','pwfeooxgwffnlbzeqdozqhqemopkhs'),('PA00041','kiywmvrimy',54,'F','9664590979','lfhpdqsfedfvgmlodnhqxfmuldxnlx'),('PA00042','tamutrnhcp',8,'F','4393050327','ganymqmaqukmetasaeocopuxnhasbu'),('PA00044','jglvxrhgmv',67,'O','3621157325','vvwxsjqnrlsrtrhguecqpsliknmhcc'),('PA00045','tcxrywjujk',41,'O','5487268559','ejreaxpkbjcifebokwyctjgtzhjqom'),('PA00046','rvsxckawbu',5,'F','6532856136','fvxxnzebmwfqpfuismpxhxnojchmil'),('PA00048','tzvcvofyjj',7,'F','8486721757','wcoqcpujqewkulddgidiohlcweutbx'),('PA00049','hbscolcskd',85,'F','6544747637','xoymtnxkpiplvfunsewlmmpvdjmfnz'),('PA00051','Akash',21,'M','1234567890','abcd'),('PA00052','abc',18,'M','1231231231','asdasdas'),('PA00053','abc',30,'M','7897897897','adsa65sda15sd1as3'),('PA00054','qwe',12,'F','1212121212','dasdasd'),('PA00055','zxc',31,'M','1212121212','123');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`admin`@`%`*/ /*!50003 TRIGGER `patient_id_seq` BEFORE INSERT ON `patient` FOR EACH ROW BEGIN
	insert into patient_seq values (NULL);
	SET NEW.id = concat('PA', lpad(last_insert_id(), 5, '0'));
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `patient_seq`
--

DROP TABLE IF EXISTS `patient_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient_seq` (
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_seq`
--

LOCK TABLES `patient_seq` WRITE;
/*!40000 ALTER TABLE `patient_seq` DISABLE KEYS */;
INSERT INTO `patient_seq` VALUES (1),(2),(3),(4),(5),(6),(7),(8),(9),(10),(11),(12),(13),(14),(15),(16),(17),(18),(19),(20),(21),(22),(23),(24),(25),(26),(27),(28),(29),(30),(31),(32),(33),(34),(35),(36),(37),(38),(39),(40),(41),(42),(43),(44),(45),(46),(47),(48),(49),(50),(51),(52),(53),(54),(55);
/*!40000 ALTER TABLE `patient_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'hospital'
--

--
-- Dumping routines for database 'hospital'
--

--
-- Final view structure for view `appointment_calendar`
--

/*!50001 DROP VIEW IF EXISTS `appointment_calendar`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`admin`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `appointment_calendar` AS select `doctor`.`id` AS `doctor_id`,group_concat(`appointment`.`patient_id` separator ',') AS `patients`,group_concat(`appointment`.`date_scheduled` separator ',') AS `appointments`,group_concat(`appointment`.`id` separator ',') AS `appointment_ids` from (`doctor` left join `appointment` on(((`doctor`.`id` = `appointment`.`doctor_id`) and (`appointment`.`date_scheduled` > curdate())))) group by `doctor`.`id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-27 17:23:54
