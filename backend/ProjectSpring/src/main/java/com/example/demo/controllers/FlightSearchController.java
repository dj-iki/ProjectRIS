package com.example.demo.controllers;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dtos.FlightDTO;
import com.example.demo.services.FlightSearchService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import model.Airport;
import model.Country;
import model.Flight;

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
	public String findFlightsFrom(@Valid @ModelAttribute("flightDTO") FlightDTO flightDTO, BindingResult result, Model model) {
		if(!result.hasErrors()) {
			List<Flight> flights = null;
			List<Country> countries = null;
			if(flightDTO.getToAirport()!=null) {
				flights = flightSearchService.getAllFlightsFromTo(flightDTO);
				for(Flight flight : flights) {
					System.out.println(flight);
				}
			}else {
				countries = flightSearchService.getToCountries(flightDTO);
				for(Country country : countries) {
					System.out.println(country);
				}
			}
			
			if(flightDTO.getReturningDate()!=null) {
				List<Flight> returning = flightSearchService.getReturningFlights(flightDTO);
				for(Flight flight : returning) {
					System.out.println(flight);
				}
			}
			return "index";
		}
		model.addAttribute("validation_error", "There was an error with validating");
		model.addAttribute("errors", result.getAllErrors());
		return "index";
		
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
}
