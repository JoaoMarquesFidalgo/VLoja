CREATE DATABASE  IF NOT EXISTS `tqs` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `tqs`;
-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: tqs
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `favorito`
--

DROP TABLE IF EXISTS `favorito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `favorito` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `id_utilizador` mediumint(9) DEFAULT NULL,
  `id_produto` mediumint(9) DEFAULT NULL,
  `id_lista` mediumint(9) DEFAULT NULL,
  `data` varchar(45) DEFAULT NULL,
  `alias` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_idx` (`id_lista`),
  KEY `id_favorito_produto_idx` (`id_produto`),
  KEY `id_favorito_utilizador_idx` (`id_utilizador`),
  CONSTRAINT `fk_favorito_lista` FOREIGN KEY (`id_lista`) REFERENCES `lista` (`id`),
  CONSTRAINT `fk_favorito_produto` FOREIGN KEY (`id_produto`) REFERENCES `produto` (`id`),
  CONSTRAINT `fk_favorito_utilizador` FOREIGN KEY (`id_utilizador`) REFERENCES `utilizador` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='						';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorito`
--

LOCK TABLES `favorito` WRITE;
/*!40000 ALTER TABLE `favorito` DISABLE KEYS */;
/*!40000 ALTER TABLE `favorito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lista`
--

DROP TABLE IF EXISTS `lista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `lista` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `categoria` varchar(100) NOT NULL,
  `data` varchar(45) NOT NULL,
  `alias` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lista`
--

LOCK TABLES `lista` WRITE;
/*!40000 ALTER TABLE `lista` DISABLE KEYS */;
/*!40000 ALTER TABLE `lista` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produto`
--

DROP TABLE IF EXISTS `produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `produto` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `categoria` varchar(100) NOT NULL,
  `preco` double NOT NULL,
  `marca` varchar(100) NOT NULL,
  `imagem` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produto_lista`
--

DROP TABLE IF EXISTS `produto_lista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `produto_lista` (
  `id` mediumint(9) NOT NULL,
  `id_produto` mediumint(9) DEFAULT NULL,
  `id_lista` mediumint(9) DEFAULT NULL,
  `comprado` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_produto_lista_idx` (`id_produto`),
  KEY `id_lista_produto_idx` (`id_lista`),
  CONSTRAINT `id_lista_produto` FOREIGN KEY (`id_lista`) REFERENCES `lista` (`id`),
  CONSTRAINT `id_produto_lista` FOREIGN KEY (`id_produto`) REFERENCES `produto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto_lista`
--

LOCK TABLES `produto_lista` WRITE;
/*!40000 ALTER TABLE `produto_lista` DISABLE KEYS */;
/*!40000 ALTER TABLE `produto_lista` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utilizador`
--

DROP TABLE IF EXISTS `utilizador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `utilizador` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `password` varchar(20) NOT NULL,
  `nome` varchar(200) NOT NULL,
  `lingua` varchar(100) NOT NULL,
  `imagem` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilizador`
--

LOCK TABLES `utilizador` WRITE;
/*!40000 ALTER TABLE `utilizador` DISABLE KEYS */;
/*!40000 ALTER TABLE `utilizador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utilizador_lista`
--

DROP TABLE IF EXISTS `utilizador_lista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `utilizador_lista` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `id_utilizador` mediumint(9) DEFAULT NULL,
  `id_lista` mediumint(9) DEFAULT NULL,
  `alias` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_utilizador_idx` (`id_utilizador`),
  KEY `fk_lista_idx` (`id_lista`),
  CONSTRAINT `fk_lista_utilizador` FOREIGN KEY (`id_lista`) REFERENCES `lista` (`id`),
  CONSTRAINT `fk_utilizador_lista` FOREIGN KEY (`id_utilizador`) REFERENCES `utilizador` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilizador_lista`
--

LOCK TABLES `utilizador_lista` WRITE;
/*!40000 ALTER TABLE `utilizador_lista` DISABLE KEYS */;
/*!40000 ALTER TABLE `utilizador_lista` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utilizador_produto`
--

DROP TABLE IF EXISTS `utilizador_produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `utilizador_produto` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `id_utilizador` mediumint(9) NOT NULL,
  `id_produto` mediumint(9) NOT NULL,
  `alias` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_utilizador_idx` (`id_utilizador`),
  KEY `fk_produto_idx` (`id_produto`),
  CONSTRAINT `fk_produto` FOREIGN KEY (`id_produto`) REFERENCES `produto` (`id`),
  CONSTRAINT `fk_utilizador` FOREIGN KEY (`id_utilizador`) REFERENCES `utilizador` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilizador_produto`
--

LOCK TABLES `utilizador_produto` WRITE;
/*!40000 ALTER TABLE `utilizador_produto` DISABLE KEYS */;
/*!40000 ALTER TABLE `utilizador_produto` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-08-30  6:06:32
