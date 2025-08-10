package com.example.demo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import model.Airport;
import model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

	@Query("select distinct a.city.country from Airport a " 
			+ "inner join a.flights2 f "
			+ "where f.airport1=:fromAirport and "
				+ "f.departureTime>:departureFromTime and "
				+ "f.departureTime<:departureToTime and "
				+ "f.canceled=false and "
				+ ":numberOfSeats<("
					+ "select count(s.idSeat) from Seat s where s.plane = f.plane and s not in ("
						+ "select t.seat from Ticket t where t.booking.flight = f" 
					+ ")" 
				+ ")")
	List<Country> getToCountries(@Param("fromAirport") Airport fromAirport,
			@Param("departureFromTime") Date departureFromTime, @Param("departureToTime") Date departureToTime,
			@Param("numberOfSeats") Integer numberOfSeats);

	@Query("select distinct c from Country c " 
			+ "inner join c.cities city " 
			+ "inner join city.airports a "
			+ "inner join a.flights1 f "
			+ "where c in (:countries) and "
				+ "f.airport2=:returningAirpot and "
				+ "f.departureTime>:departureFromTime and "
				+ "f.departureTime<:departureToTime and "
				+ "f.canceled=false and "
				+ ":numberOfSeats<("
					+ "select count(s.idSeat) from Seat s where s.plane = f.plane and s not in ("
						+ "select t.seat from Ticket t where t.booking.flight = f" 
					+ ")" 
			+ ")")
	List<Country> getReturningCountries(@Param("returningAirpot") Airport returningAirport,
			@Param("departureFromTime") Date departureFromTime, @Param("departureToTime") Date departureToTime,
			@Param("countries") List<Country> countries, @Param("numberOfSeats") Integer numberOfSeats);

	@Query("select c from Country c where c.name=:name")
	Country findByName(@Param("name") String name);
	
}
