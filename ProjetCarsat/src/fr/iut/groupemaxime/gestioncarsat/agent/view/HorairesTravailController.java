package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Deque;
import java.util.HashMap;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model.HoraireJournalier;
import fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model.HoraireTravail;
import fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model.PlageHoraire;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import fr.iut.groupemaxime.gestioncarsat.utils.Options;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HorairesTravailController {
	
	@FXML
	private SplitPane horaireTravailSplit;
	@FXML
	private Label titre;
	
	private AgentApp agentApp;
	private Options options;
	private OrdreMission missionActive;
	
	private AnchorPane pageHoraires;
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
		this.horaireTravail = new HoraireTravail(this.missionActive.getCheminDossier()
				+ this.missionActive.getNomOM().replace("OM_", "HT_") + Constante.EXTENSION_JSON);

		Integer i = 0;
		String stringDebut = ((MissionTemporaire) missionActive.getMission()).getDateDebut();

		String stringFin = ((MissionTemporaire) missionActive.getMission()).getDateFin();

		this.horaireTravail.setDateDebutMission(stringDebut);
		this.horaireTravail.setDateFinMission(stringFin);
		
		System.out.println(stringDebut);
		
		this.ajouterJour(stringDebut, stringFin);
		this.listeHoraires.get(stringDebut).ajoutHoraire();
		this.listeDate.put(i, stringDebut);
		this.listeDateInverse.put(stringDebut, i);

		this.afficherSemaine();

		this.afficherPremierJour();
	}
	
	public void sauvegarderHoraires() {
		this.horaireTravail.trierHoraireJournalier();
		this.horaireTravail.sauvegarderJson(this.horaireTravail.getAdresseFichier());
		this.agentApp.retourMenu();
	}
	
	public void afficherPremierJour() {
		this.horaireTravailSplit.getItems().add(1, this.listeHoraires.get(this.listeDate.get(0)).getPage());
	}
	
	public void afficherJourSuivant(String date) {
		Integer i = this.listeDateInverse.get(date);
		i++;
		if (this.jourSuivantExiste(date)) {
			this.horaireTravailSplit.getItems().set(1, this.listeHoraires.get(this.listeDate.get(i)).getPage());
		} else {
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(Constante.FORMAT_DATE_SLASH.parse(date));
				c.add(Calendar.DATE, 1); // number of days to add
				date = Constante.FORMAT_DATE_SLASH.format(c.getTime());
				this.ajouterJour(date, this.horaireTravail.getDateFinMission());
				i++;
				this.listeDate.put(i, date);
				this.listeDateInverse.put(date, i);
				this.listeHoraires.get(date).ajoutHoraire();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			this.horaireTravailSplit.getItems().set(1, this.listeHoraires.get(this.listeDate.get(i)).getPage());
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

		for(ItemHoraireTravailController item : horaireCtrl.getListItemHtCtrl()){
			PlageHoraire plage = new PlageHoraire();
			plage.setHeureDeb(Integer.parseInt(item.getHeure1Deb()));
			plage.setHeureFin(Integer.parseInt(item.getHeure1Fin()));
			plage.setMinDeb(Integer.parseInt(item.getMin1Deb()));
			plage.setMinFin(Integer.parseInt(item.getMin1Fin()));
			horaireJournalier.ajouterHoraire(plage);
		}

		this.horaireTravail.ajouterJournee(horaireJournalier);
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
		Integer i = 0;
		for (HoraireJournalier hj : ht.getHoraireTravail().values()) {
			this.modifierHoraireJournalier(hj);
			this.listeDate.put(i, hj.getDate());
			this.listeDateInverse.put(hj.getDate(), i);
			i++;
		}
		this.afficherSemaine();
		
		this.afficherPremierJour();
		
	}
	
	private void modifierHoraireJournalier(HoraireJournalier hj) {
		this.ajouterJour(hj.getDate(), ((MissionTemporaire) missionActive.getMission()).getDateFin());
		this.listeHoraires.get(hj.getDate()).modifierHoraireJournalier(hj);
	}
	
	public void afficherExcel(HoraireTravail ht)
	{
		this.horaireTravail = ht;
		ht.remplirExcelHT();
	}
	

	
	public void setMainApp(AgentApp agentApp) {
		this.agentApp = agentApp;
	}

	public void setOptions(Options options) {
		this.options = options;
	}
	
	public void setMissionActive(OrdreMission om) {
		this.missionActive = om;
	}
	
	public void setHoraireTravail(HoraireTravail ht) {
		this.horaireTravail = ht;
	}

	public void setTitre(String titre) {
		this.titre.setText(titre);
	}

	public Options getOptions() {
		return this.options;
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	
}
