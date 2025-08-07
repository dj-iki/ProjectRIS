package com.example.demo.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.FlightSearchDTO;
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

	public List<Flight> getAllFlightsFromTo(FlightSearchDTO flightDTO) {
		try {
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
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public List<Country> getToCountries(FlightSearchDTO flightDTO) {
		try {
			Airport from = airportRepository.findById(flightDTO.getFromAirport()).get();
			if (flightDTO.getDepartureDate().compareTo(new Date()) <= 0) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(flightDTO.getDepartureDate());
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				Date dayAfter = calendar.getTime();
				flightDTO.setDepartureDate(new Date());
				List<Country> countries = countryRepository.getToCountries(from, flightDTO.getDepartureDate(),
						dayAfter, flightDTO.getNumberOfSeats());
				return countries;
			} else {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(flightDTO.getDepartureDate());
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				Date dayAfter = calendar.getTime();
				List<Country> countries = countryRepository.getToCountries(from, flightDTO.getDepartureDate(),
						dayAfter, flightDTO.getNumberOfSeats());
				return countries;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public List<Flight> getReturningFlights(FlightSearchDTO flightDTO) {
		try {
			Airport from = airportRepository.findById(flightDTO.getToAirport()).get();
			Airport to = airportRepository.findById(flightDTO.getFromAirport()).get();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(flightDTO.getReturningDate());
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date dayAfter = calendar.getTime();
			List<Flight> flights = flightRepository.getAllFlightsFromTo(from, to, flightDTO.getReturningDate(),
					dayAfter);
			return flights;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public List<Country> getReturningCountries(FlightSearchDTO flightDTO) {
		try {
			List<Country> countries = getToCountries(flightDTO);
			Airport from = airportRepository.findById(flightDTO.getFromAirport()).get();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(flightDTO.getReturningDate());
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date dayAfter = calendar.getTime();
			countries = countryRepository.getReturningCountries(from, flightDTO.getReturningDate(), dayAfter,
					countries, flightDTO.getNumberOfSeats());
			return countries;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public List<City> getToCities(Integer countryId, FlightSearchDTO flightDTO) {
		try {
			Airport from = airportRepository.findById(flightDTO.getFromAirport()).get();
			if (flightDTO.getDepartureDate().compareTo(new Date()) <= 0) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(flightDTO.getDepartureDate());
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				Date dayAfter = calendar.getTime();
				flightDTO.setDepartureDate(new Date());
				List<City> cities = cityRepository.getToCities(countryId, from, flightDTO.getDepartureDate(), dayAfter, flightDTO.getNumberOfSeats());
				return cities;
			} else {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(flightDTO.getDepartureDate());
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				Date dayAfter = calendar.getTime();
				List<City> cities = cityRepository.getToCities(countryId, from, flightDTO.getDepartureDate(), dayAfter, flightDTO.getNumberOfSeats());
				return cities;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public List<City> getReturningCities(Integer countryId, FlightSearchDTO flightDTO) {
		try {
			List<City> cities = getToCities(countryId, flightDTO);
			Airport from = airportRepository.findById(flightDTO.getFromAirport()).get();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(flightDTO.getReturningDate());
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date dayAfter = calendar.getTime();
			cities = cityRepository.getReturningCities(from, flightDTO.getReturningDate(), dayAfter, cities, flightDTO.getNumberOfSeats());
			return cities;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public List<Airport> getToAirports(Integer cityId, FlightSearchDTO flightDTO) {
		try {
			Airport from = airportRepository.findById(flightDTO.getFromAirport()).get();
			if (flightDTO.getDepartureDate().compareTo(new Date()) <= 0) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(flightDTO.getDepartureDate());
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				Date dayAfter = calendar.getTime();
				flightDTO.setDepartureDate(new Date());
				List<Airport> airports = airportRepository.getToAirports(cityId, from, flightDTO.getDepartureDate(),
						dayAfter, flightDTO.getNumberOfSeats());
				return airports;
			} else {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(flightDTO.getDepartureDate());
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				Date dayAfter = calendar.getTime();
				List<Airport> airports = airportRepository.getToAirports(cityId, from, flightDTO.getDepartureDate(),
						dayAfter, flightDTO.getNumberOfSeats());
				return airports;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public List<Airport> getReturningAirports(Integer cityId, FlightSearchDTO flightDTO) {
		try {
			List<Airport> airports = getToAirports(cityId, flightDTO);
			Airport from = airportRepository.findById(flightDTO.getFromAirport()).get();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(flightDTO.getReturningDate());
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date dayAfter = calendar.getTime();
			airports = airportRepository.getReturningAirports(from, flightDTO.getReturningDate(), dayAfter, airports, flightDTO.getNumberOfSeats());
			return airports;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public List<Flight> getToFlights(Integer airportId, FlightSearchDTO flightDTO) {
		try {
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
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public List<Flight> getReturningFlights(Integer airportID, FlightSearchDTO flightDTO) {
		try {
			List<Flight> flights = getToFlights(airportID, flightDTO);
			Airport from = airportRepository.findById(flightDTO.getFromAirport()).get();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(flightDTO.getReturningDate());
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date dayAfter = calendar.getTime();
			flights = flightRepository.getReturningFlights(from, flightDTO.getReturningDate(), dayAfter, flights);
			return flights;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public String getAirportById(Integer id) {
		Airport airport = airportRepository.findById(id).get();
		return airport.getName() + " (" + airport.getIataCode() + ")";
	}
}
