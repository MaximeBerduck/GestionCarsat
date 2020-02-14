package fr.iut.groupemaxime.gestioncarsat.agent;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

import org.controlsfx.control.Notifications;

import fr.iut.groupemaxime.gestioncarsat.agent.form.PDF;
import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisMission;
import fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model.HoraireTravail;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.ListeOrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.view.EtatMissionSelectionneeController;
import fr.iut.groupemaxime.gestioncarsat.agent.view.FraisMissionController;
import fr.iut.groupemaxime.gestioncarsat.agent.view.HorairesTravailController;
import fr.iut.groupemaxime.gestioncarsat.agent.view.ItemOrdreMissionController;
import fr.iut.groupemaxime.gestioncarsat.agent.view.MenuAgentController;
import fr.iut.groupemaxime.gestioncarsat.agent.view.OptionsController;
import fr.iut.groupemaxime.gestioncarsat.agent.view.OrdreMissionController;
import fr.iut.groupemaxime.gestioncarsat.agent.view.RootLayoutController;
import fr.iut.groupemaxime.gestioncarsat.mail.ListeMails;
import fr.iut.groupemaxime.gestioncarsat.utils.Bibliotheque;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import fr.iut.groupemaxime.gestioncarsat.utils.EtatMission;
import fr.iut.groupemaxime.gestioncarsat.utils.Options;
import fr.iut.groupemaxime.gestioncarsat.utils.TypeDocument;
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AgentApp extends Application {

	private Stage primaryStage;
	private Stage secondaryStage;
	private BorderPane rootLayout;
	private RootLayoutController rootLayoutCtrl;
	private Options options;

	private OrdreMissionController omCtrl;
	private AnchorPane ordreMission;
	private ItemOrdreMissionController itemOmCtrl;

	private AnchorPane fraisMission;
	private FraisMissionController fmCtrl;

	private AnchorPane horairesTravail;
	private HorairesTravailController htCtrl;

	private ListeMails mailsEnAttente;

	private AnchorPane pageMenuAgent;
	private MenuAgentController controllerMenuAgent;
	private ListeOrdreMission listeOM;

	private OrdreMission missionActive;
	private FraisMission fmMissionActive;
	private HoraireTravail htMissionActive;

	private AnchorPane etatMission;

	private Service<Void> serviceEnvoiMail;

	////////////////////////////////////////
	//
	// AgentApp
	//
	////////////////////////////////////////

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Carsat - Gestion des déplacement");
		this.primaryStage.getIcons().add(new Image("file:" + Constante.CHEMIN_IMAGES + "logo.png"));
		this.primaryStage.setResizable(false);
		this.options = new Options();
		if (Bibliotheque.fichierExiste(Constante.CHEMIN_OPTIONS))
			this.options = this.options.chargerJson(Constante.CHEMIN_OPTIONS);
		else {
			this.options.sauvegarderJson(Constante.CHEMIN_OPTIONS);
		}
		this.creerDossier(this.options.getCheminOM());
		this.mailsEnAttente = new ListeMails();
		this.mailsEnAttente.chargerMails(Constante.CHEMIN_MAILS_EN_ATTENTE, this.options);
		this.serviceEnvoiMail = new Service<Void>() {

			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {

					@Override
					protected Void call() throws Exception {
						mailsEnAttente.iterationMails();
						return null;
					}
				};
			}
		};
		serviceEnvoiMail.setOnFailed((WorkerStateEvent event) -> {
			serviceEnvoiMail.reset();
			Notifications.create().title("Erreur")
					.text("Vous n'êtes pas connecté à Internet.\n Les mails en attente seront "
							+ "envoyés dès que vous serez connecté à Internet.")
					.showError();
		});
		serviceEnvoiMail.start();
		initialiseRootLayout();
		afficherListeMissions();
	}

	public void initialiseRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AgentApp.class.getResource("view/RootLayout.fxml"));
			this.rootLayout = loader.load();
			rootLayoutCtrl = loader.getController();
			rootLayoutCtrl.setAgentApp(this);

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fermerSecondaryStage() {
		this.secondaryStage.close();
	}

	public void retourMenu() {
		this.retirerDocActif();
		this.afficherListeMissions();
		if (!this.missionActiveIsNull()) {
			ItemOrdreMissionController itemOmCtrl = controllerMenuAgent.getItemOM(missionActive);
			if (itemOmCtrl != null) {
				this.setMissionActive(itemOmCtrl.getOM());
				itemOmCtrl.ajouterStyle(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
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

	public void retirerDocActif() {
		this.rootLayoutCtrl.getGridRoot().getChildren().remove(this.etatMission);
		this.rootLayoutCtrl.getGridRoot().getChildren().remove(this.ordreMission);
		this.rootLayoutCtrl.getGridRoot().getChildren().remove(this.horairesTravail);
		this.rootLayoutCtrl.getGridRoot().getChildren().remove(this.fraisMission);
		this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
	}

	////////////////////////////////////////
	//
	// Ordre de Mission
	//
	////////////////////////////////////////

	public void creerOrdreMission() {
		this.retirerMissionActive();
		this.afficherOrdresMission();
		this.rootLayoutCtrl.ajouterStyleOM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
		this.omCtrl.creerNouveauOm();
	}

	public void demanderActionOM() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Choix de l'action");
		alert.setHeaderText("Choisissez l'action souhaitée");
		alert.setContentText("Choisissez l'action que vous voulez réaliser sur votre ordre de mission.");
		ButtonType buttonTypeAfficher = new ButtonType("Afficher");
		ButtonType buttonTypeModif = new ButtonType("Modifier");
		alert.getButtonTypes().setAll(buttonTypeAfficher, buttonTypeModif);

		ButtonType buttonTypeEnvoyer = null;
		ButtonType buttonTypeSigner = null;
		if (!this.missionActive.estEnvoye()) {
			if (this.missionActive.agentSigne()) {
				buttonTypeEnvoyer = new ButtonType("Envoyer");
				alert.getButtonTypes().addAll(buttonTypeEnvoyer);
			} else {
				buttonTypeSigner = new ButtonType("Signer");
				alert.getButtonTypes().addAll(buttonTypeSigner);
			}
		}
		ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().add(buttonTypeCancel);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeAfficher) {
			this.afficherOrdreMissionPDF();
			this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
		} else if (result.get() == buttonTypeModif) {
			this.modifierOm(missionActive);
		} else if (result.get() == buttonTypeSigner) {
			this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			this.signerOM();
		} else if (result.get() == buttonTypeEnvoyer) {
			this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			this.afficherEnvoiDuMail(TypeDocument.ORDREMISSION);
		} else {
			this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			// Ne fait rien == bouton "annuler"
		}
	}

	public void afficherOrdresMission() {
		retirerDocActif();
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

	public void modifierOm(OrdreMission om) {
		this.afficherOrdresMission();
		this.omCtrl.modifierOm(om);
		this.omCtrl.setTitre(Constante.TITRE_MODIF_OM);
		this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
		this.rootLayoutCtrl.ajouterStyleOM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
	}

	public void afficherOrdreMissionPDF() {
		this.genererPDFOM();
		try {
			Desktop.getDesktop().browse(new File(
					this.missionActive.getCheminDossier() + this.missionActive.getNomOM() + Constante.EXTENSION_PDF)
							.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void genererPDFOM() {
		// TODO
		String chemin = this.missionActive.getCheminDossier()
				+ this.missionActive.getNomOM().concat(Constante.EXTENSION_PDF);
		PDF pdf;
		if (Bibliotheque.fichierExiste(chemin)) {
			try {
				pdf = new PDF(new File(chemin));
				pdf.remplirPDF(this.missionActive);
				pdf.sauvegarderPDF();

				if (this.missionActive.estSigne()) {
					pdf.ajouterDateSignatureOM(this.missionActive.getDateSignature());
					pdf.sauvegarderPDF();
					// Ajout carré blanc pour éviter doubles signatures
					PDF.signerPDF(Constante.SIGNATURE_AGENT_OM_X, Constante.SIGNATURE_AGENT_OM_Y,
							Constante.TAILLE_SIGNATURE, this.missionActive, Constante.CHEMIN_CARRE_BLANC);
					// Ajout signature
					PDF.signerPDF(Constante.SIGNATURE_AGENT_OM_X, Constante.SIGNATURE_AGENT_OM_Y,
							Constante.TAILLE_SIGNATURE, this.missionActive, this.getOptions().getCheminSignature());
				} else {
					// Retirer date signature si modif
					pdf.ajouterDateSignatureOM("");
					pdf.sauvegarderPDF();
					// Ajout carré blanc pour éviter doubles signatures
					PDF.signerPDF(Constante.SIGNATURE_AGENT_OM_X, Constante.SIGNATURE_AGENT_OM_Y,
							Constante.TAILLE_SIGNATURE, this.missionActive, Constante.CHEMIN_CARRE_BLANC);
				}
				pdf.fermerPDF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				pdf = new PDF(new File(Constante.CHEMIN_PDF_VIDE));
				pdf.remplirPDF(this.missionActive);
				pdf.sauvegarderPDF();

				if (this.missionActive.estSigne()) {
					pdf.ajouterDateSignatureOM(this.missionActive.getDateSignature());
					pdf.sauvegarderPDF();
					// Ajout signature
					PDF.signerPDF(Constante.SIGNATURE_AGENT_OM_X, Constante.SIGNATURE_AGENT_OM_Y,
							Constante.TAILLE_SIGNATURE, this.missionActive, this.getOptions().getCheminSignature());
				}
				pdf.fermerPDF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void signerOM() {
		if (Bibliotheque.fichierExiste(this.getOptions().getCheminSignature())) {
			this.missionActive.setSignatureAgent(true);
			this.missionActive.setDateSignature(Bibliotheque.getDateAujourdhui());
			this.missionActive.setEtat(EtatMission.SIGNE);
			this.missionActive.sauvegarderJson(this.missionActive.getCheminDossier());
		} else {
			TextInputDialog dialog = new TextInputDialog("");
			dialog.setTitle("Signature non renseignée");
			dialog.setHeaderText("Vous n'avez pas encore renseigné le chemin vers votre signature.");

			GridPane pageAlert = new GridPane();
			TextField tfCheminSignature = new TextField();
			tfCheminSignature.setPromptText("Chemin vers votre signature");

			Button boutonCheminSignature = new Button();
			boutonCheminSignature.setText("...");
			boutonCheminSignature.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					File cheminSignature = Bibliotheque.ouvrirFileChooser(Constante.IMAGE_FILTER);
					if (null != cheminSignature) {
						tfCheminSignature.setText(cheminSignature.toString());
					}
				}
			});

			pageAlert.add(new Label("Chemin vers votre signature : "), 0, 0);
			pageAlert.add(tfCheminSignature, 1, 0);
			pageAlert.add(boutonCheminSignature, 2, 0);

			dialog.getDialogPane().setContent(pageAlert);
			dialog.showAndWait();

			if ("".equals(tfCheminSignature.getText())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur chemin signature");
				alert.setHeaderText("Vous n'avez pas saisi l'adresse vers votre signature !");
				alert.showAndWait();
			} else {
				this.getOptions().setCheminSignature(tfCheminSignature.getText());
				this.getOptions().sauvegarderJson(Constante.CHEMIN_OPTIONS);
				this.missionActive.setSignatureAgent(true);
				this.missionActive.setEtat(EtatMission.SIGNE);
				this.missionActive.sauvegarderJson(this.getOptions().getCheminOM());
			}
		}
		this.retourMenu();
	}

	////////////////////////////////////////
	//
	// Frais de Mission
	//
	////////////////////////////////////////

	public void demanderActionFM() {
		if (Bibliotheque.fichierFmMissionExiste(missionActive)) {
			// Vérifie que le fichier fm est complet ou non
			if (Bibliotheque.fichierFmEstEntier(Bibliotheque.recupererFmAvecOm(missionActive))) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Choix de l'action");
				alert.setHeaderText("Choisissez l'action souhaitée");
				alert.setContentText("Choisissez l'action que vous voulez réaliser sur votre mission.");

				ButtonType buttonTypeAfficher = new ButtonType("Afficher");
				ButtonType buttonTypeModif = new ButtonType("Modifier");
				ButtonType buttonTypeSigner = new ButtonType("Signer");
				ButtonType buttonTypeEnvoyer = new ButtonType("Envoyer");
				ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);

				alert.getButtonTypes().setAll(buttonTypeAfficher, buttonTypeModif);
				if (!this.missionActive.fmEstSigne()) {
					alert.getButtonTypes().add(buttonTypeSigner);
				}
				if (Bibliotheque.fichierHtMissionExiste(missionActive) && this.missionActive.htEstSigne()
						&& Bibliotheque.recupererFmAvecOm(missionActive).getEtat() == EtatMission.SIGNE) {
					alert.getButtonTypes().add(buttonTypeEnvoyer);
				}
				alert.getButtonTypes().add(buttonTypeCancel);

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonTypeAfficher) {
					this.genererPdfFM();
					this.afficherPdfFM(this.missionActive);
					this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);

				} else if (result.get() == buttonTypeModif) {
					this.afficherFraisMission();
					this.modifierFrais(this.missionActive);

				} else if (result.get() == buttonTypeSigner) {
					this.signerFM(this.missionActive);

				} else if (result.get() == buttonTypeEnvoyer) {
					this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
					this.afficherEnvoiDuMail(TypeDocument.FRAISOUHORAIRES);

				} else {
					this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
					// Ne fait rien == bouton "annuler"
				}
			} else {
				this.reprendreCreationFm(missionActive);
			}

		} else {
			// Créer les frais de mission
			this.creerFraisMission();
		}
	}

	private void reprendreCreationFm(OrdreMission missionActive2) {
		FraisMission fm = new FraisMission(Bibliotheque.recupererCheminEtNomFichierFm(this.missionActive));
		fm = fm.chargerJson(fm.getAdresseFichier());

		this.afficherFraisMission();
		this.fmCtrl.setFraisMission(fm);
		this.fmCtrl.afficherSemaine();
		this.fmCtrl.reprendreDernierJour(fm);
	}

	private void signerFM(OrdreMission missionActive) {
		this.afficherFraisMission();
		this.fmCtrl.afficherSignatureFM(missionActive);
		this.fmCtrl.setTitre(Constante.TITRE_SIGNER_FM);
		this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
		this.rootLayoutCtrl.ajouterStyleFM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);

	}

	private void afficherPdfFM(OrdreMission missionActive) {
		try {
			Desktop.getDesktop().browse(new File(
					this.missionActive.getCheminDossier() + this.missionActive.getNomOM() + Constante.EXTENSION_PDF)
							.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.retourMenu();
	}

	public void genererPdfFM() {
		this.genererPDFOM();
		FraisMission fm = new FraisMission(Bibliotheque.recupererCheminEtNomFichierFm(missionActive));
		fm = fm.chargerJson(fm.getAdresseFichier());
		fm.genererPDF(this.options);
		this.afficherFraisMission();
		this.fmCtrl.setFraisMission(fm);
	}

	private void modifierFrais(OrdreMission missionActive) {
		FraisMission fm = new FraisMission(Bibliotheque.recupererCheminEtNomFichierFm(this.missionActive));
		fm = fm.chargerJson(fm.getAdresseFichier());

		this.afficherFraisMission();
		this.fmCtrl.modifierFraisMission(fm);
		this.fmCtrl.setFraisMission(fm);
		this.fmCtrl.setTitre(Constante.TITRE_MODIF_FM);
		this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
		this.rootLayoutCtrl.ajouterStyleFM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);

	}

	public void afficherFraisMission() {
		retirerDocActif();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AgentApp.class.getResource("view/FraisMission.fxml"));
			this.fraisMission = loader.load();

			this.fmCtrl = loader.getController();
			this.fmCtrl.setAgentApp(this);
			this.rootLayoutCtrl.getGridRoot().add(this.fraisMission, 2, 0);
			this.fmCtrl.setOptions(this.options);
			this.fmCtrl.setMissionActive(missionActive);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void creerFraisMission() {
		this.afficherFraisMission();
		this.fmCtrl.creerFraisMission();
	}

	////////////////////////////////////////
	//
	// Horaires de Mission
	//
	////////////////////////////////////////

	public void demanderActionHT() {
		if (Bibliotheque.fichierHtMissionExiste(missionActive)) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Choix de l'action");
			alert.setHeaderText("Choisissez l'action souhaitée");
			alert.setContentText("Choisissez l'action que vous voulez réaliser sur votre mission.");

			ButtonType buttonTypeAfficher = new ButtonType("Afficher");
			ButtonType buttonTypeModif = new ButtonType("Modifier");
			alert.getButtonTypes().setAll(buttonTypeAfficher);

			ButtonType buttonTypeSigner = null;
			if (this.missionActive.htEstSigne()) {
				alert.getButtonTypes().addAll(buttonTypeModif);
			} else {
				buttonTypeSigner = new ButtonType("Signer");
				alert.getButtonTypes().addAll(buttonTypeModif, buttonTypeSigner);
			}

			ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().add(buttonTypeCancel);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeAfficher) {
				this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
				this.afficherHtXLS();
			} else if (result.get() == buttonTypeModif) {
				this.modifierHt(missionActive);
			} else if (result.get() == buttonTypeSigner) {
				this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
				this.signerHT();
			} else {
				this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
				// Ne fait rien == bouton "annuler"
			}
		} else
			this.creerHoraireTravail();
	}

	private void signerHT() {
		// TODO Auto-generated method stub
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Certifier l'exactitude des horaires");
		alert.setHeaderText(
				"Certifiez-vous l'exactitude des informations saisies \ndans les horaires de travail de cette mission ?");
		alert.setContentText("Signer vos horaires de travail ?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			HoraireTravail ht = new HoraireTravail(null);
			ht = ht.chargerJson(
					missionActive.getCheminDossier() + missionActive.getNomOM().replace("OM_", "HT_").concat(".json"));
			ht.setDateSignature(Bibliotheque.getDateAujourdhui());
			ht.setSignature(true);
			ht.setEtat(EtatMission.SIGNE);
			ht.sauvegarderJson(ht.getAdresseFichier());
		} else {
			// ne fait rien : bouton annuler ou fermer la fenetre
		}
		this.retourMenu();
	}

	public void creerHoraireTravail() {
		this.afficherHorairesTravail();
		this.htCtrl.creerHoraireMission();
	}

	public void afficherHorairesTravail() {
		retirerDocActif();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AgentApp.class.getResource("view/HorairesTravail.fxml"));
			this.horairesTravail = loader.load();

			this.htCtrl = loader.getController();
			this.htCtrl.setMainApp(this);
			this.rootLayoutCtrl.getGridRoot().add(this.horairesTravail, 2, 0);
			this.htCtrl.setOptions(this.options);
			this.htCtrl.setMissionActive(missionActive);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void afficherHtXLS() {
		try {
			this.genererXlsHT();
			File excelFile = new File(missionActive.getCheminDossier() + missionActive.getNomOM().replace("OM_", "HT_")
					+ Constante.EXTENSION_XLS);
			getHostServices().showDocument(excelFile.toURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.retourMenu();
	}

	public void genererXlsHT() {
		this.afficherHorairesTravail();
		HoraireTravail ht = new HoraireTravail(null);
		ht = ht.chargerJson(missionActive.getCheminDossier() + missionActive.getNomOM().replace("OM_", "HT_")
				+ Constante.EXTENSION_JSON);
		this.htCtrl.afficherExcel(ht);
	}

	public void modifierHt(OrdreMission missionActive) {
		HoraireTravail ht = new HoraireTravail(Bibliotheque.recupererCheminEtNomFichierHt(this.missionActive));
		ht = ht.chargerJson(ht.getAdresseFichier());

		this.afficherHorairesTravail();
		this.htCtrl.modifierHoraireTravail(ht);
		this.htCtrl.setHoraireTravail(ht);
		this.htCtrl.setTitre(Constante.TITRE_MODIF_HT);
		this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
		this.rootLayoutCtrl.ajouterStyleHT(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
	}

	////////////////////////////////////////
	//
	// Options
	//
	////////////////////////////////////////

	public void modifierOptions() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AgentApp.class.getResource("view/Options.fxml"));
			AnchorPane optionsLayout = loader.load();

			Scene scene = new Scene(optionsLayout);
			this.secondaryStage = new Stage();
			OptionsController controllerOptions = loader.getController();
			controllerOptions.chargerPage(this, options);
			controllerOptions.chargerMailsResponsable();
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

	////////////////////////////////////////
	//
	// Mission Active
	//
	////////////////////////////////////////

	public void retirerMissionActive() {
		this.missionActive = null;
		this.rootLayoutCtrl.setLabelMissionSelectionnee("Aucune");
		this.retourMenu();
	}

	public void setMissionActive(OrdreMission missionActive) {
		this.missionActive = missionActive;
		MissionTemporaire mission = (MissionTemporaire) missionActive.getMission();
		String om = mission.getLieuDeplacement() + " du " + mission.getDateDebut() + " au " + mission.getDateFin();
		this.rootLayoutCtrl.setLabelMissionSelectionnee(om);
		this.afficherInfosMission(missionActive);
	}

	private void afficherInfosMission(OrdreMission missionActive) {
		this.retirerDocActif();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("view/EtatMissionSelectionnee.fxml"));

			etatMission = loader.load();

			EtatMissionSelectionneeController etatMissionCtrl = loader.getController();

			etatMissionCtrl.choisirCouleurOM(missionActive.getEtat().getEtat());
			etatMissionCtrl.modifierInfosMission(missionActive);

			if (Bibliotheque.fichierFmMissionExiste(missionActive)) {
				FraisMission fm = new FraisMission(null);
				fm = fm.chargerJson(missionActive.getCheminDossier() + missionActive.getNomOM().replace("OM_", "FM_")
						+ Constante.EXTENSION_JSON);

				etatMissionCtrl.choisirCouleurFM(fm.getEtat().getEtat());
			}

			if (Bibliotheque.fichierHtMissionExiste(missionActive)) {
				HoraireTravail ht = new HoraireTravail(null);
				ht = ht.chargerJson(missionActive.getCheminDossier() + missionActive.getNomOM().replace("OM_", "HT_")
						+ Constante.EXTENSION_JSON);
				etatMissionCtrl.choisirCouleurHT(ht.getEtat().getEtat());
			}

			this.rootLayoutCtrl.getGridRoot().add(etatMission, 2, 0);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void alertChoisirMission() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Erreur");
		alert.setHeaderText("Vous n'avez pas sélectionné de mission !");
		alert.setContentText("Veuillez choisir une mission avant de recommencer.");

		alert.showAndWait();
	}

	////////////////////////////////////////
	//
	// Mails
	//
	////////////////////////////////////////

	public void afficherEnvoiDuMail(TypeDocument typeDocument) {
		if (TypeDocument.ORDREMISSION == typeDocument) {
			this.genererPDFOM();
		} else {
			this.genererPdfFM();
			this.genererXlsHT();
		}
		this.afficherOrdresMission();
		this.omCtrl.setTitre("Envoyer un document");
		this.omCtrl.setPrimaryStage(primaryStage);
		this.omCtrl.afficherEnvoiDuMail(typeDocument);
		if (TypeDocument.ORDREMISSION == typeDocument) {
			this.rootLayoutCtrl.ajouterStyleOM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
		} else {
			this.rootLayoutCtrl.ajouterStyleFM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			this.rootLayoutCtrl.ajouterStyleHT(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
		}
	}

	////////////////////////////////////////
	//
	// Getters/Setter
	//
	////////////////////////////////////////

	public OrdreMissionController getOMCtrl() {
		return this.omCtrl;
	}

	public Options getOptions() {
		return this.options;
	}

	public boolean missionActiveIsNull() {
		return null == this.missionActive;
	}

	public OrdreMission getMissionActive() {
		return missionActive;
	}

	public ListeMails getMailsEnAttente() {
		return mailsEnAttente;
	}

	public Service<Void> getServiceEnvoiMail() {
		return this.serviceEnvoiMail;
	}
}
