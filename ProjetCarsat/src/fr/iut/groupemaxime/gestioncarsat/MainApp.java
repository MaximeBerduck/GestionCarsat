package fr.iut.groupemaxime.gestioncarsat;

import java.io.IOException;

import fr.iut.groupemaxime.gestioncarsat.view.AgentController;
import fr.iut.groupemaxime.gestioncarsat.view.TransportController;
import fr.iut.groupemaxime.gestioncarsat.view.MissionController;
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

			
		initialiseRootLayout();

		afficherFormInfoPerso();

	}

	public void initialiseRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = loader.load();

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
			loader.setLocation(MainApp.class.getResource("view/Agent.fxml"));
			AnchorPane page = loader.load();

			rootLayout.setCenter(page);

			AgentController controller = loader.getController();
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
			loader.setLocation(MainApp.class.getResource("view/Mission.fxml"));
			AnchorPane ordreMission = loader.load();

			rootLayout.setCenter(ordreMission);

			MissionController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void afficherFormMoyenTransport() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Transport.fxml"));
			AnchorPane ordreMission = loader.load();

			rootLayout.setCenter(ordreMission);

			TransportController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
