package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.IOException;


import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Options;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HorairesTravailController {
	
	private Stage primaryStage;
	private AgentApp mainApp;
	private Options options;
	
	@FXML
	private SplitPane horaireTravailSplit;
	
	private AnchorPane pageHoraires;
	private JourHoraireTravailController horairesController;
	
	
	@FXML
	private void initialize() {
		
	}
	

	public void afficherHorairesTravail() {
		if(this.pageHoraires!=null) {
			if(0<this.horaireTravailSplit.getItems().size())
				this.horaireTravailSplit.getItems().set(0, this.pageHoraires);
			else 
				this.horaireTravailSplit.getItems().add(0, this.pageHoraires);
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(this.getClass().getResource("JourHoraireTravail.fxml"));
				this.pageHoraires = loader.load();
				
				if (0 < this.horaireTravailSplit.getItems().size())
					this.horaireTravailSplit.getItems().set(0, this.pageHoraires);
				else
					this.horaireTravailSplit.getItems().add(0, this.pageHoraires);
				
				this.horairesController = loader.getController();
				this.horairesController.setHtController(this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void validerHoraireTravail() {
		
	}
	
	public Stage getPrimaryStage() {
		return this.primaryStage;
	}
	
	public void setMainApp(AgentApp agentApp) {
		this.mainApp = agentApp;
	}

	public void setOptions(Options options) {
		this.options = options;
	}

	public Options getOptions() {
		return this.options;
	}
}
