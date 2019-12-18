package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.IOException;


import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Options;
import fr.iut.groupemaxime.gestioncarsat.agent.view.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class HorairesTravailController {
	@FXML
	private DatePicker dateDeb; 
	
	@FXML
	private DatePicker dateFin;
	
	@FXML
	private Label labelLundiDate;
	
	@FXML 
	private Label labelMardiDate;
	
	@FXML
	private Label labelMercrediDate;
	
	@FXML
	private Label labelJeudiDate;
	
	@FXML
	private Label labelVendredidate;
	
	@FXML
	private Label labelSamedidate;
	
	@FXML
	private Label labelDimancheDate;
	
	@FXML 
	private RadioButton aiTravailleLundi;
	
	@FXML 
	private RadioButton aiTravailleMardi;
	
	@FXML 
	private RadioButton aiTravailleMercredi;
	
	@FXML 
	private RadioButton aiTravailleJeudi;
	
	@FXML 
	private RadioButton aiTravailleVendredi;
	
	@FXML 
	private RadioButton aiTravailleSamedi;
	
	@FXML 
	private RadioButton aiTravailleDimanche;
	
	@FXML 
	private RadioButton pasTravailleLundi;
	
	@FXML 
	private RadioButton pasTravailleMardi;
	
	@FXML 
	private RadioButton pasTravailleMercredi;
	
	@FXML 
	private RadioButton pasTravailleJeudi;
	
	@FXML 
	private RadioButton pasTravailleVendredi;
	
	@FXML 
	private RadioButton pasTravailleSamedi;
	
	@FXML 
	private RadioButton pasTravailleDimanche;
	
	@FXML
	private VBox vBoxAjoutHoraire;
	
	@FXML
	private SplitPane fraisMissionSplit;
	
	private AnchorPane pageHoraires;
	private DateHTravailController horairesController;
	
	private AgentApp agentApp;
	private Options options;
	
	@FXML
	private void initialize() {
	}
	
	
	public void setMainApp(AgentApp agentApp) {
		this.agentApp = agentApp;
	}

	public void setOptions(Options options) {
		this.options = options;
	}
	
	public void sauvegarderHoraires() {
		
	}

	public void afficherHorairesTravail() {
		if(this.pageHoraires!=null) {
			if(0<this.fraisMissionSplit.getItems().size())
				this.fraisMissionSplit.getItems().set(0, this.pageHoraires);
			else 
				this.fraisMissionSplit.getItems().add(0, this.pageHoraires);
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(this.getClass().getResource("HorairesTravail.fxml"));
				this.pageHoraires = loader.load();
				
				this.horairesController = loader.getController();
				this.horairesController.setHorairesTravailController(this);
				
				if (0 < this.fraisMissionSplit.getItems().size())
					this.fraisMissionSplit.getItems().set(0, this.pageHoraires);
				else
					this.fraisMissionSplit.getItems().add(0, this.pageHoraires);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
