package com.example.demo.dtos;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class BookingDTO {
	
	@NotNull
	private Integer flightIdFrom;
	
	private Integer flightIdReturning;
	
	@NotNull
	@Min(value=1)
	private Integer numberOfSeats;
	
	private List<TicketDTO> ticketsFromDTO;
	
	private List<TicketDTO> ticketsReturningDTO;

	public Integer getFlightIdFrom() {
		return flightIdFrom;
	}

	public void setFlightIdFrom(Integer flightIdFrom) {
		this.flightIdFrom = flightIdFrom;
	}

	public Integer getFlightIdReturning() {
		return flightIdReturning;
	}

	public void setFlightIdReturning(Integer flightIdReturning) {
		this.flightIdReturning = flightIdReturning;
	}

	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public List<TicketDTO> getTicketsFromDTO() {
		return ticketsFromDTO;
	}

	public void setTicketsFromDTO(List<TicketDTO> ticketsFromDTO) {
		this.ticketsFromDTO = ticketsFromDTO;
	}

	public List<TicketDTO> getTicketsReturningDTO() {
		return ticketsReturningDTO;
	}

	public void setTicketsReturningDTO(List<TicketDTO> ticketsReturningDTO) {
		this.ticketsReturningDTO = ticketsReturningDTO;
	}

	@Override
	public String toString() {
		String result = "BookingDTO = {"
				+ " flightIdFrom=" + flightIdFrom + ", flightIdReturning=" + flightIdReturning +", numberOfSeats=" + numberOfSeats;
		result += "\nticketsFromDTO=[ ";
		for(TicketDTO ticket : ticketsFromDTO) {
			result += ticket + ", ";
		}
		
		result += "]\nticketsReturningDTO=[ ";
		for(TicketDTO ticket : ticketsReturningDTO) {
			result += ticket + ", ";
		}
		return result += "]";
	}
}
