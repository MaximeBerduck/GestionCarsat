package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.fraisModel.FraisJournalier;
import fr.iut.groupemaxime.gestioncarsat.agent.fraisModel.FraisMission;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Constante;
import fr.iut.groupemaxime.gestioncarsat.agent.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Options;
import fr.iut.groupemaxime.gestioncarsat.agent.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Voiture;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

public class FraisMissionController {

	@FXML
	private SplitPane fraisMissionSplit;

	private AgentApp agentApp;
	private Options options;
	private OrdreMission missionActive;

	private FraisDateSemaineController fraisDateSemaineCtrl;
	private AnchorPane pageFraisDateSemaine;

	private HashMap<String, Frais1Controller> listeFrais1;
	private HashMap<String, Frais2Controller> listeFrais2;
	private HashMap<Integer, String> listeDate;
	private HashMap<String, Integer> listeDateInverse;

	private FraisMission fraisMission;

	@FXML
	private void initialize() {
		this.listeFrais1 = new HashMap<String, Frais1Controller>();
		this.listeFrais2 = new HashMap<String, Frais2Controller>();
		this.listeDate = new HashMap<Integer, String>();
		this.listeDateInverse = new HashMap<String, Integer>();
	}

	public void creerFraisMission() {
		this.fraisMission = new FraisMission(this.missionActive.getCheminDossier() + "FM_"
				+ ((MissionTemporaire) this.missionActive.getMission()).getLieuDeplacement() + '_'
				+ ((MissionTemporaire) this.missionActive.getMission()).getDates() + Constante.EXTENSION_JSON);
	}

	public void sauvegarderFrais() {
		// TODO
	}

	public void afficherPremierJour() {
		this.fraisMissionSplit.getItems().add(1, this.listeFrais1.get(this.listeDate.get(0)).getPage());
	}

	public void afficherJourSuivant(String date) {
		Integer i = this.listeDateInverse.get(date);
		this.sauvegarderJournee(date);
		i++;
		if (null != this.listeDate.get(i)) {
			this.fraisMissionSplit.getItems().set(1, this.listeFrais1.get(this.listeDate.get(i)).getPage());
		} else {
			this.fraisMissionSplit.getItems().remove(1);
			this.fraisMissionSplit.getItems().remove(0);
			this.fraisMission.sauvegarderJson(this.fraisMission.getAdresseFichier());
		}
	}

	private void sauvegarderJournee(String date) {
		FraisJournalier fraisJournalier = new FraisJournalier(date);
		Frais1Controller frais1Ctrl = this.listeFrais1.get(date);
		Frais2Controller frais2Ctrl = this.listeFrais2.get(date);

		if (!"".equals(frais1Ctrl.getHeureDepart()))
			fraisJournalier.setHeureDepart(
					String.valueOf(frais1Ctrl.getHeureDepart()) + ":" + String.valueOf(frais1Ctrl.getMinDepart()));

		if (!"".equals(frais1Ctrl.getHeureRetour()))
			fraisJournalier.setHeureRetour(
					String.valueOf(frais1Ctrl.getHeureRetour()) + ":" + String.valueOf(frais1Ctrl.getMinRetour()));

		if (!"".equals(frais1Ctrl.getNbrForfaitRepas()))
			fraisJournalier.setNbrRepasForfait(Integer.parseInt(frais1Ctrl.getNbrForfaitRepas()));

		if (!"".equals(frais1Ctrl.getNbrJustificatifRepas()))
			fraisJournalier.setNbrRepasJustif(Integer.parseInt(frais1Ctrl.getNbrJustificatifRepas()));

		if (!"".equals(frais1Ctrl.getNbrForfaitDecouchers()))
			fraisJournalier.setNbrDecouchForfait(Integer.parseInt(frais1Ctrl.getNbrForfaitDecouchers()));

		if (!"".equals(frais1Ctrl.getNbrJustifDecouchers()))
			fraisJournalier.setNbrDecouchJustif(Integer.parseInt(frais1Ctrl.getNbrJustifDecouchers()));

		if (!"".equals(frais2Ctrl.getTypeFraisTransport()))
			fraisJournalier.setTypeFraisTransport(frais2Ctrl.getTypeFraisTransport());

		if (!"".equals(frais2Ctrl.getMontantFraisTransport()))
			fraisJournalier.setMontantFraisTransport(Integer.parseInt(frais2Ctrl.getMontantFraisTransport()));

		if (!"".equals(frais2Ctrl.getNbrKmPerso()))
			fraisJournalier.setNbrKmVehiPerso(Integer.parseInt(frais2Ctrl.getNbrKmPerso()));

		if (!"".equals(frais2Ctrl.getNbrKmService()))
			fraisJournalier.setNbrKmVehiService(Integer.parseInt(frais2Ctrl.getNbrKmService()));

		this.fraisMission.ajouterJournee(fraisJournalier);
	}

	public void creerAllJours() {
		Integer i = 0;
		String stringDebut = ((MissionTemporaire) missionActive.getMission()).getDateDebut();

		String stringFin = ((MissionTemporaire) missionActive.getMission()).getDateFin();

		this.ajouterJour(stringDebut);
		this.listeDate.put(i, stringDebut);
		this.listeDateInverse.put(stringDebut, i);

		while (!stringDebut.equals(stringFin)) {
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(Constante.FORMAT_DATE_SLASH.parse(stringDebut));
				c.add(Calendar.DATE, 1); // number of days to add
				stringDebut = Constante.FORMAT_DATE_SLASH.format(c.getTime());
				this.ajouterJour(stringDebut);
				i++;
				this.listeDate.put(i, stringDebut);
				this.listeDateInverse.put(stringDebut, i);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public void ajouterJour(String jour) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("Frais1.fxml"));
			AnchorPane pageFrais1 = loader.load();

			Frais1Controller frais1Ctrl = loader.getController();

			frais1Ctrl.setPageFrais1(pageFrais1);
			frais1Ctrl.setDateJournee(jour);
			frais1Ctrl.setFmController(this);
			this.listeFrais1.put(jour, frais1Ctrl);

			FXMLLoader loader2 = new FXMLLoader();
			loader2.setLocation(this.getClass().getResource("Frais2.fxml"));
			AnchorPane pageFrais2 = loader2.load();

			Frais2Controller frais2Ctrl = loader2.getController();
			frais2Ctrl.setDateJournee(jour);
			frais2Ctrl.setPageFrais2(pageFrais2);
			frais2Ctrl.setFmController(this);

			if ("voiture".equals(this.missionActive.getTransport().getTypeTransport())) {
				frais2Ctrl.getNbrKilometreLayout().setDisable(false);
				Voiture voiture = (Voiture) this.missionActive.getTransport();
				if ("vehiculeService".equals(voiture.getAppartenanceVehicule())) {
					frais2Ctrl.getVehiculeServiceLayout().setVisible(true);
					frais2Ctrl.getVehiculePersoLayout().setVisible(false);
				}
			} else {
				frais2Ctrl.getNbrKilometreLayout().setVisible(false);
			}

			this.listeFrais2.put(jour, frais2Ctrl);
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
			this.pageFraisDateSemaine = loader.load();

			this.fraisDateSemaineCtrl = loader.getController();

			this.fraisDateSemaineCtrl.setLabelDateDebut(stringDebut);
			this.fraisDateSemaineCtrl.setLabelDateFin(stringFin);

			this.fraisMissionSplit.getItems().add(0, this.pageFraisDateSemaine);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setAgentApp(AgentApp agentApp) {
		this.agentApp = agentApp;
	}

	public void setOptions(Options options) {
		this.options = options;
	}

	public void setMissionActive(OrdreMission om) {
		this.missionActive = om;
	}

	public void afficherFrais2(String date) {
		this.fraisMissionSplit.getItems().set(1, this.listeFrais2.get(date).getPage());
	}

	public void retourFrais1(String date) {
		this.fraisMissionSplit.getItems().set(1, this.listeFrais1.get(date).getPage());
	}
}
