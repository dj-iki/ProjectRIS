package com.example.demo.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.BookingDTO;
import com.example.demo.dtos.SeatResponse;
import com.example.demo.services.FlightBookingService;

import jakarta.servlet.http.HttpServletRequest;
import model.Seat;

@RestController
@RequestMapping("/api/booking")
public class BookingREST {
	
	@Autowired
	FlightBookingService flightBookingService;
	
	@PostMapping("/book")
	public ResponseEntity<?> bookFligt(@RequestBody BookingDTO bookingRequest, HttpServletRequest request){
		String jwt = request.getHeader("Authorization").substring(7);
		String result = flightBookingService.saveBooking(bookingRequest, jwt);
		if(result.startsWith("Error")) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(Map.of("success", result));
	}
	
	@GetMapping("/seats")
	public ResponseEntity<List<SeatResponse>> getAvailableSeats(@RequestParam int flightId){
		List<Seat> seats = flightBookingService.getAvailabelSeats(flightId);
		for(Seat seat: seats) {
			System.out.print(seat.getIdSeat());
		}
		if(seats != null && !seats.isEmpty()) {
			List<SeatResponse> response = seats.stream().map(s -> new SeatResponse(s.getIdSeat(), s.getSeatNumber(), s.getClass_())).toList();
			return ResponseEntity.ok(response);
		}
		System.out.println((seats != null && !seats.isEmpty()));
		return ResponseEntity.badRequest().build();
	}

}
