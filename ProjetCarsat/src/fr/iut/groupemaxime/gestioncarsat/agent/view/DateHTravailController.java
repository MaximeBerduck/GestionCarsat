package fr.iut.groupemaxime.gestioncarsat.agent.view;

import javafx.fxml.FXML;

public class DateHTravailController {
	private HorairesTravailController horairesTravailController;
	
	public void setHorairesTravailController(HorairesTravailController horairesTravailController) {
		this.horairesTravailController = horairesTravailController;
	}
	
	@FXML
	private void passerAHorairesTravail() {
		this.horairesTravailController.afficherHorairesTravail();
	}
	
	@FXML
	public void sauvegarderHoraires() {
		this.horairesTravailController.sauvegarderHoraires();
	}
}
