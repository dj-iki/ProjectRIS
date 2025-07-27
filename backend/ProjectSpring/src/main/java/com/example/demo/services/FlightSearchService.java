package com.example.demo.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.FlightDTO;
import com.example.demo.repositories.AirportRepository;
import com.example.demo.repositories.CityRepository;
import com.example.demo.repositories.CountryRepository;
import com.example.demo.repositories.FlightRepository;

import model.Airport;
import model.City;
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

	@Autowired
	CityRepository cityRepository;

	public List<Airport> getAllAirports() {
		return airportRepository.findAll();
	}

	public List<Flight> getAllFlightsFromTo(FlightDTO flightDTO) {
		Airport from = airportRepository.findById(flightDTO.getFromAirport()).get();
		Airport to = airportRepository.findById(flightDTO.getToAirport()).get();
		if (flightDTO.getDepartureDate().compareTo(new Date()) <= 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(flightDTO.getDepartureDate());
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date dayAfter = calendar.getTime();
			flightDTO.setDepartureDate(new Date());
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
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(flightDTO.getDepartureDate());
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date dayAfter = calendar.getTime();
			flightDTO.setDepartureDate(new Date());
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

	public List<Flight> getReturningFlights(FlightDTO flightDTO) {
		Airport from = airportRepository.findById(flightDTO.getToAirport()).get();
		Airport to = airportRepository.findById(flightDTO.getFromAirport()).get();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(flightDTO.getReturningDate());
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date dayAfter = calendar.getTime();
		List<Flight> flights = flightRepository.getAllFlightsFromTo(from, to, flightDTO.getReturningDate(), dayAfter);
		return flights;
	}

	public List<Country> getReturningCountries(FlightDTO flightDTO) {
		List<Country> countries = getToCountries(flightDTO);
		Airport from = airportRepository.findById(flightDTO.getFromAirport()).get();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(flightDTO.getReturningDate());
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date dayAfter = calendar.getTime();
		countries = countryRepository.getReturningCountries(from, flightDTO.getReturningDate(), dayAfter, countries);
		return countries;
	}

	public List<City> getToCities(Integer countryId, FlightDTO flightDTO) {
		Airport from = airportRepository.findById(flightDTO.getFromAirport()).get();
		if (flightDTO.getDepartureDate().compareTo(new Date()) <= 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(flightDTO.getDepartureDate());
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date dayAfter = calendar.getTime();
			flightDTO.setDepartureDate(new Date());
			List<City> cities = cityRepository.getToCities(countryId, from, flightDTO.getDepartureDate(), dayAfter);
			return cities;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(flightDTO.getDepartureDate());
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date dayAfter = calendar.getTime();
			List<City> cities = cityRepository.getToCities(countryId, from, flightDTO.getDepartureDate(), dayAfter);
			return cities;
		}
	}

	public List<City> getReturningCities(Integer countryId, FlightDTO flightDTO) {
		List<City> cities = getToCities(countryId, flightDTO);
		Airport from = airportRepository.findById(flightDTO.getFromAirport()).get();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(flightDTO.getReturningDate());
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date dayAfter = calendar.getTime();
		cities = cityRepository.getReturningCities(from, flightDTO.getReturningDate(), dayAfter, cities);
		return cities;
	}
	
	public List<Airport> getToAirports(Integer cityId, FlightDTO flightDTO){
		Airport from = airportRepository.findById(flightDTO.getFromAirport()).get();
		if (flightDTO.getDepartureDate().compareTo(new Date()) <= 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(flightDTO.getDepartureDate());
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date dayAfter = calendar.getTime();
			flightDTO.setDepartureDate(new Date());
			List<Airport> airports = airportRepository.getToAirports(cityId, from, flightDTO.getDepartureDate(), dayAfter);
			return airports;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(flightDTO.getDepartureDate());
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date dayAfter = calendar.getTime();
			List<Airport> airports = airportRepository.getToAirports(cityId, from, flightDTO.getDepartureDate(), dayAfter);
			return airports;
		}
	}
	
	public List<Airport> getReturningAirports(Integer cityId, FlightDTO flightDTO){
		List<Airport> airports = getToAirports(cityId, flightDTO);
		Airport from = airportRepository.findById(flightDTO.getFromAirport()).get();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(flightDTO.getReturningDate());
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date dayAfter = calendar.getTime();
		airports = airportRepository.getReturningAirports(from, flightDTO.getReturningDate(), dayAfter, airports);
		return airports;
	}
	
	public List<Flight> getToFlights(Integer airportId, FlightDTO flightDTO){
		Airport from = airportRepository.findById(flightDTO.getFromAirport()).get();
		if (flightDTO.getDepartureDate().compareTo(new Date()) <= 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(flightDTO.getDepartureDate());
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date dayAfter = calendar.getTime();
			flightDTO.setDepartureDate(new Date());
			List<Flight> flights = flightRepository.getToFlights(from, flightDTO.getDepartureDate(), dayAfter);
			return flights;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(flightDTO.getDepartureDate());
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date dayAfter = calendar.getTime();
			List<Flight> flights = flightRepository.getToFlights(from, flightDTO.getDepartureDate(), dayAfter);
			return flights;
		}
	}
	
	public List<Flight> getReturningFlights(Integer airportID, FlightDTO flightDTO){
		List<Flight> flights = getToFlights(airportID, flightDTO);
		Airport from = airportRepository.findById(flightDTO.getFromAirport()).get();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(flightDTO.getReturningDate());
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date dayAfter = calendar.getTime();
		flights = flightRepository.getReturningFlights(from, flightDTO.getReturningDate(), dayAfter, flights);
		return flights;
	}
}
