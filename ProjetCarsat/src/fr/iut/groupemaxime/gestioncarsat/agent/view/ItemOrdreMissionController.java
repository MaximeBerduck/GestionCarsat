package fr.iut.groupemaxime.gestioncarsat.agent.view;

import fr.iut.groupemaxime.gestioncarsat.agent.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.agent.model.OrdreMission;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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
