package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the airline database table.
 * 
 */
@Entity
@NamedQuery(name="Airline.findAll", query="SELECT a FROM Airline a")
public class Airline implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idAirlines;

	@Column(name="iata_code")
	private String iataCode;

	@Column(name="icao_code")
	private String icaoCode;

	private String name;

	//bi-directional many-to-one association to AppUser
	@OneToMany(mappedBy="airline")
	private List<AppUser> appUsers;

	//bi-directional many-to-one association to Plane
	@OneToMany(mappedBy="airline")
	private List<Plane> planes;

	public Airline() {
	}

	public int getIdAirlines() {
		return this.idAirlines;
	}

	public void setIdAirlines(int idAirlines) {
		this.idAirlines = idAirlines;
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

	public List<AppUser> getAppUsers() {
		return this.appUsers;
	}

	public void setAppUsers(List<AppUser> appUsers) {
		this.appUsers = appUsers;
	}

	public AppUser addAppUser(AppUser appUser) {
		getAppUsers().add(appUser);
		appUser.setAirline(this);

		return appUser;
	}

	public AppUser removeAppUser(AppUser appUser) {
		getAppUsers().remove(appUser);
		appUser.setAirline(null);

		return appUser;
	}

	public List<Plane> getPlanes() {
		return this.planes;
	}

	public void setPlanes(List<Plane> planes) {
		this.planes = planes;
	}

	public Plane addPlane(Plane plane) {
		getPlanes().add(plane);
		plane.setAirline(this);

		return plane;
	}

	public Plane removePlane(Plane plane) {
		getPlanes().remove(plane);
		plane.setAirline(null);

		return plane;
	}

}