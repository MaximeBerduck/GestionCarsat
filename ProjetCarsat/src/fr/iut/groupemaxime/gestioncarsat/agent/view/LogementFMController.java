package fr.iut.groupemaxime.gestioncarsat.agent.view;

import javafx.fxml.FXML;

public class LogementFMController {
	private FraisMissionController fraisMissionController;

	public void setFraisMissionController(FraisMissionController fraisMissionController) {
		this.fraisMissionController = fraisMissionController;
	}
	
	@FXML
	private void afficherFraisTransport() {
		this.fraisMissionController.afficherFMTransport();
	}
	
	@FXML
	private void afficherFraisDate() {
		this.fraisMissionController.afficherFMDate();
	}
}
