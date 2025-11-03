package com.example.demo.dtos;

public class CountriesResponse {
	private int countryId;
	private String name;
	
	public CountriesResponse() {
		super();
	}
	public CountriesResponse(int countryId, String name) {
		super();
		this.countryId = countryId;
		this.name = name;
	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
