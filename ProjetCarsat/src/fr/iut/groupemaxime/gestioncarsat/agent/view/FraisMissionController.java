package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisJournalier;
import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisMission;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.Voiture;
import fr.iut.groupemaxime.gestioncarsat.utils.Bibliotheque;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import fr.iut.groupemaxime.gestioncarsat.utils.EtatMission;
import fr.iut.groupemaxime.gestioncarsat.utils.Options;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
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
	private Label titre;

	public void setTitre(String titre) {
		this.titre.setText(titre);
	}

	@FXML
	private void initialize() {
		this.listeFrais1 = new HashMap<String, Frais1Controller>();
		this.listeFrais2 = new HashMap<String, Frais2Controller>();
		this.listeDate = new HashMap<Integer, String>();
		this.listeDateInverse = new HashMap<String, Integer>();
	}

	public void creerFraisMission() {
		this.fraisMission = new FraisMission(this.missionActive.getCheminDossier()
				+ this.missionActive.getNomOM().replace("OM_", "FM_") + Constante.EXTENSION_JSON);

		Integer i = 0;
		String stringDebut = ((MissionTemporaire) missionActive.getMission()).getDateDebut();

		String stringFin = ((MissionTemporaire) missionActive.getMission()).getDateFin();

		this.fraisMission.setDateDebutMission(stringDebut);
		this.fraisMission.setDateFinMission(stringFin);

		this.ajouterJour(stringDebut, stringFin);
		this.listeDate.put(i, stringDebut);
		this.listeDateInverse.put(stringDebut, i);

		this.afficherSemaine();

		this.afficherPremierJour();
	}

	public void sauvegarderFrais() {
		this.fraisMission.trierFraisJournalier();

		if (Bibliotheque.fichierFmEstEntier(this.fraisMission))
			this.fraisMission.setEtat(EtatMission.NON_SIGNE);
		else
			this.fraisMission.setEtat(EtatMission.EN_COURS_SAISIE);

		this.fraisMission.sauvegarderJson(this.fraisMission.getAdresseFichier());
	}

	public void afficherPremierJour() {
		this.fraisMissionSplit.getItems().add(1, this.listeFrais1.get(this.listeDate.get(0)).getPage());
	}

	public void afficherJourSuivant(String date) {
		Integer i = this.listeDateInverse.get(date);
		i++;
		if (this.jourSuivantExiste(date)) {
			this.fraisMissionSplit.getItems().set(1, this.listeFrais1.get(this.listeDate.get(i)).getPage());
		} else {
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(Constante.FORMAT_DATE_SLASH.parse(date));
				c.add(Calendar.DATE, 1); // number of days to add
				date = Constante.FORMAT_DATE_SLASH.format(c.getTime());
				this.ajouterJour(date, this.fraisMission.getDateFinMission());
				i++;
				this.listeDate.put(i, date);
				this.listeDateInverse.put(date, i);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			this.fraisMissionSplit.getItems().set(1, this.listeFrais1.get(this.listeDate.get(i)).getPage());
		}
	}

	public boolean jourSuivantExiste(String jour) {
		Integer i = this.listeDateInverse.get(jour);
		i++;
		return null != this.listeDate.get(i);
	}

	public void sauvegarderJournee(String date) {
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
			fraisJournalier.setMontantFraisTransport(Float.parseFloat(frais2Ctrl.getMontantFraisTransport()));

		if (!"".equals(frais2Ctrl.getNbrKmPerso()))
			fraisJournalier.setNbrKmVehiPerso(Float.parseFloat(frais2Ctrl.getNbrKmPerso()));

		if (!"".equals(frais2Ctrl.getNbrKmService()))
			fraisJournalier.setNbrKmVehiService(Float.parseFloat(frais2Ctrl.getNbrKmService()));

		if (!"".equals(frais2Ctrl.getMontantAutreFrais())) {
			fraisJournalier.setMontantAutreFrais(Float.parseFloat(frais2Ctrl.getMontantAutreFrais()));
		}

		if (!"".equals(frais2Ctrl.getTypeAutreFrais())) {
			fraisJournalier.setTypeAutreFrais(frais2Ctrl.getTypeAutreFrais());
		}

		this.fraisMission.ajouterJournee(fraisJournalier);
		this.sauvegarderFrais();
	}

	public void ajouterJour(String jour, String stringFin) {
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
			if (jour.equals(stringFin)) {
				frais2Ctrl.setBoutonSuivantToSauvegarder();
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

	public void modifierFraisMission(FraisMission fm) {
		Integer i = 0;
		for (FraisJournalier fj : fm.getFraisMission().values()) {
			this.modifierFraisJournalier(fj);
			this.listeDate.put(i, fj.getDate());
			this.listeDateInverse.put(fj.getDate(), i);
			i++;
		}
		this.afficherSemaine();
		this.afficherPremierJour();
	}

	private void modifierFraisJournalier(FraisJournalier fj) {
		this.ajouterJour(fj.getDate(), ((MissionTemporaire) missionActive.getMission()).getDateFin());
		this.listeFrais1.get(fj.getDate()).modifierFraisJournalier(fj);
		this.listeFrais2.get(fj.getDate()).modifierFraisJournalier(fj);

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

	public void setFraisMission(FraisMission fm) {
		this.fraisMission = fm;
	}

	public void afficherFrais2(String date) {
		this.fraisMissionSplit.getItems().set(1, this.listeFrais2.get(date).getPage());
	}

	public void retourFrais1(String date) {
		this.fraisMissionSplit.getItems().set(1, this.listeFrais1.get(date).getPage());
	}

	public void chargerFM(OrdreMission mission) {
		this.fraisMission = new FraisMission(null);
		this.fraisMission = this.fraisMission.chargerJson(
				mission.getCheminDossier() + mission.getNomOM().replace("OM_", "FM_") + Constante.EXTENSION_JSON);
	}

	public void afficherSignatureFM(OrdreMission mission) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("FraisSigner.fxml"));
			AnchorPane fraisSigner = loader.load();
			FraisSignerController fraisSignerCtrl = loader.getController();
			fraisSignerCtrl.setFmCtrl(this);
			fraisSignerCtrl.setMission(mission);
			this.fraisMissionSplit.getItems().add(fraisSigner);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void signerFM(OrdreMission mission, float montantDeductionFrais, float montantAvance, int nbrRepasOffert) {
		this.chargerFM(mission);

		this.fraisMission.signerFMAgent(montantDeductionFrais, montantAvance, nbrRepasOffert);
		this.fraisMission.sauvegarderJson(this.fraisMission.getAdresseFichier());
		this.agentApp.retourMenu();
	}

	public AgentApp getAgentApp() {
		return this.agentApp;
	}
}
