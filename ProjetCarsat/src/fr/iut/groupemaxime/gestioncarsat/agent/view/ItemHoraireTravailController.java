package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import fr.iut.groupemaxime.gestioncarsat.agent.form.PDF;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.utils.Bibliotheque;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	

//	public void chargerOM(OrdreMission om) {
//		this.om = om;
//		this.lieuLabel.setText(((MissionTemporaire) om.getMission()).getLieuDeplacement());
//		this.datesLabel.setText("Du " + ((MissionTemporaire) om.getMission()).getDateDebut() + " au "
//				+ ((MissionTemporaire) om.getMission()).getDateFin());
//
//	}
	
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
