package com.sonah.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Embeddable
public class Counter implements Serializable {

	@Id
	private int id;
	@ManyToOne
	@JoinColumn(name = "id_parkingLocation")
	private ParkingLocation parkinglocation;
	@Embedded
	private Coordinate gps;
	private double length;
	private double width;
	private int orientation;
	@Column(name = "available")
	private int availableParking;
	@Column(name = "total")
	private int totalParking;
	private int level;
	private String name;

	public Counter() {
	}

	public Counter(int id, ParkingLocation parkinglocation, Coordinate gps, double length, double width,
			int orientation, int available, int total, int level, String name) {
		this.parkinglocation = parkinglocation;
		this.id = id;
		this.gps = gps;
		this.length = length;
		this.width = width;
		this.orientation = orientation;
		this.availableParking = available;
		this.totalParking = total;
		this.level = level;
		this.name = name;
		
		this.parkinglocation.getCounters().add(this);
	}

	public Counter(int id, Coordinate gps, double length, double width, int orientation, int available, int total,
			int level, String name) {

		this.id = id;
		this.gps = gps;
		this.length = length;
		this.width = width;
		this.orientation = orientation;
		this.availableParking = available;
		this.totalParking = total;
		this.level = level;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ParkingLocation getParkinglocation() {
		return parkinglocation;
	}

	public void setParkinglocation(ParkingLocation parkinglocation) {
		this.parkinglocation = parkinglocation;
	}

	public Coordinate getGps() {
		return gps;
	}

	public void setGps(Coordinate gps) {
		this.gps = gps;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public int getAvailable() {
		return availableParking;
	}

	public void setAvailable(int available) {
		this.availableParking = available;
	}

	public int getTotal() {
		return totalParking;
	}

	public void setTotal(int total) {
		this.totalParking = total;
	}

	public int getAvailableParking() {
		return availableParking;
	}

	public void setAvailableParking(int availableParking) {
		this.availableParking = availableParking;
	}

	public int getTotalParking() {
		return totalParking;
	}

	public void setTotalParking(int totalParking) {
		this.totalParking = totalParking;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
