package com.example.demo.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PlaneDTO {
	
	@NotNull
	@NotBlank
	private String registrationNumber;
	
	@NotNull
	@NotBlank
	private String manufacturer;
	
	@NotNull
	@NotBlank
	private String model;
	
	@NotNull
	@Min(value=1)
	private Integer numberOfEconomySeats;
	
	@NotNull
	@Min(value=1)
	private Integer numberOfEconomyPlusSeats;
	
	@NotNull
	@Min(value=1)
	private Integer numberOfBusinessSeats;
	
	@NotNull
	@Min(value=1)
	private Integer economyPrice;
	
	@NotNull
	@Min(value=1)
	private Integer economyPlusPrice;
	
	@NotNull
	@Min(value=1)
	private Integer businessPrice;
	
	@NotNull
	@Min(value=1)
	private Integer numberOfBusinessRows;
	
	@NotNull
	@Min(value=1)
	private Integer numberOfEconomyRows;
	
	@NotNull
	@Min(value=1)
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
