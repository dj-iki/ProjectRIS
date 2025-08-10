package com.example.demo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.Airport;
import model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer>{
	
	@Query("select distinct a.city from Airport a " 
			+ "inner join a.flights2 f "
			+ "where f.airport1=:fromAirport and "
				+ "a.city.country.idCountry=:countryId and "
				+ "f.departureTime>:departureFromTime and "
				+ "f.departureTime<:departureToTime and "
				+ "f.canceled=false and "
				+ ":numberOfSeats<("
					+ "select count(s.idSeat) from Seat s where s.plane = f.plane and s not in ("
						+ "select t.seat from Ticket t where t.booking.flight = f" 
					+ ")" 
				+ ")"
			)
	List<City> getToCities(@Param("countryId") Integer countryId, @Param("fromAirport") Airport fromAirport, @Param("departureFromTime") Date departureFromTime, @Param("departureToTime") Date departureToTime, @Param("numberOfSeats") Integer numberOfSeats);
	
	
	@Query("select distinct c from City c " 
			+ "inner join c.airports a "
			+ "inner join a.flights1 f "
			+ "where c in (:cities) and "
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
	List<City> getReturningCities(@Param("returningAirport") Airport returningAirport, @Param("departureFromTime") Date departureFromTime, @Param("departureToTime") Date departureToTime, @Param("cities") List<City> cities, @Param("numberOfSeats") Integer numberOfSeats);
}
