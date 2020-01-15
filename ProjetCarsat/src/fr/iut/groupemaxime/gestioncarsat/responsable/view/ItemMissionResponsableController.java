package fr.iut.groupemaxime.gestioncarsat.responsable.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ItemMissionResponsableController {
	@FXML
	private Label lieuLabel;
	@FXML
	private Label datesLabel;
	@FXML
	private VBox itemMission;
	@FXML
	private Label nomLabel;

	private OrdreMission om;

	private AgentApp agentApp;
	private ListeMissionsResponsableController menuAgentCtrl;

	@FXML
	private void initialize() {

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
		this.nomLabel.setText(om.getAgent().getPrenom() + " " + om.getAgent().getNom());

	}

	public void setMenuAgent(ListeMissionsResponsableController menuAgentCtrl) {
		this.menuAgentCtrl = menuAgentCtrl;
	}
	
	public void afficherPDF() {
		try {
			
			Desktop.getDesktop().browse(new File(
					this.om.getCheminDossier() + this.om.getNomOM())
							.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
