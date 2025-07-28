package com.example.demo.dtos;

import java.util.Date;

import com.example.demo.validators.DateFutureOrPresent;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public class FlightDTO {

	@NotNull
	private Integer fromAirport;
	
	private Integer toAirport;
	
	private Integer numberOfSeats;

	@NotNull
	@DateFutureOrPresent
	private Date departureDate;
	
	@Future
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
