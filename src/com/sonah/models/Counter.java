package com.sonah.models;

import java.io.Serializable;

import javax.persistence.Embeddable;
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
public class Counter implements Serializable{

	@Id @GeneratedValue
	private int id;
	@ManyToOne
	@JoinColumn(name="id_parkingLocation")
	private ParkingLocation parkinglocation;
	private Coordinate gps;
	private double length;
	private double width;
	private int orientation;
	private int availableParking;
	private int totalParking;
	
	public Counter(){}
	
	public Counter( ParkingLocation parkinglocation,  Coordinate gps, double length, double width,
			int orientation, int available, int total) {
		this.gps = gps;
		this.length = length;
		this.width = width;
		this.orientation = orientation;
		this.availableParking = available;
		this.totalParking = total;
	}
	
	public Counter( Coordinate gps, double length, double width,
			int orientation, int available, int total) {
		this.gps = gps;
		this.length = length;
		this.width = width;
		this.orientation = orientation;
		this.availableParking = available;
		this.totalParking = total;
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
	
	
}
