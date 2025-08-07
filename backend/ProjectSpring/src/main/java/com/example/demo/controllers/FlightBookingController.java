package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dtos.BookingDTO;
import com.example.demo.dtos.FlightSearchDTO;
import com.example.demo.dtos.TicketDTO;
import com.example.demo.services.FlightBookingService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import model.Seat;

@Controller
@RequestMapping("/booking")
public class FlightBookingController {

	@Autowired
	FlightBookingService flightBookingService;

	@GetMapping("/new")
	public String createNewBooking(@Valid @ModelAttribute("bookingDTO") BookingDTO bookingDTO,
			HttpServletRequest request, Model model) {
		List<TicketDTO> ticketsDTO = new ArrayList<>();
		for (int i = 0; i < bookingDTO.getNumberOfSeats(); i++) {
			ticketsDTO.add(new TicketDTO());
		}
		bookingDTO.setTicketsFromDTO(ticketsDTO);
		bookingDTO.setTicketsReturningDTO(ticketsDTO);
		List<Seat> seatsFrom = flightBookingService.getAvailabelSeats(bookingDTO.getFlightIdFrom());
		if (bookingDTO.getFlightIdReturning() != null) {
			List<Seat> seatsReturning = flightBookingService.getAvailabelSeats(bookingDTO.getFlightIdReturning());
			model.addAttribute("seatsReturning", seatsReturning);
		}
		model.addAttribute("seatsFrom", seatsFrom);

		model.addAttribute("bookingDTO", bookingDTO);

		return "bookingTickets";
	}

	@PostMapping("/save")
	public String saveNewBooking(@Valid @ModelAttribute("bookingDTO") BookingDTO bookingDTO, Model model,
			@CookieValue(value = "jwt", required = true) String jwt) {
		String bookingResult = flightBookingService.saveBooking(bookingDTO, jwt);
		if (bookingResult.startsWith("Error")) {
			model.addAttribute("error_with_booking", bookingResult);
			List<TicketDTO> ticketsDTO = new ArrayList<>();
			for (int i = 0; i < bookingDTO.getNumberOfSeats(); i++) {
				ticketsDTO.add(new TicketDTO());
			}
			bookingDTO.setTicketsFromDTO(ticketsDTO);
			bookingDTO.setTicketsReturningDTO(ticketsDTO);
			List<Seat> seatsFrom = flightBookingService.getAvailabelSeats(bookingDTO.getFlightIdFrom());
			if (bookingDTO.getFlightIdReturning() != null) {
				List<Seat> seatsReturning = flightBookingService.getAvailabelSeats(bookingDTO.getFlightIdReturning());
				model.addAttribute("seatsReturning", seatsReturning);
			}
			model.addAttribute("seatsFrom", seatsFrom);

			model.addAttribute("bookingDTO", bookingDTO);
			
			return "bookingTickets";
		}
		model.addAttribute("flightDTO", new FlightSearchDTO());
		model.addAttribute("successfull_booking", bookingResult);
		return "index";
	}
}
