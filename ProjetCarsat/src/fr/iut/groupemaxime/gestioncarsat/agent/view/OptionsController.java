package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.utils.Bibliotheque;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import fr.iut.groupemaxime.gestioncarsat.utils.Options;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OptionsController {
	@FXML
	private TextField textFieldCheminDossierOM;
	@FXML
	private TextField textFieldCheminSignature;
	@FXML
	private TextField textFieldMailAgent;
	@FXML
	private Label domaineLabel;
	@FXML
	private VBox vboxMailResponsable;

	private AgentApp mainApp;
	private Options options;
	private Stage optionAgentStage;

	@FXML
	private void initialize() {
		this.textFieldCheminDossierOM.setDisable(true);
		this.textFieldCheminSignature.setDisable(true);
		this.domaineLabel.setText('@' + Constante.HOSTNAME);
	}

	@FXML
	public void choisirCheminDossierMission() {
		File dossier = Bibliotheque.ouvrirDirectoryChooser();
		if (null != dossier && !dossier.getAbsolutePath().equals(new File(options.getCheminOM()).getAbsolutePath())) {
			if (!Bibliotheque.repertoireEstVide(dossier)) {
				dossier = new File(dossier.getAbsolutePath() + "/OM");
				try {
					Files.createDirectories(dossier.toPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (!dossier.getAbsolutePath().endsWith(File.separator)) {
				dossier = new File(dossier.getAbsolutePath() + File.separator);
			}
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation déplacement");
			alert.setHeaderText("Attention, les missions déjà enregistrées seront automatiquement"
					+ " déplacées dans le nouveau dossier.");
			alert.setContentText("Êtes-vous sûr de vouloir changer le chemin vers le répertoire ? ");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				Bibliotheque.deplacerContenuRepertoire(new File(this.options.getCheminOM()), dossier);
				this.textFieldCheminDossierOM.setText(String.valueOf(dossier));
				this.options.setCheminOM(String.valueOf(dossier));
			} else {
				// ... user chose CANCEL or closed the dialog
			}

		}
	}

	@FXML
	public void choisirCheminSignature() {
		File signature = Bibliotheque.ouvrirFileChooser(Constante.IMAGE_FILTER);
		if (null != signature) {
			this.textFieldCheminSignature.setText(String.valueOf(signature));
			this.options.setCheminSignature(String.valueOf(signature));
		}
	}

	@FXML
	public void ajouterResponsable() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setHeaderText("Ajout d'un responsable");
		dialog.setTitle("Ajouter un nouveau responsable");
		dialog.setContentText("Veuillez saisir l'adresse mail du responsable à ajouter :");

		// Récupérer la valeur saisie
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			this.options.ajouterResponsable(result.get());
			this.ajouterItemResponsable(result.get());
		}
	}

	public void ajouterItemResponsable(String adresseResponsable) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AgentApp.class.getResource("view/ItemResponsableOptions.fxml"));
			AnchorPane itemResponsable = loader.load();

			ItemResponsableOptionsController itemResponsableOptionsCtrl = loader.getController();
			itemResponsableOptionsCtrl.setOptionCtrl(this);
			itemResponsableOptionsCtrl.setAdresseMailResponsable(adresseResponsable);

			vboxMailResponsable.getChildren().add(itemResponsable);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void chargerMailsResponsable() {
		this.vboxMailResponsable.getChildren().clear();
		if (!this.options.getMailsResponsables().isEmpty()) {
			for (String res : this.options.getMailsResponsables())
				this.ajouterItemResponsable(res);
		}
	}

	public void chargerPage(AgentApp mainApp, Options options) {
		this.mainApp = mainApp;
		this.options = options;
		this.textFieldCheminDossierOM.setText(this.options.getCheminOM());
		this.textFieldCheminSignature.setText(this.options.getCheminSignature());
		this.textFieldMailAgent.setText(this.options.getMailAgent());
	}

	public void modifierAgent() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AgentApp.class.getResource("view/AgentOptions.fxml"));
			AnchorPane optionsLayout = loader.load();

			Scene scene = new Scene(optionsLayout);
			this.optionAgentStage = new Stage();
			AgentOptionsController agentOptionsController = loader.getController();
			agentOptionsController.chargerPage(this, options);
			agentOptionsController.setChamps(options.getAgent());
			this.optionAgentStage.setScene(scene);
			this.optionAgentStage.setTitle("Options Agent");
			this.optionAgentStage.getIcons().add(new Image("file:" + Constante.CHEMIN_IMAGES + "logo.png"));
			optionAgentStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fermerSecondaryStage() {
		this.optionAgentStage.close();
	}

	public void sauvegarder() {
		this.options.setMailAgent(this.textFieldMailAgent.getText());
		mainApp.setOptions(options);
		mainApp.fermerSecondaryStage();
	}

	public void supprimerResponsable(String adresseResponsable) {
		this.options.supprimerResponsable(adresseResponsable);
		this.chargerMailsResponsable();
	}
}
