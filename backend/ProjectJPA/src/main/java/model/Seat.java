package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the seat database table.
 * 
 */
@Entity
@NamedQuery(name="Seat.findAll", query="SELECT s FROM Seat s")
public class Seat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idSeat;

	@Column(name="class")
	private String class_;

	@Column(name="is_booked")
	private byte isBooked;

	private float price;

	@Column(name="seat_number")
	private String seatNumber;

	//bi-directional many-to-one association to Plane
	@ManyToOne
	private Plane plane;

	//bi-directional many-to-one association to Ticket
	@OneToMany(mappedBy="seat")
	private List<Ticket> tickets;

	public Seat() {
	}

	public int getIdSeat() {
		return this.idSeat;
	}

	public void setIdSeat(int idSeat) {
		this.idSeat = idSeat;
	}

	public String getClass_() {
		return this.class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}

	public byte getIsBooked() {
		return this.isBooked;
	}

	public void setIsBooked(byte isBooked) {
		this.isBooked = isBooked;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getSeatNumber() {
		return this.seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public Plane getPlane() {
		return this.plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	public List<Ticket> getTickets() {
		return this.tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public Ticket addTicket(Ticket ticket) {
		getTickets().add(ticket);
		ticket.setSeat(this);

		return ticket;
	}

	public Ticket removeTicket(Ticket ticket) {
		getTickets().remove(ticket);
		ticket.setSeat(null);

		return ticket;
	}

}