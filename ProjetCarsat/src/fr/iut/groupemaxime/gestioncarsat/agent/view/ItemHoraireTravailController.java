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
	public String getHeure1Deb() {
		return heure1Deb.getText();
	}

	public void setHeure1Deb(String heure1Deb) {
		this.heure1Deb.setText(heure1Deb);
	}

	public String getMin1Deb() {
		return min1Deb.getText();
	}

	public void setMin1Deb(String min1Deb) {
		this.min1Deb.setText(min1Deb);;
	}

	public String getHeure1Fin() {
		return heure1Fin.getText();
	}

	public void setHeure1Fin(String heure1Fin) {
		this.heure1Fin.setText(heure1Fin);;
	}

	public String getMin1Fin() {
		return min1Fin.getText();
	}

	public void setMin1Fin(String min1Fin) {
		this.min1Fin.setText(min1Fin);;
	}

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
