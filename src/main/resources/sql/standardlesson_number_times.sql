-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: standardlesson
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
-- Table structure for table `number_times`
--

DROP TABLE IF EXISTS `number_times`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `number_times` (
  `id` varchar(20) NOT NULL,
  `num01` int(11) DEFAULT '0',
  `num02` int(11) DEFAULT '0',
  `num03` int(11) DEFAULT '0',
  `num04` int(11) DEFAULT '0',
  `num05` int(11) DEFAULT '0',
  `num06` int(11) DEFAULT '0',
  `num07` int(11) DEFAULT '0',
  `num08` int(11) DEFAULT '0',
  `num09` int(11) DEFAULT '0',
  `num10` int(11) DEFAULT '0',
  `num11` int(11) DEFAULT '0',
  `num12` int(11) DEFAULT '0',
  `num13` int(11) DEFAULT '0',
  `num14` int(11) DEFAULT '0',
  `num15` int(11) DEFAULT '0',
  `num16` int(11) DEFAULT '0',
  `num17` int(11) DEFAULT '0',
  `num18` int(11) DEFAULT '0',
  `num19` int(11) DEFAULT '0',
  `num20` int(11) DEFAULT '0',
  `num21` int(11) DEFAULT '0',
  `num22` int(11) DEFAULT '0',
  `num23` int(11) DEFAULT '0',
  `num24` int(11) DEFAULT '0',
  `num25` int(11) DEFAULT '0',
  `num26` int(11) DEFAULT '0',
  `num27` int(11) DEFAULT '0',
  `num28` int(11) DEFAULT '0',
  `num29` int(11) DEFAULT '0',
  `num30` int(11) DEFAULT '0',
  `num31` int(11) DEFAULT '0',
  `num32` int(11) DEFAULT '0',
  `num33` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `number_times`
--

LOCK TABLES `number_times` WRITE;
/*!40000 ALTER TABLE `number_times` DISABLE KEYS */;
INSERT INTO `number_times` VALUES ('BLUE',146,139,141,130,147,144,152,125,160,140,155,163,145,150,143,156,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),('RED1',459,353,296,247,206,170,131,110,106,61,51,40,31,24,20,14,4,4,3,2,2,1,0,1,0,0,0,0,0,0,0,0,0),('RED2',0,71,124,145,169,191,211,193,167,161,138,149,130,113,76,80,60,52,31,24,23,14,7,2,2,2,0,1,0,0,0,0,0),('RED3',0,0,8,27,48,60,89,113,109,142,156,150,156,178,145,144,131,138,118,111,90,73,50,37,27,16,11,7,2,0,0,0,0),('RED4',0,0,0,0,4,14,9,23,28,47,59,73,88,115,111,131,167,149,152,181,138,172,152,117,101,110,76,53,36,22,8,0,0),('RED5',0,0,0,0,0,0,1,5,1,9,6,9,24,32,37,42,67,85,102,109,126,143,145,150,196,205,195,182,141,149,102,73,0),('RED6',0,0,0,0,0,0,0,0,0,0,1,0,0,1,5,8,11,15,19,28,36,52,57,78,99,125,144,154,229,257,290,367,360);
/*!40000 ALTER TABLE `number_times` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-03 22:54:34
