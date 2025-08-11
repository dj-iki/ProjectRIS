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
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView redirect(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("index");
		List<Airport> airports = flightSearchService.getAllAirports();
		modelAndView.addObject("airports", airports);
		HttpSession session = request.getSession();
		session.removeAttribute("returning");
		session.removeAttribute("flightsTo");
		session.removeAttribute("countries");
		session.removeAttribute("flight");
		session.removeAttribute("numberOfSeats");
		session.removeAttribute("no_one_way_flights");
		session.removeAttribute("no_returning_flights");
		session.removeAttribute("no_one_way_countries");
		session.removeAttribute("no_returning_countries");
		session.removeAttribute("validation_error_search");
		session.removeAttribute("errors_search");
		return modelAndView;
	}

	@ModelAttribute("flightDTO")
	public FlightSearchDTO createFlightDTO() {
		return new FlightSearchDTO();
	}

	@GetMapping("/findFlights")
	public String findFlightsFrom(@Valid @ModelAttribute("flightDTO") FlightSearchDTO flightDTO, BindingResult result,
			Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("returning");
		session.removeAttribute("flightsTo");
		session.removeAttribute("countries");
		session.removeAttribute("flight");
		session.removeAttribute("numberOfSeats");
		session.removeAttribute("no_one_way_flights");
		session.removeAttribute("no_returning_flights");
		session.removeAttribute("no_one_way_countries");
		session.removeAttribute("no_returning_countries");
		session.removeAttribute("validation_error_search");
		session.removeAttribute("errors_search");
		if (!result.hasErrors()) {
			List<Flight> flights = null;
			List<Country> countries = null;
			
			if (flightDTO.getToAirport() != null) {
				flights = flightSearchService.getAllFlightsFromTo(flightDTO);
				if(flights == null || flights.isEmpty()) {
					session.setAttribute("no_one_way_flights", "There are no flights from " + flightSearchService.getAirportById(flightDTO.getFromAirport()) + " to " + flightSearchService.getAirportById(flightDTO.getToAirport()) + " for departure date " + flightDTO.getDepartureDate());
					return "redirect:/search/redirect-inside";
				}
				session.setAttribute("flightsTo", flights);
				session.setAttribute("numberOfSeats", flightDTO.getNumberOfSeats());
				if (flightDTO.getReturningDate() != null) {
					List<Flight> returning = flightSearchService.getReturningFlights(flightDTO);
					if(returning == null || returning.isEmpty()) {
						session.setAttribute("no_returning_flights", "There are no flights from " + flightSearchService.getAirportById(flightDTO.getFromAirport()) + " to " + flightSearchService.getAirportById(flightDTO.getToAirport()) + " for departure date " + flightDTO.getDepartureDate() + " and returning date " + flightDTO.getReturningDate());
						return "redirect:/search/redirect-inside";
					}
					session.setAttribute("returning", returning);
				}
				session.removeAttribute("validation_error");
				session.removeAttribute("errors");
				return "flightSearch";
			} else {
				if (flightDTO.getReturningDate() != null) {
					countries = flightSearchService.getReturningCountries(flightDTO);
					if(countries == null || countries.isEmpty()) {
						session.setAttribute("no_one_way_countries", "There are no flights from " + flightSearchService.getAirportById(flightDTO.getFromAirport()) + " for departure date " + flightDTO.getDepartureDate());
						return "redirect:/search/redirect-inside";
					}
				} else {
					countries = flightSearchService.getToCountries(flightDTO);
					if(countries == null || countries.isEmpty()) {
						session.setAttribute("no_returning_countries", "There are no flights from " + flightSearchService.getAirportById(flightDTO.getFromAirport()) + " for departure date " + flightDTO.getDepartureDate() + " and returning date " + flightDTO.getReturningDate());
						return "redirect:/search/redirect-inside";
					}
				}
				
				session.setAttribute("countries", countries);
				session.setAttribute("flight", flightDTO);
				return "countrySearch";
			}
		}
		session.setAttribute("validation_error", "There was an error with validating");
		session.setAttribute("errors", result.getAllErrors());
		return "redirect:/search/redirect-inside";

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
		session.removeAttribute("validation_error");
		session.removeAttribute("errors");
		return "flightSearch";
	}
	
	@ModelAttribute("bookingDTO")
	public BookingDTO createBookingDTO() {
		return new BookingDTO();
	}
	
	@GetMapping("/redirect-inside")
	public ModelAndView redirectInside(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("index");
		List<Airport> airports = flightSearchService.getAllAirports();
		modelAndView.addObject("airports", airports);
		HttpSession session = request.getSession();
		session.removeAttribute("successfull_booking");
		return modelAndView;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}

}
