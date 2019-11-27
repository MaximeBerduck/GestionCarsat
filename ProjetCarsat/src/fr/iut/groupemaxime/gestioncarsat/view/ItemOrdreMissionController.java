package fr.iut.groupemaxime.gestioncarsat.view;

import javafx.fxml.FXML;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import fr.iut.groupemaxime.gestioncarsat.form.PDF;
import fr.iut.groupemaxime.gestioncarsat.model.Constante;
import fr.iut.groupemaxime.gestioncarsat.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.model.OrdreMission;
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
			pdf.fermerPDF();
			Desktop.getDesktop().browse(
					new File(Constante.CHEMIN_PDF + om.getFichier() + Constante.EXTENSION_PDF).toURI());
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
	public void supprimerOM(ActionEvent event) {
		this.mainApp.enleverOm(this.om);
	}

	public void chargerOM(OrdreMission om) {
		this.om = om;
		this.lieuLabel.setText(((MissionTemporaire) om.getMission()).getLieuDeplacement());
		this.datesLabel.setText("Du " + ((MissionTemporaire) om.getMission()).getDateDebut() + " au "
				+ ((MissionTemporaire) om.getMission()).getDateFin());

	}
	
	public void setMainApp(OrdreMissionController mainApp) {
		this.mainApp=mainApp;
	}
}
