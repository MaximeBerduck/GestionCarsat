package fr.iut.groupemaxime.gestioncarsat.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.IOException;

import fr.iut.groupemaxime.gestioncarsat.MainApp;
import fr.iut.groupemaxime.gestioncarsat.model.ListeOrdreMission;
import fr.iut.groupemaxime.gestioncarsat.model.OrdreMission;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;

public class MenuAgentController {

	private ListeOrdreMission listeOm;

	@FXML
	private VBox listeOmVBox;

	private MainApp mainApp;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	public void initialize() {
		listeOm = new ListeOrdreMission();
		listeOm.chargerOM(new File("target/PDF/"));
		for (OrdreMission om : listeOm.getListeOM()) {
			listeOmVBox.getChildren().add(MenuAgentController.creerItemOM(om));
		}
	}

	private static VBox creerItemOM(OrdreMission om) {
		VBox item = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/itemOrdreMission.fxml"));
			item = loader.load();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return item;
	}

	@FXML
	public void creerNouveauOm(ActionEvent event) {
		this.mainApp.creerNouveauOm();

	}
}
