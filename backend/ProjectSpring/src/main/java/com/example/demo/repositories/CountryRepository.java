package com.example.demo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.Airport;
import model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

	@Query("select distinct c from Country c " + "inner join c.cities city " + "inner join city.airports a "
			+ "inner join a.flights1 f "
			+ "inner join f.plane.seats s "
			+ "where f.airport1=:fromAirport and "
				+ "f.departureTime>:departureFromTime and "
				+ "f.departureTime<:departureToTime and "
				+ "s not in ("
					+ "select t.seat from Ticket t "
					+ "where t.booking.flight = f"
				+ ")")
	List<Country> getToCountries(@Param("fromAirport") Airport fromAirport,
			@Param("departureFromTime") Date departureFromTime, @Param("departureToTime") Date departureToTime);
	
	@Query("select c from Country c "
			+ "inner join c.cities city "
			+ "inner join city.airports a "
			+ "inner join a.flights1 f "
			+ "where f.airport2 =:returningAirpot and "
			+ "f.departureTime>:departureFromTime and "
			+ "f.departureTime<:departureToTime and "
			+ "c in (:countries)")
	List<Country> getReturningCountries(@Param("returningAirpot") Airport returningAirport, @Param("departureFromTime") Date departureFromTime, @Param("departureToTime") Date departureToTime,  @Param("countries") List<Country> countries);
}
