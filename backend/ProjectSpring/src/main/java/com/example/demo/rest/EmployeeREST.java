package com.example.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.FlightResponse;
import com.example.demo.services.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/employee")
public class EmployeeREST {
	
	@Autowired
	EmployeeService service;
	
	@GetMapping("/flights")
	public ResponseEntity<List<FlightResponse>> getFlights(HttpServletRequest request){
		String jwt = request.getHeader("Authorization").substring(7);
		List<FlightResponse> flights = service.getEmployeeFlightsJwt(jwt).stream().map( f -> new FlightResponse(
				f.getIdFlight(),
				f.getDepartureTime(),
				f.getArrivalTime(),
				f.getFlightNumber(),
				f.getAirport1().getIdAirport(),
				f.getAirport2().getIdAirport(),
				f.getAirport1().getName(),
				f.getAirport2().getName(),
				f.getAirport1().getIataCode(),
				f.getAirport2().getIataCode(),
				f.getPlane().getAirline().getName(),
				f.getPlane().getAirline().getIataCode())).toList();
		if(flights!=null && flights.size()>0)
			return ResponseEntity.ok(flights);
		return ResponseEntity.badRequest().build();
	}

}
