package fr.iut.groupemaxime.gestioncarsat.view;

import javafx.fxml.FXML;
import fr.iut.groupemaxime.gestioncarsat.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.model.OrdreMission;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class itemOrdreMissionController {
	@FXML
	private Label lieuLabel;
	@FXML
	private Label datesLabel;
	@FXML
	private VBox boite;
	@FXML
	private void initialize() {
		
	}
	// Event Listener on Button.onAction
	@FXML
	public void afficherOrdreMissionPDF(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button.onAction
	@FXML
	public void modifierOM(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button.onAction
	@FXML
	public void supprimerOM(ActionEvent event) {
		// TODO Autogenerated
	}
	public void chargerOM(OrdreMission om) {
		this.lieuLabel.setText(((MissionTemporaire) om.getMission()).getLieuDeplacement());

		
	}
}
