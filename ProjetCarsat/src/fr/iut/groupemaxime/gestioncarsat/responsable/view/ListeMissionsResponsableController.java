package fr.iut.groupemaxime.gestioncarsat.responsable.view;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import fr.iut.groupemaxime.gestioncarsat.agent.form.PDF;
import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisMission;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.ListeOrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.view.ItemOrdreMissionController;
import fr.iut.groupemaxime.gestioncarsat.agent.view.OrdreMissionController;
import fr.iut.groupemaxime.gestioncarsat.mail.Mail;
import fr.iut.groupemaxime.gestioncarsat.responsable.ResponsableApp;
import fr.iut.groupemaxime.gestioncarsat.utils.Bibliotheque;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import fr.iut.groupemaxime.gestioncarsat.utils.EtatMission;
import fr.iut.groupemaxime.gestioncarsat.utils.EtatsResponsable;
import fr.iut.groupemaxime.gestioncarsat.utils.Options;
import fr.iut.groupemaxime.gestioncarsat.utils.TypeDocument;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class ListeMissionsResponsableController {
	private ListeOrdreMission listeOm;

	@FXML
	private VBox listeOmVBox;

	private OrdreMissionController mainApp;
	private Options options;

	private HashSet<ItemMissionResponsableController> listeOmCtrl;

	private ResponsableApp responsableApp;

	@FXML
	public void initialize() {
		this.listeOmCtrl = new HashSet<>();
	}

	public void setMenuController(OrdreMissionController mainApp) {
		this.mainApp = mainApp;
	}

	public void setOptions(Options options) {
		this.options = options;
	}

	public void chargerOM() {
		listeOm = new ListeOrdreMission();
		File dossier = new File(options.getCheminOM() + "responsable/");
		this.responsableApp.getMailsEnAttente().chargerMails(Constante.CHEMIN_MAILS_EN_ATTENTE, options);
		if (!dossier.exists())
			dossier.mkdir();
		File[] pdfs = dossier.listFiles();
		PDF pdf;
		for (File file : pdfs) {
			if (!".DS_Store".equals(file.getName())) {
				if (file.canWrite()) {
					File filee = new File(file.getAbsolutePath() + "/OM_" + file.getName() + Constante.EXTENSION_PDF);
					try {
						pdf = new PDF(filee);
						OrdreMission om = pdf.chargerPDFtoOM();
						om.setCheminDossier(file.getAbsolutePath());
						om.setNomOM(
								filee.getAbsolutePath().substring(filee.getAbsolutePath().lastIndexOf(File.separator)));
						File etat = new File(om.getCheminDossier() + File.separator + om.getNomOM()
								.substring(om.getNomOM().indexOf("_") + 1, om.getNomOM().lastIndexOf(".")) + ".json");
						EtatsResponsable nouveau = new EtatsResponsable(etat.getAbsolutePath());
						if (!etat.exists()) {
							nouveau.sauvegarderJson();
						} else {
							nouveau = nouveau.chargerJson(etat.getAbsolutePath());
							if (nouveau.getOm() == EtatMission.EN_COURS_ENVOI) {

								boolean trouve = false;

								for (Mail mail : this.responsableApp.getMailsEnAttente().getListeMails()) {
									if (mail.getPath().length() > 0
											&& om.getNomOM().substring(1, om.getNomOM().lastIndexOf('.'))
													.equals(mail.getPath().substring(
															mail.getPath().lastIndexOf(File.separator) + 1,
															mail.getPath().lastIndexOf('.')))) {
										trouve = true;
									}
								}
								if (!trouve) {
									nouveau.setOm(EtatMission.ENVOYE);
								}
							}
							if (nouveau.getFm() == EtatMission.EN_COURS_ENVOI) {
								boolean trouve = false;

								for (Mail mail : this.responsableApp.getMailsEnAttente().getListeMails()) {
									if (mail.getPath().length() > 0 && om.getNomOM().replace("OM_", "HT_")
											.substring(1, om.getNomOM().lastIndexOf('.'))
											.equals(mail.getPath().substring(
													mail.getPath().lastIndexOf(File.separator) + 1,
													mail.getPath().lastIndexOf('.')))) {
										trouve = true;
									}
								}
								if (!trouve) {
									nouveau.setFm(EtatMission.ENVOYE);
									nouveau.setHt(EtatMission.ENVOYE);
								}

							}
							nouveau.sauvegarderJson();
						}
						listeOm.ajouterOM(om);
						pdf.fermerPDF();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}
		for (

		OrdreMission om : listeOm.getListeOM()) {
			listeOmVBox.getChildren().add(this.creerItemOM(om));
		}

	}

	private VBox creerItemOM(OrdreMission om) {
		VBox item = null;
		ItemMissionResponsableController ctrl;

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("ItemMissionResponsable.fxml"));
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

	public ListeOrdreMission getListeOm() {
		return this.listeOm;
	}

	public void setListeOm(ListeOrdreMission listeOm) {
		this.listeOm = listeOm;
	}

	public void setResponsableApp(ResponsableApp agentApp) {
		this.responsableApp = agentApp;
	}

	public void setMissionActive(OrdreMission om) {
		this.responsableApp.setMissionActive(om);
		for (ItemMissionResponsableController item : listeOmCtrl) {
			item.retirerStyle(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
		}

	}

	public ResponsableApp getAgentApp() {
		return this.responsableApp;
	}

	public ItemMissionResponsableController getItemOM(OrdreMission om) {
		for (ItemMissionResponsableController item : this.listeOmCtrl) {
			if (item.getOM().getNomOM().equals(om.getNomOM())) {
				return item;
			}
		}
		return null;
	}
}
