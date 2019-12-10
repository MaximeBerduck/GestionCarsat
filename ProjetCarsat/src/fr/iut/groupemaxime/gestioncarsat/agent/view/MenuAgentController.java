package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.File;
import java.io.IOException;

import fr.iut.groupemaxime.gestioncarsat.agent.model.Constante;
import fr.iut.groupemaxime.gestioncarsat.agent.model.ListeOrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Options;
import fr.iut.groupemaxime.gestioncarsat.agent.model.OrdreMission;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class MenuAgentController {

	private ListeOrdreMission listeOm;

	@FXML
	private VBox listeOmVBox;

	private OrdreMissionController mainApp;
	private Options options;

	public void setMenuController(OrdreMissionController mainApp) {
		this.mainApp = mainApp;
	}
	public void setOptions(Options options) {
		this.options = options;
	}

	@FXML
	public void initialize() {
		
	}
	
	public void chargerOM() {
		listeOm = new ListeOrdreMission();
		listeOm.chargerOM(new File(options.getCheminOM()));
		for (OrdreMission om : listeOm.getListeOM()) {
			listeOmVBox.getChildren().add(this.creerItemOM(om));
		}
	}

	private VBox creerItemOM(OrdreMission om) {
		VBox item = null;
		ItemOrdreMissionController ctrl;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("ItemOrdreMission.fxml"));
			item = loader.load();
			
			ctrl = loader.getController();
			ctrl.setMainApp(this.mainApp);
			ctrl.chargerOM(om);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return item;
	}

	@FXML
	public void creerNouveauOm(ActionEvent event) {
		this.mainApp.creerNouveauOm();

	}
	public ListeOrdreMission getListeOm() {
		return this.listeOm;
	}
	
	public void setListeOm(ListeOrdreMission listeOm) {
		this.listeOm = listeOm;
	}
}
