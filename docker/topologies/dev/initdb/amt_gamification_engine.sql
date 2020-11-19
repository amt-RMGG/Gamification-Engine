-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema amt_gamification
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema amt_gamification
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `amt_gamification` DEFAULT CHARACTER SET utf8 ;
USE `amt_gamification` ;

-- -----------------------------------------------------
-- Table `amt_gamification`.`application`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `amt_gamification`.`application`(
  `apikey` VARCHAR(36) NOT NULL,
  `name` VARCHAR(30) NOT NULL,
  `description` MEDIUMTEXT NULL,
  PRIMARY KEY (`apikey`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `amt_gamification`.`badges`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `amt_gamification`.`badge`(
  `id` INT NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `experienceValue` INT NOT NULL,
  `application_apikey` VARCHAR(36) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_badge_application`
    FOREIGN KEY (`application_apikey`)
    REFERENCES `amt_gamification`.`application` (`apikey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_badge_application_idx` ON `amt_gamification`.`badge` (`application_apikey` ASC) VISIBLE;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
