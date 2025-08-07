package com.example.demo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.Airport;
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
				+ "s not in ("
					+ "select t.seat from Ticket t "
					+ "where t.booking.flight = f"
				+ ")")
	List<Flight> getAllFlightsFromTo(@Param("from") Airport from, @Param("to") Airport to,
			@Param("departureFromTime") Date departureFromTime, @Param("departureToTime") Date departureToTime);
	
	@Query("select f from Flight f "
			+ "inner join f.plane.seats s "
			+ "where f.airport1=:fromAirport and "
			+ "f.departureTime>:departureFromTime and "
			+ "f.departureTime<:departureToTime and "
			+ "s not in ("
			+ "	select t.seat from Ticket t"
			+ "	where t.booking.flight = f"
			+ ")")
	List<Flight> getToFlights(@Param("fromAirport") Airport fromAirport, @Param("departureFromTime") Date departureFromTime, @Param("departureToTime") Date departureToTime);
	
	@Query("select f from Flight f "
			+ "where f.airport2=:returningAirport and "
			+ "f.departureTime>:departureFromTime and "
			+ "f.departureTime<:departureToTime and "
			+ "f in (:flights)")
	List<Flight> getReturningFlights(@Param("returningAirport")Airport returningAirport, @Param("departureFromTime") Date departureFromTime, @Param("departureToTime") Date departureToTime, @Param("flights") List<Flight> flights);
	
	
	@Query("select f from Flight f where f.plane=:plane and f.departureTime<=:arrivalTime and f.arrivalTime>=:departureTime")
	List<Flight> findAllCollisions(@Param("plane") Plane plane, @Param("departureTime") Date departureTime, @Param("arrivalTime") Date arrivalTime);
}
