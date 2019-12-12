package fr.iut.groupemaxime.gestioncarsat.agent.view;

import javafx.fxml.FXML;

public class DateFMController {

	private FraisMissionController fraisMissionController;
	
	public void setFraisMissionController(FraisMissionController fraisMissionController) {
		this.fraisMissionController = fraisMissionController;
	}
	
	@FXML
	private void passerATypeOM() {
			this.fraisMissionController.afficherFMDate();
	}
	
	@FXML
	private void afficherFraisLogement() {
		this.fraisMissionController.afficherFMLogement();
	}

}
