package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dtos.BookingDTO;
import com.example.demo.dtos.FlightSearchDTO;
import com.example.demo.dtos.TicketDTO;
import com.example.demo.services.FlightBookingService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import model.Seat;

@Controller
@RequestMapping("/booking")
public class FlightBookingController {

	@Autowired
	FlightBookingService flightBookingService;

	@GetMapping("/new")
	public String createNewBooking(@Valid @ModelAttribute("bookingDTO") BookingDTO bookingDTO, BindingResult result,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("seatsFrom");
		session.removeAttribute("seatsReturning");
		session.removeAttribute("bookingDTO");
		session.removeAttribute("validation_error");
		session.removeAttribute("errors");
		if(!result.hasErrors()) {
			List<TicketDTO> ticketsDTO = new ArrayList<>();
			for (int i = 0; i < bookingDTO.getNumberOfSeats(); i++) {
				ticketsDTO.add(new TicketDTO());
			}
			bookingDTO.setTicketsFromDTO(ticketsDTO);
			bookingDTO.setTicketsReturningDTO(ticketsDTO);
			List<Seat> seatsFrom = flightBookingService.getAvailabelSeats(bookingDTO.getFlightIdFrom());
			if (bookingDTO.getFlightIdReturning() != null) {
				List<Seat> seatsReturning = flightBookingService.getAvailabelSeats(bookingDTO.getFlightIdReturning());
				session.setAttribute("seatsReturning", seatsReturning);
			}
			session.setAttribute("seatsFrom", seatsFrom);
	
			session.setAttribute("bookingDTO", bookingDTO);
	
			return "bookingTickets";
		}else {
			session.setAttribute("validation_error", "There was an error with validating");
			session.setAttribute("errors", result.getAllErrors());
			return "flightSearch";
		}
	}

	@PostMapping("/save")
	public String saveNewBooking(@Valid @ModelAttribute("bookingDTO") BookingDTO bookingDTO, BindingResult result, RedirectAttributes redirectAttributes, Model model,
			@CookieValue(value = "jwt", required = true) String jwt, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(!result.hasErrors()) {
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
					session.setAttribute("seatsReturning", seatsReturning);
				}
				session.setAttribute("seatsFrom", seatsFrom);
	
				session.setAttribute("bookingDTO", bookingDTO);
				
				return "redirect:/booking/new";
			}
			model.addAttribute("flightDTO", new FlightSearchDTO());
			model.addAttribute("successfull_booking", bookingResult);
			return "index";
		}else {
			redirectAttributes.addFlashAttribute("bookingDTO", bookingDTO);
			model.addAttribute("validation_error", "There was an error with validating");
			model.addAttribute("errors", result.getAllErrors());
			return "redirect:/booking/new";
		}
	}
}
