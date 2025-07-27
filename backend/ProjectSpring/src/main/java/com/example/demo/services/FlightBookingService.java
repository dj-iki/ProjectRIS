package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.FlightRepository;
import com.example.demo.repositories.PlaneRepository;
import com.example.demo.repositories.SeatRepository;

import model.Flight;
import model.Seat;

@Service
public class FlightBookingService {
	
	@Autowired
	SeatRepository seatRepository;
	
	@Autowired
	FlightRepository flightRepository;
	
	
	public List<Seat> getAvailabelSeats(Integer flightId){
		Flight flight = flightRepository.findById(flightId).get();
		List<Seat> seats = seatRepository.getAvailableSeats(flight.getPlane(), flight);
		return seats;
	}
	
}
