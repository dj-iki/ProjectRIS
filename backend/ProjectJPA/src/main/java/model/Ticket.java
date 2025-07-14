package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the ticket database table.
 * 
 */
@Entity
@NamedQuery(name="Ticket.findAll", query="SELECT t FROM Ticket t")
public class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTicket;

	private String name;

	@Column(name="passport_number")
	private String passportNumber;

	private String surname;

	//bi-directional many-to-one association to Baggage
	@OneToMany(mappedBy="ticket")
	private List<Baggage> baggages;

	//bi-directional many-to-one association to Booking
	@ManyToOne
	private Booking booking;

	//bi-directional many-to-one association to Seat
	@ManyToOne
	private Seat seat;

	public Ticket() {
	}

	public int getIdTicket() {
		return this.idTicket;
	}

	public void setIdTicket(int idTicket) {
		this.idTicket = idTicket;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassportNumber() {
		return this.passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<Baggage> getBaggages() {
		return this.baggages;
	}

	public void setBaggages(List<Baggage> baggages) {
		this.baggages = baggages;
	}

	public Baggage addBaggage(Baggage baggage) {
		getBaggages().add(baggage);
		baggage.setTicket(this);

		return baggage;
	}

	public Baggage removeBaggage(Baggage baggage) {
		getBaggages().remove(baggage);
		baggage.setTicket(null);

		return baggage;
	}

	public Booking getBooking() {
		return this.booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public Seat getSeat() {
		return this.seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

}