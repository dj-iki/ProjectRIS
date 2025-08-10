package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.AirportDTO;
import com.example.demo.repositories.AirportRepository;
import com.example.demo.repositories.CityRepository;
import com.example.demo.repositories.CountryRepository;

import model.Airport;
import model.City;
import model.Country;

@Service
public class AdminService {
	
	@Autowired
	CountryRepository countryRepository;
	
	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	AirportRepository airportRepository;
	
	public String saveCountry(String name) {
		try {
			if(countryRepository.findByName(name) != null)
				throw new IllegalArgumentException("Country was allready added");
			Country country = new Country();
			country.setName(name);
			countryRepository.save(country);
			return "Success - country successfully added";
		}catch(Exception e) {
			return "Error - " + e.getMessage();
		}
	}

	public List<Country> getCountries() {
		return countryRepository.findAll();
	}

	public String saveCity(String name, Integer countryId) {
		try {
			Country country = countryRepository.findById(countryId).get();
			City city = new City();
			city.setName(name);
			city.setCountry(country);
			cityRepository.save(city);
			return "Success - city was added successfully";
		}catch(Exception e) {
			return "Error - " + e.getMessage();
		}
	}
	
	public List<City> getCities(){
		return cityRepository.findAll();
	}
	
	
	public String saveAirport(AirportDTO airportDTO) {
		try {
			if(airportRepository.findByIata_code(airportDTO.getIata_code())!=null)
				throw new IllegalArgumentException("Airport with the same iata code allready exists");
			if(airportRepository.findByIcao_code(airportDTO.getIcao_code())!=null)
				throw new IllegalArgumentException("Airport with the same icao code allready exists");
			City city = cityRepository.findById(airportDTO.getCityId()).get();
			Airport airport = new Airport();
			airport.setName(airportDTO.getName());
			airport.setIataCode(airportDTO.getIata_code());
			airport.setIcaoCode(airportDTO.getIcao_code());
			airport.setCity(city);
			airportRepository.save(airport);
			return "Success - airport was added successfully";
		}catch(Exception e) {
			return "Error - " + e.getMessage();
		}
	}
	
}
