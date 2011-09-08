SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `gr4pizza` DEFAULT CHARACTER SET latin1 ;
USE `gr4pizza` ;

-- -----------------------------------------------------
-- Table `gr4pizza`.`customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gr4pizza`.`customer` ;

CREATE  TABLE IF NOT EXISTS `gr4pizza`.`customer` (
  `idcustomer` INT(11) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(60) NULL DEFAULT NULL ,
  `phone` VARCHAR(20) NULL DEFAULT NULL ,
  PRIMARY KEY (`idcustomer`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `gr4pizza`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gr4pizza`.`address` ;

CREATE  TABLE IF NOT EXISTS `gr4pizza`.`address` (
  `idaddress` INT(11) NOT NULL AUTO_INCREMENT ,
  `idcustomer` INT(11) NOT NULL ,
  `addressline` VARCHAR(50) NULL DEFAULT NULL ,
  `postalcode` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`idaddress`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `gr4pizza`.`dish`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gr4pizza`.`dish` ;

CREATE  TABLE IF NOT EXISTS `gr4pizza`.`dish` (
  `iddish` INT(11) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL DEFAULT NULL ,
  `price` DECIMAL(5,2) NULL DEFAULT NULL ,
  `description` VARCHAR(1024) NULL DEFAULT NULL ,
  PRIMARY KEY (`iddish`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `gr4pizza`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gr4pizza`.`order` ;

CREATE  TABLE IF NOT EXISTS `gr4pizza`.`order` (
  `idorder` INT(11) NOT NULL AUTO_INCREMENT ,
  `ordertime` DATETIME NULL DEFAULT NULL ,
  `deliverytime` DATETIME NULL DEFAULT NULL ,
  `state` INT(11) NULL DEFAULT NULL ,
  `idaddress` INT(11) NOT NULL ,
  `idcustomer` INT(11) NOT NULL ,
  PRIMARY KEY (`idorder`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `gr4pizza`.`orderitem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gr4pizza`.`orderitem` ;

CREATE  TABLE IF NOT EXISTS `gr4pizza`.`orderitem` (
  `idorderitem` INT(11) NOT NULL AUTO_INCREMENT ,
  `idorder` INT(11) NOT NULL ,
  `iddish` INT(11) NOT NULL ,
  `name` VARCHAR(45) NULL ,
  `amount` DECIMAL(5,2) NULL ,
  PRIMARY KEY (`idorderitem`) )
ENGINE = MyISAM;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
