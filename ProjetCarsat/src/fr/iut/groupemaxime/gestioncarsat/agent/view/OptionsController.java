package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.utils.Bibliotheque;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import fr.iut.groupemaxime.gestioncarsat.utils.Options;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
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
	private ChoiceBox<String> selectMailResponsable;

	private AgentApp mainApp;
	private Options options;
	private Stage optionAgentStage;

	@FXML
	private void initialize() {
		this.selectMailResponsable.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue,
						String newValue) -> modifierResponsable(newValue));
		this.textFieldCheminDossierOM.setDisable(true);
		this.textFieldCheminSignature.setDisable(true);
		this.domaineLabel.setText('@' + Constante.HOSTNAME);
	}

	@FXML
	public void choisirCheminDossierMission() {
		File dossier = Bibliotheque.ouvrirDirectoryChooser();
		if (null != dossier) {
			this.textFieldCheminDossierOM.setText(String.valueOf(dossier) + '/');
			this.options.setCheminOM(String.valueOf(dossier) + '/');
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
			this.selectMailResponsable.getItems().add(result.get());
		}
	}

	public void chargerMailsResponsable() {
		if (!this.options.getMailsResponsables().isEmpty()) {
			for (String res : this.options.getMailsResponsables())
				this.selectMailResponsable.getItems().add(res);
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

	public void modifierResponsable(String responsable) {
		TextInputDialog dialog = new TextInputDialog(responsable);
		dialog.setHeaderText("Modification de l'adresse d'un responsable");
		dialog.setTitle("Modifier l'adresse de votre responsable");
		dialog.setContentText("Veuillez modifier l'adresse mail de votre responsable :");

		// Récupérer la valeur saisie
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			this.options.modifierResponsable(responsable, result.get());
			this.selectMailResponsable.getItems().remove(responsable);
			this.selectMailResponsable.getItems().add(result.get());
		}
	}
}
