package com.example.demo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.Airport;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Integer> {
	
	@Query("select a from Airport a "
			+ "inner join a.flights2 f "
			+ "inner join f.plane.seats s "
			+ "where a.city.idCity=:cityId and "
			+ "f.airport1=:fromAirport and "
			+ "f.departureTime>:departureFromTime and "
			+ "f.departureTime<:departureToTime and "
			+ "s not in ("
			+ "	select t.seat from Ticket t"
			+ "	where t.booking.flight = f"
			+ ")")
	List<Airport> getToAirports(@Param("cityId") Integer cityId, @Param("fromAirport") Airport fromAirport, @Param("departureFromTime") Date departureFromTime, @Param("departureToTime") Date depratureToTime);
	
	@Query("select a from Airport a "
			+ "inner join a.flights1 f "
			+ "where f.airport2=:returningAirport and "
			+ "f.departureTime>:departureFromTime and "
			+ "f.departureTime<:departureToTime and "
			+ "a in (:airports)")
	List<Airport> getReturningAirports(@Param("returningAirport") Airport returningAirport, @Param("departureFromTime") Date departureFromTime, @Param("departureToTime") Date departureToTime, @Param("airports") List<Airport> airports);
}
