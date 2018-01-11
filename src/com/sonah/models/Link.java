package com.sonah.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author saad
 * This class is a simple provider for links, that will be sent with each API,
 * for easy access to the frontend developer, so he knows what are the related links
 * to the API he has accessed.
 * 
 * Currently only used with session.
 */

@XmlRootElement
public class Link {
	private String name;
	private String url;
	
	public Link(){}
	
	public Link(String name, String url) {
		this.name = name;
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
