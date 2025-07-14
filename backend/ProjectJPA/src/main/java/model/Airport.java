package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the airport database table.
 * 
 */
@Entity
@NamedQuery(name="Airport.findAll", query="SELECT a FROM Airport a")
public class Airport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idAirport;

	private int city_idCity;

	@Column(name="iata_code")
	private String iataCode;

	@Column(name="icao_code")
	private String icaoCode;

	private String name;

	//bi-directional many-to-one association to Flight
	@OneToMany(mappedBy="airport1")
	private List<Flight> flights1;

	//bi-directional many-to-one association to Flight
	@OneToMany(mappedBy="airport2")
	private List<Flight> flights2;

	public Airport() {
	}

	public int getIdAirport() {
		return this.idAirport;
	}

	public void setIdAirport(int idAirport) {
		this.idAirport = idAirport;
	}

	public int getCity_idCity() {
		return this.city_idCity;
	}

	public void setCity_idCity(int city_idCity) {
		this.city_idCity = city_idCity;
	}

	public String getIataCode() {
		return this.iataCode;
	}

	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}

	public String getIcaoCode() {
		return this.icaoCode;
	}

	public void setIcaoCode(String icaoCode) {
		this.icaoCode = icaoCode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Flight> getFlights1() {
		return this.flights1;
	}

	public void setFlights1(List<Flight> flights1) {
		this.flights1 = flights1;
	}

	public Flight addFlights1(Flight flights1) {
		getFlights1().add(flights1);
		flights1.setAirport1(this);

		return flights1;
	}

	public Flight removeFlights1(Flight flights1) {
		getFlights1().remove(flights1);
		flights1.setAirport1(null);

		return flights1;
	}

	public List<Flight> getFlights2() {
		return this.flights2;
	}

	public void setFlights2(List<Flight> flights2) {
		this.flights2 = flights2;
	}

	public Flight addFlights2(Flight flights2) {
		getFlights2().add(flights2);
		flights2.setAirport2(this);

		return flights2;
	}

	public Flight removeFlights2(Flight flights2) {
		getFlights2().remove(flights2);
		flights2.setAirport2(null);

		return flights2;
	}

}