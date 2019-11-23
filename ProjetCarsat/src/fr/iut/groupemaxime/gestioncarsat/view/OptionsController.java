package fr.iut.groupemaxime.gestioncarsat.view;

import java.io.File;
import java.io.IOException;

import fr.iut.groupemaxime.gestioncarsat.MainApp;
import fr.iut.groupemaxime.gestioncarsat.model.Options;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class OptionsController {
	@FXML
	private TextField labelCheminDossierOM;
	
	private MainApp mainApp;
	private Options options;
	private Stage secondaryStage;
	
	@FXML
	private void initialize() {

	}
	
	@FXML
	public void ouvrirFileChooser() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		Stage stage = new Stage();
		File dossier = directoryChooser.showDialog(stage);
		if(null != dossier) {
			this.labelCheminDossierOM.setText(String.valueOf(dossier));
			this.options.setCheminOM(String.valueOf(dossier));
		}
	}
	
	public void chargerPage(MainApp mainApp, Options options) {
		this.mainApp = mainApp;
		this.options = options;
		this.labelCheminDossierOM.setText(this.options.getCheminOM());
	}
	
	public void modifierAgent() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/AgentOptions.fxml"));
			AnchorPane optionsLayout = loader.load();

			Scene scene = new Scene(optionsLayout);
			secondaryStage = new Stage();
			AgentOptionsController agentOptionsController = loader.getController();
			agentOptionsController.chargerPage(this, options);
			agentOptionsController.setChamps(options.getAgent());
			secondaryStage.setScene(scene);
			secondaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void fermerSecondaryStage() {
		this.secondaryStage.close();
	}
	
	public void sauvegarder() {
		mainApp.setOptions(options);
		mainApp.fermerSecondaryStage();
	}
}
