package fr.iut.groupemaxime.gestioncarsat;

import java.io.File;
import java.io.IOException;

import fr.iut.groupemaxime.gestioncarsat.model.Agent;
import fr.iut.groupemaxime.gestioncarsat.view.MenuAgentController;
import fr.iut.groupemaxime.gestioncarsat.model.Avion;
import fr.iut.groupemaxime.gestioncarsat.model.ListeOrdreMission;
import fr.iut.groupemaxime.gestioncarsat.model.Mission;
import fr.iut.groupemaxime.gestioncarsat.model.MissionPermanent;
import fr.iut.groupemaxime.gestioncarsat.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.model.Options;
import fr.iut.groupemaxime.gestioncarsat.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.model.Train;
import fr.iut.groupemaxime.gestioncarsat.model.Transport;
import fr.iut.groupemaxime.gestioncarsat.model.Voiture;
import fr.iut.groupemaxime.gestioncarsat.view.AgentController;
import fr.iut.groupemaxime.gestioncarsat.view.MenuAgentController;
import fr.iut.groupemaxime.gestioncarsat.view.TransportController;
import fr.iut.groupemaxime.gestioncarsat.view.MissionController;
import fr.iut.groupemaxime.gestioncarsat.view.OptionsController;
import fr.iut.groupemaxime.gestioncarsat.view.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private Stage secondaryStage;
	private BorderPane rootLayout;
	private AnchorPane pageAgent;
	private AnchorPane pageMission;
	private AnchorPane pageTransport;
	private AnchorPane pageMenuAgent;
	private RootLayoutController controllerRoot;
	private AgentController controllerAgent;
	private MissionController controllerMission;
	private TransportController controllerTransport;
	private MenuAgentController controllerMenuAgent;
	private ListeOrdreMission listeOM;

	private Options options;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("OM");
		this.primaryStage.setResizable(false);
		this.options = Options.chargerOptions();
		initialiseRootLayout();
		afficherListOm(this.options);

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

	public void afficherListOm(Options options) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MenuAgent.fxml"));

			pageMenuAgent = loader.load();

			rootLayout.setLeft(pageMenuAgent);

			controllerMenuAgent = loader.getController();
			controllerMenuAgent.setMainApp(this);
			controllerMenuAgent.setOptions(this.options);
			controllerMenuAgent.chargerOM();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void initialiseRootLayout() {
		listeOM = new ListeOrdreMission();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = loader.load();
			controllerRoot = loader.getController();
			controllerRoot.setMainApp(this);

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void afficherFormInfoPerso() {
		if (this.pageAgent != null) {
			rootLayout.setCenter(this.pageAgent);
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainApp.class.getResource("view/Agent.fxml"));
				pageAgent = loader.load();

				controllerAgent = loader.getController();
				controllerAgent.setMainApp(this);
				controllerAgent.setChamps(this.options.getAgent());

				rootLayout.setCenter(pageAgent);
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		if (this.pageMission != null) {
			rootLayout.setCenter(this.pageMission);
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainApp.class.getResource("view/Mission.fxml"));
				pageMission = loader.load();

				rootLayout.setCenter(pageMission);
				controllerMission = loader.getController();
				controllerMission.setMainApp(this);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void afficherFormMoyenTransport() {
		if (this.pageTransport != null) {
			rootLayout.setCenter(this.pageTransport);
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainApp.class.getResource("view/Transport.fxml"));
				pageTransport = loader.load();

				rootLayout.setCenter(pageTransport);

				controllerTransport = loader.getController();
				controllerTransport.setMainApp(this);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void validerOrdreMission() {
		Agent agent = new Agent(controllerAgent.getNomTextField().getText(),
				controllerAgent.getPrenomTextField().getText(),
				Integer.parseInt(controllerAgent.getNumCAPSSATextField().getText()),
				controllerAgent.getFonctionTextField().getText(),
				controllerAgent.getResidenceAdminTextField().getText(),
				controllerAgent.getUniteTavailTextField().getText(),
				Integer.parseInt(controllerAgent.getCoefficientTextField().getText()),
				Integer.parseInt(controllerAgent.getCodeAnalytiqueTextField().getText()));
		Mission mission;
		if (controllerMission.getOrdrePermanentRadioBtn().isSelected()) {
			mission = new MissionPermanent();
		} else {
			String dateDebut = String.valueOf(controllerMission.getDateDebut().getValue().getDayOfMonth()) + '/'
					+ String.valueOf(controllerMission.getDateDebut().getValue().getMonthValue()) + '/'
					+ String.valueOf(controllerMission.getDateDebut().getValue().getYear());
			String dateFin = String.valueOf(controllerMission.getDateFin().getValue().getDayOfMonth()) + '/'
					+ String.valueOf(controllerMission.getDateFin().getValue().getMonthValue()) + '/'
					+ String.valueOf(controllerMission.getDateFin().getValue().getYear());
			String titre;
			if (controllerMission.getFonctionHabituelleRadioBtn().isSelected()) {
				titre = "fonctionHabituelle";
			} else {
				titre = "formation";
			}
			mission = new MissionTemporaire(dateDebut, "12:00", dateFin, "12:00",
					controllerMission.getMotifDeplacementTextField().getText(),
					controllerMission.getLieuDeplacementTextField().getText(), titre);
		}

		Transport transport;

		if (controllerTransport.getAvionRadioBtn().isSelected()) {
			String cramco;
			if (controllerTransport.getCramcoNonRadioBtn().isSelected()) {
				cramco = "non";
			} else {
				cramco = "oui";
			}
			transport = new Avion(cramco);
		}

		else if (controllerTransport.getVoitureRadioBtn().isSelected()) {
			String appartenanceVehicule;
			if (controllerTransport.getVehiculePersoRadioBtn().isSelected()) {
				appartenanceVehicule = "vehiculePerso";
			} else {
				appartenanceVehicule = "vehiculeService";
			}
			transport = new Voiture(controllerTransport.getTypeVoitureTextField().getText(),
					controllerTransport.getImmatriculationTextField().getText(),
					Integer.parseInt(controllerTransport.getNbrCVTextField().getText()), appartenanceVehicule);
		}

		else {
			String classe;
			if (controllerTransport.getTrain1ereClasseRadioBtn().isSelected()) {
				classe = "premiereClasse";
			} else {
				classe = "deuxiemeClasse";
			}

			String cramco;
			if (controllerTransport.getCramcoNonRadioBtn().isSelected()) {
				cramco = "non";
			} else {
				cramco = "oui";
			}
			transport = new Train(classe, cramco);
		}

		OrdreMission om = new OrdreMission(agent, mission, transport);
		listeOM.ajouterOM(om);
		rootLayout.setCenter(null);
		om.sauvegarder(new File(om.getAgent().getNom()));
		afficherListOm(this.options);

	}

	public void creerNouveauOm() {
		this.controllerAgent = null;
		this.controllerMission = null;
		this.controllerTransport = null;
		this.pageAgent = null;
		this.pageMission = null;
		this.pageTransport = null;
		afficherFormInfoPerso();
	}

	public void enleverOm(OrdreMission om) {
		this.listeOM.supprimerOM(om);
		afficherListOm(this.options);
	}

	public void modifierOm(OrdreMission om) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Agent.fxml"));
			pageAgent = loader.load();

			rootLayout.setCenter(pageAgent);

			controllerAgent = loader.getController();
			controllerAgent.setMainApp(this);
			controllerAgent.setChamps(om.getAgent());

			loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Mission.fxml"));
			pageMission = loader.load();

			controllerMission = loader.getController();
			controllerMission.setMainApp(this);
			controllerMission.setChamps((MissionTemporaire) om.getMission());

			loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Transport.fxml"));
			pageTransport = loader.load();

			controllerTransport = loader.getController();
			controllerTransport.setMainApp(this);
			controllerTransport.setChamps(om.getTransport());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setOptions(Options options) {
		this.options = options;
		options.sauvegarderOptions();
		afficherListOm(this.options);
	}

	public void fermerSecondaryStage() {
		this.secondaryStage.close();
	}
}
