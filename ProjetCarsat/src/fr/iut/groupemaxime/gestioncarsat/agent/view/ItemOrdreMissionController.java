package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.form.PDF;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Bibliotheque;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Constante;
import fr.iut.groupemaxime.gestioncarsat.agent.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.agent.model.OrdreMission;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ItemOrdreMissionController {
	@FXML
	private Label lieuLabel;
	@FXML
	private Label datesLabel;
	@FXML
	private VBox itemMission;
	@FXML
	private Button boutonSigner;
	@FXML
	private Button boutonEnvoyer;

	private OrdreMission om;

	private OrdreMissionController mainApp;
	
	private AgentApp agentApp;
	private MenuAgentController menuAgentCtrl;

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
						menuAgentCtrl.getAgentApp().getOptions().getCheminSignature());
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
	
	@FXML
	public void signerOM() {
		if (Bibliotheque.fichierExiste(this.mainApp.getOptions().getCheminSignature())) {
			this.om.setSignatureAgent(true);
			this.boutonSigner.setVisible(false);
			this.boutonEnvoyer.setVisible(true);
			this.om.sauvegarderJson(this.om.getCheminDossier());
		}
		else {
			TextInputDialog dialog = new TextInputDialog("");
			dialog.setTitle("Signature non renseignée");
			dialog.setHeaderText("Vous n'avez pas encore renseigné le chemin vers votre signature.");
			
			GridPane pageAlert = new GridPane();
			TextField tfCheminSignature = new TextField();
			tfCheminSignature.setPromptText("Chemin vers votre signature");
			
			Button boutonCheminSignature = new Button();
			boutonCheminSignature.setText("...");
			boutonCheminSignature.setOnAction(new EventHandler<ActionEvent>() {
				 
			    @Override
			    public void handle(ActionEvent event) {
			    	File cheminSignature = Bibliotheque.ouvrirFileChooser(Constante.IMAGE_FILTER);
			    	if(null != cheminSignature) {
			    		tfCheminSignature.setText(cheminSignature.toString());
			    	}
			    }
			});
			
			pageAlert.add(new Label("Chemin vers votre signature : "), 0, 0);
			pageAlert.add(tfCheminSignature, 1, 0);
			pageAlert.add(boutonCheminSignature,2,0);
			
			dialog.getDialogPane().setContent(pageAlert);
			dialog.showAndWait();
			
			if("".equals(tfCheminSignature.getText())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur chemin signature");
				alert.setHeaderText("Vous n'avez pas saisi l'adresse vers votre signature !");
				alert.showAndWait();
			}
			else {
				this.mainApp.getOptions().setCheminSignature(tfCheminSignature.getText());
				this.mainApp.getOptions().sauvegarderJson(Constante.CHEMIN_OPTIONS);
				this.om.setSignatureAgent(true);
				this.boutonSigner.setVisible(false);
				this.boutonEnvoyer.setVisible(true);
				this.om.sauvegarderJson(this.mainApp.getOptions().getCheminOM());
			}
		}
	}
	
	@FXML
	public void choisirMission() {
		this.menuAgentCtrl.setMissionActive(this.om);
	}

	public void chargerOM(OrdreMission om) {
		this.om = om;
		this.lieuLabel.setText(((MissionTemporaire) om.getMission()).getLieuDeplacement());
		this.datesLabel.setText("Du " + ((MissionTemporaire) om.getMission()).getDateDebut() + " au "
				+ ((MissionTemporaire) om.getMission()).getDateFin());
		if(om.agentSigne()) {
			this.boutonSigner.setVisible(false);
			this.boutonEnvoyer.setVisible(true);
		}
		else {
			this.boutonSigner.setVisible(true);
			this.boutonEnvoyer.setVisible(false);
		}

	}

	public void setMainApp(OrdreMissionController mainApp) {
		this.mainApp = mainApp;
	}
	
	public void setMenuAgent(MenuAgentController menuAgentCtrl) {
		this.menuAgentCtrl = menuAgentCtrl;
	}
}
