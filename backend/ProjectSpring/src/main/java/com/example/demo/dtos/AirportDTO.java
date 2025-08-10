package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AirportDTO {
	
	@NotNull(message="Name cannot be null")
	@NotBlank(message="Name cannot be emtpy")
	@Pattern(regexp="[a-zA-Z-]", message="Name must contain only letters and '-'")
	private String name;
	
	@NotNull(message="iata_code cannot be null")
	@Pattern(regexp="[A-Z]", message="iata_code can only contain uppercase letters")
	@Size(min=3, max=3, message="iata_code must be 3 letters long")
	private String iata_code;
	
	@NotNull(message="icao_code cannot be null")
	@Pattern(regexp="[A-Z]", message="icao_code can only contain uppercase letters")
	@Size(min=4, max=4, message="icao_code must be 4 letters long")
	private String icao_code;
	
	private Integer cityId;
	
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIata_code() {
		return iata_code;
	}
	public void setIata_code(String iata_code) {
		this.iata_code = iata_code;
	}
	public String getIcao_code() {
		return icao_code;
	}
	public void setIcao_code(String icao_code) {
		this.icao_code = icao_code;
	}
}
