package com.sonah.models;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author saad
 * This class will provide the server name, in the form of city
 * and a link to access that server.
 */

@XmlRootElement
@Entity
public class City {
	@Id
	private int id;
	private String name;
	@Transient
	private Link link;
	private boolean secure;
	@Embedded
	Coordinate gps;

	public City(){}
	
	
	
	public City(int id, String name, boolean secure, Coordinate gps) {
		this.id = id;
		this.name = name;
		this.secure = secure;
		this.gps = gps;
	}



	public City(String name) {
		this.name = name;
	}
	
	public City(String name, Link link) {
		this.name = name;
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}

	public Coordinate getGps() {
		return gps;
	}

	public void setGps(Coordinate gps) {
		this.gps = gps;
	}

	
}
