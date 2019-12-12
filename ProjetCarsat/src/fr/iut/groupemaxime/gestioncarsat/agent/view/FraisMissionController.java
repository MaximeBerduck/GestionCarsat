package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.IOException;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.model.ListeOrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Options;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

public class FraisMissionController {

	@FXML
	private SplitPane fraisMissionSplit;
	private AnchorPane pageDate;
	private DateFMController dateController;
	private AgentApp agentApp;
	private Options options;

	@FXML
	private void initialize() {
	}

	public void afficherFMDate() {
		if (this.pageDate != null) {
			if (1 < this.fraisMissionSplit.getItems().size())
				this.fraisMissionSplit.getItems().set(0, this.pageDate);
			else
				this.fraisMissionSplit.getItems().add(0, this.pageDate);
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(this.getClass().getResource("FraisDate.fxml"));
				this.pageDate = loader.load();

				this.dateController = loader.getController();
				this.dateController.setMainApp(this);

				if (1 < this.fraisMissionSplit.getItems().size())
					this.fraisMissionSplit.getItems().set(0, this.pageDate);
				else
					this.fraisMissionSplit.getItems().add(0, this.pageDate);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void setMainApp(AgentApp agentApp) {
		this.agentApp = agentApp;
	}

	public void setOptions(Options options) {
		this.options = options;
	}
}
