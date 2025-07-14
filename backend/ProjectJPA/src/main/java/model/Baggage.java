package model;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the baggage database table.
 * 
 */
@Entity
@NamedQuery(name="Baggage.findAll", query="SELECT b FROM Baggage b")
public class Baggage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idBaggage;

	private int price;

	private String type;

	private int weight;

	//bi-directional many-to-one association to Ticket
	@ManyToOne
	private Ticket ticket;

	public Baggage() {
	}

	public int getIdBaggage() {
		return this.idBaggage;
	}

	public void setIdBaggage(int idBaggage) {
		this.idBaggage = idBaggage;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getWeight() {
		return this.weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Ticket getTicket() {
		return this.ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

}