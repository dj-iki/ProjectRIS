package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.Flight;
import model.Plane;
import model.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

	@Query("select s from Seat s where s.plane=:plane and s not in (select t.seat from Ticket t where t.booking.flight=:flight)")
	List<Seat> getAvailableSeats(@Param("plane") Plane plane, @Param("flight") Flight flight);
	@Query("select s.price from Seat s where s.idSeat=:seatId")
	float getPrice(@Param("seatId") Integer seatId);

}
