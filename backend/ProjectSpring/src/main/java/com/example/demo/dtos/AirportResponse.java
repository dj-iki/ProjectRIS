package com.example.demo.dtos;

public class AirportResponse {
	private long id;
	private String name;
	private String iataCode;
	public AirportResponse() {
		super();
	}
	public AirportResponse(long id, String name, String iataCode) {
		super();
		this.id = id;
		this.name = name;
		this.iataCode = iataCode;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
	
}
