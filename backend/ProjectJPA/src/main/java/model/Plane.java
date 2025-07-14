package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the plane database table.
 * 
 */
@Entity
@NamedQuery(name="Plane.findAll", query="SELECT p FROM Plane p")
public class Plane implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPlane;

	private String manufacturer;

	private String model;

	@Column(name="number_of_seats")
	private int numberOfSeats;

	@Column(name="registration_number")
	private String registrationNumber;

	//bi-directional many-to-one association to Flight
	@OneToMany(mappedBy="plane")
	private List<Flight> flights;

	//bi-directional many-to-one association to Airline
	@ManyToOne
	@JoinColumn(name="Airlines_idAirlines")
	private Airline airline;

	//bi-directional many-to-one association to Seat
	@OneToMany(mappedBy="plane")
	private List<Seat> seats;

	public Plane() {
	}

	public int getIdPlane() {
		return this.idPlane;
	}

	public void setIdPlane(int idPlane) {
		this.idPlane = idPlane;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getNumberOfSeats() {
		return this.numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public String getRegistrationNumber() {
		return this.registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public List<Flight> getFlights() {
		return this.flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}

	public Flight addFlight(Flight flight) {
		getFlights().add(flight);
		flight.setPlane(this);

		return flight;
	}

	public Flight removeFlight(Flight flight) {
		getFlights().remove(flight);
		flight.setPlane(null);

		return flight;
	}

	public Airline getAirline() {
		return this.airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public List<Seat> getSeats() {
		return this.seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public Seat addSeat(Seat seat) {
		getSeats().add(seat);
		seat.setPlane(this);

		return seat;
	}

	public Seat removeSeat(Seat seat) {
		getSeats().remove(seat);
		seat.setPlane(null);

		return seat;
	}

}