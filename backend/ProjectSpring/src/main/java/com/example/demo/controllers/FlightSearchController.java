package com.example.demo.controllers;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dtos.FlightDTO;
import com.example.demo.services.FlightSearchService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import model.Airport;

@Controller
@RequestMapping("/search")
public class FlightSearchController {
	
	@Autowired
	FlightSearchService flightSearchService;
	
	
	@GetMapping("/redirect")
	public String redirect(HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Airport> airports = flightSearchService.getAllAirports();
		session.setAttribute("airports", airports);
		return "index";
	}
	
	@ModelAttribute("flightDTO")
	public FlightDTO createFlightDTO() {
		return new FlightDTO();
	}
	
	@GetMapping("/findFlights")
	public String findFlights(@Valid @ModelAttribute("flightDTO") FlightDTO flightDTO, BindingResult result) {
		return "index";
	}
	
}
