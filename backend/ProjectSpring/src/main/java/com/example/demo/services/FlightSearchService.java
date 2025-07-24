package com.example.demo.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.FlightDTO;
import com.example.demo.repositories.AirportRepository;
import com.example.demo.repositories.CountryRepository;
import com.example.demo.repositories.FlightRepository;

import model.Airport;
import model.Country;
import model.Flight;

@Service
public class FlightSearchService {

	@Autowired
	AirportRepository airportRepository;

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	CountryRepository countryRepository;

	public List<Airport> getAllAirports() {
		return airportRepository.findAll();
	}

	public List<Flight> getAllFlightsFromTo(FlightDTO flightDTO) {
		Airport from = airportRepository.findById(flightDTO.getFromAirport()).get();
		Airport to = airportRepository.findById(flightDTO.getToAirport()).get();
		if (flightDTO.getDepartureDate().compareTo(new Date()) <= 0) {
			flightDTO.setDepartureDate(new Date());
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date dayAfter = calendar.getTime();
			List<Flight> flights = flightRepository.getAllFlightsFromTo(from, to, flightDTO.getDepartureDate(),
					dayAfter);
			return flights;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(flightDTO.getDepartureDate());
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date dayAfter = calendar.getTime();
			List<Flight> flights = flightRepository.getAllFlightsFromTo(from, to, flightDTO.getDepartureDate(),
					dayAfter);
			return flights;
		}
	}

	public List<Country> getToCountries(FlightDTO flightDTO) {
		Airport from = airportRepository.findById(flightDTO.getFromAirport()).get();
		if (flightDTO.getDepartureDate().compareTo(new Date()) <= 0) {
			flightDTO.setDepartureDate(new Date());
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date dayAfter = calendar.getTime();
			List<Country> countries = countryRepository.getToCountries(from, flightDTO.getDepartureDate(), dayAfter);
			return countries;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(flightDTO.getDepartureDate());
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date dayAfter = calendar.getTime();
			List<Country> countries = countryRepository.getToCountries(from, flightDTO.getDepartureDate(), dayAfter);
			return countries;
		}
	}
	
	public List<Flight> getReturningFlights(FlightDTO flightDTO){
		Airport from = airportRepository.findById(flightDTO.getToAirport()).get();
		Airport to = airportRepository.findById(flightDTO.getFromAirport()).get();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(flightDTO.getReturningDate());
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date dayAfter = calendar.getTime();
		List<Flight> flights = flightRepository.getAllFlightsFromTo(from, to, flightDTO.getReturningDate(),
				dayAfter);
		return flights;
	}
}
