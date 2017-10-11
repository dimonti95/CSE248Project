package parkingLotSystem;

import java.util.HashSet;

//Singleton Design Pattern

public class ParkingLot {

	private static ParkingLot _parkingLot;
	private HashSet<Vehicle> vehicleParkingSpots = new HashSet<Vehicle>();

	public ParkingLot() {}

	public static ParkingLot createParkingLot() {
		if(_parkingLot == null) {
			_parkingLot = new ParkingLot();
			System.out.println("A parkingLot is created");
		} else {
			System.out.println("A parkingLot already exists, no need for a new one.");
		}
		return _parkingLot;
	}

	public HashSet<Vehicle> getVehicleParkingSpots() {
		return vehicleParkingSpots;
	}

}
