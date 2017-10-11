package parkingLotSystem;

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

	public abstract double calculateBill();

}