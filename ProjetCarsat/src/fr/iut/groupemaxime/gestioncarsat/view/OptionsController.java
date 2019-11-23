package fr.iut.groupemaxime.gestioncarsat.view;

import java.io.File;

import fr.iut.groupemaxime.gestioncarsat.MainApp;
import fr.iut.groupemaxime.gestioncarsat.model.Options;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class OptionsController {
	@FXML
	private TextField labelCheminDossierOM;
	
	private MainApp mainApp;
	private Options options;
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
	
	public void sauvegarder() {
		mainApp.setOptions(options);
		mainApp.fermerSecondaryStage();
	}
}
