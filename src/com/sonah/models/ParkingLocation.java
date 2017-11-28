package com.sonah.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * @author saad This class replicates the Parking Location of the Database.
 */

@XmlRootElement
@Entity
public class ParkingLocation implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Embedded
	private Coordinate gps;
	@Column(nullable = false)
	private String name;
	private int totalParking;
	private int availableParking;

	@OneToMany(mappedBy = "parkinglocation", fetch = FetchType.EAGER)
	// @Fetch(value = FetchMode.SUBSELECT)
	private List<Spot> spots = new ArrayList<>();
	@OneToMany(mappedBy = "parkinglocation", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Counter> counters = new ArrayList<>();

	public ParkingLocation() {
	}

	public ParkingLocation(int id, String name, Coordinate gps, int total, int free) {
		this(id, name, gps, total, free, new ArrayList<>());
	}

	public ParkingLocation(int id, String name, Coordinate gps, int total, int free, List<?> type) {

		this.id = id;
		this.gps = gps;
		this.name = name;
		this.totalParking = total;
		this.availableParking = free;
		if (!type.isEmpty() && type.get(0) instanceof Counter) {
			this.counters = (List<Counter>) type;
			this.spots = null;
		} else if (!type.isEmpty() && type.get(0) instanceof Spot) {
			this.spots = (List<Spot>) type;
			this.counters = null;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Coordinate getGps() {
		return gps;
	}

	public void setGps(Coordinate gps) {
		this.gps = gps;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotalParking() {
		return totalParking;
	}

	public void setTotalParking(int totalParking) {
		this.totalParking = totalParking;
	}

	public int getAvailableParking() {
		return availableParking;
	}

	public void setAvailableParking(int availableParking) {
		this.availableParking = availableParking;
	}

	@Override
	public String toString() {
		return "ParkingLocation [id=" + id + ", name=" + name + "]\n";
	}

	public List<Spot> getSpots() {
		return spots;
	}

	public void setSpots(List<Spot> spots) {
		this.spots = spots;
	}

	public List<Counter> getCounters() {
		return counters;
	}

	public void setCounters(List<Counter> counters) {
		this.counters = counters;
	}

}
