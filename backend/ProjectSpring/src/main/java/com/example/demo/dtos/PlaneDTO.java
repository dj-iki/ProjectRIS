package com.example.demo.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PlaneDTO {
	
	@NotNull(message="Registration number is required.")
	@NotBlank(message="Registration number cannot be empty.")
	private String registrationNumber;
	
	@NotNull(message="Manufacturer is required.")
	@NotBlank(message="Manufacturer cannot be empty.")
	private String manufacturer;
	
	@NotNull(message="Model is required.")
	@NotBlank(message="Model cannot be empty.")
	private String model;
	
	@NotNull(message="Number of economy seats is required.")
	@Min(value=1, message="Number of economy seats must be at least 1.")
	private Integer numberOfEconomySeats;
	
	@NotNull(message="Number of economy plus seats is required.")
	@Min(value=1, message="Number of economy plus seats must be at least 1.")
	private Integer numberOfEconomyPlusSeats;
	
	@NotNull(message="Number of business seats is required.")
	@Min(value=1, message="Number of business seats must be at least 1.")
	private Integer numberOfBusinessSeats;
	
	@NotNull(message="Economy price is required.")
	@Min(value=1, message="Economy price must be at least 1.")
	private Integer economyPrice;
	
	@NotNull(message="Economy Plus price is required.")
	@Min(value=1, message="Economy Plus price must be at least 1.")
	private Integer economyPlusPrice;
	
	@NotNull(message="Business price is required.")
	@Min(value=1, message="Business price must be at least 1.")
	private Integer businessPrice;
	
	@NotNull(message="Number of business rows is required.")
	@Min(value=1, message="Number of business rows must be at least 1.")
	private Integer numberOfBusinessRows;
	
	@NotNull(message="Number of economy rows is required.")
	@Min(value=1, message="Number of economy rows must be at least 1.")
	private Integer numberOfEconomyRows;
	
	@NotNull(message="Number of economy plus rows is required.")
	@Min(value=1, message="Number of economy plus rows must be at least 1.")
	private Integer numberOfEconomyPlusRows;

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getEconomyPrice() {
		return economyPrice;
	}

	public void setEconomyPrice(Integer economyPrice) {
		this.economyPrice = economyPrice;
	}

	public Integer getEconomyPlusPrice() {
		return economyPlusPrice;
	}

	public void setEconomyPlusPrice(Integer economyPlusPrice) {
		this.economyPlusPrice = economyPlusPrice;
	}

	public Integer getBusinessPrice() {
		return businessPrice;
	}

	public void setBusinessPrice(Integer buisinessPrice) {
		this.businessPrice = buisinessPrice;
	}

	public Integer getNumberOfEconomySeats() {
		return numberOfEconomySeats;
	}

	public void setNumberOfEconomySeats(Integer numberOfEconomySeats) {
		this.numberOfEconomySeats = numberOfEconomySeats;
	}

	public Integer getNumberOfEconomyPlusSeats() {
		return numberOfEconomyPlusSeats;
	}

	public void setNumberOfEconomyPlusSeats(Integer numberOfEconomyPlusSeats) {
		this.numberOfEconomyPlusSeats = numberOfEconomyPlusSeats;
	}

	public Integer getNumberOfBusinessSeats() {
		return numberOfBusinessSeats;
	}

	public void setNumberOfBusinessSeats(Integer numberOfBusinessSeats) {
		this.numberOfBusinessSeats = numberOfBusinessSeats;
	}

	public Integer getNumberOfBusinessRows() {
		return numberOfBusinessRows;
	}

	public void setNumberOfBusinessRows(Integer numberOfBusinessRows) {
		this.numberOfBusinessRows = numberOfBusinessRows;
	}

	public Integer getNumberOfEconomyRows() {
		return numberOfEconomyRows;
	}

	public void setNumberOfEconomyRows(Integer numberOfEconomyRows) {
		this.numberOfEconomyRows = numberOfEconomyRows;
	}

	public Integer getNumberOfEconomyPlusRows() {
		return numberOfEconomyPlusRows;
	}

	public void setNumberOfEconomyPlusRows(Integer numberOfEconomyPlusRows) {
		this.numberOfEconomyPlusRows = numberOfEconomyPlusRows;
	}
}
