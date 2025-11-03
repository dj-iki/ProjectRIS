package com.example.demo.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.AirportResponse;
import com.example.demo.dtos.CancelDTO;
import com.example.demo.dtos.EmployeeResponse;
import com.example.demo.dtos.FlightDelayDTO;
import com.example.demo.dtos.FlightInsertionDTO;
import com.example.demo.dtos.FlightRequest;
import com.example.demo.dtos.HireUserRequest;
import com.example.demo.dtos.PlaneDTO;
import com.example.demo.dtos.PlaneResponse;
import com.example.demo.services.ManagerService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/manager")
public class ManagerREST {

	@Autowired
	ManagerService service;
	
	@GetMapping("/flight")
	public ResponseEntity<Map<String, List<? extends Object>>> getFlightParams(HttpServletRequest request){
		String jwt = request.getHeader("Authorization").substring(7);
		List<AirportResponse> airports = service.getAllAirports().stream().map( a -> new AirportResponse(a.getIdAirport(), a.getName(), a.getIataCode())).toList();
		List<PlaneResponse> planes = service.getAllPlanes(jwt).stream().map( p -> new PlaneResponse(p.getIdPlane(), p.getManufacturer(), p.getModel(), p.getRegistrationNumber())).toList();
		List<EmployeeResponse> employees = service.getAllEmployees(jwt).stream().map( au -> new EmployeeResponse(au.getIdUsers(), au.getName(), au.getSurname())).toList();
		if(airports == null || planes == null || employees == null || airports.isEmpty() || planes.isEmpty() || employees.isEmpty())
			return ResponseEntity.badRequest().build();
		return ResponseEntity.ok(Map.of(
				"airports", airports,
				"planes", planes,
				"employees", employees
				));
	}
	
	@PostMapping("/save-flight")
	public ResponseEntity<Map<String, String>> saveFlight(@RequestBody FlightInsertionDTO flight){
		if (flight.getArrival() != null && flight.getDeparture() != null
				&& flight.getArrival().before(flight.getDeparture()))
			return ResponseEntity.badRequest().body(Map.of("message", "Arrival cannot be before departure"));
		if (!service.isPlaneAvailable(flight))
			return ResponseEntity.badRequest().body(Map.of("message", "Plane is not available in that time"));
		if (flight.getFromAirport() == flight.getToAirport())
			return ResponseEntity.badRequest().body(Map.of("message", "Airports must be different"));
		String result = service.insertFlight(flight);
		if(result.startsWith("Successfull"))
			return ResponseEntity.ok(Map.of("message", result));
		return ResponseEntity.badRequest().body(Map.of("message", result));
	}
	
	@PostMapping("/hire-user")
	public ResponseEntity<Map<String, String>> hireUser(@RequestBody HireUserRequest hireUser, HttpServletRequest request){
		String jwt = request.getHeader("Authorization").substring(7);
		String result = service.hireUser(hireUser.getUsername(), jwt);
		if(result.startsWith("Error"))
			return ResponseEntity.badRequest().body(Map.of("message", result));
		return ResponseEntity.ok(Map.of("message", result));
	}
	
	@PostMapping("/add-plane")
	public ResponseEntity<Map<String, String>> addPlane(@RequestBody PlaneDTO planeDTO, HttpServletRequest request){
		if (planeDTO.getNumberOfEconomySeats() != null && planeDTO.getNumberOfEconomyRows() != null
				&& planeDTO.getNumberOfEconomySeats() % planeDTO.getNumberOfEconomyRows() != 0)
			return ResponseEntity.badRequest().body(Map.of("message",
					"Number of economy seats must be devisialbe with number of economy rows"));
		if (planeDTO.getNumberOfEconomyPlusSeats() != null && planeDTO.getNumberOfEconomyPlusRows() != null
				&& planeDTO.getNumberOfEconomyPlusSeats() % planeDTO.getNumberOfEconomyPlusRows() != 0)
			return ResponseEntity.badRequest().body(Map.of("message",
					"Number of economy plus seats must be devisialbe with number of economy plus rows"));
		if (planeDTO.getNumberOfBusinessSeats() != null && planeDTO.getNumberOfBusinessRows() != null
				&& planeDTO.getNumberOfBusinessSeats() % planeDTO.getNumberOfBusinessRows() != 0)
			return ResponseEntity.badRequest().body(Map.of("message",
					"Number of economy seats must be devisialbe with number of economy rows"));
		String jwt = request.getHeader("Authorization").substring(7);
		String insertionResult = service.insertPlane(planeDTO, jwt);
		if (insertionResult.startsWith("Successfull"))
			return ResponseEntity.ok(Map.of("message", insertionResult));
		return ResponseEntity.badRequest().body(Map.of("message", insertionResult));
	}
	
	@GetMapping("/flights")
	public ResponseEntity<List<FlightRequest>> getFlights(HttpServletRequest request){
		String jwt = request.getHeader("Authorization").substring(7);
		List<FlightRequest> flights = service.getFlights(jwt).stream().map( f -> new FlightRequest(f.getIdFlight(), f.getDepartureTime(), f.getArrivalTime(), f.getAirport1().getName(), f.getAirport2().getName())).toList();
		if(flights != null && !flights.isEmpty()) {
			return ResponseEntity.ok(flights);
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/delay")
	public ResponseEntity<Map<String, String>> delayFlight(@RequestBody FlightDelayDTO delayDTO){
		String result = service.delayFlight(delayDTO.getFlightId(), delayDTO.getDeparture(), delayDTO.getArrival());
		if(result.startsWith("Success"))
			return ResponseEntity.ok(Map.of("message", result));
		return ResponseEntity.badRequest().body(Map.of("message", result));
	}
	
	@PostMapping("/cancel")
	public ResponseEntity<Map<String, String>> cancelFlight(@RequestBody CancelDTO request){
		String result = service.cancelFlight(request.getId());
		if(result.startsWith("Success"))
			return ResponseEntity.ok(Map.of("message", result));
		return ResponseEntity.badRequest().body(Map.of("message", result));
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		simpleDateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(simpleDateFormat, true));
	}
}
