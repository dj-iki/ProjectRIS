package model;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the city database table.
 * 
 */
@Entity
@NamedQuery(name="City.findAll", query="SELECT c FROM City c")
public class City implements Serializable {
	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCity;

	private String name;
	
	@ManyToOne
	private Country country;

	public City() {
	}
	
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public int getIdCity() {
		return this.idCity;
	}

	public void setIdCity(int idCity) {
		this.idCity = idCity;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}