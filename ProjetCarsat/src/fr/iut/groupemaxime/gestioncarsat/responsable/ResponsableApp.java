package fr.iut.groupemaxime.gestioncarsat.responsable;

import java.io.IOException;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Constante;
import fr.iut.groupemaxime.gestioncarsat.agent.model.ListeOrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Options;
import fr.iut.groupemaxime.gestioncarsat.agent.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.responsable.view.OptionsResponsableController;
import fr.iut.groupemaxime.gestioncarsat.responsable.view.RootLayoutController;
import fr.iut.groupemaxime.gestioncarsat.responsable.view.ListeMissionsResponsableController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ResponsableApp extends Application {

	private Stage primaryStage;
	private Options options;
	private AnchorPane listeMissionsResponsable;
	private BorderPane rootLayout;
	private RootLayoutController rootLayoutCtrl;
	private Stage secondaryStage;
	private ListeMissionsResponsableController controllerListeMissionsResponsable;
	private ListeOrdreMission listeOM;
	private OrdreMission missionActive;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Carsat - Gestion des déplacement");
		this.primaryStage.getIcons().add(new Image("file:" + Constante.CHEMIN_IMAGES + "logo.png"));
		this.primaryStage.setResizable(true);
		this.options = new Options();
		this.options = this.options.chargerJson(Constante.CHEMIN_OPTIONS);
		initialiseRootLayout();
		afficherListeMissions();
	}

	public void initialiseRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ResponsableApp.class.getResource("view/RootLayout.fxml"));
			this.rootLayout = loader.load();
			rootLayoutCtrl = loader.getController();
			rootLayoutCtrl.setMainApp(this);

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
			loader.setLocation(AgentApp.class.getResource("view/OptionsResp.fxml"));
			AnchorPane optionsLayout = loader.load();

			Scene scene = new Scene(optionsLayout);
			this.secondaryStage = new Stage();
//			OptionsResponsableController controllerOptions = loader.getController();
//			controllerOptions.chargerPage(this, options);
//			controllerOptions.chargerMailsResponsable();
			secondaryStage.setScene(scene);
			this.secondaryStage.setTitle("Paramètres");
			this.secondaryStage.getIcons().add(new Image("file:" + Constante.CHEMIN_IMAGES + "logo.png"));

			secondaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setOptions(Options options) {
		this.options = options;
		this.options.sauvegarderJson(Constante.CHEMIN_OPTIONS);
	}
	
	public void afficherListeMissions() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("view/listeMissionsResponsable.fxml"));

			this.listeMissionsResponsable = loader.load();

			if (!this.rootLayoutCtrl.getGridRoot().getChildren().contains(this.listeMissionsResponsable))
				this.rootLayoutCtrl.getGridRoot().add(this.listeMissionsResponsable, 0, 0);
			controllerListeMissionsResponsable = loader.getController();
			if (controllerListeMissionsResponsable == null)
				System.out.println("gg");
			controllerListeMissionsResponsable.setResponsableApp(this);
			controllerListeMissionsResponsable.setOptions(this.options);
			controllerListeMissionsResponsable.chargerOM();
			this.listeOM = controllerListeMissionsResponsable.getListeOm();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public void setMissionActive(OrdreMission om) {
		this.missionActive = om;
	}

}
