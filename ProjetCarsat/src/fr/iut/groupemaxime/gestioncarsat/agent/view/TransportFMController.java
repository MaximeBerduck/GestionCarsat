package fr.iut.groupemaxime.gestioncarsat.agent.view;

import javafx.fxml.FXML;

public class TransportFMController {
	private FraisMissionController fraisMissionController;

	public void setFraisMissionController(FraisMissionController fraisMissionController) {
		this.fraisMissionController = fraisMissionController;
	}
	
	@FXML
	private void afficherFraisLogement() {
		this.fraisMissionController.afficherFMLogement();
	}
	
	@FXML
	private void sauvegarderFrais() {
		this.fraisMissionController.sauvegarderFrais();
	}
}
