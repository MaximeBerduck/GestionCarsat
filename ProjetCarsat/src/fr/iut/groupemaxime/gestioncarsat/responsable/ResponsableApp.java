package fr.iut.groupemaxime.gestioncarsat.responsable;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.controlsfx.control.Notifications;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.form.PDF;
import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisMission;
import fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model.HoraireTravail;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.ListeOrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.view.EtatMissionSelectionneeController;
import fr.iut.groupemaxime.gestioncarsat.agent.view.SaisieMailController;
import fr.iut.groupemaxime.gestioncarsat.agent.view.choixMailsDestinatairesController;
import fr.iut.groupemaxime.gestioncarsat.mail.ListeMails;
import fr.iut.groupemaxime.gestioncarsat.mail.MailProcessor;
import fr.iut.groupemaxime.gestioncarsat.responsable.view.RootLayoutController;
import fr.iut.groupemaxime.gestioncarsat.utils.Bibliotheque;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import fr.iut.groupemaxime.gestioncarsat.utils.EtatMission;
import fr.iut.groupemaxime.gestioncarsat.utils.EtatsResponsable;
import fr.iut.groupemaxime.gestioncarsat.utils.Options;
import fr.iut.groupemaxime.gestioncarsat.utils.TypeDocument;
import fr.iut.groupemaxime.gestioncarsat.responsable.view.EtatMissionResponsableController;
import fr.iut.groupemaxime.gestioncarsat.responsable.view.ItemMissionResponsableController;
import fr.iut.groupemaxime.gestioncarsat.responsable.view.ListeMissionsResponsableController;
import fr.iut.groupemaxime.gestioncarsat.responsable.view.MailController;
import fr.iut.groupemaxime.gestioncarsat.responsable.view.OptionsResponsableController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

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

	private AnchorPane etatMission;

	private ListeMails mailsEnAttente;
	private ScheduledService<Void> serviceRecuperationMails;
	private Service<Void> serviceEnvoiMail;

	private EtatsResponsable etatMissionActive;
	private AnchorPane pageMail;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Carsat - Gestion des déplacement");
		this.primaryStage.getIcons().add(new Image("file:" + Constante.CHEMIN_IMAGES + "logo.png"));
		this.primaryStage.setResizable(true);

		this.options = new Options();
		this.options = this.options.chargerJson(Constante.CHEMIN_OPTIONS);

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

		this.serviceRecuperationMails = new ScheduledService<Void>() {

			@Override
			protected Task<Void> createTask() {

				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {

						MailProcessor.recevoirEmail(Constante.HOSTNAME,
								options.getMailAgent() + '@' + Constante.HOSTNAME, "root", options.getCheminOM());
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								afficherListeMissions();
							}
						});

						return null;
					}

				};
			};
		};
		serviceRecuperationMails.setPeriod(Duration.minutes(5));
		serviceRecuperationMails.start();

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
			loader.setLocation(ResponsableApp.class.getResource("view/OptionsResponsable.fxml"));
			AnchorPane optionsLayout = loader.load();

			Scene scene = new Scene(optionsLayout);
			this.secondaryStage = new Stage();
			OptionsResponsableController controllerOptions = loader.getController();
			controllerOptions.chargerPage(this, options);
			controllerOptions.chargerMailsAutres();
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

	public Options getOptions() {
		return this.options;
	}

	public void afficherListeMissions() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("view/listeMissionsResponsable.fxml"));
			this.listeMissionsResponsable = loader.load();
			if (!this.rootLayoutCtrl.getGridRoot().getChildren().contains(this.listeMissionsResponsable))
				this.rootLayoutCtrl.getGridRoot().add(this.listeMissionsResponsable, 0, 0);
			controllerListeMissionsResponsable = loader.getController();
			controllerListeMissionsResponsable.setResponsableApp(this);
			controllerListeMissionsResponsable.setOptions(this.options);
			controllerListeMissionsResponsable.chargerOM();
			this.listeOM = controllerListeMissionsResponsable.getListeOm();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setMissionActive(OrdreMission missionActive) {
		this.missionActive = missionActive;
		MissionTemporaire mission = (MissionTemporaire) missionActive.getMission();
		String om = mission.getLieuDeplacement() + " du " + mission.getDateDebut() + " au " + mission.getDateFin();
		this.rootLayoutCtrl.setLabelMissionSelectionnee(om);
		EtatsResponsable er = new EtatsResponsable(missionActive.getCheminDossier() + File.separator
				+ missionActive.getNomOM().substring(missionActive.getNomOM().indexOf('_') + 1,
						missionActive.getNomOM().lastIndexOf("."))
				+ ".json");
		this.etatMissionActive = er.chargerJson(er.getNom());
		this.afficherInfosMission();
	}

	private void afficherInfosMission() {
		this.retirerDocActif();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("view/EtatMissionResponsable.fxml"));

			etatMission = loader.load();

			EtatMissionResponsableController etatMissionCtrl = loader.getController();

			etatMissionCtrl.choisirCouleurOM(etatMissionActive.getOm().getEtat());
			etatMissionCtrl.modifierInfosMission(missionActive);

			etatMissionCtrl.choisirCouleurFM(etatMissionActive.getFm().getEtat());

			etatMissionCtrl.choisirCouleurHT(etatMissionActive.getHt().getEtat());

			this.rootLayoutCtrl.getGridRoot().add(etatMission, 2, 0);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public void fermerSecondaryStage() {
		this.secondaryStage.close();
	}

	public boolean missionActiveIsNull() {
		return null == missionActive;
	}

	public void alertChoisirMission() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Erreur");
		alert.setHeaderText("Vous n'avez pas sélectionné de mission !");
		alert.setContentText("Veuillez choisir une mission avant de recommencer.");

		alert.showAndWait();
	}

	public void demanderActionFM() {
		if (recuHT()) {
			if (etatMissionActive.getFm() == EtatMission.NON_RECU) {
				etatMissionActive.setFm(EtatMission.NON_SIGNE);
				etatMissionActive.setHt(EtatMission.NON_SIGNE);
				etatMissionActive.sauvegarderJson();
			}
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Choix de l'action");
			alert.setHeaderText("Choisissez l'action souhaitée");
			alert.setContentText("Choisissez l'action que vous voulez réaliser sur votre mission.");

			ButtonType buttonTypeAfficher = new ButtonType("Afficher");
			ButtonType buttonTypeSigner = new ButtonType("Signer");
			ButtonType buttonTypeEnvoyer = new ButtonType("Envoyer");
			ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(buttonTypeAfficher);
			if (etatMissionActive.getFm() == EtatMission.NON_SIGNE) {
				alert.getButtonTypes().add(buttonTypeSigner);
			}
			if (etatMissionActive.getFm() == EtatMission.SIGNE && etatMissionActive.getHt() == EtatMission.SIGNE) {
				alert.getButtonTypes().add(buttonTypeEnvoyer);
			}
			alert.getButtonTypes().add(buttonTypeCancel);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeAfficher) {
				this.afficherPdfOM();
				this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			} else if (result.get() == buttonTypeSigner) {
				this.signerFM();

			} else if (result.get() == buttonTypeEnvoyer) {
				this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
				this.envoyerMail(TypeDocument.FRAISOUHORAIRES);

			} else {
				this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
				// Ne fait rien == bouton "annuler"
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Frais non reçus");
			alert.setContentText("Vous n'avez pas encore recu les frais de mission pour cette mission");
			alert.show();
		}
		retourMenu();
	}

	public void demanderActionOM() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Choix de l'action");
		alert.setHeaderText("Choisissez l'action souhaitée");
		alert.setContentText("Choisissez l'action que vous voulez réaliser sur votre ordre de mission.");
		ButtonType buttonTypeAfficher = new ButtonType("Afficher");
		alert.getButtonTypes().setAll(buttonTypeAfficher);

		ButtonType buttonTypeEnvoyer = null;
		ButtonType buttonTypeSigner = null;
		if (etatMissionActive.getOm() == EtatMission.SIGNE) {
			buttonTypeEnvoyer = new ButtonType("Envoyer");
			alert.getButtonTypes().addAll(buttonTypeEnvoyer);
		}
		if (etatMissionActive.getOm() == EtatMission.NON_SIGNE) {
			buttonTypeSigner = new ButtonType("Signer");
			alert.getButtonTypes().addAll(buttonTypeSigner);
		}
		ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().add(buttonTypeCancel);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeAfficher) {
			this.afficherPdfOM();
			this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
		} else if (result.get() == buttonTypeSigner) {
			this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			this.signerOM();
		} else if (result.get() == buttonTypeEnvoyer) {
			this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			this.envoyerMail(TypeDocument.ORDREMISSION);
		} else {
			this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			// Ne fait rien == bouton "annuler"
		}
	}

	private void envoyerMail(TypeDocument typeDocument) {
		if ("".equals(options.getMailAgent())) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(AgentApp.class.getResource("view/SaisieMail.fxml"));
				AnchorPane saisieLayout = loader.load();

				Scene scene = new Scene(saisieLayout);
				this.secondaryStage = new Stage();
				SaisieMailController controllerMail = loader.getController();
				controllerMail.setStage(this.secondaryStage);
				controllerMail.setHostname(Constante.HOSTNAME);

				secondaryStage.setScene(scene);
				this.secondaryStage.setTitle("Paramètres");
				secondaryStage.initOwner(primaryStage);
				secondaryStage.initModality(Modality.WINDOW_MODAL);
				this.secondaryStage.getIcons().add(new Image("file:" + Constante.CHEMIN_IMAGES + "logo.png"));

				secondaryStage.showAndWait();

				options.setMailAgent(controllerMail.getMailAgent());
				options.sauvegarder();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("view/Mail.fxml"));
			this.pageMail = loader.load();

			GridPane pane = (GridPane) this.rootLayout.getLeft();
			this.retirerDocActif();
			pane.add(this.pageMail, 2, 0);

			MailController controllerMail = loader.getController();
			controllerMail.setMainApp(this);
			File pdfOM = new File((this.missionActive.getCheminDossier() + this.missionActive.getNomOM()));
			if (TypeDocument.ORDREMISSION == typeDocument) {
				controllerMail.setPiecesJointes(new File[] { pdfOM });
			} else {
				File pdfHT = new File((this.missionActive.getCheminDossier() + this.missionActive.getNomOM()
						.replace(Constante.EXTENSION_PDF, Constante.EXTENSION_XLS).replace("OM_", "HT_")));
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
		if (TypeDocument.ORDREMISSION == typeDocument) {
			this.rootLayoutCtrl.ajouterStyleOM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
		} else {
			this.rootLayoutCtrl.ajouterStyleFM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			this.rootLayoutCtrl.ajouterStyleHT(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
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

	public void retirerDocActif() {
		this.rootLayoutCtrl.getGridRoot().getChildren().remove(this.pageMail);
		this.rootLayoutCtrl.getGridRoot().getChildren().remove(this.etatMission);
		this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
	}

	public void demanderActionHT() {
		if (recuHT()) {
			if (etatMissionActive.getFm() == EtatMission.NON_RECU) {
				etatMissionActive.setFm(EtatMission.NON_SIGNE);
				etatMissionActive.setHt(EtatMission.NON_SIGNE);
				etatMissionActive.sauvegarderJson();
			}
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Choix de l'action");
			alert.setHeaderText("Choisissez l'action souhaitée");
			alert.setContentText("Choisissez l'action que vous voulez réaliser sur votre mission.");

			ButtonType buttonTypeAfficher = new ButtonType("Afficher");
			alert.getButtonTypes().setAll(buttonTypeAfficher);

			ButtonType buttonTypeSigner = null;
			if (etatMissionActive.getHt() == EtatMission.NON_SIGNE) {
				buttonTypeSigner = new ButtonType("Signer");
				alert.getButtonTypes().addAll(buttonTypeSigner);
			}

			ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().add(buttonTypeCancel);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeAfficher) {
				this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
				this.afficherHtXLS();
			} else if (result.get() == buttonTypeSigner) {
				this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
				this.signerHT();
			} else {
				this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
				// Ne fait rien == bouton "annuler"
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Frais non reçus");
			alert.setContentText("Vous n'avez pas encore recu les frais de mission pour cette mission");
			alert.show();
		}
		retourMenu();
	}

	public boolean recuHT() {
		if (new File(missionActive.getCheminDossier() + missionActive.getNomOM().replace("OM_", "HT_")
				.replace(Constante.EXTENSION_PDF, Constante.EXTENSION_XLS)).exists()) {
			return true;
		} else {
			return false;
		}
	}

	private void signerHT() {
		if (PDF.signerHTResponsable(
				this.missionActive.getCheminDossier() + this.missionActive.getNomOM().replace("OM_", "HT_")
						.replace(Constante.EXTENSION_PDF, Constante.EXTENSION_XLS),
				this.options.getCheminSignature())) {
			this.etatMissionActive.setHt(EtatMission.SIGNE);
			this.etatMissionActive.sauvegarderJson();
		}
		this.retourMenu();
	}

	private void afficherHtXLS() {
		try {
			Desktop.getDesktop().browse(new File(this.missionActive.getCheminDossier() + this.missionActive.getNomOM()
					.replace("OM_", "HT_").replace(Constante.EXTENSION_PDF, Constante.EXTENSION_XLS)).toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	private void signerOM() {
		PDF.signerPdfOmResponsable(Constante.SIGNATURE_RESPONSABLE_OM_X, Constante.SIGNATURE_RESPONSABLE_OM_Y,
				Constante.TAILLE_SIGNATURE_OM_RESP, missionActive.getCheminDossier() + missionActive.getNomOM(),
				options.getCheminSignature());
		this.etatMissionActive.setOm(EtatMission.SIGNE);
		this.etatMissionActive.sauvegarderJson();
		this.retourMenu();
	}

	private void signerFM() {
		PDF.signerPdfFmResponsable(Constante.SIGNATURE_RESPONSABLE_FM_X, Constante.SIGNATURE_RESPONSABLE_FM_Y,
				Constante.TAILLE_SIGNATURE_FM, missionActive.getCheminDossier() + missionActive.getNomOM(),
				options.getCheminSignature());
		this.etatMissionActive.setFm(EtatMission.SIGNE);
		this.etatMissionActive.sauvegarderJson();
		this.retourMenu();
	}

	private void afficherPdfOM() {
		try {
			Desktop.getDesktop().browse(new File(missionActive.getCheminDossier() + missionActive.getNomOM()).toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void retourMenu() {
		this.retirerDocActif();
		this.afficherListeMissions();
		if (!this.missionActiveIsNull()) {
			ItemMissionResponsableController itemOmCtrl = controllerListeMissionsResponsable.getItemOM(missionActive);
			if (itemOmCtrl != null) {
				this.setMissionActive(itemOmCtrl.getOM());
				itemOmCtrl.ajouterStyle(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			}
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public OrdreMission getMissionActive() {
		return missionActive;
	}

	public ListeMails getMailsEnAttente() {
		return mailsEnAttente;
	}

	public Service<Void> getServiceEnvoiMail() {
		return serviceEnvoiMail;
	}

	public EtatsResponsable getEtatMissionActive() {
		return this.etatMissionActive;
	}

}
