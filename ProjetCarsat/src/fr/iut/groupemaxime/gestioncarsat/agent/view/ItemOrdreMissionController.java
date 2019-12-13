package fr.iut.groupemaxime.gestioncarsat.agent.view;

import javafx.fxml.FXML;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import fr.iut.groupemaxime.gestioncarsat.agent.form.PDF;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Constante;
import fr.iut.groupemaxime.gestioncarsat.agent.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.agent.model.OrdreMission;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ItemOrdreMissionController {
	@FXML
	private Label lieuLabel;
	@FXML
	private Label datesLabel;
	@FXML
	private VBox boite;

	private OrdreMission om;

	private OrdreMissionController mainApp;

	@FXML
	private void initialize() {

	}

	// Event Listener on Button.onAction
	@FXML
	public void afficherOrdreMissionPDF(ActionEvent event) {
		PDF pdf;
		try {
			pdf = new PDF(new File(Constante.CHEMIN_PDF_VIDE));
			pdf.remplirPDF(om);
			pdf.sauvegarderPDF();
			if (om.estSigne()) {
				PDF.signerPDF(Constante.SIGNATURE_AGENT_X, Constante.SIGNATURE_AGENT_Y, Constante.TAILLE_SIGNATURE, om,
						mainApp.getOptions().getCheminSignature());
			}
			pdf.fermerPDF();
			Desktop.getDesktop()
					.browse(new File(om.getCheminDossier() + om.getNomOM() + Constante.EXTENSION_PDF).toURI());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	// Event Listener on Button.onAction
	@FXML
	public void modifierOM(ActionEvent event) {
		this.mainApp.modifierOm(this.om);
	}

	// Event Listener on Button.onAction
	@FXML
	public void afficherEnvoiMail(ActionEvent event) {
		this.mainApp.afficherEnvoiDuMail();
	}

	public void chargerOM(OrdreMission om) {
		this.om = om;
		this.lieuLabel.setText(((MissionTemporaire) om.getMission()).getLieuDeplacement());
		this.datesLabel.setText("Du " + ((MissionTemporaire) om.getMission()).getDateDebut() + " au "
				+ ((MissionTemporaire) om.getMission()).getDateFin());

	}

	public void setMainApp(OrdreMissionController mainApp) {
		this.mainApp = mainApp;
	}
}
