package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisJournalier;
import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisMission;
import fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model.HoraireJournalier;
import fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model.HoraireTravail;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.Voiture;
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
	
	private AgentApp agentApp;
	private Options options;
	private OrdreMission missionActive;
	
	private AnchorPane pageHoraires;
	private JourHoraireTravailController horairesController;
	private FraisDateSemaineController fraisDateSemaineCtrl;
	
	
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
		
		Integer i = 0;
		String stringDebut = ((MissionTemporaire) missionActive.getMission()).getDateDebut();

		String stringFin = ((MissionTemporaire) missionActive.getMission()).getDateFin();

		this.horaireTravail.setDateDebutMission(stringDebut);
		this.horaireTravail.setDateFinMission(stringFin);

		this.ajouterJour(stringDebut, stringFin);
		this.listeDate.put(i, stringDebut);
		this.listeDateInverse.put(stringDebut, i);

		this.afficherSemaine();

		this.afficherPremierJour();
	}
	
	public void sauvegarderHoraires() {
		this.horaireTravail.sauvegarderJson(this.horaireTravail.getAdresseFichier());
		this.agentApp.retirerDocActif();
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
	
	public boolean jourSuivantExiste(String jour) {
		Integer i = this.listeDateInverse.get(jour);
		i++;
		return null != this.listeDate.get(i);
	}
	
	public void sauvegarderJournee(String date) {
		HoraireJournalier horaireJournalier = new HoraireJournalier(date);
		JourHoraireTravailController horaireCtrl = this.listeHoraires.get(date);
		
		if(!"".equals(horaireCtrl.getTransportUtiliseSurPlace())) {
			horaireJournalier.setTransportUtiliseSurPlace(
					String.valueOf(horaireCtrl.getTransportUtiliseSurPlace()));
		}
		if(!"".equals(horaireCtrl.getDureeDuTrajetSurPlace())){
			horaireJournalier.setDureeDuTrajetSurPlace(
					String.valueOf(horaireCtrl.getDureeDuTrajetSurPlace()));
		}
		
		this.horaireTravail.ajouterJournee(horaireJournalier);
		
		// reste a gerer le if il y a 1 ou plusieurs horaires de saisis, faire un for jusqu'au nb de ligne d'horaire choisi

	}
	
	public void ajouterJour(String jour, String stringFin) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("JourHoraireTravail.fxml"));
			AnchorPane pageHoraires = loader.load();

			JourHoraireTravailController horairesCtrl = loader.getController();

			horairesCtrl.setPageHoraire(pageHoraires);
			horairesCtrl.setDateJournee(jour);
			horairesCtrl.setHtController(this);
			this.listeHoraires.put(jour, horairesCtrl);

			if (jour.equals(stringFin)) {
				horairesCtrl.setBoutonSuivantToSauvegarder();
			}

			this.listeHoraires.put(jour, horairesCtrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void afficherSemaine() {
		String stringDebut = ((MissionTemporaire) missionActive.getMission()).getDateDebut();
		String stringFin = ((MissionTemporaire) missionActive.getMission()).getDateFin();

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("FraisDateSemaine.fxml"));
			this.pageHoraires = loader.load();

			this.fraisDateSemaineCtrl = loader.getController();

			this.fraisDateSemaineCtrl.setLabelDateDebut(stringDebut);
			this.fraisDateSemaineCtrl.setLabelDateFin(stringFin);

			this.horaireTravailSplit.getItems().add(0, this.pageHoraires);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void modifierHoraireTravail(HoraireTravail ht) {
		// TODO
		Integer i = 0;
		for (HoraireJournalier hj : ht.getHoraireTravail().values()) {
			this.modifierHoraireJournalier(hj);
			this.listeDate.put(0, hj.getDate());
			this.listeDateInverse.put(hj.getDate(), 0);
			i++;
		}
		this.afficherSemaine();
		this.afficherPremierJour();
	}
	
	private void modifierHoraireJournalier(HoraireJournalier hj) {
		// TODO Auto-generated method stub
		this.ajouterJour(hj.getDate(), ((MissionTemporaire) missionActive.getMission()).getDateFin());
		this.listeHoraires.get(hj.getDate()).modifierHoraireJournalier(hj);
	}
	

	
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
	
	public void setMissionActive(OrdreMission om) {
		this.missionActive = om;
	}
}
