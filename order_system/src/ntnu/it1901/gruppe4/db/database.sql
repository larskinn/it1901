USE `gr4pizza`;

-- -----------------------------------------------------
-- Table `customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `customer` ;

CREATE  TABLE IF NOT EXISTS `customer` (
  `idcustomer` INT(11) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(60) NULL DEFAULT NULL ,
  `phone` VARCHAR(20) NULL DEFAULT NULL ,
  PRIMARY KEY (`idcustomer`) );


-- -----------------------------------------------------
-- Table `address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `address` ;

CREATE  TABLE IF NOT EXISTS `address` (
  `idaddress` INT(11) NOT NULL AUTO_INCREMENT ,
  `idcustomer` INT(11) NOT NULL ,
  `addressline` VARCHAR(50) NULL DEFAULT NULL ,
  `postalcode` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`idaddress`) );


-- -----------------------------------------------------
-- Table `dish`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dish` ;

CREATE  TABLE IF NOT EXISTS `dish` (
  `iddish` INT(11) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL DEFAULT NULL ,
  `price` DECIMAL(5,2) NULL DEFAULT NULL ,
  `description` VARCHAR(1024) NULL DEFAULT NULL ,
  PRIMARY KEY (`iddish`) );


-- -----------------------------------------------------
-- Table `order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `order` ;

CREATE  TABLE IF NOT EXISTS `order` (
  `idorder` INT(11) NOT NULL AUTO_INCREMENT ,
  `ordertime` DATETIME NULL DEFAULT NULL ,
  `deliverytime` DATETIME NULL DEFAULT NULL ,
  `state` INT(11) NULL DEFAULT NULL ,
  `idaddress` INT(11) NOT NULL ,
  `idcustomer` INT(11) NOT NULL ,
  PRIMARY KEY (`idorder`) );


-- -----------------------------------------------------
-- Table `orderitem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `orderitem` ;

CREATE  TABLE IF NOT EXISTS `orderitem` (
  `idorderitem` INT(11) NOT NULL AUTO_INCREMENT ,
  `idorder` INT(11) NOT NULL ,
  `iddish` INT(11) NOT NULL ,
  `name` VARCHAR(45) NULL ,
  `amount` DECIMAL(5,2) NULL ,
  PRIMARY KEY (`idorderitem`) );
