package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.IOException;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.model.ListeOrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Options;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class FraisMissionController {

	@FXML
	private SplitPane fraisMissionSplit;
	private AnchorPane pageDate;
	private DateFMController dateController;

	private AnchorPane pageLogement;
	private LogementFMController logementController;

	private TransportFMController transportController;
	private SplitPane pageTransport;

	private AgentApp agentApp;
	private Options options;

	@FXML
	private void initialize() {
	}

	public void afficherFMDate() {
		if (this.pageDate != null) {
			if (0 < this.fraisMissionSplit.getItems().size())
				this.fraisMissionSplit.getItems().set(0, this.pageDate);
			else
				this.fraisMissionSplit.getItems().add(0, this.pageDate);
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(this.getClass().getResource("FraisDate.fxml"));
				this.pageDate = loader.load();

				this.dateController = loader.getController();
				this.dateController.setFraisMissionController(this);

				if (0 < this.fraisMissionSplit.getItems().size())
					this.fraisMissionSplit.getItems().set(0, this.pageDate);
				else
					this.fraisMissionSplit.getItems().add(0, this.pageDate);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void afficherFMLogement() {
		if (this.pageLogement != null) {
			if (0 < this.fraisMissionSplit.getItems().size())
				this.fraisMissionSplit.getItems().set(0, this.pageLogement);
			else
				this.fraisMissionSplit.getItems().add(0, this.pageLogement);
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(this.getClass().getResource("FraisLogement.fxml"));
				this.pageLogement = loader.load();

				this.logementController = loader.getController();
				this.logementController.setFraisMissionController(this);
				if (0 < this.fraisMissionSplit.getItems().size())
					this.fraisMissionSplit.getItems().set(0, this.pageLogement);
				else
					this.fraisMissionSplit.getItems().add(0, this.pageLogement);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void afficherFMTransport() {
		if (this.pageTransport != null) {
			if (0 < this.fraisMissionSplit.getItems().size())
				this.fraisMissionSplit.getItems().set(0, this.pageTransport);
			else
				this.fraisMissionSplit.getItems().add(0, this.pageTransport);
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(this.getClass().getResource("FraisTransport.fxml"));
				this.pageLogement = loader.load();

				this.transportController = loader.getController();
				this.transportController.setFraisMissionController(this);

				if (0 < this.fraisMissionSplit.getItems().size())
					this.fraisMissionSplit.getItems().set(0, this.pageTransport);
				else
					this.fraisMissionSplit.getItems().add(0, this.pageTransport);

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
