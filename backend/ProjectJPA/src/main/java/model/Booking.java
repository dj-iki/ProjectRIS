package model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;

/**
 * The persistent class for the booking database table.
 * 
 */
@Entity
@NamedQuery(name = "Booking.findAll", query = "SELECT b FROM Booking b")
public class Booking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idBooking;

	@Column(name = "number_of_seats")
	private int numberOfSeats;

	private float price;

	// bi-directional many-to-one association to AppUser
	@ManyToOne
	@JoinColumn(name = "Users_idUsers")
	private AppUser appUser;

	// bi-directional many-to-one association to Flight
	@ManyToOne
	private Flight flight;

	// bi-directional many-to-one association to Ticket
	@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
	private List<Ticket> tickets;

	public Booking() {
	}

	public int getIdBooking() {
		return this.idBooking;
	}

	public void setIdBooking(int idBooking) {
		this.idBooking = idBooking;
	}

	public int getNumberOfSeats() {
		return this.numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public AppUser getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public Flight getFlight() {
		return this.flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public List<Ticket> getTickets() {
		return this.tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public Ticket addTicket(Ticket ticket) {
		getTickets().add(ticket);
		ticket.setBooking(this);

		return ticket;
	}

	public Ticket removeTicket(Ticket ticket) {
		getTickets().remove(ticket);
		ticket.setBooking(null);

		return ticket;
	}

}