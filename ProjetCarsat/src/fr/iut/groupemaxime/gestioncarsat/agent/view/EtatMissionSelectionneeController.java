package fr.iut.groupemaxime.gestioncarsat.agent.view;

import javafx.fxml.FXML;

import javafx.scene.control.Label;

public class EtatMissionSelectionneeController {
	@FXML
	private Label etatOM;
	@FXML
	private Label etatFM;
	@FXML
	private Label etatHT;

	public void setEtatOM(String etat) {
		this.etatOM.setText(etat);
	}

	public void setEtatFM(String etat) {
		this.etatFM.setText(etat);
	}

	public void setEtatHT(String etat) {
		this.etatHT.setText(etat);
	}
}
