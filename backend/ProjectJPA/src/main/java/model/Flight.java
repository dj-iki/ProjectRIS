package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the flight database table.
 * 
 */
@Entity
@NamedQuery(name="Flight.findAll", query="SELECT f FROM Flight f")
public class Flight implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idFlight;

	@Column(name="arrival_time")
	private Timestamp arrivalTime;

	@Column(name="departure_time")
	private Timestamp departureTime;

	@Column(name="flight_number")
	private String flightNumber;

	//bi-directional many-to-one association to Booking
	@OneToMany(mappedBy="flight")
	private List<Booking> bookings;

	//bi-directional many-to-one association to Airport
	@ManyToOne
	@JoinColumn(name="from_airport_idAirport")
	private Airport airport1;

	//bi-directional many-to-one association to Airport
	@ManyToOne
	@JoinColumn(name="to_airport_idAirport")
	private Airport airport2;

	//bi-directional many-to-one association to Plane
	@ManyToOne
	private Plane plane;

	//bi-directional many-to-many association to AppUser
	@ManyToMany
	@JoinTable(
		name="user_flight_assignment"
		, joinColumns={
			@JoinColumn(name="Flight_idFlight")
			}
		, inverseJoinColumns={
			@JoinColumn(name="Users_idUsers")
			}
		)
	private List<AppUser> appUsers;

	public Flight() {
	}

	public int getIdFlight() {
		return this.idFlight;
	}

	public void setIdFlight(int idFlight) {
		this.idFlight = idFlight;
	}

	public Timestamp getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(Timestamp arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Timestamp getDepartureTime() {
		return this.departureTime;
	}

	public void setDepartureTime(Timestamp departureTime) {
		this.departureTime = departureTime;
	}

	public String getFlightNumber() {
		return this.flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public List<Booking> getBookings() {
		return this.bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public Booking addBooking(Booking booking) {
		getBookings().add(booking);
		booking.setFlight(this);

		return booking;
	}

	public Booking removeBooking(Booking booking) {
		getBookings().remove(booking);
		booking.setFlight(null);

		return booking;
	}

	public Airport getAirport1() {
		return this.airport1;
	}

	public void setAirport1(Airport airport1) {
		this.airport1 = airport1;
	}

	public Airport getAirport2() {
		return this.airport2;
	}

	public void setAirport2(Airport airport2) {
		this.airport2 = airport2;
	}

	public Plane getPlane() {
		return this.plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	public List<AppUser> getAppUsers() {
		return this.appUsers;
	}

	public void setAppUsers(List<AppUser> appUsers) {
		this.appUsers = appUsers;
	}

}