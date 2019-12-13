package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Agent;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Avion;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Constante;
import fr.iut.groupemaxime.gestioncarsat.agent.model.ListeOrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Mail;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Mission;
import fr.iut.groupemaxime.gestioncarsat.agent.model.MissionPermanent;
import fr.iut.groupemaxime.gestioncarsat.agent.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Options;
import fr.iut.groupemaxime.gestioncarsat.agent.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Train;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Transport;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Voiture;
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
	private AnchorPane pageMail;
	private AgentController controllerAgent;
	private MissionController controllerMission;
	private TransportController controllerTransport;
	private MenuAgentController controllerMenuAgent;
	private ListeOrdreMission listeOM;
	private MailController controllerMail;

	private Stage primaryStage;

	private AgentApp mainApp;

	private Options options;

	@FXML
	private void initialize() {
		this.listeOM = new ListeOrdreMission();
	}

	public void afficherListOm() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("MenuAgent.fxml"));

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
				loader.setLocation(this.getClass().getResource("Agent.fxml"));
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
				loader.setLocation(this.getClass().getResource("Mission.fxml"));
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

	public void afficherEnvoiDuMail() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("Mail.fxml"));
			this.pageMail = loader.load();

			if (1 < this.ordreMissionSplit.getItems().size())
				this.ordreMissionSplit.getItems().set(1, this.pageMail);
			else
				this.ordreMissionSplit.getItems().add(1, this.pageMail);

			controllerMail = loader.getController();
			controllerMail.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
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
				loader.setLocation(this.getClass().getResource("Transport.fxml"));
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

		if (om.getCheminDossier() == null) {
			om.setCheminDossier(this.options.getCheminOM() + ((MissionTemporaire) om.getMission()).getLieuDeplacement()
					+ '_' + ((MissionTemporaire) om.getMission()).getDates() + '/');
			mainApp.creerDossier(om.getCheminDossier());
		}

		om.sauvegarderJson(om.getCheminDossier());

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
			loader.setLocation(this.getClass().getResource("Agent.fxml"));
			pageAgent = loader.load();

			if (1 < this.ordreMissionSplit.getItems().size())
				this.ordreMissionSplit.getItems().set(1, this.pageAgent);
			else
				this.ordreMissionSplit.getItems().add(1, this.pageAgent);

			controllerAgent = loader.getController();
			controllerAgent.setMainApp(this);
			controllerAgent.setChamps(om.getAgent());

			loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("Mission.fxml"));
			pageMission = loader.load();

			controllerMission = loader.getController();
			controllerMission.setMainApp(this);
			controllerMission.setChamps((MissionTemporaire) om.getMission());

			loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("Transport.fxml"));
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

	public void setMainApp(AgentApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setOptions(Options options) {
		this.options = options;
	}
}