package com.example.demo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import model.Airline;
import model.Airport;
import model.AppUser;
import model.Flight;
import model.Plane;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {
	@Query("select distinct f from Flight f "
			+ "inner join f.plane.seats s "
			+ "where f.airport1=:from and "
				+ "f.airport2=:to and "
				+ "f.departureTime>:departureFromTime and "
				+ "f.departureTime<:departureToTime and "
				+ "f.canceled=false and "
				+ ":numberOfSeats<("
				+ "select count(s.idSeat) from Seat s where s.plane = f.plane and s not in ("
						+ "select t.seat from Ticket t where t.booking.flight = f" 
					+ ")"
				+ ")"
			)
	List<Flight> getAllFlightsFromTo(@Param("from") Airport from, @Param("to") Airport to,
			@Param("departureFromTime") Date departureFromTime, @Param("departureToTime") Date departureToTime, @Param("numberOfSeats") Integer numberOfSeats);
	
	@Query("select f from Flight f "
			+ "inner join f.plane.seats s "
			+ "where f.airport1=:fromAirport and "
			+ "f.departureTime>:departureFromTime and "
			+ "f.departureTime<:departureToTime and "
			+ "f.canceled=false and "
			+ ":numberOfSeats<("
					+ "select count(s.idSeat) from Seat s where s.plane = f.plane and s not in ("
							+ "select t.seat from Ticket t where t.booking.flight = f" 
						+ ")"
					+ ")"
			)
	List<Flight> getToFlights(@Param("fromAirport") Airport fromAirport, @Param("departureFromTime") Date departureFromTime, @Param("departureToTime") Date departureToTime, @Param("numberOfSeats") Integer numberOfSeats);
	
	@Query("select f from Flight f where f.plane=:plane and f.departureTime<=:arrivalTime and f.arrivalTime>=:departureTime and f.canceled=false")
	List<Flight> findAllCollisions(@Param("plane") Plane plane, @Param("departureTime") Date departureTime, @Param("arrivalTime") Date arrivalTime);
	
	@Query("select f from Flight f inner join f.appUsers au where au=:appUser and f.departureTime>:date and f.canceled=false")
	List<Flight> findAllEmployeeFlights(@Param("appUser") AppUser appUser, @Param("date") Date date);
	
	@Query("select f from Flight f where f.plane.airline=:airline and f.departureTime>:now and f.canceled=false")
	List<Flight> findAirlineFlihgts(@Param("airline")Airline airline, @Param("now") Date now);
	
	@Transactional
	@Modifying
	@Query("update Flight f set f.departureTime=:departure, f.arrivalTime=:arrival where f.idFlight=:flightId")
	int delayFlight(@Param("departure") Date departure, @Param("arrival") Date arrival, @Param("flightId") int flightId);
	
	@Transactional
	@Modifying
	@Query("update Flight f set f.canceled=true where f.idFlight=:flightId")
	int cancelFlight(@Param("flightId") Integer flightId);
	
	@Query("select f from Flight f where f.departureTime>=:tomorrow and f.departureTime<:dayAfterTomorrow and f.canceled=false")
	List<Flight> getFlightsForNotification(@Param("tomorrow") Date tomorrow, @Param("dayAfterTomorrow") Date dayAfterTomorrow);
	
	
	@Query("select distinct f from Flight f "
			+ "inner join f.plane.seats s "
			+ "where f.airport1=:from and "
				+ "f.airport2=:to and "
				+ "f.departureTime>:departureFromTime and "
				+ "f.departureTime<:departureToTime and "
				+ "f.canceled=false and "
				+ ":numberOfSeats<("
				+ "select count(s.idSeat) from Seat s where s.plane = f.plane and s not in ("
						+ "select t.seat from Ticket t where t.booking.flight = f" 
					+ ")"
				+ ")"
			)
	Page<Flight> getAllFlightsFromTo(@Param("from") Airport from, @Param("to") Airport to,
			@Param("departureFromTime") Date departureFromTime, @Param("departureToTime") Date departureToTime, @Param("numberOfSeats") Integer numberOfSeats, Pageable pageable);
}
