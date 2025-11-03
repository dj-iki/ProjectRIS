package com.example.demo.dtos;

import java.util.Date;

public class FlightResponse {
	
	private int idFlight;
	private Date departureTime;
	private Date arrivalTime;
	private String flightNumber;
	private int airportFromId;
	private int airportToId;
	private String airportFromName;
	private String airportToName;
	private String airportFromIata;
	private String airportToIata;
	private String airlinesName;
	private String airlinesIataCode;
	
	public FlightResponse(int idFlight, Date departureTime, Date arrivalTime, String flightNumber, int airportFromId,
			int airportToId, String airportFromName, String airportToName, String airportFromIata, String airportToIata,
			String airlinesName, String airlinesIataCode) {
		super();
		this.idFlight = idFlight;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.flightNumber = flightNumber;
		this.airportFromId = airportFromId;
		this.airportToId = airportToId;
		this.airportFromName = airportFromName;
		this.airportToName = airportToName;
		this.airportFromIata = airportFromIata;
		this.airportToIata = airportToIata;
		this.airlinesName = airlinesName;
		this.airlinesIataCode = airlinesIataCode;
	}
	public FlightResponse() {
	}
	public int getIdFlight() {
		return idFlight;
	}
	public void setIdFlight(int idFlight) {
		this.idFlight = idFlight;
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
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getAirlinesName() {
		return airlinesName;
	}
	public void setAirlinesName(String airlinesName) {
		this.airlinesName = airlinesName;
	}
	public String getAirlinesIataCode() {
		return airlinesIataCode;
	}
	public void setAirlinesIataCode(String airlinesIataCode) {
		this.airlinesIataCode = airlinesIataCode;
	}
	public int getAirportFromId() {
		return airportFromId;
	}
	public void setAirportFromId(int airportFromId) {
		this.airportFromId = airportFromId;
	}
	public int getAirportToId() {
		return airportToId;
	}
	public void setAirportToId(int airportToId) {
		this.airportToId = airportToId;
	}
	public String getAirportFromName() {
		return airportFromName;
	}
	public void setAirportFromName(String airportFromName) {
		this.airportFromName = airportFromName;
	}
	public String getAirportToName() {
		return airportToName;
	}
	public void setAirportToName(String airportToName) {
		this.airportToName = airportToName;
	}
	public String getAirportFromIata() {
		return airportFromIata;
	}
	public void setAirportFromIata(String airportFromIata) {
		this.airportFromIata = airportFromIata;
	}
	public String getAirportToIata() {
		return airportToIata;
	}
	public void setAirportToIata(String airportToIata) {
		this.airportToIata = airportToIata;
	}
}
