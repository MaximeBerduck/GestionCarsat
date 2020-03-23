package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisMission;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.ListeOrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.mail.Mail;
import fr.iut.groupemaxime.gestioncarsat.utils.Bibliotheque;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import fr.iut.groupemaxime.gestioncarsat.utils.EtatMission;
import fr.iut.groupemaxime.gestioncarsat.utils.Options;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class MenuAgentController {

	private ListeOrdreMission listeOm;

	@FXML
	private VBox listeOmVBox;

	private OrdreMissionController mainApp;
	private Options options;
	private RootLayoutController layout;

	private AgentApp agentApp;

	private HashSet<ItemOrdreMissionController> listeOmCtrl;

	public void setMenuController(OrdreMissionController mainApp) {
		this.mainApp = mainApp;
	}

	public void setOptions(Options options) {
		this.options = options;
	}

	@FXML
	public void initialize() {
		this.listeOmCtrl = new HashSet<ItemOrdreMissionController>();
	}

	public void chargerOM() {
		this.listeOmCtrl = new HashSet<>();
		listeOm = new ListeOrdreMission();
		listeOm.chargerOM(new File(options.getCheminOM()));
		this.agentApp.getMailsEnAttente().chargerMails(Constante.CHEMIN_MAILS_EN_ATTENTE, options);
		for (OrdreMission om : listeOm.getListeOM()) {
			if (om.getEtat() == EtatMission.EN_COURS_ENVOI) {

				boolean trouve = false;

				for (Mail mail : this.agentApp.getMailsEnAttente().getListeMails()) {
					if (mail.getPath().length() > 0 && om.getNomOM().equals(mail.getPath().substring(
							mail.getPath().lastIndexOf(File.separatorChar) + 1, mail.getPath().lastIndexOf(".")))) {
						trouve = true;
					}
				}
				if (!trouve) {
					om.setEtat(EtatMission.ENVOYE);
				}
			}
			if (Bibliotheque.fichierFmMissionExiste(om)) {
				FraisMission fm = Bibliotheque.recupererFmAvecOm(om);
				if (fm.getEtat() == EtatMission.EN_COURS_ENVOI) {
					boolean trouve = false;

					for (Mail mail : this.agentApp.getMailsEnAttente().getListeMails()) {
						if (mail.getPath().length() > 0 && om.getNomOM().replace("OM_", "HT_")
								.equals(mail.getPath().substring(mail.getPath().lastIndexOf(File.separatorChar) + 1,
										mail.getPath().lastIndexOf(".")))) {
							trouve = true;
						}
					}
					if (!trouve) {
						Bibliotheque.setEtatFm(om, EtatMission.ENVOYE);
					}
				}
			}

			listeOmVBox.getChildren().add(this.creerItemOM(om));
		}
	}

	private VBox creerItemOM(OrdreMission om) {
		VBox item = null;
		ItemOrdreMissionController ctrl;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("ItemOrdreMission.fxml"));
			item = loader.load();

			ctrl = loader.getController();
			ctrl.chargerOM(om);
			ctrl.setMenuAgent(this);
			this.listeOmCtrl.add(ctrl);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return item;
	}

	public ItemOrdreMissionController getItemOM(OrdreMission om) {
		for (ItemOrdreMissionController item : this.listeOmCtrl) {
			if (item.getOM().getNomOM().equals(om.getNomOM())) {
				return item;
			}
		}
		return null;
	}

	@FXML
	public void creerNouveauOm(ActionEvent event) {
		this.agentApp.creerOrdreMission();
	}

	public ListeOrdreMission getListeOm() {
		return this.listeOm;
	}

	public void setListeOm(ListeOrdreMission listeOm) {
		this.listeOm = listeOm;
	}

	public void setAgentApp(AgentApp agentApp) {
		this.agentApp = agentApp;
	}

	public void setMissionActive(OrdreMission om) {
		this.agentApp.retirerDocActif();
		this.agentApp.setMissionActive(om);
		for (ItemOrdreMissionController item : listeOmCtrl) {
			item.retirerStyle(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
		}
		this.agentApp.retourMenu();
	}

	public AgentApp getAgentApp() {
		return this.agentApp;

	}
}
