package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.File;
import java.io.IOException;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.Agent;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.AutreTransport;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.Avion;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.ListeOrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.MissionPermanent;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.Train;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.Transport;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.TypeMission;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.Voiture;
import fr.iut.groupemaxime.gestioncarsat.utils.Bibliotheque;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import fr.iut.groupemaxime.gestioncarsat.utils.EtatMission;
import fr.iut.groupemaxime.gestioncarsat.utils.Options;
import fr.iut.groupemaxime.gestioncarsat.utils.TypeDocument;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
	private Label titre;

	public void setTitre(String titre) {
		this.titre.setText(titre);
	}

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
			if (1 == this.ordreMissionSplit.getItems().size())
				this.ordreMissionSplit.getItems().set(0, this.pageAgent);
			else
				this.ordreMissionSplit.getItems().add(0, this.pageAgent);
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(this.getClass().getResource("Agent.fxml"));
				this.pageAgent = loader.load();

				controllerAgent = loader.getController();
				controllerAgent.setMainApp(this);
				controllerAgent.setChamps(this.options.getAgent());
				if (1 == this.ordreMissionSplit.getItems().size())
					this.ordreMissionSplit.getItems().set(0, this.pageAgent);
				else
					this.ordreMissionSplit.getItems().add(0, this.pageAgent);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void afficherFormTypeOM() {
		if (this.pageMission != null) {
			if (1 == this.ordreMissionSplit.getItems().size())
				this.ordreMissionSplit.getItems().set(0, this.pageMission);
			else
				this.ordreMissionSplit.getItems().add(0, this.pageMission);
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(this.getClass().getResource("Mission.fxml"));
				pageMission = loader.load();

				if (1 == this.ordreMissionSplit.getItems().size())
					this.ordreMissionSplit.getItems().set(0, this.pageMission);
				else
					this.ordreMissionSplit.getItems().add(0, this.pageMission);

				controllerMission = loader.getController();
				controllerMission.setMainApp(this);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void afficherEnvoiDuMail(TypeDocument typeDocument) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("Mail.fxml"));
			this.pageMail = loader.load();

			if (1 == this.ordreMissionSplit.getItems().size())
				this.ordreMissionSplit.getItems().set(0, this.pageMail);
			else
				this.ordreMissionSplit.getItems().add(0, this.pageMail);

			controllerMail = loader.getController();
			controllerMail.setMainApp(this);
			File pdfOM = new File((this.mainApp.getMissionActive().getCheminDossier()
					+ this.mainApp.getMissionActive().getNomOM() + Constante.EXTENSION_PDF));
			if (TypeDocument.ORDREMISSION == typeDocument) {
				controllerMail.setPiecesJointes(new File[] { pdfOM });
			} else {
				File pdfHT = new File((this.mainApp.getMissionActive().getCheminDossier()
						+ this.mainApp.getMissionActive().getNomOM() + Constante.EXTENSION_XLS).replace("OM_", "HT_"));
				controllerMail.setPiecesJointes(new File[] { pdfOM, pdfHT });
			}

			controllerMail.setExpediteur(this.options.getMailAgent() + '@' + Constante.HOSTNAME);

			String desti = demanderMailsDestinataire(options);

			if (desti.length() != 0)
				desti = desti.substring(0, desti.length() - 1);
			controllerMail.setDestinataires(desti);
			controllerMail.chargerOptions();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String demanderMailsDestinataire(Options options) {
		String desti = "";

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AgentApp.class.getResource("view/choixMailsDestinataires.fxml"));
			AnchorPane choixMails = loader.load();

			choixMailsDestinatairesController choixMailsCtrl = loader.getController();

			for (String responsable : this.options.getMailsResponsables()) {
				choixMailsCtrl.ajouterMails(responsable);
			}

			Scene scene = new Scene(choixMails);

			// New stage
			Stage fenetreChoixDesti = new Stage();
			fenetreChoixDesti.setResizable(false);
			choixMailsCtrl.initialize(fenetreChoixDesti, options);

			fenetreChoixDesti.setTitle("Choix destinataires");
			fenetreChoixDesti.setScene(scene);

			fenetreChoixDesti.initOwner(primaryStage);
			fenetreChoixDesti.initModality(Modality.WINDOW_MODAL);
			fenetreChoixDesti.showAndWait();
			desti = choixMailsCtrl.getDestinaires();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return desti;
	}

	public void afficherFormMoyenTransport() {
		if (this.pageTransport != null) {
			if (1 == this.ordreMissionSplit.getItems().size())
				this.ordreMissionSplit.getItems().set(0, this.pageTransport);
			else
				this.ordreMissionSplit.getItems().add(0, this.pageTransport);

		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(this.getClass().getResource("Transport.fxml"));
				pageTransport = loader.load();

				if (1 == this.ordreMissionSplit.getItems().size())
					this.ordreMissionSplit.getItems().set(0, this.pageTransport);
				else
					this.ordreMissionSplit.getItems().add(0, this.pageTransport);

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
		TypeMission mission;
		if (controllerMission.getOrdrePermanentRadioBtn().isSelected()) {
			mission = new MissionPermanent();
		} else {
			String dateDebut = Constante.FORMATTER_DATEPICKER.format(controllerMission.getDateDebut().getValue());
			String dateFin = Constante.FORMATTER_DATEPICKER.format(controllerMission.getDateFin().getValue());
			String heureDebut = controllerMission.getHeureDepart().getText() + ":"
					+ controllerMission.getMinuteDepart().getText();
			String heureFin = controllerMission.getHeureRetour().getText() + ":"
					+ controllerMission.getMinuteRetour().getText();

			String titre;

			if (controllerMission.getFonctionHabituelleRadioBtn().isSelected()) {
				titre = "fonctionHabituelle";
			} else {
				titre = "formation";
			}
			mission = new MissionTemporaire(dateDebut, heureDebut, dateFin, heureFin,
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

		else if (controllerTransport.getTrainRadioBtn().isSelected()) {
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
		} else {
			transport = new AutreTransport(controllerTransport.getAutreTransport().getText());
		}

		OrdreMission om = new OrdreMission(agent, mission, transport);
		if (this.controllerTransport.agentSigne()) {
			om.setSignatureAgent(true);
			om.setDateSignature(Bibliotheque.getDateAujourdhui());
			om.setEtat(EtatMission.SIGNE);
		} else {
			om.setEtat(EtatMission.NON_SIGNE);
		}

		this.listeOM.ajouterOM(om);
		this.ordreMissionSplit.getItems().remove(0);

		if (om.getCheminDossier() == null) {
			om.setCheminDossier(this.options.getCheminOM()
					+ ((MissionTemporaire) om.getMission()).getLieuDeplacement().replace(" ", "_") + '_'
					+ ((MissionTemporaire) om.getMission()).getDates() + '/');
			mainApp.creerDossier(om.getCheminDossier());
		}

		om.sauvegarderJson(om.getCheminDossier());

		// Permet de réinitialiser l'état saisies FM et HT si modif OM
		this.mainApp.setEtatFM(om, EtatMission.EN_COURS_SAISIE);
		this.mainApp.setEtatHT(om, EtatMission.EN_COURS_SAISIE);

		this.mainApp.retourMenu();
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

			if (1 == this.ordreMissionSplit.getItems().size())
				this.ordreMissionSplit.getItems().set(0, this.pageAgent);
			else
				this.ordreMissionSplit.getItems().add(0, this.pageAgent);

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

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void setMainApp(AgentApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setOptions(Options options) {
		this.options = options;
	}

	public Options getOptions() {
		return this.options;
	}

	public AgentApp getMainApp() {
		return mainApp;
	}
}
