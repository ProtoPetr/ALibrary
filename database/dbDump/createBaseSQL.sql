-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema librdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `librdb` ;

-- -----------------------------------------------------
-- Schema librdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `librdb` ;
USE `librdb` ;

-- -----------------------------------------------------
-- Table `librdb`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `librdb`.`role` ;

CREATE TABLE IF NOT EXISTS `librdb`.`role` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `librdb`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `librdb`.`user` ;

CREATE TABLE IF NOT EXISTS `librdb`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `role_id` INT NOT NULL DEFAULT 3,
  `mail` VARCHAR(45) NOT NULL,
  `status` ENUM('active', 'block') NOT NULL DEFAULT 'active',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  INDEX `role_id_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `librdb`.`role` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `librdb`.`author`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `librdb`.`author` ;

CREATE TABLE IF NOT EXISTS `librdb`.`author` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `librdb`.`genre`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `librdb`.`genre` ;

CREATE TABLE IF NOT EXISTS `librdb`.`genre` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `librdb`.`publisher`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `librdb`.`publisher` ;

CREATE TABLE IF NOT EXISTS `librdb`.`publisher` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `librdb`.`book`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `librdb`.`book` ;

CREATE TABLE IF NOT EXISTS `librdb`.`book` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `author_id` INT NOT NULL,
  `genre_id` INT NOT NULL,
  `publisher_id` INT NOT NULL,
  `year_of_publishing` INT NOT NULL,
  `count` INT NOT NULL,
  `image` LONGBLOB NULL,
  PRIMARY KEY (`id`),
  INDEX `author_id_idx` (`author_id` ASC) VISIBLE,
  INDEX `genre_id_idx` (`genre_id` ASC) VISIBLE,
  INDEX `publisher_id_idx` (`publisher_id` ASC) VISIBLE,
  CONSTRAINT `author_id`
    FOREIGN KEY (`author_id`)
    REFERENCES `librdb`.`author` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `genre_id`
    FOREIGN KEY (`genre_id`)
    REFERENCES `librdb`.`genre` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `publisher_id`
    FOREIGN KEY (`publisher_id`)
    REFERENCES `librdb`.`publisher` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `librdb`.`delivery_desk`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `librdb`.`delivery_desk` ;

CREATE TABLE IF NOT EXISTS `librdb`.`delivery_desk` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL,
  `created_at` DATETIME NOT NULL DEFAULT (CURRENT_DATE),
  PRIMARY KEY (`id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `librdb`.`user` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `librdb`.`delivery_desk_has_book`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `librdb`.`delivery_desk_has_book` ;

CREATE TABLE IF NOT EXISTS `librdb`.`delivery_desk_has_book` (
  `delivery_desk_id` INT NOT NULL,
  `book_id` INT NOT NULL,
  `date_of_issue` DATE NULL,
  `return_date` DATE NULL,
  `status` ENUM('ordered', 'issued', 'returned') NOT NULL DEFAULT 'ordered',
  `penalty` INT NULL DEFAULT 0,
  PRIMARY KEY (`delivery_desk_id`, `book_id`),
  INDEX `fk_delivery-desk_has_book_book1_idx` (`book_id` ASC) VISIBLE,
  INDEX `fk_delivery-desk_has_book_delivery-desk1_idx` (`delivery_desk_id` ASC) VISIBLE,
  CONSTRAINT `fk_delivery-desk_has_book_delivery-desk1`
    FOREIGN KEY (`delivery_desk_id`)
    REFERENCES `librdb`.`delivery_desk` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_delivery-desk_has_book_book1`
    FOREIGN KEY (`book_id`)
    REFERENCES `librdb`.`book` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
