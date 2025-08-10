package com.example.demo.services;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
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
public class ManagerService {

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

	@Autowired
	JavaMailSender javaMailSender;
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
	
	public List<Flight> getFlights(String jwt) {
		String username = jwtService.extractUsername(jwt);
		AppUser manager = appUserRepository.findAppUserByUsername(username);
		Date now = new Date();
		return flightRepository.findAirlineFlihgts(manager.getAirline(), now);
	}
	
	@Transactional
	public String delayFlight(Integer flightId, Date departure, Date arrival) {
		try {
			Flight flight = flightRepository.findById(flightId).get();
			if(flight.getDepartureTime().before(departure) && flight.getArrivalTime().before(arrival) && departure.before(arrival)) {
				if(flightRepository.delayFlight(departure, arrival, flightId)!=0) {
					List<AppUser> appUsers = appUserRepository.getRecipients(flight);
					if(appUsers != null && appUsers.size()>0) {
						String[] recipients = appUsers.stream().map(user -> user.getEmail()).toArray(String[]::new);
						sendMailsDelayed(recipients, flight, departure);
					}
					return "Success - flight was delayed";
				}
			}else
				throw new IllegalArgumentException("You can not move flight to earlier");
			return "Error - chosen flight was not delayed";
		}catch(Exception e) {
			return "Error - " + e.getMessage();
		}
		
	}
	
	@Async
	private void sendMailsDelayed(String[] recipients, Flight flight, Date oldDeparture) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(recipients);
		message.setSubject("Flight delay");
		message.setText("Dear Passenger,\r\n"
				+ "\r\n"
				+ "We would like to inform you that your scheduled flight " + flight.getFlightNumber() + " from " + flight.getAirport1().getCity().getName() + " to " + flight.getAirport2().getCity().getName() + ", originally planned for " + oldDeparture.toString() + ", has been delayed.\r\n"
				+ "\r\n"
				+ "The new estimated departure time is " + flight.getDepartureTime().toString() + ".\r\n"
				+ "\r\n"
				+ "We apologize for any inconvenience this may cause and appreciate your understanding. If you have any questions or require assistance with rebooking, please contact our customer service team at [Phone Number] or [Email Address].\r\n"
				+ "\r\n"
				+ "Thank you for choosing " + flight.getPlane().getAirline().getName() + ".\r\n"
				+ "\r\n"
				+ "Sincerely,\r\n"
				+ flight.getPlane().getAirline().getName() + " Customer Service Team\r\n"
				);
		boolean sent = false;
		while(!sent) {
			try {
				javaMailSender.send(message);
				sent = true;
			}catch(MailException me) {
				System.out.println(me.getMessage());
				try {
	                Thread.sleep(2000); // wait 2 seconds before retrying
	            } catch (InterruptedException ie) {
	                Thread.currentThread().interrupt(); // clear the interrupt flag
	                throw new RuntimeException("Retry interrupted", ie);
	            }
			}
		}
	}
	
	@Transactional
	public String cancelFlight(Integer flightId) {
		try {
			Flight flight = flightRepository.findById(flightId).get();
			if(flightRepository.cancelFlight(flightId)!=0) {
				List<AppUser> appUsers = appUserRepository.getRecipients(flight);
				if(appUsers != null && appUsers.size()>0) {
					String[] recipients = appUsers.stream().map(user -> user.getEmail()).toArray(String[]::new);
					sendMailsCanceled(recipients, flight);
				}
				return "Success - flight was successfully canceled";
			}
			
			return "Error - flight was not canceled";	
			
		}catch(Exception e) {
			return "Error - " + e.getMessage();
		}
	}
	
	@Async
	private void sendMailsCanceled(String[] recipients, Flight flight){
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(recipients);
		message.setSubject("Flight cancellation");
		message.setText("Dear Passenger,\r\n"
				+ "\r\n"
				+ "We regret to inform you that your scheduled flight " + flight.getFlightNumber() + " from " + flight.getAirport1().getCity().getName() + " to " + flight.getAirport2().getCity().getName() + " on " + flight.getDepartureTime() + " has been cancelled.\r\n"
				+ "\r\n"
				+ "We sincerely apologize for the inconvenience caused. Our team is ready to assist you with:\r\n"
				+ "- Rebooking to an alternative flight\r\n"
				+ "- Offering a full refund of your ticket\r\n"
				+ "- Providing additional travel assistance as needed\r\n"
				+ "\r\n"
				+ "Please contact our support team at [Support Phone Number] or [Support Email] to arrange the next steps.\r\n"
				+ "\r\n"
				+ "Thank you for your understanding and patience.\r\n"
				+ "\r\n"
				+ "Sincerely,  \r\n"
				+ flight.getPlane().getAirline().getName() + " Customer Service");
		boolean sent = false;
		while(!sent) {
			try {
				javaMailSender.send(message);
				sent = true;
			}catch(MailException me) {
				System.out.println(me.getMessage());
				try {
	                Thread.sleep(2000); // wait 2 seconds before retrying
	            } catch (InterruptedException ie) {
	                Thread.currentThread().interrupt(); // clear the interrupt flag
	                throw new RuntimeException("Retry interrupted", ie);
	            }
			}
		}
		
	}
	
	@Scheduled(cron = "0 0 9 * * *")
	public void sendNotification() {
		LocalDate tomorrowLocalDate = LocalDate.now().plusDays(1);
		LocalDate dayAfterTomorrowLocalDate = LocalDate.now().plusDays(2);
		Date tomorrow = Date.from(tomorrowLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		Date dayAfterTomorrow = Date.from(dayAfterTomorrowLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		
		List<Flight> flights = flightRepository.getFlightsForNotification(tomorrow, dayAfterTomorrow);
		
		for(Flight flight : flights ) {
			List<String> emails = appUserRepository.getEmailsForNotification(flight);
			if(emails!=null && emails.size()>0) {
				String[] recipients = emails.stream().toArray(String[]::new);
				boolean sent = false;
				SimpleMailMessage message = new SimpleMailMessage();
				message.setTo(recipients);
				message.setSubject("Flight notification");
				message.setText("Dear Passenger,\r\n"
						+ "\r\n"
						+ "This is a friendly reminder that your flight with the number " + flight.getFlightNumber() + " from " + flight.getAirport1().getCity().getName() + " to " + flight.getAirport2().getCity().getName() + " is scheduled for tomorrow, " + flight.getDepartureTime() + ".\r\n"
						+ "\r\n"
						+ "Please ensure you arrive at the airport with sufficient time for check-in and security procedures.\r\n"
						+ "\r\n"
						+ "If you have any questions or need assistance, feel free to contact our support team at [Support Phone Number] or [Support Email].\r\n"
						+ "\r\n"
						+ "We wish you a pleasant journey!\r\n"
						+ "\r\n"
						+ "Sincerely,\r\n"
						+ flight.getPlane().getAirline().getName() + " Customer Service\r\n"
						);
				while(!sent) {
					try {
						javaMailSender.send(message);
						sent=true;
					}catch(MailException me) {
						System.out.println(me.getMessage());
						try {
							Thread.sleep(2000);
						}catch(InterruptedException ie) {
							Thread.currentThread().interrupt();
							throw new RuntimeException("Retry interrupted", ie);
						}
					}
				}
			}
		}
		
	}
}
