package fr.iut.groupemaxime.gestioncarsat.view;

import java.io.File;
import java.io.IOException;

import fr.iut.groupemaxime.gestioncarsat.MainApp;
import fr.iut.groupemaxime.gestioncarsat.model.Agent;
import fr.iut.groupemaxime.gestioncarsat.model.Avion;
import fr.iut.groupemaxime.gestioncarsat.model.ListeOrdreMission;
import fr.iut.groupemaxime.gestioncarsat.model.Mail;
import fr.iut.groupemaxime.gestioncarsat.model.Mission;
import fr.iut.groupemaxime.gestioncarsat.model.MissionPermanent;
import fr.iut.groupemaxime.gestioncarsat.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.model.Options;
import fr.iut.groupemaxime.gestioncarsat.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.model.Train;
import fr.iut.groupemaxime.gestioncarsat.model.Transport;
import fr.iut.groupemaxime.gestioncarsat.model.Voiture;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class OrdreMissionController {

	@FXML
	private SplitPane ordreMissionSplit;

	private AnchorPane pageAgent;
	private AnchorPane pageMission;
	private AnchorPane pageTransport;
	private AnchorPane pageMenuAgent;
	private AgentController controllerAgent;
	private MissionController controllerMission;
	private TransportController controllerTransport;
	private MenuAgentController controllerMenuAgent;
	private ListeOrdreMission listeOM;

	private AnchorPane pageMail;
	private MailController controllerMail;

	private Stage primaryStage;

	private MainApp mainApp;

	private Options options;

	@FXML
	private void initialize() {
		this.listeOM = new ListeOrdreMission();
	}

	public void afficherListOm() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass()
					.getResource("/"
							+ MenuAgentController.class.getCanonicalName().replace(".", "/").replace("Controller", "")
							+ ".fxml"));

			this.pageMenuAgent = loader.load();
			if (!this.ordreMissionSplit.getItems().isEmpty()) {
				this.ordreMissionSplit.getItems().set(0, this.pageMenuAgent);
			} else {
				this.ordreMissionSplit.getItems().add(0, this.pageMenuAgent);
			}
			controllerMenuAgent = loader.getController();
			controllerMenuAgent.setMenuController(this);
			controllerMenuAgent.setOptions(this.options);
			controllerMenuAgent.chargerOM();
			this.listeOM = controllerMenuAgent.getListeOm();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void afficherFormInfoPerso() {
		if (this.pageAgent != null) {
			if (1 < this.ordreMissionSplit.getItems().size())
				this.ordreMissionSplit.getItems().set(1, this.pageAgent);
			else
				this.ordreMissionSplit.getItems().add(1, this.pageAgent);
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(this.getClass()
						.getResource("/"
								+ AgentController.class.getCanonicalName().replace(".", "/").replace("Controller", "")
								+ ".fxml"));
				this.pageAgent = loader.load();

				controllerAgent = loader.getController();
				controllerAgent.setMainApp(this);
				controllerAgent.setChamps(this.options.getAgent());
				if (1 < this.ordreMissionSplit.getItems().size())
					this.ordreMissionSplit.getItems().set(1, this.pageAgent);
				else
					this.ordreMissionSplit.getItems().add(1, this.pageAgent);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void afficherFormTypeOM() {
		if (this.pageMission != null) {
			if (1 < this.ordreMissionSplit.getItems().size())
				this.ordreMissionSplit.getItems().set(1, this.pageMission);
			else
				this.ordreMissionSplit.getItems().add(1, this.pageMission);
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(this.getClass()
						.getResource("/"
								+ MissionController.class.getCanonicalName().replace(".", "/").replace("Controller", "")
								+ ".fxml"));
				pageMission = loader.load();

				if (1 < this.ordreMissionSplit.getItems().size())
					this.ordreMissionSplit.getItems().set(1, this.pageMission);
				else
					this.ordreMissionSplit.getItems().add(1, this.pageMission);

				controllerMission = loader.getController();
				controllerMission.setMainApp(this);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void afficherFormMoyenTransport() {
		if (this.pageTransport != null) {
			if (1 < this.ordreMissionSplit.getItems().size())
				this.ordreMissionSplit.getItems().set(1, this.pageTransport);
			else
				this.ordreMissionSplit.getItems().add(1, this.pageTransport);

		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(this.getClass().getResource(
						"/" + TransportController.class.getCanonicalName().replace(".", "/").replace("Controller", "")
								+ ".fxml"));
				pageTransport = loader.load();

				if (1 < this.ordreMissionSplit.getItems().size())
					this.ordreMissionSplit.getItems().set(1, this.pageTransport);
				else
					this.ordreMissionSplit.getItems().add(1, this.pageTransport);

				controllerTransport = loader.getController();
				controllerTransport.setMainApp(this);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void afficherEnvoiMail() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(OrdreMissionController.class.getResource("view/Mail.fxml"));

			pageMail = loader.load();

			controllerMail = loader.getController();

			this.ordreMissionSplit.getItems().add(1, this.pageMail);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	public void envoyerLeMail() {
//		String[] listDest;
//		int i=0;
//		String[] listEnCopie;
//		int j=0;
//		for (String email : controllerMail.getDestinataireTextField().getText().split(",")) {
//			listDest[i] = email;
//			i++;
//		}
//		for (String email : controllerMail.getEnCopieTextField().getText().split(",")) {
//			listEnCopie[j] = email;
//			j++;
//		}
//		Mail mail = new Mail(controllerMail.getExpediteurTextField().getText(),
//				listDest, 
//				listEnCopie, 
//				controllerMail.getObjetDuMail().getText(), 
//				controllerMail.getCorpsDuMail().getText(),
//				controllerMail.getFileEnPieceJointe()); //Le problème est que je ne sais pas comment récuperer le fichier File dans la class ControllerMail
//	}

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
		this.listeOM.ajouterOM(om);
		this.ordreMissionSplit.getItems().remove(1);
		om.sauvegarder(new File(om.getAgent().getNom()));
		afficherListOm();

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
		afficherListOm();
	}

	public void modifierOm(OrdreMission om) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("/"
					+ AgentController.class.getCanonicalName().replace(".", "/").replace("Controller", "") + ".fxml"));
			pageAgent = loader.load();

			if (1 < this.ordreMissionSplit.getItems().size())
				this.ordreMissionSplit.getItems().set(1, this.pageAgent);
			else
				this.ordreMissionSplit.getItems().add(1, this.pageAgent);


			controllerAgent = loader.getController();
			controllerAgent.setMainApp(this);
			controllerAgent.setChamps(om.getAgent());

			loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource(
					"/" + MissionController.class.getCanonicalName().replace(".", "/").replace("Controller", "")
							+ ".fxml"));
			pageMission = loader.load();

			controllerMission = loader.getController();
			controllerMission.setMainApp(this);
			controllerMission.setChamps((MissionTemporaire) om.getMission());

			loader = new FXMLLoader();
			loader.setLocation(this.getClass()
					.getResource("/"
							+ TransportController.class.getCanonicalName().replace(".", "/").replace("Controller", "")
							+ ".fxml"));
			pageTransport = loader.load();

			controllerTransport = loader.getController();
			controllerTransport.setMainApp(this);
			controllerTransport.setChamps(om.getTransport());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return this.primaryStage;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setOptions(Options options) {
		this.options = options;
	}
}
