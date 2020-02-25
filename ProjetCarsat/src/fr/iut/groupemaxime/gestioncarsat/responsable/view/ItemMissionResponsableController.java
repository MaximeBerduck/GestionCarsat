package fr.iut.groupemaxime.gestioncarsat.responsable.view;

import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
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

	private ListeMissionsResponsableController menuAgentCtrl;

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
	
	@FXML
	public void choisirMission() {
		this.menuAgentCtrl.setMissionActive(this.om);
		this.ajouterStyle(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
	}

	public void ajouterStyle(String style) {
		this.itemMission.setStyle(this.itemMission.getStyle() + style);
	}

	public void retirerStyle(String style) {
		this.itemMission.setStyle(this.itemMission.getStyle().replace(style, ""));

	}
	
	public void setMissionActive(OrdreMission om) {
		this.menuAgentCtrl.setMissionActive(om);
	}
}
