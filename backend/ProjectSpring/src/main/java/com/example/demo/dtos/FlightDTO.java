package com.example.demo.dtos;

import java.util.Date;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import model.Airport;

public class FlightDTO {

	@NotNull
	private Airport fromAirport;
	
	@NotNull
	private Airport toAirport;

	@NotNull
	@FutureOrPresent
	private Date departureDate;
	
	@Future
	private Date returningDate;

	public Airport getFromAirport() {
		return fromAirport;
	}

	public void setFromAirport(Airport fromAirport) {
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

	public void setreturningDate(Date returningDate) {
		this.returningDate = returningDate;
	}

	public Airport getToAirport() {
		return toAirport;
	}

	public void setToAirport(Airport toAirport) {
		this.toAirport = toAirport;
	}



}
