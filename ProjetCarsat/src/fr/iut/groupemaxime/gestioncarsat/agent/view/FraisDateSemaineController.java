package fr.iut.groupemaxime.gestioncarsat.agent.view;

import javafx.fxml.FXML;

import javafx.scene.control.Label;

public class FraisDateSemaineController {
	@FXML
	private Label labelDateDebut;
	@FXML
	private Label labelDateFin;

	public void setLabelDateDebut(String date) {
		this.labelDateDebut.setText(date);
	}

	public void setLabelDateFin(String date) {
		this.labelDateFin.setText(date);
	}

}
