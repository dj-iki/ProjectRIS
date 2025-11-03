package com.example.demo.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controllers.FlightSearchController;
import com.example.demo.dtos.AirportResponse;
import com.example.demo.dtos.CitiesResponse;
import com.example.demo.dtos.CountriesResponse;
import com.example.demo.dtos.FlightResponse;
import com.example.demo.dtos.FlightSearchDTO;
import com.example.demo.services.FlightSearchService;

import model.Airport;
import model.Flight;

@RestController
@RequestMapping("/api/search")
public class SearchREST {

    private final FlightSearchController flightSearchController;
	
	@Autowired
	FlightSearchService flightSearchService;

    SearchREST(FlightSearchController flightSearchController) {
        this.flightSearchController = flightSearchController;
    }
	
	@GetMapping("/airports")
	public ResponseEntity<List<AirportResponse>> getAirports(){
		List<AirportResponse> airports = mapAirports(flightSearchService.getAllAirports());
		if(airports != null || !airports.isEmpty()) {
			return ResponseEntity.ok(airports);
		}
		return ResponseEntity.internalServerError().build();
	}

	private List<AirportResponse> mapAirports(List<Airport> airports){
		List<AirportResponse> responses = new ArrayList<>();
		for(Airport airport : airports) {
			AirportResponse response = new AirportResponse();
			response.setId(airport.getIdAirport());
			response.setName(airport.getName());
			response.setIataCode(airport.getIataCode());
			responses.add(response);
		}
		return responses;
	}
	
	@GetMapping("/flights")
	public ResponseEntity<List<FlightResponse>> getFlights(
			@RequestParam Integer fromAirport,
			@RequestParam Integer toAirport,
			@RequestParam Integer numberOfSeats,
			@RequestParam Date departureDate){
		FlightSearchDTO flightDTO = new FlightSearchDTO();
		flightDTO.setDepartureDate(departureDate);
		flightDTO.setFromAirport(fromAirport);
		flightDTO.setNumberOfSeats(numberOfSeats);
		flightDTO.setToAirport(toAirport);
		List<FlightResponse> response = mapFlights(flightSearchService.getAllFlightsFromTo(flightDTO));
		if(response != null && !response.isEmpty())
			return ResponseEntity.ok(response);
		return ResponseEntity.badRequest().build();
	}
	
	private List<FlightResponse> mapFlights(List<Flight> flights){
		List<FlightResponse> responses = new ArrayList<>();
		for(Flight flight : flights) {
			FlightResponse response = new FlightResponse();
			response.setIdFlight(flight.getIdFlight());
			response.setAirportFromId(flight.getAirport1().getIdAirport());
			response.setAirportFromName(flight.getAirport1().getName());
			response.setAirportFromIata(flight.getAirport1().getIataCode());
			response.setAirportToId(flight.getAirport2().getIdAirport());
			response.setAirportToName(flight.getAirport2().getName());
			response.setAirportToIata(flight.getAirport2().getIataCode());
			response.setArrivalTime(flight.getArrivalTime());
			response.setDepartureTime(flight.getDepartureTime());
			response.setFlightNumber(flight.getFlightNumber());
			response.setAirlinesName(flight.getPlane().getAirline().getName());
			response.setAirlinesIataCode(flight.getPlane().getAirline().getIataCode());
			responses.add(response);
		}
		return responses;
	}
	
	@GetMapping("/returning")
	public ResponseEntity<Map<String, Object>> getReturning(
			@RequestParam Integer fromAirport,
			@RequestParam Integer toAirport,
			@RequestParam Integer numberOfSeats,
			@RequestParam Date departureDate,
			@RequestParam Date returningDate){
		FlightSearchDTO flightDTO = new FlightSearchDTO();
		flightDTO.setDepartureDate(departureDate);
		flightDTO.setFromAirport(fromAirport);
		flightDTO.setNumberOfSeats(numberOfSeats);
		flightDTO.setToAirport(toAirport);
		flightDTO.setReturningDate(returningDate);
		List<FlightResponse> responseTo = mapFlights(flightSearchService.getAllFlightsFromTo(flightDTO));
		List<FlightResponse> responseReturning = mapFlights(flightSearchService.getReturningFlights(flightDTO));
		if(responseTo != null && responseReturning != null && !responseTo.isEmpty() && !responseReturning.isEmpty()) {
			return ResponseEntity.ok(Map.of("responseTo", responseTo, "responseReturning", responseReturning));
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/countries")
	public ResponseEntity<List<CountriesResponse>> getCountries(
			@RequestParam Integer fromAirport,
			@RequestParam Integer numberOfSeats,
			@RequestParam Date departureDate){
		FlightSearchDTO flightSearchDTO = new FlightSearchDTO();
		flightSearchDTO.setFromAirport(fromAirport);
		flightSearchDTO.setNumberOfSeats(numberOfSeats);
		flightSearchDTO.setDepartureDate(departureDate);
		List<CountriesResponse> response = flightSearchService.getToCountries(flightSearchDTO).stream().map(c -> new CountriesResponse(c.getIdCountry(), c.getName())).toList();
		if(response != null && !response.isEmpty())
			return ResponseEntity.ok(response);
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/returning-countries")
	public ResponseEntity<List<CountriesResponse>> getReturningCountries(
			@RequestParam Integer fromAirport,
			@RequestParam Integer numberOfSeats,
			@RequestParam Date departureDate,
			@RequestParam Date returningDate){
		FlightSearchDTO flightSearchDTO = new FlightSearchDTO();
		flightSearchDTO.setFromAirport(fromAirport);
		flightSearchDTO.setNumberOfSeats(numberOfSeats);
		flightSearchDTO.setDepartureDate(departureDate);
		flightSearchDTO.setReturningDate(returningDate);
		List<CountriesResponse> response = flightSearchService.getReturningCountries(flightSearchDTO).stream().map(c -> new CountriesResponse(c.getIdCountry(), c.getName())).toList();
		if(response != null && !response.isEmpty())
			return ResponseEntity.ok(response);
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/cities")
	public ResponseEntity<List<CitiesResponse>> getCities(
			@RequestParam Integer fromAirport,
			@RequestParam Integer numberOfSeats,
			@RequestParam Date departureDate,
			@RequestParam Integer idCountry){
		FlightSearchDTO flightDTO = new FlightSearchDTO();
		flightDTO.setDepartureDate(departureDate);
		flightDTO.setFromAirport(fromAirport);
		flightDTO.setNumberOfSeats(numberOfSeats);
		List<CitiesResponse> response = flightSearchService.getToCities(idCountry, flightDTO).stream().map( c -> new CitiesResponse(c.getIdCity(), c.getName())).toList();
		if(response != null && !response.isEmpty())
			return ResponseEntity.ok(response);
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/returning-cities")
	public ResponseEntity<List<CitiesResponse>> getReturningCities(
			@RequestParam Integer fromAirport,
			@RequestParam Integer numberOfSeats,
			@RequestParam Date departureDate,
			@RequestParam Date returningDate,
			@RequestParam Integer idCountry){
		FlightSearchDTO flightDTO = new FlightSearchDTO();
		flightDTO.setDepartureDate(departureDate);
		flightDTO.setReturningDate(returningDate);
		flightDTO.setFromAirport(fromAirport);
		flightDTO.setNumberOfSeats(numberOfSeats);
		List<CitiesResponse> response = flightSearchService.getToCities(idCountry, flightDTO).stream().map( c -> new CitiesResponse(c.getIdCity(), c.getName())).toList();
		if(response != null && !response.isEmpty())
			return ResponseEntity.ok(response);
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/airports-to")
	public ResponseEntity<List<AirportResponse>> getAirports(
			@RequestParam Integer fromAirport,
			@RequestParam Integer numberOfSeats,
			@RequestParam Date departureDate,
			@RequestParam Integer idCity){
		FlightSearchDTO flightDTO = new FlightSearchDTO();
		flightDTO.setDepartureDate(departureDate);
		flightDTO.setFromAirport(fromAirport);
		flightDTO.setNumberOfSeats(numberOfSeats);
		List<AirportResponse> response = flightSearchService.getToAirports(idCity, flightDTO).stream().map(a -> new AirportResponse(a.getIdAirport(), a.getName(), a.getIataCode())).toList();
		if(response != null && !response.isEmpty())
			return ResponseEntity.ok(response);
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/returning-airports")
	public ResponseEntity<List<AirportResponse>> getReturningAirports(
			@RequestParam Integer fromAirport,
			@RequestParam Integer numberOfSeats,
			@RequestParam Date departureDate,
			@RequestParam Date returningDate,
			@RequestParam Integer idCity){
		FlightSearchDTO flightDTO = new FlightSearchDTO();
		flightDTO.setDepartureDate(departureDate);
		flightDTO.setReturningDate(returningDate);
		flightDTO.setFromAirport(fromAirport);
		flightDTO.setNumberOfSeats(numberOfSeats);
		List<AirportResponse> response = flightSearchService.getToAirports(idCity, flightDTO).stream().map(a -> new AirportResponse(a.getIdAirport(), a.getName(), a.getIataCode())).toList();
		if(response != null && !response.isEmpty())
			return ResponseEntity.ok(response);
		return ResponseEntity.badRequest().build();
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
}
