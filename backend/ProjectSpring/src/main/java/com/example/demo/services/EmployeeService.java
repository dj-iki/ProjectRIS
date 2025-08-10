package com.example.demo.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.AppUserRepository;
import com.example.demo.repositories.FlightRepository;

import model.AppUser;
import model.Flight;

@Service
public class EmployeeService {
	
	@Autowired
	AppUserRepository appUserRepository;
	
	@Autowired
	FlightRepository flightRepository;
	
	public List<Flight> getEmployeeFlights(String jwt){
		AppUser appUser = appUserRepository.findAppUserByUsername(jwt);
		Date date = new Date();
		return flightRepository.findAllEmployeeFlights(appUser, date);
		
	}
}
