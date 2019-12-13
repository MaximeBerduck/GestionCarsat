package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.File;
import java.io.IOException;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Bibliotheque;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Constante;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Options;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class OptionsController {
	@FXML
	private TextField textFieldCheminDossierOM;
	@FXML
	private TextField textFieldCheminSignature;
	@FXML
	private TextField textFieldMailAgent;

	private AgentApp mainApp;
	private Options options;
	private Stage optionAgentStage;

	@FXML
	private void initialize() {
	}

	@FXML
	public void choisirCheminDossierMission() {
		File dossier = Bibliotheque.ouvrirDirectoryChooser();
		if (null != dossier) {
			this.textFieldCheminDossierOM.setText(String.valueOf(dossier));
			this.options.setCheminOM(String.valueOf(dossier));
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
}
