package fr.iut.groupemaxime.gestioncarsat.agent.view;

import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ItemHoraireTravailController {
	@FXML
	private VBox itemHoraire;
	
	@FXML
	private TextField heure1Deb;
	@FXML 
	private TextField min1Deb;
	@FXML
	private TextField heure1Fin;
	@FXML
	private TextField min1Fin;

	private OrdreMission om;

	private HorairesTravailController mainApp2;
	private OrdreMissionController mainApp;
	private JourHoraireTravailController mainApp3;

	
	public void setMainApp(OrdreMissionController mainApp) {
		this.mainApp = mainApp;
	}
	
	public void setMainApp(HorairesTravailController mainApp2) {
		this.mainApp2 = mainApp2;
	}
	
	public void setMainApp(JourHoraireTravailController mainApp3) {
		this.mainApp3 = mainApp3;
	}
	
	
}
