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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dtos.BookingDTO;
import com.example.demo.dtos.FlightSearchDTO;
import com.example.demo.services.FlightSearchService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import model.Airport;
import model.City;
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
	public FlightSearchDTO createFlightDTO() {
		return new FlightSearchDTO();
	}

	@GetMapping("/findFlights")
	public String findFlightsFrom(@Valid @ModelAttribute("flightDTO") FlightSearchDTO flightDTO, BindingResult result,
			Model model, HttpServletRequest request) {
		if (!result.hasErrors()) {
			HttpSession session = request.getSession();
			List<Flight> flights = null;
			List<Country> countries = null;
			session.removeAttribute("returning");
			session.removeAttribute("flightsTo");
			session.removeAttribute("countries");
			session.removeAttribute("flight");
			session.removeAttribute("numberOfSeats");
			if (flightDTO.getToAirport() != null) {
				flights = flightSearchService.getAllFlightsFromTo(flightDTO);
				if(flights == null || flights.isEmpty()) {
					model.addAttribute("no_one_way_flights", "There are no flights from " + flightSearchService.getAirportById(flightDTO.getFromAirport()) + " to " + flightSearchService.getAirportById(flightDTO.getToAirport()) + " for departure date " + flightDTO.getDepartureDate());
					return "index";
				}
				session.setAttribute("flightsTo", flights);
				session.setAttribute("numberOfSeats", flightDTO.getNumberOfSeats());
				if (flightDTO.getReturningDate() != null) {
					List<Flight> returning = flightSearchService.getReturningFlights(flightDTO);
					if(returning == null || returning.isEmpty()) {
						model.addAttribute("no_returning_flights", "There are no flights from " + flightSearchService.getAirportById(flightDTO.getFromAirport()) + " to " + flightSearchService.getAirportById(flightDTO.getToAirport()) + " for departure date " + flightDTO.getDepartureDate() + " and returning date " + flightDTO.getReturningDate());
						return "index";
					}
					session.setAttribute("returning", returning);
				}
				return "flightSearch";
			} else {
				if (flightDTO.getReturningDate() != null) {
					countries = flightSearchService.getReturningCountries(flightDTO);
					if(countries == null || countries.isEmpty()) {
						model.addAttribute("no_one_way_countries", "There are no flights from " + flightSearchService.getAirportById(flightDTO.getFromAirport()) + " for departure date " + flightDTO.getDepartureDate());
						return "index";
					}
				} else {
					countries = flightSearchService.getToCountries(flightDTO);
					if(countries == null || countries.isEmpty()) {
						model.addAttribute("no_returning_countries", "There are no flights from " + flightSearchService.getAirportById(flightDTO.getFromAirport()) + " for departure date " + flightDTO.getDepartureDate() + " and returning date " + flightDTO.getReturningDate());
						return "index";
					}
				}
				
				session.setAttribute("countries", countries);
				session.setAttribute("flight", flightDTO);
				return "countrySearch";
			}
		}
		model.addAttribute("validation_error", "There was an error with validating");
		model.addAttribute("errors", result.getAllErrors());
		return "index";

	}

	@GetMapping("/cities")
	public String findCities(@RequestParam("countryId") Integer countryId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("cities");
		FlightSearchDTO flightDTO = (FlightSearchDTO) session.getAttribute("flight");
		List<City> cities;
		if (flightDTO.getReturningDate() == null) {
			cities = flightSearchService.getToCities(countryId, flightDTO);
		} else {
			cities = flightSearchService.getReturningCities(countryId, flightDTO);
			
		}
		session.setAttribute("cities", cities);
		return "citySearch";
	}
	
	@GetMapping("/airports")
	public String findAirports(@RequestParam("cityId")Integer cityId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		FlightSearchDTO flightDTO = (FlightSearchDTO) session.getAttribute("flight");
		session.removeAttribute("airports");
		List<Airport> airports;
		if(flightDTO.getReturningDate()==null) {
			airports = flightSearchService.getToAirports(cityId, flightDTO);
		}else {
			airports = flightSearchService.getReturningAirports(cityId, flightDTO);
		}
		session.setAttribute("airports", airports);
		return "airportSearch";
	}
	
	@GetMapping("/flightsFromCountries")
	public String findFlightsFromCountries(@RequestParam("airportId") Integer airportId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		FlightSearchDTO flightDTO = (FlightSearchDTO) session.getAttribute("flight");
		flightDTO.setToAirport(airportId);
		List<Flight> flights = flightSearchService.getAllFlightsFromTo(flightDTO);
		session.setAttribute("flightsTo", flights);
		session.setAttribute("numberOfSeats", flightDTO.getNumberOfSeats());
		if (flightDTO.getReturningDate() != null) {
			List<Flight> returning = flightSearchService.getReturningFlights(flightDTO);
			session.setAttribute("returning", returning);
		}
		return "flightSearch";
	}
	
	@ModelAttribute("bookingDTO")
	public BookingDTO createBookingDTO() {
		return new BookingDTO();
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}

}
