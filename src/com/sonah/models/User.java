package com.sonah.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class User {
	@Id
	private int id;
	private String firstName;
	private String lastName;
	@Column(unique = true)
	private String username;
	private String password;
	@ManyToMany (mappedBy = "user")
	private List<ParkingLocation> parkingLocations= new ArrayList<>();
	
	public User() {
	}

	
	public User(int id, String firstName, String lastName, String username, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public List<ParkingLocation> getParkingLocations() {
		return parkingLocations;
	}


	public void setParkingLocations(List<ParkingLocation> parkingLocations) {
		this.parkingLocations = parkingLocations;
	}
	
	

}
