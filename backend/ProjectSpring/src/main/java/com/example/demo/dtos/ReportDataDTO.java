package com.example.demo.dtos;

import java.util.Date;

public class ReportDataDTO {
	private String ticketHolderName;
	private String ticketHolderSurname;
	private String passportNumber;
	private Date departure;
	private String seatClass;
	private String seatNumber;
	private String from;
	private String to;
	private String airline;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getTicketHolderName() {
		return ticketHolderName;
	}

	public void setTicketHolderName(String ticketHolderName) {
		this.ticketHolderName = ticketHolderName;
	}

	public String getTicketHolderSurname() {
		return ticketHolderSurname;
	}

	public void setTicketHolderSurname(String ticketHolderSurname) {
		this.ticketHolderSurname = ticketHolderSurname;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public Date getDeparture() {
		return departure;
	}

	public void setDeparture(Date departure) {
		this.departure = departure;
	}

	public String getSeatClass() {
		return seatClass;
	}

	public void setSeatClass(String seatClass) {
		this.seatClass = seatClass;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
}
