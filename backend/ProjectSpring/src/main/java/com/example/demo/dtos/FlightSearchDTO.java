package com.example.demo.dtos;

import java.util.Date;

import com.example.demo.validators.DateFutureOrPresent;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class FlightSearchDTO {

	@NotNull(message="Departure Airport cannot be null")
	private Integer fromAirport;
	
	private Integer toAirport;
	
	@NotNull(message="Number of seats cannot be null")
	@Min(value=1)
	private Integer numberOfSeats;

	@NotNull(message="Departure date cannot be null")
	@DateFutureOrPresent
	private Date departureDate;
	
	@Future(message="Returning date must be in future")
	private Date returningDate;

	public Integer getFromAirport() {
		return fromAirport;
	}

	public void setFromAirport(Integer fromAirport) {
		this.fromAirport = fromAirport;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Date getReturningDate() {
		return returningDate;
	}

	public void setReturningDate(Date returningDate) {
		this.returningDate = returningDate;
	}

	public Integer getToAirport() {
		return toAirport;
	}

	public void setToAirport(Integer toAirport) {
		this.toAirport = toAirport;
	}
	
	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

}
