package parkingLotModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Vehicle {

	public String licensePlate;
	public double calculatedBill;
	public double requestedParkingTime;
	public String timeParked;
	public int spotNumber;
	public SimpleDateFormat format = new SimpleDateFormat();
	public Date date = new Date();
	public String vehicleType;

	public abstract double calculateBill();

	/* GETTER METHODS NEEDED FOR TABLE VIEW */
	public String getLicensePlate() {
		return licensePlate;
	}

	public int getSpotNumber() {
		return spotNumber;
	}

	public double getCalculatedBill() {
		return calculatedBill;
	}

	public String getTimeParked() {
		return timeParked;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public double getRequestedParkingTime() {
		return requestedParkingTime;
	}









}
