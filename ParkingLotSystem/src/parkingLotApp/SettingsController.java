package parkingLotApp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import parkingLotModel.Car;
import parkingLotModel.ParkingLot;
import parkingLotModel.Truck;
/**
*   The controller for the SettingsView
*
*   @author dimonti90 <A HREF="mailto:dimonti90@yahoo.com"> (dimonti90@yahoo.com) </A>
*
*   @version December 16, 2017
*
*   **/
public class SettingsController implements Initializable{

	@FXML private Slider carHourlyRateSlider = new Slider();
	@FXML private Slider truckHourlyRateSlider = new Slider();
	@FXML private TextArea carHourlyRateTextArea = new TextArea();
	@FXML private TextArea truckHourlyRateTextArea = new TextArea();
	@FXML private TextArea totalParkingSpotsTextArea = new TextArea();

	/**
	 * This method is used when the carHourlyRateSlider is used.
	 * **/
	public void carSliderClicked(){
		int carHourlyRate =  (int) carHourlyRateSlider.getValue();
		Car._hourlyRate = carHourlyRate;
		carHourlyRateTextArea.setText(Integer.toString(carHourlyRate));
	}

	/**
	 * This method is used when the truckHourlyRateSlider is used.
	 * **/
	public void truckSliderClicked(){
		int truckHourlyRate = (int) truckHourlyRateSlider.getValue();
		Truck._hourlyRate = truckHourlyRate;
		truckHourlyRateTextArea.setText(Integer.toString(truckHourlyRate));
	}

	/**
	 * This method is used when the total number of spots is incremented
	 * **/
	public void totalSpotsIncremented(){
		int totalParkingSpots = ParkingLot.totalParkingSpots;//Integer.parseInt(totalParkingSpotsTextArea.getText());
		totalParkingSpots++;
		totalParkingSpotsTextArea.setText(Integer.toString(totalParkingSpots));
		ParkingLot.totalParkingSpots = totalParkingSpots;
	}

	/**
	 * This method is used when the total number of spots is decremented. Before the total number of spots
	 * is decremented the method will check to make sure it is not decremented lower than the number of spots in use.
	 * @see checkNumberOfSpotsInUse
	 * **/
	public void totalSpotsDecremented(){
		int totalParkingSpots = ParkingLot.totalParkingSpots; //Integer.parseInt(totalParkingSpotsTextArea.getText());
		totalParkingSpots--;
		if(checkNumberOfSpotsInUse(totalParkingSpots)){
			totalParkingSpotsTextArea.setText(Integer.toString(totalParkingSpots));
			ParkingLot.totalParkingSpots = totalParkingSpots;
		}
	}

		public boolean checkNumberOfSpotsInUse(int newTotalSpotsValue){ // TEST
			if(ParkingLot.totalSpotsInUse > newTotalSpotsValue){
				AlertWindow.displayInputAlertBox("Error", "Total parking spots in use cannot exceed \n total parking spots.");
				return false;
			} else {
				return true;
			}

		}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carHourlyRateTextArea.setText(Integer.toString(Car._hourlyRate));
		carHourlyRateSlider.setValue(Car._hourlyRate); // TEST
		truckHourlyRateTextArea.setText(Integer.toString(Truck._hourlyRate));
		truckHourlyRateSlider.setValue(Truck._hourlyRate); // TEST
		totalParkingSpotsTextArea.setText(Integer.toString(ParkingLot.totalParkingSpots));
	}

}
