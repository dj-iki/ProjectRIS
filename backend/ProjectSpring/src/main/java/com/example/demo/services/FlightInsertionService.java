package com.example.demo.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.FlightInsertionDTO;
import com.example.demo.dtos.PlaneDTO;
import com.example.demo.repositories.AirportRepository;
import com.example.demo.repositories.AppUserRepository;
import com.example.demo.repositories.FlightRepository;
import com.example.demo.repositories.PlaneRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.security.JWTService;

import jakarta.transaction.Transactional;
import model.Airport;
import model.AppUser;
import model.Flight;
import model.Plane;
import model.Role;
import model.Seat;

@Service
public class FlightInsertionService {

	Random r = new Random();

	@Autowired
	AirportRepository airportRepository;

	@Autowired
	AppUserRepository appUserRepository;

	@Autowired
	PlaneRepository planeRepository;

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	JWTService jwtService;

	public List<Airport> getAllAirports() {
		return airportRepository.findAll();
	}

	private AppUser getAppUser(String jwt) {
		String username = jwtService.extractUsername(jwt);
		AppUser appUser = appUserRepository.findAppUserByUsername(username);
		return appUser;
	}

	public List<Plane> getAllPlanes(String jwt) {
		AppUser appUser = getAppUser(jwt);
		List<Plane> planes = planeRepository.findAllByAirline(appUser.getAirline());
		return planes;
	}

	public List<AppUser> getAllEmployees(String jwt) {
		AppUser appUser = getAppUser(jwt);
		List<AppUser> employees = appUserRepository.findAllEmployees(appUser.getAirline());
		return employees;
	}

	@Transactional
	public String insertFlight(FlightInsertionDTO flightInsertionDTO) {
		try {
			Airport fromAirport = airportRepository.findById(flightInsertionDTO.getFromAirport()).get();
			Airport toAirport = airportRepository.findById(flightInsertionDTO.getToAirport()).get();
			Plane plane = planeRepository.findById(flightInsertionDTO.getPlane()).get();
			List<AppUser> employees = new ArrayList<>();
			for (Integer i : flightInsertionDTO.getEmployees()) {
				employees.add(appUserRepository.findById(i).get());
			}
			Flight flight = new Flight();
			flight.setAirport1(fromAirport);
			flight.setAirport2(toAirport);
			flight.setDepartureTime(new Timestamp(flightInsertionDTO.getDeparture().getTime()));
			flight.setArrivalTime(new Timestamp(flightInsertionDTO.getArrival().getTime()));
			flight.setFlightNumber(generateFlightNumber());
			flight.setPlane(plane);
			flight.setAppUsers(employees);
			flight = flightRepository.save(flight);
			for (AppUser employee : employees) {
				employee.getFlights().add(flight);
			}
			return "Successfull insertion";
		} catch (Exception e) {
			return "Error - there was an error with inserting flight - " + e.getMessage();
		}
	}

	private String generateFlightNumber() {
		String flightNumber;

		Set<String> flightNumbers = flightRepository.findAll().stream().map(flight -> flight.getFlightNumber())
				.collect(Collectors.toSet());
		do {
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < 10; i++) {
				stringBuilder.append(r.nextInt(0, 10));
			}
			flightNumber = stringBuilder.toString();
		} while (flightNumbers.contains(flightNumber));
		return flightNumber;
	}

	public boolean isPlaneAvailable(FlightInsertionDTO flightInsertionDTO) {
		Plane plane = planeRepository.findById(flightInsertionDTO.getPlane()).get();
		List<Flight> flights = flightRepository.findAllCollisions(plane, flightInsertionDTO.getDeparture(),
				flightInsertionDTO.getArrival());
		if (flights == null || flights.size() == 0)
			return true;

		return false;

	}

	@Transactional
	public String insertPlane(PlaneDTO planeDTO, String jwt) {
		try {
			if (planeRepository.findByRegistrationNumber(planeDTO.getRegistrationNumber()) != null)
				throw new IllegalArgumentException("Registration number allready in use");
			AppUser user = getAppUser(jwt);
			Plane plane = new Plane();
			plane.setAirline(user.getAirline());
			plane.setManufacturer(planeDTO.getManufacturer());
			plane.setModel(planeDTO.getModel());
			plane.setRegistrationNumber(planeDTO.getRegistrationNumber());
			plane.setNumberOfSeats(planeDTO.getNumberOfEconomySeats() + planeDTO.getNumberOfEconomyPlusSeats()
					+ planeDTO.getNumberOfBusinessSeats());
			List<Seat> seats = generateSeats(planeDTO);
			for (Seat seat : seats) {
				seat.setPlane(plane);
			}
			plane.setSeats(seats);
			plane = planeRepository.save(plane);

			return "Successfull insertion";
		} catch (Exception e) {
			return "Error - " + e.getMessage();
		}

	}

	private List<Seat> generateSeats(PlaneDTO planeDTO) {
		List<Seat> seats = new ArrayList<>();
		int numberOfRows = planeDTO.getNumberOfBusinessRows();
		for (int i = 1; i <= planeDTO.getNumberOfBusinessSeats() / numberOfRows; i++) {
			for (int j = 0; j < numberOfRows; j++) {
				Seat seat = new Seat();
				seat.setClass_("BUSINESS");
				seat.setPrice(planeDTO.getBusinessPrice());
				char row = (char) ('A' + j);
				String seatNumber = "" + i + row;
				seat.setSeatNumber(seatNumber);
				seats.add(seat);
			}
		}
		numberOfRows = planeDTO.getNumberOfEconomyPlusRows();
		for (int i = 1; i <= planeDTO.getNumberOfEconomyPlusSeats() / numberOfRows; i++) {
			for (int j = 0; j < numberOfRows; j++) {
				Seat seat = new Seat();
				seat.setClass_("ECONOMY_PLUS");
				seat.setPrice(planeDTO.getEconomyPlusPrice());
				char row = (char) ('A' + j);
				String seatNumber = "" + i + row;
				seat.setSeatNumber(seatNumber);
				seats.add(seat);
			}
		}
		numberOfRows = planeDTO.getNumberOfEconomyRows();
		for (int i = 1; i <= planeDTO.getNumberOfEconomySeats() / numberOfRows; i++) {
			for (int j = 0; j < numberOfRows; j++) {
				Seat seat = new Seat();
				seat.setClass_("ECONOMY");
				seat.setPrice(planeDTO.getEconomyPrice());
				char row = (char) ('A' + j);
				String seatNumber = "" + i + row;
				seat.setSeatNumber(seatNumber);
				seats.add(seat);
			}
		}
		return seats;
	}

	public String hireUser(String username, String jwt) {
		AppUser appUser = appUserRepository.findAppUserByUsername(username);
		AppUser manager = appUserRepository.findAppUserByUsername(jwtService.extractUsername(jwt));
		if (appUser == null)
			return "Error - There is no user with this username";
		else if (!appUser.getRole().getName().equals("USER"))
			return "Error - This user is allready employee";
		else if (appUser.getUsername().equals(manager.getUsername()))
			return "Error - You cannot hire yourself";
		Role role = roleRepository.findByName("EMPLOYEE");
		appUserRepository.hireAppUser(appUser, role, manager.getAirline());
		return "User was hired successfully";
	}
}
