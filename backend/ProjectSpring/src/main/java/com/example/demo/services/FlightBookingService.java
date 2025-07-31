package com.example.demo.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.BookingDTO;
import com.example.demo.dtos.ReportDataDTO;
import com.example.demo.dtos.TicketDTO;
import com.example.demo.repositories.AppUserRepository;
import com.example.demo.repositories.BookingRepository;
import com.example.demo.repositories.FlightRepository;
import com.example.demo.repositories.SeatRepository;
import com.example.demo.repositories.TicketRepository;
import com.example.demo.security.JWTService;

import jakarta.mail.internet.MimeMessage;
import model.AppUser;
import model.Booking;
import model.Flight;
import model.Seat;
import model.Ticket;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@Service
public class FlightBookingService {
	


	@Autowired
	SeatRepository seatRepository;

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	AppUserRepository appUserRepository;

	@Autowired
	BookingRepository bookingRepository;

	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	JWTService jwtService;

	public List<Seat> getAvailabelSeats(Integer flightId) {
		Flight flight = flightRepository.findById(flightId).get();
		List<Seat> seats = seatRepository.getAvailableSeats(flight.getPlane(), flight);
		return seats;
	}

	public String saveBooking(BookingDTO bookingDTO, String jwt) {
		try {
			String username = jwtService.extractUsername(jwt);
			AppUser user = appUserRepository.findAppUserByUsername(username);
			Map<String, Object> params = new HashMap<>();
			params.put("name", user.getName());
			params.put("surname", user.getSurname());
			params.put("email", user.getEmail());
			if (isValidTickets(bookingDTO.getTicketsFromDTO())) {
				List<ReportDataDTO> reportData = new ArrayList<>();
				
				Flight flightFrom = flightRepository.findById(bookingDTO.getFlightIdFrom()).get();
				Booking bookingFrom = new Booking();
				bookingFrom.setAppUser(user);
				bookingFrom.setFlight(flightFrom);
				bookingFrom.setNumberOfSeats(bookingDTO.getNumberOfSeats());
				List<Ticket> ticketsFrom = new ArrayList<>();
				float price = 0;
				for (TicketDTO ticketDTO : bookingDTO.getTicketsFromDTO()) {
					Ticket ticket = new Ticket();
					ticket.setName(ticketDTO.getName());
					ticket.setSurname(ticketDTO.getSurname());
					ticket.setBaggage(ticketDTO.getBaggage());
					ticket.setPassportNumber(ticketDTO.getPassportNumber());
					ticket.setSeat(seatRepository.findById(ticketDTO.getSeatId()).get());
					ticket.setBooking(bookingFrom);
					ticketsFrom.add(ticket);
					price += seatRepository.getPrice(ticketDTO.getSeatId());
				}
				bookingFrom.setPrice(price);
				bookingFrom.setTickets(ticketsFrom);
				bookingFrom = bookingRepository.save(bookingFrom);
				
				
				for(Ticket ticket : ticketsFrom) {
					ReportDataDTO reportDataDTO = new ReportDataDTO();
					reportDataDTO.setTicketHolderName(ticket.getName());
					reportDataDTO.setTicketHolderSurname(ticket.getSurname());
					reportDataDTO.setPassportNumber(ticket.getPassportNumber());
					reportDataDTO.setDeparture(flightFrom.getDepartureTime());
					reportDataDTO.setSeatClass(ticket.getSeat().getClass_());
					reportDataDTO.setSeatNumber(ticket.getSeat().getSeatNumber());
					reportDataDTO.setFrom(ticket.getBooking().getFlight().getAirport1().getIataCode());
					reportDataDTO.setTo(ticket.getBooking().getFlight().getAirport2().getIataCode());
					reportDataDTO.setAirline(ticket.getBooking().getFlight().getPlane().getAirline().getName());
					reportData.add(reportDataDTO);
				}
				
				
				
				if (bookingDTO.getFlightIdReturning() != null) {
					if (isValidTickets(bookingDTO.getTicketsReturningDTO())) {
						Flight returningFlight = flightRepository.findById(bookingDTO.getFlightIdReturning()).get();
						Booking bookingReturning = new Booking();
						bookingReturning.setAppUser(user);
						bookingReturning.setFlight(returningFlight);
						bookingReturning.setNumberOfSeats(bookingDTO.getNumberOfSeats());
						List<Ticket> ticketsReturning = new ArrayList<>();
						price = 0;
						for (TicketDTO ticketDTO : bookingDTO.getTicketsReturningDTO()) {
							Ticket ticket = new Ticket();
							ticket.setName(ticketDTO.getName());
							ticket.setSurname(ticketDTO.getSurname());
							ticket.setBaggage(ticketDTO.getBaggage());
							ticket.setPassportNumber(ticketDTO.getPassportNumber());
							ticket.setSeat(seatRepository.findById(ticketDTO.getSeatId()).get());
							ticket.setBooking(bookingReturning);
							ticketsReturning.add(ticket);
							price += seatRepository.getPrice(ticketDTO.getSeatId());
						}
						bookingReturning.setPrice(price);
						bookingReturning.setTickets(ticketsReturning);
						bookingReturning = bookingRepository.save(bookingReturning);
						
						for(Ticket ticket : ticketsReturning) {
							ReportDataDTO reportDataDTO = new ReportDataDTO();
							reportDataDTO.setTicketHolderName(ticket.getName());
							reportDataDTO.setTicketHolderSurname(ticket.getSurname());
							reportDataDTO.setPassportNumber(ticket.getPassportNumber());
							reportDataDTO.setDeparture(flightFrom.getDepartureTime());
							reportDataDTO.setSeatClass(ticket.getSeat().getClass_());
							reportDataDTO.setSeatNumber(ticket.getSeat().getSeatNumber());
							reportDataDTO.setFrom(ticket.getBooking().getFlight().getAirport1().getIataCode());
							reportDataDTO.setTo(ticket.getBooking().getFlight().getAirport2().getIataCode());
							reportDataDTO.setAirline(ticket.getBooking().getFlight().getPlane().getAirline().getName());
							reportData.add(reportDataDTO);
						}
					} else
						return "Error with booking - Each passenger must have a unique seat. You cannot select the same seat more than once.";
				}
				if(sendTicketsViaEmail(reportData, params))
					return "You booked your tickets successfully";
				else
					return "Error with booking - there was an error with sending email";
			}
			return "Error with booking - Each passenger must have a unique seat. You cannot select the same seat more than once.";
		} catch (Exception e) {
			return "Error with booking - " + e.getMessage();
		}
	}

	private boolean isValidTickets(List<TicketDTO> tickets) {
		Set<Integer> set = new HashSet<>();
		for (TicketDTO ticket : tickets) {
			if (set.contains(ticket.getSeatId()))
				return false;
			set.add(ticket.getSeatId());
		}
		return true;
	}
	
	private byte[] generateReport(List<ReportDataDTO> reportData, Map<String, Object> params) throws JRException, IOException {
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportData);
		InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/ticketTamplate.jrxml");
		JasperReport report = JasperCompileManager.compileReport(inputStream);
		JasperPrint print = JasperFillManager.fillReport(report, params, dataSource);
		inputStream.close();
		return JasperExportManager.exportReportToPdf(print);
	}
	
	private boolean sendTicketsViaEmail(List<ReportDataDTO> reportData, Map<String, Object> params) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			
			
			byte[] pdfBytes = generateReport(reportData, params);
			
			helper.setTo(params.get("email").toString());
			helper.setSubject("Flight tickets");
			helper.setText("");
			helper.addAttachment("ticketsForFlight.pdf", new ByteArrayResource(pdfBytes));
			
			javaMailSender.send(message);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
