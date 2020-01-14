package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model.HoraireJournalier;
import fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model.HoraireTravail;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.utils.Options;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
	
	@FXML
	private Button boutonValider; 

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
	
	// Event Listener on Button.onAction
		@FXML
		public void validerJournee(ActionEvent event) {
			this.htController.afficherJourSuivant(this.dateJournee.getText());
		}

		// Event Listener on Button.onAction
		@FXML
		public void sauvegarderFraisMission(ActionEvent event) {
			this.htController.sauvegarderHoraires();
		}
	
	public void modifierHoraireJournalier(HoraireJournalier hj) {
		this.setTransportUtiliseSurPlace(hj.getTransportUtiliseSurPlace());
		this.setDureeDuTrajetSurPlace(hj.getDureeDuTrajetSurPlace());
	
		//TODO le scroll pane a gérer
	}
	
	public void setBoutonSuivantToSauvegarder() {
		this.boutonValider.setText("Valider");
		this.boutonValider.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				htController.sauvegarderJournee(dateJournee.getText());
				htController.sauvegarderHoraires();
			}
		});
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

	public AnchorPane getPage() {
		return this.pageHoraire;
	}
	
	public void setPageHoraire(AnchorPane pageHoraire) {
		this.pageHoraire = pageHoraire;
	}
	
	public String getTransportUtiliseSurPlace() {
		return transportUtiliseSurPlace.getText();
	}

	public void setTransportUtiliseSurPlace(String transportUtiliseSurPlace) {
		this.transportUtiliseSurPlace.setText(transportUtiliseSurPlace);;
	}

	public String getDureeDuTrajetSurPlace() {
		return dureeDuTrajetSurPlace.getText();
	}

	public void setDureeDuTrajetSurPlace(String dureeDuTrajetSurPlace) {
		this.dureeDuTrajetSurPlace.setText(dureeDuTrajetSurPlace);
	}
	
	
	
	
	
}
