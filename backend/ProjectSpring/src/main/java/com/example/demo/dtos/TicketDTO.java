package com.example.demo.dtos;

import java.util.List;

public class TicketDTO {
	private String name;
	private String surname;
	private String passportNumber;
	private Integer seatId;
	private String baggage;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public Integer getSeatId() {
		return seatId;
	}

	public void setSeatId(Integer seatId) {
		this.seatId = seatId;
	}

	public String getBaggage() {
		return baggage;
	}

	public void setBaggage(String baggage) {
		this.baggage = baggage;
	}
	
	@Override
	public String toString() {
		return "TicketDTO [name=" + name + ", surname=" + surname + ", passportNumber=" + passportNumber + ", seatId="
				+ seatId + ", baggage=" + baggage + "]";
	}
}
