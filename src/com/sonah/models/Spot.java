package com.sonah.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class Spot implements Serializable{
	
	@Id
	private int id;
	@ManyToOne
	@JoinColumn(name="id_parkingLocation")
	private ParkingLocation parkinglocation;
	@Embedded
	private Coordinate gps;
	private double length;
	private double width;
	private int orientation;
	private boolean state;
	private int level;
	
	public Spot(){}

	public Spot(int id, ParkingLocation parkinglocation,  Coordinate gps, double length, double width,
			int orientation, boolean state, int level) {
		this.id = id;
		this.parkinglocation = parkinglocation;
		this.gps = gps;
		this.length = length;
		this.width = width;
		this.orientation = orientation;
		this.state = state;
		this.level = level;
	}

	public Spot( int id, Coordinate gps, double length, double width, int orientation, boolean state, int level) {
		this.id = id;
		this.gps = gps;
		this.length = length;
		this.width = width;
		this.orientation = orientation;
		this.state = state;
		this.level = level;
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

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "Spot [id=" + id + ", parkinglocation=" + parkinglocation + ", gps=" + gps + ", length=" + length
				+ ", width=" + width + ", orientation=" + orientation + ", state=" + state + "]\n";
		
	}
	
	
}
