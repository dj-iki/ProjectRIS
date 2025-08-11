package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.AirlineDTO;
import com.example.demo.dtos.AirportDTO;
import com.example.demo.repositories.AirlineRepository;
import com.example.demo.repositories.AirportRepository;
import com.example.demo.repositories.AppUserRepository;
import com.example.demo.repositories.CityRepository;
import com.example.demo.repositories.CountryRepository;
import com.example.demo.repositories.RoleRepository;

import model.Airline;
import model.Airport;
import model.AppUser;
import model.City;
import model.Country;
import model.Role;

@Service
public class AdminService {

    private final PasswordEncoder getPasswordEncoder;
	
	@Autowired
	CountryRepository countryRepository;
	
	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	AirportRepository airportRepository;
	
	@Autowired
	AppUserRepository appUserRepository;
	
	@Autowired
	AirlineRepository airlineRepository;
	
	@Autowired
	RoleRepository roleRepository;

    AdminService(PasswordEncoder getPasswordEncoder) {
        this.getPasswordEncoder = getPasswordEncoder;
    }
	
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
			if(airportRepository.findByIata_code(airportDTO.getIataCode())!=null)
				throw new IllegalArgumentException("Airport with the same iata code allready exists");
			if(airportRepository.findByIcao_code(airportDTO.getIcaoCode())!=null)
				throw new IllegalArgumentException("Airport with the same icao code allready exists");
			City city = cityRepository.findById(airportDTO.getCityId()).get();
			Airport airport = new Airport();
			airport.setName(airportDTO.getName());
			airport.setIataCode(airportDTO.getIataCode());
			airport.setIcaoCode(airportDTO.getIcaoCode());
			airport.setCity(city);
			airportRepository.save(airport);
			return "Success - airport was added successfully";
		}catch(Exception e) {
			return "Error - " + e.getMessage();
		}
	}
	
	public List<Airline> getAirlines(){
		return airlineRepository.findAll();
	}
	
	public String promoteEmployee(String username, Integer airlineId) {
		try {
			Airline airline = airlineRepository.findById(airlineId).get();
			AppUser appUser = appUserRepository.findAppUserByUsername(username);
			if(appUser == null)
				throw new IllegalArgumentException("User with username - " + username + " - does not exist");
			Role role = roleRepository.findByName("MANAGER");
			if(airline.getAppUsers()!=null && airline.getAppUsers().size()>0) {
				if(appUser.getRole().getName().equals("EMPLOYEE") && appUser.getAirline().getIdAirlines() == airline.getIdAirlines()){
					if(appUserRepository.promoteEmployee(role, appUser)==0) {
						return "Error - employee was not promoted";
					}
				}else
					throw new IllegalArgumentException("User with username - " + username + " - is not an employee of this airline");
			}else if(appUser.getRole().getName().equals("USER")){
				if(appUserRepository.promoteUser(role, appUser, airline)==0)
					return "Error - user was not promoted";
			}else {
				throw new IllegalArgumentException("User with username - " + username + " - is allready a manager");
			}
			return "Success - user was successfully promoted to manager";
		}catch(Exception e) {
			return "Error - " + e.getMessage();
		}
	}
	
	public String saveAirline(AirlineDTO airlineDTO) {
		try {
			if(airlineRepository.findByName(airlineDTO.getName())!=null)
				throw new IllegalArgumentException("Airline with name - " + airlineDTO.getName() + " - allready exists");
			if(airlineRepository.findByIataCode(airlineDTO.getIataCode())!=null)
				throw new IllegalArgumentException("Airline with iata code - " + airlineDTO.getIataCode() + " - allready exists");
			if(airlineRepository.findByIcaoCode(airlineDTO.getIcaoCode())!=null)
				throw new IllegalArgumentException("Airline with icao code - " + airlineDTO.getIcaoCode() + " - allready exists");
			Airline airline = new Airline();
			airline.setName(airlineDTO.getName());
			airline.setIataCode(airlineDTO.getIataCode());
			airline.setIcaoCode(airlineDTO.getIcaoCode());
			airlineRepository.save(airline);
			return "Success - airline was added successfully";
		}catch(Exception e){
			return "Error - " + e.getMessage();
		}
	}
}
