package com.example.demo.dtos;

public class PlaneResponse {
	private int planeId;
	private String manufacturer;
	private String model;
	private String registrationNumber;
	public PlaneResponse() {
		super();
	}
	public PlaneResponse(int planeId, String manufacturer, String model, String registrationNumber) {
		super();
		this.planeId = planeId;
		this.manufacturer = manufacturer;
		this.model = model;
		this.registrationNumber = registrationNumber;
	}
	public int getPlaneId() {
		return planeId;
	}
	public void setPlaneId(int planeId) {
		this.planeId = planeId;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
}
