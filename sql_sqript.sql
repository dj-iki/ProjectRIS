-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Role` (
  `idRole` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idRole`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `idRole_UNIQUE` ON `mydb`.`Role` (`idRole` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `mydb`.`Airline`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Airline` (
  `idAirlines` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `iata_code` VARCHAR(2) NOT NULL,
  `icao_code` VARCHAR(3) NOT NULL,
  PRIMARY KEY (`idAirlines`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `idAirlines_UNIQUE` ON `mydb`.`Airline` (`idAirlines` ASC) VISIBLE;

CREATE UNIQUE INDEX `name_UNIQUE` ON `mydb`.`Airline` (`name` ASC) VISIBLE;

CREATE UNIQUE INDEX `iata_code_UNIQUE` ON `mydb`.`Airline` (`iata_code` ASC) VISIBLE;

CREATE UNIQUE INDEX `icao_code_UNIQUE` ON `mydb`.`Airline` (`icao_code` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `mydb`.`App_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`App_user` (
  `idUsers` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `surname` VARCHAR(100) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `passport_number` VARCHAR(45) NOT NULL,
  `Role_idRole` INT UNSIGNED NOT NULL,
  `Airlines_idAirlines` INT UNSIGNED NULL,
  PRIMARY KEY (`idUsers`),
  CONSTRAINT `fk_Users_Role`
    FOREIGN KEY (`Role_idRole`)
    REFERENCES `mydb`.`Role` (`idRole`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Users_Airlines1`
    FOREIGN KEY (`Airlines_idAirlines`)
    REFERENCES `mydb`.`Airline` (`idAirlines`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `idUsers_UNIQUE` ON `mydb`.`App_user` (`idUsers` ASC) VISIBLE;

CREATE UNIQUE INDEX `username_UNIQUE` ON `mydb`.`App_user` (`username` ASC) VISIBLE;

CREATE UNIQUE INDEX `passport_number_UNIQUE` ON `mydb`.`App_user` (`passport_number` ASC) VISIBLE;

CREATE UNIQUE INDEX `email_UNIQUE` ON `mydb`.`App_user` (`email` ASC) VISIBLE;

CREATE INDEX `fk_Users_Role_idx` ON `mydb`.`App_user` (`Role_idRole` ASC) VISIBLE;

CREATE INDEX `fk_Users_Airlines1_idx` ON `mydb`.`App_user` (`Airlines_idAirlines` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `mydb`.`Country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Country` (
  `idCountry` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`idCountry`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `idCountry_UNIQUE` ON `mydb`.`Country` (`idCountry` ASC) VISIBLE;

CREATE UNIQUE INDEX `name_UNIQUE` ON `mydb`.`Country` (`name` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `mydb`.`City`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`City` (
  `idCity` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `Country_idCountry` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idCity`),
  CONSTRAINT `fk_City_Country1`
    FOREIGN KEY (`Country_idCountry`)
    REFERENCES `mydb`.`Country` (`idCountry`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `idCity_UNIQUE` ON `mydb`.`City` (`idCity` ASC) VISIBLE;

CREATE INDEX `fk_City_Country1_idx` ON `mydb`.`City` (`Country_idCountry` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `mydb`.`Airport`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Airport` (
  `idAirport` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `iata_code` VARCHAR(3) NOT NULL,
  `icao_code` VARCHAR(4) NOT NULL,
  `City_idCity` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idAirport`),
  CONSTRAINT `fk_Airport_City1`
    FOREIGN KEY (`City_idCity`)
    REFERENCES `mydb`.`City` (`idCity`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `icao_code_UNIQUE` ON `mydb`.`Airport` (`icao_code` ASC) VISIBLE;

CREATE UNIQUE INDEX `iata_code_UNIQUE` ON `mydb`.`Airport` (`iata_code` ASC) VISIBLE;

CREATE UNIQUE INDEX `idAirport_UNIQUE` ON `mydb`.`Airport` (`idAirport` ASC) VISIBLE;

CREATE INDEX `fk_Airport_City1_idx` ON `mydb`.`Airport` (`City_idCity` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `mydb`.`Plane`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Plane` (
  `idPlane` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `registration_number` VARCHAR(45) NOT NULL,
  `model` VARCHAR(45) NOT NULL,
  `manufacturer` VARCHAR(60) NOT NULL,
  `number_of_seats` INT UNSIGNED NOT NULL,
  `Airlines_idAirlines` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idPlane`),
  CONSTRAINT `fk_Plane_Airlines1`
    FOREIGN KEY (`Airlines_idAirlines`)
    REFERENCES `mydb`.`Airline` (`idAirlines`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `idPlane_UNIQUE` ON `mydb`.`Plane` (`idPlane` ASC) VISIBLE;

CREATE UNIQUE INDEX `registration_number_UNIQUE` ON `mydb`.`Plane` (`registration_number` ASC) VISIBLE;

CREATE UNIQUE INDEX `manufacturer_UNIQUE` ON `mydb`.`Plane` (`manufacturer` ASC) VISIBLE;

CREATE INDEX `fk_Plane_Airlines1_idx` ON `mydb`.`Plane` (`Airlines_idAirlines` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `mydb`.`Flight`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Flight` (
  `idFlight` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `flight_number` VARCHAR(45) NOT NULL,
  `departure_time` TIMESTAMP NOT NULL,
  `arrival_time` TIMESTAMP NOT NULL,
  `departure` INT UNSIGNED NOT NULL,
  `arrival` INT UNSIGNED NOT NULL,
  `Plane_idPlane` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idFlight`),
  CONSTRAINT `fk_Flight_Airport1`
    FOREIGN KEY (`departure`)
    REFERENCES `mydb`.`Airport` (`idAirport`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Flight_Airport2`
    FOREIGN KEY (`arrival`)
    REFERENCES `mydb`.`Airport` (`idAirport`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Flight_Plane1`
    FOREIGN KEY (`Plane_idPlane`)
    REFERENCES `mydb`.`Plane` (`idPlane`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `flight_number_UNIQUE` ON `mydb`.`Flight` (`flight_number` ASC) VISIBLE;

CREATE UNIQUE INDEX `idFlight_UNIQUE` ON `mydb`.`Flight` (`idFlight` ASC) VISIBLE;

CREATE INDEX `fk_Flight_Airport1_idx` ON `mydb`.`Flight` (`departure` ASC) VISIBLE;

CREATE INDEX `fk_Flight_Airport2_idx` ON `mydb`.`Flight` (`arrival` ASC) VISIBLE;

CREATE INDEX `fk_Flight_Plane1_idx` ON `mydb`.`Flight` (`Plane_idPlane` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `mydb`.`Booking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Booking` (
  `idBooking` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `price` FLOAT UNSIGNED NOT NULL,
  `Users_idUsers` INT UNSIGNED NOT NULL,
  `Flight_idFlight` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idBooking`),
  CONSTRAINT `fk_Booking_Users1`
    FOREIGN KEY (`Users_idUsers`)
    REFERENCES `mydb`.`App_user` (`idUsers`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Booking_Flight1`
    FOREIGN KEY (`Flight_idFlight`)
    REFERENCES `mydb`.`Flight` (`idFlight`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `idBooking_UNIQUE` ON `mydb`.`Booking` (`idBooking` ASC) VISIBLE;

CREATE INDEX `fk_Booking_Users1_idx` ON `mydb`.`Booking` (`Users_idUsers` ASC) VISIBLE;

CREATE INDEX `fk_Booking_Flight1_idx` ON `mydb`.`Booking` (`Flight_idFlight` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `mydb`.`Seat`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Seat` (
  `idSeat` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `seat_number` VARCHAR(45) NOT NULL,
  `price` FLOAT UNSIGNED NOT NULL,
  `class` ENUM('ECONOMY', 'ECONOMY_PLUS', 'BUSINESS') NOT NULL,
  `is_booked` TINYINT NOT NULL,
  `Plane_idPlane` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idSeat`),
  CONSTRAINT `fk_Seat_Plane1`
    FOREIGN KEY (`Plane_idPlane`)
    REFERENCES `mydb`.`Plane` (`idPlane`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `idSeat_UNIQUE` ON `mydb`.`Seat` (`idSeat` ASC) VISIBLE;

CREATE INDEX `fk_Seat_Plane1_idx` ON `mydb`.`Seat` (`Plane_idPlane` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `mydb`.`Ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Ticket` (
  `idTicket` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `surname` VARCHAR(100) NOT NULL,
  `passport_number` VARCHAR(45) NOT NULL,
  `Booking_idBooking` INT UNSIGNED NOT NULL,
  `Seat_idSeat` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idTicket`),
  CONSTRAINT `fk_Ticket_Booking1`
    FOREIGN KEY (`Booking_idBooking`)
    REFERENCES `mydb`.`Booking` (`idBooking`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ticket_Seat1`
    FOREIGN KEY (`Seat_idSeat`)
    REFERENCES `mydb`.`Seat` (`idSeat`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Ticket_Booking1_idx` ON `mydb`.`Ticket` (`Booking_idBooking` ASC) VISIBLE;

CREATE INDEX `fk_Ticket_Seat1_idx` ON `mydb`.`Ticket` (`Seat_idSeat` ASC) VISIBLE;

CREATE UNIQUE INDEX `passport_number_UNIQUE` ON `mydb`.`Ticket` (`passport_number` ASC) VISIBLE;

CREATE UNIQUE INDEX `idTicket_UNIQUE` ON `mydb`.`Ticket` (`idTicket` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `mydb`.`Baggage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Baggage` (
  `idBaggage` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` ENUM('CARRY_ON', 'CHECKED', 'SPORTS_EQUIPMENT') NOT NULL,
  `weight` INT NULL,
  `price` INT UNSIGNED NOT NULL,
  `Ticket_idTicket` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idBaggage`),
  CONSTRAINT `fk_Baggage_Ticket1`
    FOREIGN KEY (`Ticket_idTicket`)
    REFERENCES `mydb`.`Ticket` (`idTicket`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `idBaggage_UNIQUE` ON `mydb`.`Baggage` (`idBaggage` ASC) VISIBLE;

CREATE INDEX `fk_Baggage_Ticket1_idx` ON `mydb`.`Baggage` (`Ticket_idTicket` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `mydb`.`Users_has_Flight`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Users_has_Flight` (
  `Users_idUsers` INT UNSIGNED NOT NULL,
  `Flight_idFlight` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`Users_idUsers`, `Flight_idFlight`),
  CONSTRAINT `fk_Users_has_Flight_Users1`
    FOREIGN KEY (`Users_idUsers`)
    REFERENCES `mydb`.`App_user` (`idUsers`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Users_has_Flight_Flight1`
    FOREIGN KEY (`Flight_idFlight`)
    REFERENCES `mydb`.`Flight` (`idFlight`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Users_has_Flight_Flight1_idx` ON `mydb`.`Users_has_Flight` (`Flight_idFlight` ASC) VISIBLE;

CREATE INDEX `fk_Users_has_Flight_Users1_idx` ON `mydb`.`Users_has_Flight` (`Users_idUsers` ASC) VISIBLE;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
