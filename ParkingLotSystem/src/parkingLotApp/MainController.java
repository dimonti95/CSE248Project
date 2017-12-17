package parkingLotApp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import parkingLotModel.Car;
import parkingLotModel.ParkingLot;
import parkingLotModel.Truck;
import parkingLotModel.Vehicle;
/**
*   The controller for the MainView
*
*   @author dimonti90 <A HREF="mailto:dimonti90@yahoo.com"> (dimonti90@yahoo.com) </A>
*
*   @version December 16, 2017
*
*   **/
public class MainController implements Initializable{

	@FXML ParkingLot theParkingLot = new ParkingLot();
	@FXML private BorderPane mainBorderPane; // UNFINISHED
	@FXML private TableView<Vehicle> vehicleTableView = new TableView<Vehicle>();
	@FXML private TableColumn<Vehicle, String> licensePlateTableColumn = new TableColumn<>();
	@FXML private TableColumn<Vehicle, Integer> spotNumberTableColumn = new TableColumn<>();
	@FXML private TableColumn<Vehicle, Double> billTableColumn = new TableColumn<>();
	@FXML private TableColumn<Vehicle, String> timeParkedTableColumn = new TableColumn<>();
	@FXML private TableColumn<Vehicle, String> vehicleTypeTableColumn = new TableColumn<>();
	@FXML private TableColumn<Vehicle, Double> requestedParkingTimeTableColumn = new TableColumn<>();
	@FXML private TextField licensePlateField = new TextField();
	@FXML private TextField timeRequestedField = new TextField();
	@FXML private TextField parkingSpotNumberField = new TextField();
	@FXML private TextField searchField = new TextField();
	@FXML private RadioButton isATruckRb = new RadioButton();
	@FXML private TextArea errorFeedbackTextArea = new TextArea();
	@FXML private ComboBox<String> searchByComboBox = new ComboBox<String>(); // <String> added as test
	private boolean searchByLicensePlate;
	private boolean searchBySpotNumber;


	/**
	 * This is the method used to add vehicles to the table view and the hash set.
	 * @param Action event
	 * **/
	public void addVehicle(ActionEvent event){
		if(isATruckRb.isSelected() && checkTextFieldInput()){
    		String licensePlate = licensePlateField.getText();
    		int spotNumber = Integer.parseInt(parkingSpotNumberField.getText());
    		double requestedParkingTime = Double.parseDouble(timeRequestedField.getText());
    		Truck newTruck = new Truck(licensePlate, requestedParkingTime, spotNumber); //vehicle created
    		theParkingLot.getVehicleParkingSpots().add(newTruck);
    		vehicleTableView.getItems().add(newTruck);
    		vehicleAdded();
    	} else if (checkTextFieldInput()){
    		String licensePlate = licensePlateField.getText();
    		int spotNumber = Integer.parseInt(parkingSpotNumberField.getText());
    		double requestedParkingTime = Double.parseDouble(timeRequestedField.getText());
    		Car newCar = new Car(licensePlate, requestedParkingTime, spotNumber); //vehicle created
    		theParkingLot.getVehicleParkingSpots().add(newCar);
    		vehicleTableView.getItems().add(newCar);
    		vehicleAdded();
    	}
	}

			public void vehicleAdded(){
				clearTextFields();
				ParkingLot.totalSpotsInUse = ParkingLot.totalSpotsInUse+1;
			}



	/**
	* This is the method used to cancel/delete vehicle from the table view and the hash set.
	* @param Action event
    * **/
	public void cancelVehicle(ActionEvent event){
            ObservableList<Vehicle> allVehicles;
            Vehicle vehicleSelected;
       		allVehicles = vehicleTableView.getItems();
           	vehicleSelected = vehicleTableView.getSelectionModel().getSelectedItem();
           	promptUserForVehicleDeletion(vehicleSelected, allVehicles); //make sure user wants to delete vehicle
           	vehicleRemoved();
           	displayVehiclesInHashSet(); //TEST METHOD FOR HASH SET
    }

			public void vehicleRemoved(){
				clearTextFields();
				ParkingLot.totalSpotsInUse = ParkingLot.totalSpotsInUse-1;
			}


	/**
	* This is the method used to search vehicles by either license plate or their spot number.
	* @see selectSearchByValue
	* @see checkLicensePlateSearchInput
	* @see checkSpotNumberSearchInput
	* @see checkSearchFieldInput
	* @param Action event
	* **/
	public void searchVehicle(ActionEvent event){
		if(searchByLicensePlate == true && checkSearchFieldInput() == true){
			String licensePlateInput = searchField.getText();
			Vehicle v = theParkingLot.findVehicleByLicensePlate(licensePlateInput);
			checkLicensePlateSearchInput(v, licensePlateInput);
		}
		else if(searchBySpotNumber == true && checkSearchFieldInput() == true){
			int spotNumberInput = Integer.parseInt(searchField.getText());
			Vehicle v = theParkingLot.findVehicleBySpotNumber(spotNumberInput);
			checkSpotNumberSearchInput(v, spotNumberInput);
		} else if (searchByLicensePlate == false && searchBySpotNumber == false){
			errorFeedbackTextArea.setText("A Search By option must be selected");
		}
		searchField.clear();
	}

		public void checkLicensePlateSearchInput(Vehicle v, String plateInput){
			if(v == null){
				errorFeedbackTextArea.setText("No vehicle was found with the License Plate number: " + plateInput);
			} else{
				errorFeedbackTextArea.setText("Vehicle Found: " + v.toString());
			}
		}


		public void checkSpotNumberSearchInput(Vehicle v, int spotInput){
			if(v == null){
				errorFeedbackTextArea.setText("No vehicle was found in the Spot number: " + spotInput);
			} else{
				errorFeedbackTextArea.setText("Vehicle Found: " + v.toString());
			}
		}


	/**
	* This method opens the settings window .
	* @throws IOException
	* **/
	public void openSettings() throws IOException {
		Main.showSettingsScene();
	}


	// Action event for user selecting a 'Search By' combo box button
	public void selectSearchByValue(ActionEvent event){
		String search = (String) searchByComboBox.getValue();
		if(search.equalsIgnoreCase("License Plate")){
			searchByLicensePlate = true;
			searchBySpotNumber = false;
		}else if(search.equalsIgnoreCase("Spot Number")){
			searchByLicensePlate = false;
			searchBySpotNumber = true;
		}
	}


	/**
	* This method checks all input requirements before allowing a vehicle to be stored. If invalid
	* input is entered an alert window informs the user.
	* @return boolean value determining whether or not the input was valid
	* **/
	public boolean checkTextFieldInput(){
		if(licensePlateField.getText().isEmpty() || licensePlateField.getText() == null){
			AlertWindow.displayInputAlertBox("Input Error", "License Plate field empty");
			return false;
		}
		if(timeRequestedField.getText().isEmpty() || timeRequestedField.getText() == null){
			AlertWindow.displayInputAlertBox("Input Error", "Time requested field empty");
			return false;
		}
		if(licensePlateField.getText().length() < 4 || licensePlateField.getText().length() > 7){
			AlertWindow.displayInputAlertBox("Input Error", "License Plate field must be between 4-7 characters");
			return false;
		}
		if(Double.parseDouble(timeRequestedField.getText()) > 72 ||
				Double.parseDouble(timeRequestedField.getText()) <= 0){
			AlertWindow.displayInputAlertBox("Input Error", "Time requested must be between 0-72 hours");
			return false;
		}
		if(theParkingLot.licensePlateExists(licensePlateField.getText())){
			AlertWindow.displayInputAlertBox("Input Error", "The license plate entered already exists");
			return false;
		}
		if(parkingSpotNumberField.getText().isEmpty() || parkingSpotNumberField.getText() == null){
			AlertWindow.displayInputAlertBox("Input Error", "Parking spot field empty");
			return false;
		}
		if(Integer.parseInt(parkingSpotNumberField.getText()) < 0 ||
				Integer.parseInt(parkingSpotNumberField.getText()) > ParkingLot.totalParkingSpots){
			AlertWindow.displayInputAlertBox("Input Error", "Parking spot bounds set from 0-" + ParkingLot.totalParkingSpots);
			return false;
		}
		if(theParkingLot.spotNumberExists(Integer.parseInt(parkingSpotNumberField.getText()))){
			AlertWindow.displayInputAlertBox("Input Error", "Parking spot entered already exists");
			return false;
		}
		if(!licensePlateField.getText().matches("[A-Z0-9]*")){ // regular expression
			AlertWindow.displayInputAlertBox("Input Error", "License Plate can contain only numbers \nand capital letters");
			return false;
		}
		 else{
			errorFeedbackTextArea.clear();
			return true;
		}
	}


	//checks field input before search
	public boolean checkSearchFieldInput(){
		if(searchField.getText().isEmpty() || searchField.getText() == null){
			errorFeedbackTextArea.setText("Error: Search text field empty");
			return false;
		} else return true;
	}

	/**
	 * This method clears each text field
	 * **/
	public void clearTextFields(){
		licensePlateField.clear();
		timeRequestedField.clear();
		parkingSpotNumberField.clear();
		searchField.clear();
		errorFeedbackTextArea.clear();
		isATruckRb.setSelected(false); //un-select truck button
	}


 	//TEST METHOD: DISPLAY ON CONSOLE theParkingLot.getVehicleParkingSpots()
	public void displayVehiclesInHashSet(){
		for(Vehicle v : theParkingLot.getVehicleParkingSpots()){
			System.out.println(v.toString());
		}
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// initializing search ComboBox
		searchByComboBox.getItems().addAll("License Plate", "Spot Number");

		// initializing vehicle Table View columns and centering the text for each
		licensePlateTableColumn.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
		licensePlateTableColumn.setStyle("-fx-alignment: CENTER;"); //center column text

		spotNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("spotNumber"));
		spotNumberTableColumn.setStyle("-fx-alignment: CENTER;"); //center column text

		billTableColumn.setCellValueFactory(new PropertyValueFactory<>("calculatedBill"));
		billTableColumn.setStyle("-fx-alignment: CENTER;"); //center column text

		timeParkedTableColumn.setCellValueFactory(new PropertyValueFactory<>("timeParked"));
		timeParkedTableColumn.setStyle("-fx-alignment: CENTER;"); //center column text

		vehicleTypeTableColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
		vehicleTypeTableColumn.setStyle("-fx-alignment: CENTER;"); //center column text

		requestedParkingTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("requestedParkingTime"));
		requestedParkingTimeTableColumn.setStyle("-fx-alignment: CENTER;"); //center column text

		// set boolean default to false
		searchByLicensePlate = false;
		searchBySpotNumber = false;
	}

	/**
	 * This method displays an alert window to check whether user wants to delete saved vehicle information
	 * @param The vehicle selected
	 * @param The observable list of vehicles
	 * **/
	public void promptUserForVehicleDeletion(Vehicle vehicleSelected, ObservableList<Vehicle> allVehicles){
		if(vehicleSelected != null){
			AlertWindow.displayCancelAlertBox("Alert Window", "Are you sure you want to cancel? \nAll vehicle information will be lost."); // note: static methods can be called
		}
		if(AlertWindow.isVehicleDeleted == true){
		  	allVehicles.removeAll(vehicleSelected);	//remove vehicle selected from table
           	theParkingLot.getVehicleParkingSpots().remove(vehicleSelected);	//remove vehicle selected from hash set
		}
	}

}
