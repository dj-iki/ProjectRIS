package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idRole;

	private String name;

	//bi-directional many-to-one association to AppUser
	@OneToMany(mappedBy="role")
	private List<AppUser> appUsers;

	public Role() {
	}

	public int getIdRole() {
		return this.idRole;
	}

	public void setIdRole(int idRole) {
		this.idRole = idRole;
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
		appUser.setRole(this);

		return appUser;
	}

	public AppUser removeAppUser(AppUser appUser) {
		getAppUsers().remove(appUser);
		appUser.setRole(null);

		return appUser;
	}

	@Override
	public String toString() {
		return "Role [idRole=" + idRole + ", name=" + name + "]";
	}

}