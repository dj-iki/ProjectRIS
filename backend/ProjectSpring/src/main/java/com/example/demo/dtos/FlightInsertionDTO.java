package com.example.demo.dtos;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FlightInsertionDTO {
	@NotNull
	public Integer fromAirport;
	
	@NotNull
	public Integer toAirport;
	
	@NotNull
	@Future
	public Date departure;
	
	@NotNull
	@Future
	public Date arrival;
	
	@NotNull
	public List<Integer> employees;
	
	@NotNull
	public Integer plane;

	public Integer getFromAirport() {
		return fromAirport;
	}

	public void setFromAirport(Integer fromAirport) {
		this.fromAirport = fromAirport;
	}

	public Integer getToAirport() {
		return toAirport;
	}

	public void setToAirport(Integer toAirport) {
		this.toAirport = toAirport;
	}

	public Date getDeparture() {
		return departure;
	}

	public void setDeparture(Date departure) {
		this.departure = departure;
	}

	public Date getArrival() {
		return arrival;
	}

	public void setArrival(Date arrival) {
		this.arrival = arrival;
	}

	public List<Integer> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Integer> employees) {
		this.employees = employees;
	}

	public Integer getPlane() {
		return plane;
	}

	public void setPlane(Integer plane) {
		this.plane = plane;
	}
}
