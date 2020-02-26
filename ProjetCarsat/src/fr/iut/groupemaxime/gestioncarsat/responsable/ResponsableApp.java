package fr.iut.groupemaxime.gestioncarsat.responsable;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.form.PDF;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.ListeOrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.view.ItemOrdreMissionController;
import fr.iut.groupemaxime.gestioncarsat.mail.MailProcessor;
import fr.iut.groupemaxime.gestioncarsat.responsable.view.RootLayoutController;
import fr.iut.groupemaxime.gestioncarsat.utils.Bibliotheque;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import fr.iut.groupemaxime.gestioncarsat.utils.EtatMission;
import fr.iut.groupemaxime.gestioncarsat.utils.Options;
import fr.iut.groupemaxime.gestioncarsat.utils.TypeDocument;
import fr.iut.groupemaxime.gestioncarsat.responsable.view.ItemMissionResponsableController;
import fr.iut.groupemaxime.gestioncarsat.responsable.view.ListeMissionsResponsableController;
import fr.iut.groupemaxime.gestioncarsat.responsable.view.OptionsResponsableController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
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
	private ScheduledService<Void> serviceRecuperationMails;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Carsat - Gestion des déplacement");
		this.primaryStage.getIcons().add(new Image("file:" + Constante.CHEMIN_IMAGES + "logo.png"));
		this.primaryStage.setResizable(true);
		this.options = new Options();
		this.options = this.options.chargerJson(Constante.CHEMIN_OPTIONS);
		initialiseRootLayout();
		this.afficherListeMissions();
		this.serviceRecuperationMails = new ScheduledService<Void>() {

			@Override
			protected Task<Void> createTask() {

				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						
						MailProcessor.recevoirEmail(Constante.HOSTNAME, options.getMailAgent() + '@' + Constante.HOSTNAME, "root",
								options.getCheminOM());
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
		serviceRecuperationMails.setPeriod(Duration.minutes(1));
		serviceRecuperationMails.start();
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
		if (this.recuHT()) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Choix de l'action");
				alert.setHeaderText("Choisissez l'action souhaitée");
				alert.setContentText("Choisissez l'action que vous voulez réaliser sur votre mission.");

				ButtonType buttonTypeAfficher = new ButtonType("Afficher");
				ButtonType buttonTypeSigner = new ButtonType("Signer");
				ButtonType buttonTypeEnvoyer = new ButtonType("Envoyer");
				ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);

				alert.getButtonTypes().setAll(buttonTypeAfficher);
				if (!PDF.fmEstSigneResp(missionActive.getCheminDossier() + missionActive.getNomOM())) {
					alert.getButtonTypes().add(buttonTypeSigner);
				}
				if (true) { //les 2 signe
					alert.getButtonTypes().add(buttonTypeEnvoyer);
				}
				alert.getButtonTypes().add(buttonTypeCancel);

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonTypeAfficher) {
					this.afficherPdfFM();
					this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
				} else if (result.get() == buttonTypeSigner) {
					this.signerFM();

				} else if (result.get() == buttonTypeEnvoyer) {
					this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
					//this.afficherEnvoiDuMail(TypeDocument.FRAISOUHORAIRES);

				} else {
					this.rootLayoutCtrl.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
					// Ne fait rien == bouton "annuler"
				}
		} else {
			//alert pas fm
		}
	}

	private void signerFM() {
		PDF.signerPdfFmResponsable(Constante.SIGNATURE_RESPONSABLE_FM_X, Constante.SIGNATURE_RESPONSABLE_FM_Y, Constante.TAILLE_SIGNATURE_FM, missionActive.getCheminDossier() + missionActive.getNomOM(), options.getCheminSignature());
	}

	private void afficherPdfFM() {
		try {
			Desktop.getDesktop().browse(new File(missionActive.getCheminDossier()+missionActive.getNomOM()).toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean recuHT() {
		if (new File(missionActive.getCheminDossier() + missionActive.getNomOM().replace("OM_", "HT_").replace(Constante.EXTENSION_PDF, Constante.EXTENSION_XLS)).exists()) {
			return true;
		}else {
			return false;			
		}
	}

}
