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
	
	@Query("select distinct a from Airport a " 
			+ "inner join a.flights2 f "
			+ "where f.airport1=:fromAirport and "
				+ "a.city.idCity=:cityId and "
				+ "f.departureTime>:departureFromTime and "
				+ "f.departureTime<:departureToTime and "
				+ "f.canceled=false and "
				+ ":numberOfSeats<("
					+ "select count(s.idSeat) from Seat s where s.plane = f.plane and s not in ("
						+ "select t.seat from Ticket t where t.booking.flight = f" 
					+ ")" 
				+ ")"
			)
	List<Airport> getToAirports(@Param("cityId") Integer cityId, @Param("fromAirport") Airport fromAirport, @Param("departureFromTime") Date departureFromTime, @Param("departureToTime") Date depratureToTime, @Param("numberOfSeats") Integer numberOfSeats);
	
	@Query("select distinct a from Airport a " 
			+ "inner join a.flights1 f "
			+ "where a in (:airports) and "
				+ "f.airport2=:returningAirport and "
				+ "f.departureTime>:departureFromTime and "
				+ "f.departureTime<:departureToTime and "
				+ "f.canceled=false and "
				+ ":numberOfSeats<("
					+ "select count(s.idSeat) from Seat s where s.plane = f.plane and s not in ("
						+ "select t.seat from Ticket t where t.booking.flight = f" 
					+ ")" 
				+ ")"
			)
	List<Airport> getReturningAirports(@Param("returningAirport") Airport returningAirport, @Param("departureFromTime") Date departureFromTime, @Param("departureToTime") Date departureToTime, @Param("airports") List<Airport> airports, @Param("numberOfSeats") Integer numberOfSeats);

	@Query("select a from Airport a where a.iataCode=:iata_code")
	Airport findByIata_code(@Param("iata_code")String iata_code);
	
	@Query("select a from Airport a where a.icaoCode=:icao_code")
	Airport findByIcao_code(@Param("icao_code")String icao_code);
}
