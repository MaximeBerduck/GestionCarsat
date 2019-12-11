package fr.iut.groupemaxime.gestioncarsat.agent.view;

import javafx.fxml.FXML;

public class DateFMController {

	private FraisMissionController mainApp;
	
	public void setMainApp(FraisMissionController fraisMissionController) {
		// TODO Auto-generated method stub
		this.mainApp = mainApp;
	}
	
	@FXML
	private void passerATypeOM() {
			this.mainApp.afficherFMDate();
	}

}
