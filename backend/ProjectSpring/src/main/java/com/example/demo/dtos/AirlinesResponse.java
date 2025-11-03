package com.example.demo.dtos;

public class AirlinesResponse {
	private int id;
	private String name;
	public AirlinesResponse() {
		super();
	}
	public AirlinesResponse(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
