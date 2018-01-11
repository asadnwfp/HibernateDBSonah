package com.sonah.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.sonah.models.City;
import com.sonah.models.Coordinate;

public class Servers {

	
	public static void main(String[] args){
		
		SessionFactory hibernateSessionFactory = new Configuration().configure().buildSessionFactory();
		 Session session = hibernateSessionFactory.openSession();
			session.beginTransaction();
			session.save(new City(11,"aachen",false, new Coordinate(50.773369,6.083496,3,0,0)));
			session.save(new City(12,"cologne",false, new Coordinate(50.957801,6.687115,5,0,0)));
			session.save(new City(13,"frankfurt",false, new Coordinate(50.121315,8.356355,8,0,0)));
			session.save(new City(14,"munich",false, new Coordinate(48.155028,11.26162,7,0,0)));
			session.save(new City(15,"RWTH",true, new Coordinate(50.773369,6.083496,3,0,0)));
			session.getTransaction().commit();
			session.close();
			
			System.out.println("DatabseGenerated");

		
	}
	
}
