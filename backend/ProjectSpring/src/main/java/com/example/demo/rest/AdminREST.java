package com.example.demo.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.AirlineDTO;
import com.example.demo.dtos.AirlinesResponse;
import com.example.demo.dtos.AirportDTO;
import com.example.demo.dtos.CitiesResponse;
import com.example.demo.dtos.CountriesResponse;
import com.example.demo.services.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/admin")
public class AdminREST {
	
	@Autowired
	AdminService adminService;
	
	@GetMapping("/data")
	public ResponseEntity<Map<String, List<? extends Object>>> getData(){
		List<CountriesResponse> countries = adminService.getCountries().stream().map(c -> new CountriesResponse(c.getIdCountry(), c.getName())).toList();
		List<CitiesResponse> cities = adminService.getCities().stream().map(c -> new CitiesResponse(c.getIdCity(), c.getName())).toList();
		List<AirlinesResponse> airlines = adminService.getAirlines().stream().map(a -> new AirlinesResponse(a.getIdAirlines(), a.getName())).toList();
		return ResponseEntity.ok(Map.of(
				"countries", countries,
				"cities", cities,
				"airlines", airlines
				));
	}
	
	
	@PostMapping("/save-country")
	public ResponseEntity<Map<String, String>> saveCountry(@RequestParam("country_name") String name) {
		String result = adminService.saveCountry(name);
		if(result.startsWith("Success"))
			return ResponseEntity.ok(Map.of("message", result));
		return ResponseEntity.badRequest().body(Map.of("message", result));
	}
	
	@PostMapping("/save-city")
	public ResponseEntity<Map<String, String>> saveCity(@RequestParam("city_name") String name, @RequestParam("country") Integer countryId) {
		String result = adminService.saveCity(name, countryId);
		if(result.startsWith("Success"))
			return ResponseEntity.ok(Map.of("message", result));
		return ResponseEntity.badRequest().body(Map.of("message", result));
	}
	
	@PostMapping("/save-airport")
	public ResponseEntity<Map<String, String>> saveAirport(@RequestBody AirportDTO airportDTO) {
		String result = adminService.saveAirport(airportDTO);
		if(result.startsWith("Success"))
			return ResponseEntity.ok(Map.of("message", result));
		return ResponseEntity.badRequest().body(Map.of("message", result));
	}
	
	@PostMapping("/save-airline")
	public ResponseEntity<Map<String, String>> saveAirport(@RequestBody AirlineDTO airlineDTO) {
		String result = adminService.saveAirline(airlineDTO);
		if(result.startsWith("Success"))
			return ResponseEntity.ok(Map.of("message", result));
		return ResponseEntity.badRequest().body(Map.of("message", result));
	}
	
	@PostMapping("/promote-employee")
	public ResponseEntity<Map<String, String>> promoteEmplyee(@RequestParam("username") String username, @RequestParam("airline") Integer airligneId) {
		String result = adminService.promoteEmployee(username, airligneId);
		if(result.startsWith("Success")) 
			return ResponseEntity.ok(Map.of("message", result));
		return ResponseEntity.badRequest().body(Map.of("message", result));
	}
}
