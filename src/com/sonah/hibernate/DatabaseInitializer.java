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

		private static void testDb() {
			DatabaseInitializer testObj = new DatabaseInitializer();
			blutcherPlatz = testObj.createParkingLocation();
			session = hibernateSessionFactory.openSession();
			session.beginTransaction();
			session.save(blutcherPlatz);
			session.save(testObj.createCounter(blutcherPlatz));
			session.save(testObj.createSpot(blutcherPlatz));

			session.getTransaction().commit();
			session.close();
		}

		private static void generateDatabase() {
			session = hibernateSessionFactory.openSession();
			session.beginTransaction();
			// CreatingTable Parking Location
			for (ParkingLocation parkingLocation : parkingLocations)
				session.save(parkingLocation);
			// Creating Table counters
			for (Counter counter : counters){
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
			System.exit(0);
		}

		private ParkingLocation createParkingLocation() {
			return new ParkingLocation(1001, "Bluecherplatz", new Coordinate(50.779683, 6.101926, 0, 0), 48, 48);
		}

		private Counter createCounter(ParkingLocation parkingLocation) {
			Counter counter = new Counter(new Coordinate(50.779723, 6.101991, 0, 0), 0, 0, 45, 4, 4);
			counter.setParkinglocation(parkingLocation);
			return counter;
		}

		private Spot createSpot(ParkingLocation parkingLocation) {
			Spot spot = new Spot(new Coordinate(50.779697, 6.102027, 0, 0), 2.5, 5, 14, false);
			spot.setParkinglocation(parkingLocation);
			return spot;
		}

		private static List<ParkingLocation> createListOfParkingLocations() {

			parkingLocations
					.add(new ParkingLocation(1001, "Bluecherplatz", new Coordinate(50.779683, 6.101926, 0, 0), 48, 48));
			parkingLocations
					.add(new ParkingLocation(1002, "Aquis Plaza", new Coordinate(50.774736, 6.092977, 0, 0), 100, 100));
			parkingLocations
					.add(new ParkingLocation(1003, "Parkhaus am Dom", new Coordinate(50.773289, 6.082237, 0, 0), 75, 75));
			parkingLocations.add(
					new ParkingLocation(1004, "APAG Parkhaus Hbf", new Coordinate(50.767923, 6.089123, 0, 0), 250, 250));
			parkingLocations.add(new ParkingLocation(1005, "APAG Parkhaus Adalbertstrasse",
					new Coordinate(50.775612, 6.09017, 0, 0), 125, 125));
			blutcherPlatz = parkingLocations.get(0);
			return parkingLocations;
		}

		private static List<Counter> createListOfCounters() {
			counters.add(new Counter(new Coordinate(50.779723, 6.101991, 0, 0), 0, 0, 45, 4, 4));
			counters.add(new Counter(new Coordinate(50.779607, 6.101933, 0, 0), 0, 0, 135, 4, 4));
			counters.add(new Counter(new Coordinate(50.779711, 6.101855, 0, 0), 0, 0, 225, 4, 4));
			counters.add(new Counter(new Coordinate(50.779734, 6.101983, 0, 0), 0, 0, 315, 4, 4));
			counters.add(new Counter(new Coordinate(50.779501, 6.101868, 0, 0), 0, 0, 60, 4, 4));
			counters.add(new Counter(new Coordinate(50.779816, 6.10179, 0, 0), 0, 0, 150, 4, 4));
			counters.add(new Counter(new Coordinate(50.779745, 6.102106, 0, 0), 0, 0, 240, 4, 4));
			counters.add(new Counter(new Coordinate(50.779494, 6.101901, 0, 0), 0, 0, 330, 4, 4));
			return counters;
		}

		private static List<Spot> createListOfSpots() {
			spots.add(new Spot(new Coordinate(50.779606, 6.102043, 0, 0), 2.5, 5, 21, false));
			spots.add(new Spot(new Coordinate(50.779697, 6.102027, 0, 0), 2.5, 5, 14, false));
			spots.add(new Spot(new Coordinate(50.779512, 6.101974, 0, 0), 2.5, 5, 28, false));
			spots.add(new Spot(new Coordinate(50.779488, 6.101834, 0, 0), 2.5, 5, 35, false));
			spots.add(new Spot(new Coordinate(50.779581, 6.101693, 0, 0), 2.5, 5, 42, false));
			spots.add(new Spot(new Coordinate(50.779771, 6.101647, 0, 0), 2.5, 5, 49, false));
			spots.add(new Spot(new Coordinate(50.779965, 6.101754, 0, 0), 2.5, 5, 56, false));
			spots.add(new Spot(new Coordinate(50.780021, 6.101983, 0, 0), 2.5, 5, 63, false));
			spots.add(new Spot(new Coordinate(50.779924, 6.102221, 0, 0), 2.5, 5, 70, false));
			spots.add(new Spot(new Coordinate(5.077967, 6.102345, 0, 0), 2.5, 5, 77, false));
			spots.add(new Spot(new Coordinate(50.779709, 6.101984, 0, 0), 2.5, 5, 353, false));
			spots.add(new Spot(new Coordinate(50.779776, 6.101968, 0, 0), 2.5, 5, 346, false));
			spots.add(new Spot(new Coordinate(50.779817, 6.101886, 0, 0), 2.5, 5, 339, false));
			spots.add(new Spot(new Coordinate(50.779778, 6.101775, 0, 0), 2.5, 5, 332, false));
			spots.add(new Spot(new Coordinate(5.077965, 6.101713, 0, 0), 2.5, 5, 325, false));
			spots.add(new Spot(new Coordinate(50.779489, 6.101762, 0, 0), 2.5, 5, 318, false));
			spots.add(new Spot(new Coordinate(50.779391, 6.101931, 0, 0), 2.5, 5, 311, false));
			spots.add(new Spot(new Coordinate(50.779438, 6.102147, 0, 0), 2.5, 5, 304, false));
			spots.add(new Spot(new Coordinate(50.779642, 6.102267, 0, 0), 2.5, 5, 297, false));
			spots.add(new Spot(new Coordinate(50.779897, 6.102241, 0, 0), 2.5, 5, 290, false));
			spots.add(new Spot(new Coordinate(50.780089, 6.102032, 0, 0), 2.5, 5, 283, false));
			spots.add(new Spot(new Coordinate(50.779705, 6.101853, 0, 0), 2.5, 5, 5, false));
			spots.add(new Spot(new Coordinate(50.779587, 6.101864, 0, 0), 2.5, 5, 10, false));
			spots.add(new Spot(new Coordinate(50.779567, 6.102025, 0, 0), 2.5, 5, 15, false));
			spots.add(new Spot(new Coordinate(50.779761, 0.061021, 0, 0), 2.5, 5, 20, false));
			spots.add(new Spot(new Coordinate(5.077991, 6.101896, 0, 0), 2.5, 5, 25, false));
			spots.add(new Spot(new Coordinate(50.779724, 6.101662, 0, 0), 2.5, 5, 30, false));
			spots.add(new Spot(new Coordinate(50.779407, 6.101795, 0, 0), 2.5, 5, 35, false));
			spots.add(new Spot(new Coordinate(50.779454, 6.102182, 0, 0), 2.5, 5, 40, false));
			spots.add(new Spot(new Coordinate(50.779883, 0.610225, 0, 0), 2.5, 5, 45, false));
			spots.add(new Spot(new Coordinate(50.779611, 6.101952, 0, 0), 2.5, 5, 185, false));
			spots.add(new Spot(new Coordinate(50.779691, 0.610204, 0, 0), 2.5, 5, 190, false));
			spots.add(new Spot(new Coordinate(50.779832, 6.101959, 0, 0), 2.5, 5, 195, false));
			spots.add(new Spot(new Coordinate(50.779776, 0.610176, 0, 0), 2.5, 5, 200, false));
			spots.add(new Spot(new Coordinate(50.779523, 6.101763, 0, 0), 2.5, 5, 205, false));
			spots.add(new Spot(new Coordinate(50.779447, 6.102051, 0, 0), 2.5, 5, 210, false));
			spots.add(new Spot(new Coordinate(50.779743, 6.102225, 0, 0), 2.5, 5, 215, false));
			spots.add(new Spot(new Coordinate(50.780025, 6.101956, 0, 0), 2.5, 5, 220, false));
			spots.add(new Spot(new Coordinate(50.779823, 6.101571, 0, 0), 2.5, 5, 225, false));

			return spots;
		}

	}
