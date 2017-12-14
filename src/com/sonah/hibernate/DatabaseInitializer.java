package com.sonah.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.sonah.models.Coordinate;
import com.sonah.models.Counter;
import com.sonah.models.ParkingLocation;
import com.sonah.models.Spot;

public class DatabaseInitializer {
		static SessionFactory hibernateSessionFactory = new Configuration().configure().buildSessionFactory();
		static Session session;
		private static List<ParkingLocation> parkingLocations = new ArrayList<>();
		private static List<Counter> counters = new ArrayList<>();
		private static List<Spot> spots = new ArrayList<>();
		private static ParkingLocation blutcherPlatz;

		static {
			createListOfParkingLocations();
			createListOfCounters();
			createListOfSpots();
			for (Counter counter : counters) {
				counter.setParkinglocation(blutcherPlatz);
			}
	
			for (Spot spot : spots) {
				spot.setParkinglocation(blutcherPlatz);
			}
		}


		public static void main (String [] args){
//			testDb();
//			ParkingGenerator.initiateCityParking();
//			Coordinate c = new Coordinate();
//			c.setCoordinate_x("50.794582203435596");
//			c.setCoordinate_y("6.104709203435596");
//			c.setZoomLevel(1);
//			
//			List<ParkingLocation> pl  = ParkingGenerator.getListOfParkingInCluster(c);
//			System.out.println(pl.size());
//			for(ParkingLocation p: pl)
//				System.out.println(p);
			
			generateDatabase();
		}

		public static ParkingLocation getParkingLocation() {
			session = hibernateSessionFactory.openSession();
			session.beginTransaction();
			ParkingLocation location = (ParkingLocation) session.get("ParkingLocation", 1);
			session.getTransaction().commit();
			session.close();
			return location;
		}

//		private static void testDb() {
//			DatabaseInitializer testObj = new DatabaseInitializer();
//			blutcherPlatz = testObj.createParkingLocation();
//			session = hibernateSessionFactory.openSession();
//			session.beginTransaction();
//			session.save(blutcherPlatz);
//			session.save(testObj.createCounter(blutcherPlatz));
//			session.save(testObj.createSpot(blutcherPlatz));
//
//			session.getTransaction().commit();
//			session.close();
//		}

		private static void generateDatabase() {
			session = hibernateSessionFactory.openSession();
			session.beginTransaction();
			// CreatingTable Parking Location
			for (ParkingLocation parkingLocation : parkingLocations)
				session.save(parkingLocation);
			// Creating Table counters
			for (Counter counter : counters) {
				counter.setParkinglocation(blutcherPlatz);
				session.save(counter);
			}

			// Creating Table spots
			for (Spot spot : spots) {
				spot.setParkinglocation(blutcherPlatz);
				session.save(spot);
			}

			session.getTransaction().commit();
			session.close();

			System.out.println("DatabseGenerated");
		}

		private ParkingLocation createParkingLocation() {
			return new ParkingLocation(1001, "Bluecherplatz", new Coordinate(50.779683, 6.101926, 0, 0), 48, 48,false);
		}

		private Counter createCounter(ParkingLocation parkingLocation) {
			Counter counter = new Counter(10001,  new Coordinate("50.779723", "6.101991",0,0), 0.0, 0.0, 45, 4, 4,0);
			counter.setParkinglocation(parkingLocation);
			return counter;
		}

		private Spot createSpot(ParkingLocation parkingLocation) {
			Spot spot =  new Spot (10009, new Coordinate("50.779697", "6.102027",0,0), 2.5, 5.0, 14,false, 0);
			spot.setParkinglocation(parkingLocation);
			return spot;
		}

		private static List<ParkingLocation> createListOfParkingLocations() {

			parkingLocations.add(new ParkingLocation(1001, "Bluecherplatz", new Coordinate( "50.779683", "6.101926",0,0), 48, 48, false));
			parkingLocations.add(new ParkingLocation(1002, "Aquis Plaza", new Coordinate( "50.774736", "6.092977",0,0), 100, 100, false));
			parkingLocations.add(new ParkingLocation(1003, "Parkhaus am Dom", new Coordinate( "50.773289", "6.082237",0,0), 75, 75, false));
			parkingLocations.add(new ParkingLocation(1004, "APAG Parkhaus Hbf", new Coordinate( "50.767923", "6.089123",0,0), 250, 250, false));
			parkingLocations.add(new ParkingLocation(1005, "APAG Parkhaus Adalbertstrasse", new Coordinate( "50.775612", "6.090170",0,0), 125, 125, false));
			blutcherPlatz = parkingLocations.get(0);
			return parkingLocations;
		}

		private static List<Counter> createListOfCounters() {
			counters.add(new Counter(10001,  new Coordinate("50.779723", "6.101991",0,0), 0.0, 0.0, 45, 4, 4,0));
			counters.add(new Counter(10002,  new Coordinate("50.779607", "6.101933",0,0), 0.0, 0.0, 135, 4, 4,0));
			counters.add(new Counter(10003,  new Coordinate("50.779711", "6.101855",0,0), 0.0, 0.0, 225, 4, 4,0));
			counters.add(new Counter(10004,  new Coordinate("50.779734", "6.101983",0,0), 0.0, 0.0, 315, 4, 4,0));
			counters.add(new Counter(10005,  new Coordinate("50.779501", "6.101868",0,0), 0.0, 0.0, 60, 4, 4,0));
			counters.add(new Counter(10006,  new Coordinate("50.779816", "6.101790",0,0), 0.0, 0.0, 150, 4, 4,0));
			counters.add(new Counter(10007,  new Coordinate("50.779745", "6.102106",0,0), 0.0, 0.0, 240, 4, 4,0));
			counters.add(new Counter(10008,  new Coordinate("50.779494", "6.101901",0,0), 0.0, 0.0, 330, 4, 4,0));
				return counters;
		}

		private static List<Spot> createListOfSpots() {
			spots.add ( new Spot (10009, new Coordinate("50.779697", "6.102027",0,0), 2.5, 5.0, 14,false, 0));
			spots.add ( new Spot (10010, new Coordinate("50.779606", "6.102043",0,0), 2.5, 5.0, 21,false, 0));
			spots.add ( new Spot (10011, new Coordinate("50.779512", "6.101974",0,0), 2.5, 5.0, 28,false, 0));
			spots.add ( new Spot (10012, new Coordinate("50.779488", "6.101834",0,0), 2.5, 5.0, 35,false, 0));
			spots.add ( new Spot (10013, new Coordinate("50.779581", "6.101693",0,0), 2.5, 5.0, 42,false, 0));
			spots.add ( new Spot (10014, new Coordinate("50.779771", "6.101647",0,0), 2.5, 5.0, 49,false, 0));
			spots.add ( new Spot (10015, new Coordinate("50.779965", "6.101754",0,0), 2.5, 5.0, 56,false, 0));
			spots.add ( new Spot (10016, new Coordinate("50.780021", "6.101983",0,0), 2.5, 5.0, 63,false, 0));
			spots.add ( new Spot (10017, new Coordinate("50.779924", "6.102221",0,0), 2.5, 5.0, 70,false, 0));
			spots.add ( new Spot (10018, new Coordinate("5.077967", "6.102345",0,0), 2.5, 5.0, 77,false, 0));
			spots.add ( new Spot (10019, new Coordinate("50.779709", "6.101984",0,0), 2.5, 5.0, 353,false, 0));
			spots.add ( new Spot (10020, new Coordinate("50.779776", "6.101968",0,0), 2.5, 5.0, 346,false, 0));
			spots.add ( new Spot (10021, new Coordinate("50.779817", "6.101886",0,0), 2.5, 5.0, 339,false, 0));
			spots.add ( new Spot (10022, new Coordinate("50.779778", "6.101775",0,0), 2.5, 5.0, 332,false, 0));
			spots.add ( new Spot (10023, new Coordinate("5.077965", "6.101713",0,0), 2.5, 5.0, 325,false, 0));
			spots.add ( new Spot (10024, new Coordinate("50.779489", "6.101762",0,0), 2.5, 5.0, 318,false, 0));
			spots.add ( new Spot (10025, new Coordinate("50.779391", "6.101931",0,0), 2.5, 5.0, 311,false, 0));
			spots.add ( new Spot (10026, new Coordinate("50.779438", "6.102147",0,0), 2.5, 5.0, 304,false, 0));
			spots.add ( new Spot (10027, new Coordinate("50.779642", "6.102267",0,0), 2.5, 5.0, 297,false, 0));
			spots.add ( new Spot (10028, new Coordinate("50.779897", "6.102241",0,0), 2.5, 5.0, 290,false, 0));
			spots.add ( new Spot (10029, new Coordinate("50.780089", "6.102032",0,0), 2.5, 5.0, 283,false, 0));
			spots.add ( new Spot (10030, new Coordinate( "50.779705", "6.101853",0,0), 2.5, 5.0, 5,false, 0));
			spots.add ( new Spot (10031, new Coordinate( "50.779587", "6.101864",0,0), 2.5, 5.0, 10,false, 0));
			return spots;
		}

	}
