package fr.iut.groupemaxime.gestioncarsat.agent;

import java.io.File;
import java.io.IOException;

import fr.iut.groupemaxime.gestioncarsat.agent.model.Constante;
import fr.iut.groupemaxime.gestioncarsat.agent.model.ListeMails;
import fr.iut.groupemaxime.gestioncarsat.agent.model.ListeOrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Options;
import fr.iut.groupemaxime.gestioncarsat.agent.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.view.FraisMissionController;
import fr.iut.groupemaxime.gestioncarsat.agent.view.HorairesTravailController;
import fr.iut.groupemaxime.gestioncarsat.agent.view.MenuAgentController;
import fr.iut.groupemaxime.gestioncarsat.agent.view.OptionsController;
import fr.iut.groupemaxime.gestioncarsat.agent.view.OrdreMissionController;
import fr.iut.groupemaxime.gestioncarsat.agent.view.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AgentApp extends Application {

	private Stage primaryStage;
	private Stage secondaryStage;
	private BorderPane rootLayout;
	private RootLayoutController rootLayoutCtrl;
	private Options options;

	private OrdreMissionController omCtrl;
	private AnchorPane ordreMission;

	private AnchorPane fraisMission;
	private FraisMissionController fmCtrl;

	private AnchorPane horairesTravail;
	private HorairesTravailController htCtrl;

	private ListeMails mailsEnAttente;

	private AnchorPane pageMenuAgent;
	private MenuAgentController controllerMenuAgent;
	private ListeOrdreMission listeOM;

	private OrdreMission missionActive;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Carsat - Gestion des déplacement");
		this.primaryStage.getIcons().add(new Image("file:" + Constante.CHEMIN_IMAGES + "logo.png"));
		this.primaryStage.setResizable(true);
		this.options = new Options();
		this.options = this.options.chargerJson(Constante.CHEMIN_OPTIONS);
		this.creerDossier(this.options.getCheminOM());
		this.mailsEnAttente = new ListeMails();
		this.mailsEnAttente.chargerJson(Constante.CHEMIN_MAILS_EN_ATTENTE);
		initialiseRootLayout();
		afficherListeMissions();
	}

	public void initialiseRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AgentApp.class.getResource("view/RootLayout.fxml"));
			this.rootLayout = loader.load();
			rootLayoutCtrl = loader.getController();
			rootLayoutCtrl.setMainApp(this);
			rootLayoutCtrl.afficherOrdresMission();

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
			loader.setLocation(AgentApp.class.getResource("view/Options.fxml"));
			AnchorPane optionsLayout = loader.load();

			Scene scene = new Scene(optionsLayout);
			this.secondaryStage = new Stage();
			OptionsController controllerOptions = loader.getController();
			controllerOptions.chargerPage(this, options);
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

	public void fermerSecondaryStage() {
		this.secondaryStage.close();
	}

	public void afficherFraisMission() {
		retirerDocActif();
		if (this.fmCtrl != null) {
			this.rootLayoutCtrl.getGridRoot().add(this.fraisMission, 2, 0);
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(AgentApp.class.getResource("view/FraisMission.fxml"));
				this.fraisMission = loader.load();

				this.fmCtrl = loader.getController();
				this.fmCtrl.setMainApp(this);
				this.rootLayoutCtrl.getGridRoot().add(this.fraisMission, 2, 0);
				this.fmCtrl.setOptions(this.options);
				this.fmCtrl.afficherFMDate();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void afficherHorairesTravail() {
		retirerDocActif();
		if (this.htCtrl != null) {
			this.rootLayoutCtrl.getGridRoot().add(this.horairesTravail, 2, 0);
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(AgentApp.class.getResource("view/HorairesTravail.fxml"));
				this.horairesTravail = loader.load();

				this.htCtrl = loader.getController();
				this.htCtrl.setMainApp(this);
				this.rootLayoutCtrl.getGridRoot().add(this.horairesTravail, 2, 0);
				this.htCtrl.setOptions(this.options);
				this.htCtrl.afficherHorairesTravail();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void afficherOrdresMission() {
		retirerDocActif();
		if (this.omCtrl != null) {
			this.rootLayoutCtrl.getGridRoot().add(this.ordreMission, 2, 0);
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(AgentApp.class.getResource("view/OrdreMission.fxml"));
				this.ordreMission = loader.load();

				this.omCtrl = loader.getController();
				this.omCtrl.setMainApp(this);
				this.rootLayoutCtrl.getGridRoot().add(this.ordreMission, 2, 0);
				this.omCtrl.setOptions(this.options);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void afficherListeMissions() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("view/MenuAgent.fxml"));

			this.pageMenuAgent = loader.load();

			if (!this.rootLayoutCtrl.getGridRoot().getChildren().contains(this.pageMenuAgent))
				this.rootLayoutCtrl.getGridRoot().add(this.pageMenuAgent, 0, 0);
			controllerMenuAgent = loader.getController();
			controllerMenuAgent.setAgentApp(this);
			controllerMenuAgent.setOptions(this.options);
			controllerMenuAgent.chargerOM();
			this.listeOM = controllerMenuAgent.getListeOm();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void creerDossier(String chemin) {
		File fichier = new File(chemin);
		if (!fichier.exists()) {
			fichier.mkdir();
		}
	}

	public OrdreMissionController getOMCtrl() {
		return this.omCtrl;
	}

	public void setMissionActive(OrdreMission missionActive) {
		this.missionActive = missionActive;
	}

	public void retirerDocActif() {
		this.rootLayoutCtrl.getGridRoot().getChildren().remove(this.ordreMission);
		this.rootLayoutCtrl.getGridRoot().getChildren().remove(this.horairesTravail);
		this.rootLayoutCtrl.getGridRoot().getChildren().remove(this.fraisMission);
	}
	
	public Options getOptions() {
		return this.options;
	}

}
