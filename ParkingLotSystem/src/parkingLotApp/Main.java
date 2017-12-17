package parkingLotApp;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

	static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/parkingLotApp/MainView.fxml"));
			Scene scene = new Scene(root, 750, 650);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			// NOTE: CSS files are used to style the GUI
			primaryStage.setTitle("Parking Manager");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	// initializes and opens settings stage
	public static void showSettingsScene() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/parkingLotApp/SettingsView.fxml"));
		BorderPane settingsBorderPane = loader.load();
		Stage settingsStage = new Stage();
		settingsStage.setTitle("Settings");
		settingsStage.initModality(Modality.WINDOW_MODAL);
		settingsStage.initOwner(primaryStage);
		Scene scene = new Scene(settingsBorderPane, 700, 400);
		settingsStage.setScene(scene);
		settingsStage.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
