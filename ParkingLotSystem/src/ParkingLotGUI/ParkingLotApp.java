package ParkingLotGUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import parkingLotSystem.Car;
import parkingLotSystem.ParkingLot;
import parkingLotSystem.Truck;
import parkingLotSystem.Vehicle;

public class ParkingLotApp extends Application {

	/**
	*  ParkingLotApp javafx application gives the user a platform to store customer parking information
	*  and gives feedback information regarding the work order.
	*
	*  Including:
	*  - A License plate (set at 4-7 characters)
	*  - Customer requested hours of parking time. (set at minimum: 1 hour, maximum: 72 hours)
	*  - Storing vehicle as either a Truck (set to $30 hourly) or a Car (set to $15 hourly)
	*  - Assigns customer to corresponding parking lot number (set from 0-100)
	*  - Calculating the final bill amount $.
	*  - Time of parking permit expiration, and time and date of purchase.
	*  - Note: The application will not allow duplicate license plates.
	*
	*   @see <A HREF="..application/ParkingLotApp.java">
	*   (www.sunysuffolk.edu/nickdimonti/application/ParkingLotApp.java)
	*
	*   @author dimon71 <A HREF="mailto:dimon71@sunysuffolk.edu"> (dimon71@sunysuffolk.edu) </A>
	*
	*   @version October 10, 2017
	*
	*   **/

	ParkingLot theParkingLot = new ParkingLot();

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Parking Garage Manager");

		GridPane gridPane = new GridPane();
		Scene scene = new Scene(gridPane, 700, 550);
		gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(20, 20, 20, 20));


        // Add Vehicle button
        Button addVehicleBtn = new Button("Add Vehicle");
        addVehicleBtn.setPrefWidth(150);
        addVehicleBtn.setAlignment(Pos.CENTER);
        gridPane.add(addVehicleBtn, 0, 0);


        // Cancel Vehicle button
        Button cancelVehicleBtn = new Button("Cancel Vehicle");
        cancelVehicleBtn.setPrefWidth(150);
        cancelVehicleBtn.setAlignment(Pos.CENTER);
        gridPane.add(cancelVehicleBtn, 1, 0);


        // License Plate label and text field
        Label licensePlateLbl = new Label("License Plate: ");
        gridPane.add(licensePlateLbl, 0, 1);
        TextField licensePlateField = new TextField();
        licensePlateField.setPromptText("Enter plate # to Cancel Vehicle (4-7 characters)");
        gridPane.add(licensePlateField, 1, 1);


        // Amount of parking time requested label and text field
        Label timeRequestedLbl = new Label("Time Requested: ");
        gridPane.add(timeRequestedLbl, 0, 2);
        TextField timeRequestedField = new TextField();
        timeRequestedField.setPromptText("hourly (1-72)");
        gridPane.add(timeRequestedField, 1, 2);


        // Parking spot number label and text field
        Label parkingSpotNumberLbl = new Label("Parking Spot Number: ");
        gridPane.add(parkingSpotNumberLbl, 0, 3);
        TextField parkingSpotNumberField = new TextField();
        parkingSpotNumberField.setPromptText("(0-100)");
        gridPane.add(parkingSpotNumberField, 1, 3);


        // Car or Truck radio button
        RadioButton isATruckRb = new RadioButton("Vehicle is a Truck");
        gridPane.add(isATruckRb, 0, 4);


        // List of parked Vehicles by License plate
        ListView vehicleList = new ListView();
        gridPane.add(vehicleList, 1, 4);
        vehicleList.setPrefWidth(450);


        // Action event for new vehicle button (created new vehicle then adds it to the lot)
        addVehicleBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	if(!licensePlateField.getText().isEmpty() && licensePlateField.getText() != null
            			&& isATruckRb.isSelected() && !timeRequestedField.getText().isEmpty() &&
            				timeRequestedField.getText() != null && licensePlateField.getText().length() >= 4 &&
            					licensePlateField.getText().length() <= 7 && Double.parseDouble(timeRequestedField.getText())
            						< 72 && Double.parseDouble(timeRequestedField.getText()) >= 1 &&
            							!licensePlateExists(licensePlateField.getText()) && !parkingSpotNumberField.getText().isEmpty()
            								&& parkingSpotNumberField.getText() != null && Integer.parseInt(parkingSpotNumberField.getText())
        									>= 0 && Integer.parseInt(parkingSpotNumberField.getText()) <= 100){
            		String licensePlate = licensePlateField.getText();
            		int spotNumber = Integer.parseInt(parkingSpotNumberField.getText());
            		double requestedParkingTime = Double.parseDouble(timeRequestedField.getText());
            		Truck newTruck = new Truck(licensePlate, requestedParkingTime, spotNumber); //vehicle created
            		theParkingLot.getVehicleParkingSpots().add(newTruck);
            		vehicleList.getItems().add(newTruck);
            		licensePlateField.clear();
            		timeRequestedField.clear();
            		parkingSpotNumberField.clear();
            		isATruckRb.setSelected(false); //unselect truck button
            	} else if (!licensePlateField.getText().isEmpty() && licensePlateField.getText() != null
            			&& !isATruckRb.isSelected() && !timeRequestedField.getText().isEmpty() &&
            				timeRequestedField.getText() != null && licensePlateField.getText().length() >= 4 &&
            					licensePlateField.getText().length() <= 7 && Double.parseDouble(timeRequestedField.getText())
            						< 72 && Double.parseDouble(timeRequestedField.getText()) >= 1 &&
            							!licensePlateExists(licensePlateField.getText()) && !parkingSpotNumberField.getText().isEmpty()
        									&& parkingSpotNumberField.getText() != null && Integer.parseInt(parkingSpotNumberField.getText())
        									>= 0 && Integer.parseInt(parkingSpotNumberField.getText()) <= 100){
            		String licensePlate = licensePlateField.getText();
            		int spotNumber = Integer.parseInt(parkingSpotNumberField.getText());
            		double requestedParkingTime = Double.parseDouble(timeRequestedField.getText());
            		Car newCar = new Car(licensePlate, requestedParkingTime, spotNumber); //vehicle created
            		theParkingLot.getVehicleParkingSpots().add(newCar);
            		vehicleList.getItems().add(newCar);
            		licensePlateField.clear();
            		timeRequestedField.clear();
            		parkingSpotNumberField.clear();
            		//displayVehiclesInHashSet(); TEST METHOD FOR HASH SET
            	}
        	}
        });

        // Action event for canceling a vehicle (deletes vehicle from list view and hashSet)
        cancelVehicleBtn.setOnAction(new EventHandler<ActionEvent>() {
       		@Override
        	public void handle(ActionEvent e) {
       			if(!licensePlateField.getText().isEmpty() && licensePlateField.getText() != null){
            		String licensePlate = licensePlateField.getText();
            		Vehicle vehicle = findVehicleByLicensePlate(licensePlate);
            		theParkingLot.getVehicleParkingSpots().remove(vehicle);
					vehicleList.getItems().remove(vehicle);
            		licensePlateField.clear();
            	}
        	}
        });

        primaryStage.setScene(scene);
        primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}


	public Vehicle findVehicleByLicensePlate(String licensePlate){
		for(Vehicle v : theParkingLot.getVehicleParkingSpots()){
			if(v.licensePlate.equals(licensePlate)){
				return v;
			}
		}
		return null;
	}


	public boolean licensePlateExists(String licensePlate){
		for(Vehicle v : theParkingLot.getVehicleParkingSpots()){
			if(v.licensePlate.equals(licensePlate)){
				return true;
			}
		}
		return false;
	}


	/*
 	//TEST METHOD: DISPLAY ON CONSOLE theParkingLot.getVehicleParkingSpots()
	public void displayVehiclesInHashSet(){
		for(Vehicle v : theParkingLot.getVehicleParkingSpots()){
			System.out.println(v.toString());
		}
	}
	*/

}

