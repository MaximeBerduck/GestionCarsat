package fr.iut.groupemaxime.gestioncarsat.responsable.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.form.PDF;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.ListeOrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.view.ItemOrdreMissionController;
import fr.iut.groupemaxime.gestioncarsat.agent.view.OrdreMissionController;
import fr.iut.groupemaxime.gestioncarsat.responsable.ResponsableApp;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import fr.iut.groupemaxime.gestioncarsat.utils.Options;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class ListeMissionsResponsableController {
	private ListeOrdreMission listeOm;

	@FXML
	private VBox listeOmVBox;

	private OrdreMissionController mainApp;
	private Options options;

	private ResponsableApp responsableApp;

	public void setMenuController(OrdreMissionController mainApp) {
		this.mainApp = mainApp;
	}

	public void setOptions(Options options) {
		this.options = options;
	}

	public void chargerOM() {
		listeOm = new ListeOrdreMission();
		listeOm.chargerOMMail(options);
		File dossier = new File(options.getCheminOM() + "responsable/");
		File[] pdfs = dossier.listFiles();
		PDF pdf;
		for (File file : pdfs) {
			File filee = new File(file.getAbsolutePath() + "/OM_" + file.getName() + Constante.EXTENSION_PDF);
			try {
				pdf = new PDF(filee);
				OrdreMission om = pdf.chargerPDFtoOM();
				om.setCheminDossier(file.getAbsolutePath());
				om.setNomOM(filee.getAbsolutePath().substring(filee.getAbsolutePath().lastIndexOf('\\')));
				listeOm.ajouterOM(om);
				pdf.fermerPDF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (OrdreMission om : listeOm.getListeOM()) {
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
	}

	public ResponsableApp getAgentApp() {
		return this.responsableApp;
	}
}
