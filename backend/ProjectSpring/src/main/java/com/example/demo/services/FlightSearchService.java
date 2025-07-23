package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.AirportRepository;

import model.Airport;

@Service
public class FlightSearchService {
	
	@Autowired
	AirportRepository airportRepository;
	
	public List<Airport> getAllAirports(){
		return airportRepository.findAll();
	}
}
