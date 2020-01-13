package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model.HoraireTravail;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.utils.Options;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class JourHoraireTravailController {
		
	@FXML
	private VBox listeHoraireVBox;
	
	@FXML 
	private Label dateJournee;
	
	@FXML 
	private TextField transportUtiliseSurPlace;
	
	@FXML
	private TextField dureeDuTrajetSurPlace;
	
	private AnchorPane pageHoraire;
	
	private HorairesTravailController htController;
	
	private HashSet<ItemHoraireTravailController> listeHtCtrl;
	
	public void initialize(OrdreMission om) {
		this.listeHtCtrl = new HashSet<ItemHoraireTravailController>();
		VBox item = null;
		ItemHoraireTravailController ctrl;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("ItemHoraireTravail.fxml"));
			item = loader.load();
			
			ctrl = loader.getController();
//			ctrl.chargerOM(om);
			ctrl.setMainApp(this);
			
			this.listeHtCtrl.add(ctrl);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void validerHT() {
		
	}
	
	@FXML
	public void btnSuivantHoraire(ActionEvent event) {
		
	}
	
	@FXML
	public void btnAjouterHoraire(ActionEvent event) {
		//TODO
		
		
	}
	
	@FXML
	public void btnSupprimerHoraire(ActionEvent event) {
		
		//TODO
	}
	
	public void setDateJournee(String date) {
		this.dateJournee.setText(date);
	}


	public void setHtController(HorairesTravailController htController) {
		this.htController = htController;
	}

	
	
	public void setChamps(HoraireTravail horaire) {
		
	}

	public AnchorPane getPage() {
		return this.pageHoraire;
	}
	
	
	
	
}
