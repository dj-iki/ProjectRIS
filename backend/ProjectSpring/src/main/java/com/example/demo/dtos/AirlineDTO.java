package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AirlineDTO {
	@NotNull(message="Name cannot be null")
	@NotBlank(message="Name cannot be emtpy")
	@Pattern(regexp="[a-zA-Z- ]", message="Name must contain only letters and '-'")
	private String name;
	
	@NotNull(message="iata_code cannot be null")
	@Pattern(regexp="[A-Z]", message="iata_code can only contain uppercase letters")
	@Size(min=2, max=2, message="iata_code must be 2 letters long")
	private String iataCode;
	
	@NotNull(message="icao_code cannot be null")
	@Pattern(regexp="[A-Z]", message="icao_code can only contain uppercase letters")
	@Size(min=3, max=3, message="icao_code must be 3 letters long")
	private String icaoCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIataCode() {
		return iataCode;
	}

	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}

	public String getIcaoCode() {
		return icaoCode;
	}

	public void setIcaoCode(String icaoCode) {
		this.icaoCode = icaoCode;
	}
}
