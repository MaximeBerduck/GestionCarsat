package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.IOException;
import java.util.HashMap;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisJournalier;
import fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model.HoraireJournalier;
import fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model.HoraireTravail;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import fr.iut.groupemaxime.gestioncarsat.utils.Options;
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
	
	@FXML
	private SplitPane horaireTravailSplit;
	
	//private Stage primaryStage;
	private AgentApp agentApp;
	private Options options;
	private OrdreMission missionActive;
	
	private AnchorPane pageHoraires;
	private JourHoraireTravailController horairesController;
	
	private HashMap<String, JourHoraireTravailController> listeHoraires;
	private HashMap<Integer, String> listeDate;
	private HashMap<String, Integer> listeDateInverse;
	
	private HoraireTravail horaireTravail;
	private Stage primaryStage;
	
	
	@FXML
	private void initialize() {
		this.listeHoraires = new HashMap<String, JourHoraireTravailController>();
		this.listeDate = new HashMap<Integer, String>();
		this.listeDateInverse = new HashMap<String, Integer>();
	}
	
	public void creerHoraireMission() {
		this.horaireTravail = new HoraireTravail(this.missionActive.getCheminDossier() + "HT_"
				+ ((MissionTemporaire) this.missionActive.getMission()).getLieuDeplacement() + '_'
				+ ((MissionTemporaire) this.missionActive.getMission()).getDates() + Constante.EXTENSION_JSON);
	}
	
	public void sauvegarderHoraires() {
		// TODO
	}
	
	public void afficherPremierJour() {
		this.horaireTravailSplit.getItems().add(1, this.listeHoraires.get(this.listeDate.get(0)).getPage());
	}
	
	public void afficherJourSuivant(String date) {
		Integer i = this.listeDateInverse.get(date);
		this.sauvegarderJournee(date);
		i++;
		if (null != this.listeDate.get(i)) {
			this.horaireTravailSplit.getItems().set(1,  this.listeHoraires.get(this.listeDate.get(i)).getPage());
		} else {
			this.horaireTravailSplit.getItems().remove(1);
			this.horaireTravailSplit.getItems().remove(0);
			this.horaireTravail.sauvegarderJson(this.horaireTravail.getAdresseFichier());
		}
	}
	
	private void sauvegarderJournee(String date) {
		HoraireJournalier horaireJournalier = new HoraireJournalier(date);
		JourHoraireTravailController horaireCtrl = this.listeHoraires.get(date);
		
		//TODO

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
	
	public void modifierHt(OrdreMission om) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("JourHoraireTravail.fxml"));
			pageHoraires = loader.load();

			if (1 == this.horaireTravailSplit.getItems().size())
				this.horaireTravailSplit.getItems().set(0, this.pageHoraires);
			else
				this.horaireTravailSplit.getItems().add(0, this.pageHoraires);

			horairesController = loader.getController();
			horairesController.setHtController(this);
			//horairesController.setChamps(om.getAgent());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	
	public void validerHoraireTravail() {
		
	}
	
//	public Stage getPrimaryStage() {
//		return this.primaryStage;
//	}
	
	public void setMainApp(AgentApp agentApp) {
		this.agentApp = agentApp;
	}

	public void setOptions(Options options) {
		this.options = options;
	}

	public Options getOptions() {
		return this.options;
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
}
