package fr.iut.groupemaxime.gestioncarsat;

import java.io.IOException;

import fr.iut.groupemaxime.gestioncarsat.model.Constante;
import fr.iut.groupemaxime.gestioncarsat.model.Options;
import fr.iut.groupemaxime.gestioncarsat.view.OptionsController;
import fr.iut.groupemaxime.gestioncarsat.view.OrdreMissionController;
import fr.iut.groupemaxime.gestioncarsat.view.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private Stage secondaryStage;
	private BorderPane rootLayout;
	private Options options;
	private RootLayoutController controllerRoot;
	private OrdreMissionController omCtrl;
	private AnchorPane ordreMission;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Carsat - Gestion des déplacement");
		this.primaryStage.getIcons().add(new Image("file:" + Constante.CHEMIN_IMAGES + "logo.png"));
		this.primaryStage.setResizable(false);
		this.options = Options.chargerOptions();
		initialiseRootLayout();
	}

	public void initialiseRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			this.rootLayout = loader.load();
			this.controllerRoot = loader.getController();
			this.controllerRoot.setMainApp(this);

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void modifierOptions() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Options.fxml"));
			AnchorPane optionsLayout = loader.load();

			Scene scene = new Scene(optionsLayout);
			secondaryStage = new Stage();
			OptionsController controllerOptions = loader.getController();
			controllerOptions.chargerPage(this, options);
			secondaryStage.setScene(scene);
			secondaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setOptions(Options options) {
		this.options = options;
		options.sauvegarderOptions();
	}

	public void fermerSecondaryStage() {
		this.secondaryStage.close();
	}

	public void afficherFraisMission() {
		// TODO Auto-generated method stub

	}

	public void afficherHorairesTravail() {
		// TODO Auto-generated method stub

	}

	public void afficherOrdresMission() {
		if (this.omCtrl != null) {
			this.rootLayout.setCenter(this.ordreMission);
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainApp.class.getResource("view/OrdreMission.fxml"));
				this.ordreMission = loader.load();

				this.omCtrl = loader.getController();
				this.omCtrl.setMainApp(this);
				this.rootLayout.setCenter(this.ordreMission);
				this.omCtrl.setOptions(this.options);
				this.omCtrl.afficherListOm();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
