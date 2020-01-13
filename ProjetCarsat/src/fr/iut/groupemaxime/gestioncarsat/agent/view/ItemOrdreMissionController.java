package fr.iut.groupemaxime.gestioncarsat.agent.view;

import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ItemOrdreMissionController {
	@FXML
	private Label lieuLabel;
	@FXML
	private Label datesLabel;
	@FXML
	private VBox itemMission;

	private OrdreMission om;

	private MenuAgentController menuAgentCtrl;

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

	public void chargerOM(OrdreMission om) {
		this.om = om;
		this.lieuLabel.setText(((MissionTemporaire) om.getMission()).getLieuDeplacement());
		this.datesLabel.setText("Du " + ((MissionTemporaire) om.getMission()).getDateDebut() + " au "
				+ ((MissionTemporaire) om.getMission()).getDateFin());

	}

	public void setMenuAgent(MenuAgentController menuAgentCtrl) {
		this.menuAgentCtrl = menuAgentCtrl;
	}
}
