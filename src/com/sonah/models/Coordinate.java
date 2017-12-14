package com.sonah.models;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author saad This is the GPS object, that represents coordinates and their
 *         zoom level. The smaller the zoomlevel, the nearer to the parking
 *         location ZoomLevel 0 is the Parking Spot itself.
 */

@XmlRootElement
@Embeddable
public class Coordinate {
	@Column(name = "latitude")
	private String coordinate_x;
	@Column(name = "longitude")
	private String coordinate_y;
	@Transient
	private int zoomLevel;
	@Transient
	private int degree;
	@Transient
	private int distanceFromCenter;

	public Coordinate() {
		// for serialization / De serialization
	}

	public Coordinate(String coordinate_x, String coordinate_y, int zoomLevel, int degree, int distanceFromCenter) {
		this.coordinate_x = coordinate_x;
		this.coordinate_y = coordinate_y;
		this.zoomLevel = zoomLevel;
		this.distanceFromCenter = distanceFromCenter;
		this.degree = degree;
	}

	public Coordinate(String coordinate_x, String coordinate_y, int degree, int distanceFromCenter) {
		this(coordinate_x, coordinate_y, 0, degree, distanceFromCenter);
	}

	public Coordinate(Double coordinate_x, Double coordinate_y,int degree, int distanceFromCenter) {
		this(coordinate_x, coordinate_y,0,degree, distanceFromCenter);
	}

	public Coordinate(Double coordinate_x, Double coordinate_y, int zoomLevel, int degree, int distanceFromCenter) {
		this.coordinate_x = String.valueOf(coordinate_x);
		this.coordinate_y = String.valueOf(coordinate_y);
		this.degree = degree;
		this.zoomLevel = zoomLevel;
		this.distanceFromCenter = distanceFromCenter;
	}

	// Getters for coordinates
	public String getCoordinate_x() {
		return coordinate_x;
	}

	public String getCoordinate_y() {
		return coordinate_y;
	}

	public double getCoordinate_x_Double() {
		return Double.parseDouble(coordinate_x);
	}

	public double getCoordinate_y_Double() {
		return Double.parseDouble(coordinate_y);
	}

	public void setCoordinate_x(String coordinate_x) {
		this.coordinate_x = coordinate_x;
	}

	public void setCoordinate_y(String coordinate_y) {
		this.coordinate_y = coordinate_y;
	}

	public int getZoomLevel() {
		return zoomLevel;
	}

	public void setZoomLevel(int zoomLevel) {
		this.zoomLevel = zoomLevel;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public int getDistanceFromCenter() {
		return distanceFromCenter;
	}

	public void setDistanceFromCenter(int distanceFromCenter) {
		this.distanceFromCenter = distanceFromCenter;
	}

	

	@Override
	public String toString() {
		return "Coordinate [coordinate_x=" + coordinate_x + ", coordinate_y=" + coordinate_y + ", zoomLevel="
				+ zoomLevel + ", degree=" + degree + ", distanceFromCenter=" + distanceFromCenter + "]\n";
	}

	/**
	 * This method is overrded, for the usecase, to match two GPS locations.
	 * which is used for the matching purpose in database, when a user provideds
	 * a GPS location.
	 */
	@Override
	public boolean equals(Object o) {
		return (o instanceof Coordinate && ((Coordinate) o).coordinate_x.equals(coordinate_x)
				&& ((Coordinate) o).coordinate_y.equals(coordinate_y) && ((Coordinate) o).zoomLevel == zoomLevel);

	}

}
