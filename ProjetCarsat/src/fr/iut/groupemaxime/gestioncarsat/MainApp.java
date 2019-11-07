package fr.iut.groupemaxime.gestioncarsat;

import java.io.IOException;

import fr.iut.groupemaxime.gestioncarsat.view.OrdreMissionInfoPersoController;
import fr.iut.groupemaxime.gestioncarsat.view.OrdreMissionMoyenTransportController;
import fr.iut.groupemaxime.gestioncarsat.view.OrdreMissionTypeOMController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("OM");
		//this.primaryStage.isResizable(); --> propriété redimensionnement de la fenêtre

		
		
		
		
		
	
		
		
		
		
		
		
		
		
		
		
		
		initRootLayout();

		afficherFormInfoPerso();

	}

	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void afficherFormInfoPerso() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/OrdreMissionInfoPerso.fxml"));
			AnchorPane page = loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(page);

			// Give the controller access to the main app.
			OrdreMissionInfoPersoController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public BorderPane getRootLayout() {
		return rootLayout;
	}

	public void afficherFormTypeOM() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/OrdreMissionTypeOM.fxml"));
			AnchorPane ordreMission = loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(ordreMission);

			// Give the controller access to the main app.
			OrdreMissionTypeOMController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void afficherFormMoyenTransport() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/OrdreMissionMoyenTransport.fxml"));
			AnchorPane ordreMission = loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(ordreMission);

			// Give the controller access to the main app.
			OrdreMissionMoyenTransportController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
