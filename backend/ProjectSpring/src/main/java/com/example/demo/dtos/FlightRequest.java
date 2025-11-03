package com.example.demo.dtos;

import java.util.Date;

public class FlightRequest {
	private int id;
	private Date departureTime;
	private Date arrivalTime;
	private String airportFrom;
	private String airportTo;
	public FlightRequest() {
		super();
	}
	public FlightRequest(int id, Date departureTime, Date arrivalTime, String airportFrom, String airportTo) {
		super();
		this.id = id;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.airportFrom = airportFrom;
		this.airportTo = airportTo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}
	public Date getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getAirportFrom() {
		return airportFrom;
	}
	public void setAirportFrom(String airportFrom) {
		this.airportFrom = airportFrom;
	}
	public String getAirportTo() {
		return airportTo;
	}
	public void setAirportTo(String airportTo) {
		this.airportTo = airportTo;
	}
	
}
