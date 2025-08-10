package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TicketDTO {
	
	@NotNull
	@NotBlank(message = "Name can't be blank!")
	@Size(min = 2, max = 100, message="Name must be between 2 and 100 characters long!")
	@Pattern(regexp = "^[A-Za-z ]+$", message="Name can only contain letters and spaces!")
	private String name;
	
	@NotNull
	@NotBlank(message = "Surname can't be blank!")
	@Size(min = 2, max = 100, message="Surname must be between 2 and 100 characters long!")
	@Pattern(regexp = "^[A-Za-z ]+$", message="Surname can only contain letters and spaces")
	private String surname;
	
	@NotNull
	@NotBlank(message = "Passport number can't be blank!")
	@Size(min = 10, max = 20, message="Passport number must be between 10 and 20 characters long!")
	@Pattern(regexp = "^[A-Za-z0-9]+$", message="Passport number can only contain numbers and letters")
	private String passportNumber;
	
	@NotNull
	private Integer seatId;
	
	@NotNull
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
