package com.sonah.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.mysql.fabric.xmlrpc.base.Array;
import com.sonah.models.City;
import com.sonah.models.Coordinate;
import com.sonah.models.Counter;
import com.sonah.models.ParkingLocation;
import com.sonah.models.Spot;
import com.sonah.models.User;

public class OpenServerInitializer {
	static SessionFactory hibernateSessionFactory = new Configuration().configure().buildSessionFactory();
	static Session session;
	private static List<ParkingLocation> parkingLocations = new ArrayList<>();
	private static List<Counter> counters = new ArrayList<>();
	private static List<Spot> spots = new ArrayList<>();
	private static Collection<User> users = new ArrayList<>();

	private static ParkingLocation dummy_Adalbertstrasse;
	private static ParkingLocation blutcherPlatz;

	private static final int blutcherSpotIdLastValue = 10048;
	private static final int blutcherCounterIdLastValue = 10038;

	static {
		createListOfParkingLocations();
		createListOfCounters();
		createListOfSpots();
		createListOfUsers();

		int totalParking = 0;
		int availableParking = 0;
		int dummyTotal = 0;
		int dummyAvailable = 0;

		for (Counter counter : counters) {

			if (counter.getId() <= blutcherCounterIdLastValue) {
				availableParking += counter.getAvailable();
				totalParking += counter.getTotal();
				counter.setParkinglocation(blutcherPlatz);
				parkingLocations.get(0).getCounters().add(counter);
			}
			// Initializing DummyCounter
			else {
				dummyAvailable += counter.getAvailable();
				dummyTotal += counter.getTotal();

				counter.setParkinglocation(dummy_Adalbertstrasse);
				parkingLocations.get(1).getCounters().add(counter);
			}
		}

		for (Spot spot : spots) {
			if (spot.getId() <= blutcherSpotIdLastValue) {
				if (spot.isState())
					availableParking++;
				totalParking++; // each spot is a parking.
				spot.setParkinglocation(blutcherPlatz);
				parkingLocations.get(0).getSpots().add(spot);

				// Initializing DummyCounter
			} else {
				if (spot.isState())
					dummyAvailable++;
				dummyTotal++;

				spot.setParkinglocation(dummy_Adalbertstrasse);
				parkingLocations.get(1).getSpots().add(spot);
			}
		}

		parkingLocations.get(0).setTotalParking(totalParking);
		parkingLocations.get(0).setAvailableParking(availableParking);

		parkingLocations.get(1).setTotalParking(dummyTotal);
		parkingLocations.get(1).setAvailableParking(dummyAvailable);

	}

	public static void main(String[] args) {
		// testDb();
		// ParkingGenerator.initiateCityParking();
		// Coordinate c = new Coordinate();
		// c.setCoordinate_x("50.794582203435596");
		// c.setCoordinate_y("6.104709203435596");
		// c.setZoomLevel(1);
		//
		// List<ParkingLocation> pl =
		// ParkingGenerator.getListOfParkingInCluster(c);
		// System.out.println(pl.size());
		// for(ParkingLocation p: pl)
		// System.out.println(p);

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

	// private static void testDb() {
	// SecureServerInitializer testObj = new SecureServerInitializer();
	// blutcherPlatz = testObj.createParkingLocation();
	// session = hibernateSessionFactory.openSession();
	// session.beginTransaction();
	// session.save(blutcherPlatz);
	// session.save(testObj.createCounter(blutcherPlatz));
	// session.save(testObj.createSpot(blutcherPlatz));
	//
	// session.getTransaction().commit();
	// session.close();
	// }

	private static void generateDatabase() {
		session = hibernateSessionFactory.openSession();
		session.beginTransaction();
		// CreatingTable Parking Location

		for (ParkingLocation parkingLocation : parkingLocations)
			session.save(parkingLocation);
		// Creating Table counters
		for (Counter counter : counters) {
			if (counter.getId() <= blutcherCounterIdLastValue)
				counter.setParkinglocation(blutcherPlatz);
			else
				counter.setParkinglocation(dummy_Adalbertstrasse);
			session.save(counter);
		}

		// Creating Table spots
		for (Spot spot : spots) {
			if (spot.getId() <= blutcherSpotIdLastValue)
				spot.setParkinglocation(blutcherPlatz);
			else
				spot.setParkinglocation(dummy_Adalbertstrasse);

			session.save(spot);
		}

		for (User user : users) {
			session.save(user);
		}

		session.getTransaction().commit();
		session.close();

		System.out.println("DatabseGenerated");
	}

	private ParkingLocation createParkingLocation() {
		return new ParkingLocation(1001, "Bluecherplatz", new Coordinate(50.779683, 6.101926, 0, 0), 0, 0, false);
	}

	private Counter createCounter(ParkingLocation parkingLocation) {
		Counter counter = new Counter(10018, new Coordinate("50.779834", "6.101338", 0, 0), 5.0, 5.0, 45, 0, 2, 0,
				"p1_r0_17");
		counter.setParkinglocation(parkingLocation);
		return counter;
	}

	private Spot createSpot(ParkingLocation parkingLocation) {
		Spot spot = new Spot(10001, new Coordinate("50.779809", "6.10201", 0, 0), 5.0, 2.5, 0, false, 0, "p1_r0_0");
		spot.setParkinglocation(parkingLocation);
		return spot;
	}

	private static void createListOfUsers() {
		users.add(new User(1, "Campus", "Melaten", "campus_melaten", "dummyCampusMelaten"));
		users.add(new User(2, "Ausweis", "Parkzone", "dummyrwth", "dummyRwthPass"));
		users.add(new User(3, "Dummy", "User", "dummyuser", "dummyPass"));

		Iterator<User> iterator = users.iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			switch (user.getUsername()) {
			case "campus_melaten":
				parkingLocations.get(0).getUser().add(user);
				user.getParkingLocations().add(parkingLocations.get(0));
				break;
			case "dummyrwth":
				parkingLocations.get(1).getUser().add(user);
				user.getParkingLocations().add(parkingLocations.get(1));
				break;
			case "dummyuser":
				for (ParkingLocation location : parkingLocations) {
					location.getUser().add(user);
					user.getParkingLocations().add(location);
				}
				break;

			default:
				break;
			}
		}

	}

	private static List<ParkingLocation> createListOfParkingLocations() {

		// Open Server Parking Location
		parkingLocations.add(
				new ParkingLocation(1001, "Bluecherplatz", new Coordinate("50.779683", "6.101926", 0, 0), 0, 0, false));
		parkingLocations.add(new ParkingLocation(1005, "APAG Parkhaus Adalbertstrasse",
				new Coordinate("50.775612", "6.090170", 0, 0), 0, 0, false));
		parkingLocations.add(
				new ParkingLocation(1002, "Aquis Plaza", new Coordinate("50.774736", "6.092977", 0, 0), 0, 0, false));
		parkingLocations.add(new ParkingLocation(1003, "Parkhaus am Dom", new Coordinate("50.773289", "6.082237", 0, 0),
				0, 0, false));
		parkingLocations.add(new ParkingLocation(1004, "APAG Parkhaus Hbf",
				new Coordinate("50.767923", "6.089123", 0, 0), 0, 0, false));

		blutcherPlatz = parkingLocations.get(0);
		dummy_Adalbertstrasse = parkingLocations.get(1);
		return parkingLocations;
	}

	private static List<Counter> createListOfCounters() {

		// BlutcherPlatz Counter
		counters.add(
				new Counter(10018, new Coordinate("50.779834", "6.101338", 0, 0), 5.0, 5.0, 45, 2, 2, 0, "p1_r0_17"));
		counters.add(
				new Counter(10010, new Coordinate("50.779828", "6.101651", 0, 0), 5.0, 5.0, 45, 0, 2, 0, "p1_r0_9"));
		counters.add(
				new Counter(10019, new Coordinate("50.779714", "6.102003", 0, 0), 5.0, 5.0, 45, 3, 3, 0, "p1_r1_0"));
		counters.add(
				new Counter(10020, new Coordinate("50.779724", "6.101855", 0, 0), 5.0, 5.0, 45, 0, 6, 0, "p1_r1_1"));
		counters.add(
				new Counter(10021, new Coordinate("50.779724", "6.101855", 0, 0), 5.0, 5.0, 45, 3, 2, 0, "p1_r1_2"));
		counters.add(
				new Counter(10022, new Coordinate("50.779738", "6.101497", 0, 0), 5.0, 5.0, 45, 9, 9, 0, "p1_r1_3"));
		counters.add(
				new Counter(10023, new Coordinate("50.779668", "6.101941", 0, 0), 5.0, 5.0, 45, 7, 7, 0, "p1_r2_0"));
		counters.add(
				new Counter(10024, new Coordinate("50.779679", "6.101699", 0, 0), 5.0, 5.0, 45, 0, 7, 0, "p1_r2_1"));
		counters.add(
				new Counter(10025, new Coordinate("50.779679", "6.101576", 0, 0), 5.0, 5.0, 45, 0, 6, 0, "p1_r2_2"));
		counters.add(
				new Counter(10026, new Coordinate("50.779685", "6.101441", 0, 0), 5.0, 5.0, 45, 9, 9, 0, "p1_r2_3"));
		counters.add(
				new Counter(10036, new Coordinate("50.779567", "6.101752", 0, 0), 5.0, 5.0, 45, 0, 6, 0, "p1_r3_10"));
		counters.add(
				new Counter(10037, new Coordinate("50.779567", "6.101752", 0, 0), 5.0, 5.0, 45, 6, 6, 0, "p1_r3_9"));

		// Adalbertstrasse
		counters.add(new Counter(10039, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 5.0, 45, 0, 2, 1,
				"dummyCounter"));
		counters.add(new Counter(10040, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 5.0, 45, 3, 3, 2,
				"dummyCounter"));
		counters.add(new Counter(10041, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 5.0, 45, 0, 6, 3,
				"dummyCounter"));
		counters.add(new Counter(10042, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 5.0, 45, 3, 2, 4,
				"dummyCounter"));
		counters.add(new Counter(10043, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 5.0, 45, 9, 9, 5,
				"dummyCounter"));
		counters.add(new Counter(10044, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 5.0, 45, 7, 7, 4,
				"dummyCounter"));
		counters.add(new Counter(10045, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 5.0, 45, 7, 7, 3,
				"dummyCounter"));
		counters.add(new Counter(10046, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 5.0, 45, 6, 6, 2,
				"dummyCounter"));
		counters.add(new Counter(10047, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 5.0, 45, 9, 9, 1,
				"dummyCounter"));
		counters.add(new Counter(10048, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 5.0, 45, 6, 6, 2,
				"dummyCounter"));
		counters.add(new Counter(10049, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 5.0, 45, 6, 6, 3,
				"dummyCounter"));
		counters.add(new Counter(10050, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 5.0, 45, 2, 2, 0,
				"dummyCounter"));

		return counters;
	}

	private static List<Spot> createListOfSpots() {
		// BlutcherPlatz Counter
		spots.add(new Spot(10001, new Coordinate("50.779809", "6.10201", 0, 0), 5.0, 2.5, 0, true, 0, "p1_r0_0"));
		spots.add(new Spot(10002, new Coordinate("50.779814", "6.101971", 0, 0), 5.0, 2.5, 0, true, 0, "p1_r0_1"));
		spots.add(new Spot(10003, new Coordinate("50.779816", "6.101933", 0, 0), 5.0, 2.5, 0, true, 0, "p1_r0_2"));
		spots.add(new Spot(10004, new Coordinate("50.779816", "6.101893", 0, 0), 5.0, 2.5, 0, true, 0, "p1_r0_3"));
		spots.add(new Spot(10005, new Coordinate("50.779815", "6.101855", 0, 0), 5.0, 2.5, 0, true, 0, "p1_r0_4"));
		spots.add(new Spot(10006, new Coordinate("50.779818", "6.101811", 0, 0), 5.0, 2.5, 0, true, 0, "p1_r0_5"));
		spots.add(new Spot(10007, new Coordinate("50.779821", "6.101772", 0, 0), 5.0, 2.5, 0, true, 0, "p1_r0_6"));
		spots.add(new Spot(10008, new Coordinate("50.779824", "6.101733", 0, 0), 5.0, 2.5, 0, true, 0, "p1_r0_7"));
		spots.add(new Spot(10009, new Coordinate("50.779826", "6.101693", 0, 0), 5.0, 2.5, 0, true, 0, "p1_r0_8"));
		spots.add(new Spot(10011, new Coordinate("50.779832", "6.101612", 0, 0), 5.0, 2.5, 0, true, 0, "p1_r0_10"));
		spots.add(new Spot(10012, new Coordinate("50.779831", "6.101573", 0, 0), 5.0, 2.5, 0, true, 0, "p1_r0_11"));
		spots.add(new Spot(10013, new Coordinate("50.77983", "6.101533", 0, 0), 5.0, 2.5, 0, true, 0, "p1_r0_12"));
		spots.add(new Spot(10014, new Coordinate("50.77982", "6.101521", 0, 0), 5.0, 2.5, 0, true, 0, "p1_r0_13"));
		spots.add(new Spot(10015, new Coordinate("50.779831", "6.101452", 0, 0), 5.0, 2.5, 0, true, 0, "p1_r0_14"));
		spots.add(new Spot(10016, new Coordinate("50.779833", "6.101412", 0, 0), 5.0, 2.5, 0, true, 0, "p1_r0_15"));
		spots.add(new Spot(10017, new Coordinate("50.779834", "6.101372", 0, 0), 5.0, 2.5, 0, true, 0, "p1_r0_16"));
		spots.add(new Spot(10027, new Coordinate("50.779557", "6.102058", 0, 0), 5.0, 2.5, 0, true, 0, "p1_r3_0"));
		spots.add(new Spot(10028, new Coordinate("50.779557", "6.102023", 0, 0), 5.0, 2.5, 0, true, 0, "p1_r3_1"));
		spots.add(new Spot(10029, new Coordinate("50.779558", "6.101994", 0, 0), 5.0, 2.5, 0, true, 0, "p1_r3_2"));
		spots.add(new Spot(10030, new Coordinate("50.779559", "6.10196", 0, 0), 5.0, 2.5, 0, true, 0, "p1_r3_3"));
		spots.add(new Spot(10031, new Coordinate("50.779561", "6.101924", 0, 0), 5.0, 2.5, 0, false, 0, "p1_r3_4"));
		spots.add(new Spot(10032, new Coordinate("50.779561", "6.101892", 0, 0), 5.0, 2.5, 0, false, 0, "p1_r3_5"));
		spots.add(new Spot(10033, new Coordinate("50.779564", "6.101857", 0, 0), 5.0, 2.5, 0, false, 0, "p1_r3_6"));
		spots.add(new Spot(10034, new Coordinate("50.779566", "6.101827", 0, 0), 5.0, 2.5, 0, false, 0, "p1_r3_7"));
		spots.add(new Spot(10035, new Coordinate("50.779565", "6.101785", 0, 0), 5.0, 2.5, 0, false, 0, "p1_r3_8"));
		spots.add(new Spot(10038, new Coordinate("50.779568", "6.101722", 0, 0), 5.0, 2.5, 0, false, 0, "p1_r3_11"));
		spots.add(new Spot(10039, new Coordinate("50.779572", "6.101686", 0, 0), 5.0, 2.5, 0, false, 0, "p1_r3_12"));
		spots.add(new Spot(10040, new Coordinate("50.779572", "6.101652", 0, 0), 5.0, 2.5, 0, false, 0, "p1_r3_13"));
		spots.add(new Spot(10041, new Coordinate("50.779572", "6.101616", 0, 0), 5.0, 2.5, 0, false, 0, "p1_r3_14"));
		spots.add(new Spot(10042, new Coordinate("50.779573", "6.101584", 0, 0), 5.0, 2.5, 0, false, 0, "p1_r3_15"));
		spots.add(new Spot(10043, new Coordinate("50.779574", "6.101554", 0, 0), 5.0, 2.5, 0, false, 0, "p1_r3_16"));
		spots.add(new Spot(10044, new Coordinate("50.779576", "6.101514", 0, 0), 5.0, 2.5, 0, false, 0, "p1_r3_17"));
		spots.add(new Spot(10045, new Coordinate("50.779577", "6.101482", 0, 0), 5.0, 2.5, 0, false, 0, "p1_r3_18"));
		spots.add(new Spot(10046, new Coordinate("50.779578", "6.101447", 0, 0), 5.0, 2.5, 0, false, 0, "p1_r3_19"));
		spots.add(new Spot(10047, new Coordinate("50.779581", "6.101413", 0, 0), 5.0, 2.5, 0, false, 0, "p1_r3_20"));
		spots.add(new Spot(10048, new Coordinate("50.779581", "6.101386", 0, 0), 5.0, 2.5, 0, false, 0, "p1_r3_21"));

		// Adalbertstrasse spots.add(new Spot(10049, new
		spots.add(new Spot(10049, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 2.5, 0, false, 0, "dummyName"));
		spots.add(new Spot(10050, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 2.5, 0, false, 0, "dummyName"));
		spots.add(new Spot(10051, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 2.5, 0, false, 0, "dummyName"));
		spots.add(new Spot(10052, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 2.5, 0, false, 0, "dummyName"));
		spots.add(new Spot(10053, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 2.5, 0, true, 0, "dummyName"));
		spots.add(new Spot(10054, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 2.5, 0, true, 0, "dummyName"));
		spots.add(new Spot(10055, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 2.5, 0, true, 0, "dummyName"));
		spots.add(new Spot(10056, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 2.5, 0, true, 0, "dummyName"));
		spots.add(new Spot(10057, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 2.5, 0, true, 0, "dummyName"));
		spots.add(new Spot(10058, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 2.5, 0, true, 0, "dummyName"));
		spots.add(new Spot(10059, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 2.5, 0, true, 0, "dummyName"));
		spots.add(new Spot(10060, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 2.5, 0, false, 0, "dummyName"));
		spots.add(new Spot(10061, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 2.5, 0, false, 0, "dummyName"));
		spots.add(new Spot(10062, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 2.5, 0, false, 0, "dummyName"));
		spots.add(new Spot(10063, new Coordinate("50.775612", "6.090170", 0, 0), 5.0, 2.5, 0, false, 0, "dummyName"));

		return spots;
	}

}
