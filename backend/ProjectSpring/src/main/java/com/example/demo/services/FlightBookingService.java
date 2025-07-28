package com.example.demo.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.BookingDTO;
import com.example.demo.dtos.TicketDTO;
import com.example.demo.repositories.AppUserRepository;
import com.example.demo.repositories.BookingRepository;
import com.example.demo.repositories.FlightRepository;
import com.example.demo.repositories.SeatRepository;
import com.example.demo.repositories.TicketRepository;
import com.example.demo.security.JWTService;

import model.AppUser;
import model.Booking;
import model.Flight;
import model.Seat;
import model.Ticket;

@Service
public class FlightBookingService {

	@Autowired
	SeatRepository seatRepository;

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	AppUserRepository appUserRepository;

	@Autowired
	BookingRepository bookingRepository;

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	JWTService jwtService;

	public List<Seat> getAvailabelSeats(Integer flightId) {
		Flight flight = flightRepository.findById(flightId).get();
		List<Seat> seats = seatRepository.getAvailableSeats(flight.getPlane(), flight);
		return seats;
	}

	public String saveBooking(BookingDTO bookingDTO, String jwt) {
		try {
			String username = jwtService.extractUsername(jwt);
			AppUser user = appUserRepository.findAppUserByUsername(username);
			if (isValidTickets(bookingDTO.getTicketsFromDTO())) {
				Flight flightFrom = flightRepository.findById(bookingDTO.getFlightIdFrom()).get();
				Booking bookingFrom = new Booking();
				bookingFrom.setAppUser(user);
				bookingFrom.setFlight(flightFrom);
				bookingFrom.setNumberOfSeats(bookingDTO.getNumberOfSeats());
				List<Ticket> ticketsFrom = new ArrayList<>();
				float price = 0;
				for (TicketDTO ticketDTO : bookingDTO.getTicketsFromDTO()) {
					Ticket ticket = new Ticket();
					ticket.setName(ticketDTO.getName());
					ticket.setSurname(ticketDTO.getSurname());
					ticket.setBaggage(ticketDTO.getBaggage());
					ticket.setPassportNumber(ticketDTO.getPassportNumber());
					ticket.setSeat(seatRepository.findById(ticketDTO.getSeatId()).get());
					ticket.setBooking(bookingFrom);
					ticketsFrom.add(ticket);
					price += seatRepository.getPrice(ticketDTO.getSeatId());
				}
				bookingFrom.setPrice(price);
				bookingFrom.setTickets(ticketsFrom);
				bookingFrom = bookingRepository.save(bookingFrom);
				if (bookingDTO.getFlightIdReturning() != null) {
					if (isValidTickets(bookingDTO.getTicketsReturningDTO())) {
						Flight returningFlight = flightRepository.findById(bookingDTO.getFlightIdReturning()).get();
						Booking bookingReturning = new Booking();
						bookingReturning.setAppUser(user);
						bookingReturning.setFlight(returningFlight);
						bookingReturning.setNumberOfSeats(bookingDTO.getNumberOfSeats());
						List<Ticket> ticketsReturning = new ArrayList<>();
						price = 0;
						for (TicketDTO ticketDTO : bookingDTO.getTicketsReturningDTO()) {
							Ticket ticket = new Ticket();
							ticket.setName(ticketDTO.getName());
							ticket.setSurname(ticketDTO.getSurname());
							ticket.setBaggage(ticketDTO.getBaggage());
							ticket.setPassportNumber(ticketDTO.getPassportNumber());
							ticket.setSeat(seatRepository.findById(ticketDTO.getSeatId()).get());
							ticket.setBooking(bookingReturning);
							ticketsReturning.add(ticket);
							price += seatRepository.getPrice(ticketDTO.getSeatId());
						}
						bookingReturning.setPrice(price);
						bookingReturning.setTickets(ticketsReturning);
						bookingReturning = bookingRepository.save(bookingReturning);
					} else
						return "Error with booking - Each passenger must have a unique seat. You cannot select the same seat more than once.";
				}
				return "You booked your tickets successfully";
			}
			return "Error with booking - Each passenger must have a unique seat. You cannot select the same seat more than once.";
		} catch (Exception e) {
			return "Error with booking - " + e.getMessage();
		}
	}

	private boolean isValidTickets(List<TicketDTO> tickets) {
		Set<Integer> set = new HashSet<>();
		for (TicketDTO ticket : tickets) {
			if (set.contains(ticket.getSeatId()))
				return false;
			set.add(ticket.getSeatId());
		}
		return true;
	}
}
