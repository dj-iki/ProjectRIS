package com.example.demo.dtos;

public class CitiesResponse {
	private int cityId;
	private String name;
	
	public CitiesResponse() {
		super();
	}
	public CitiesResponse(int cityId, String name) {
		super();
		this.cityId = cityId;
		this.name = name;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
