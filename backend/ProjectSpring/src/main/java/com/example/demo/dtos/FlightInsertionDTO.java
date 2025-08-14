package com.example.demo.dtos;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FlightInsertionDTO {
	@NotNull(message="Departure airport is required")
	public Integer fromAirport;
	
	@NotNull(message="Arrival airport is required")
	public Integer toAirport;
	
	@NotNull(message="Departure time is required")
	@Future(message="Departure time must be in future")
	public Date departure;
	
	@NotNull(message="Airrival time is required")
	@Future(message="Arrival time must be in future")
	public Date arrival;
	
	@NotNull(message="Employees are required")
	public List<Integer> employees;
	
	@NotNull(message="Plane is required")
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
