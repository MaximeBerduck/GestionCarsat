package fr.iut.groupemaxime.gestioncarsat.agent.view;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Frais1Controller {
	@FXML
	private Label dateJournée;
	@FXML
	private TextField heureDepart;
	@FXML
	private TextField minDepart;
	@FXML
	private TextField heureRetour;
	@FXML
	private TextField minRetour;
	@FXML
	private TextField nbrForfaitRepas;
	@FXML
	private TextField nbrJustificatifRepas;
	@FXML
	private TextField nbrForfaitDecouchers;
	@FXML
	private TextField nbrJustifDecouchers;

	private AnchorPane pageFrais1;

	private FraisMissionController fmController;

	public void setPageFrais1(AnchorPane pageFrais1) {
		this.pageFrais1 = pageFrais1;
	}

	public void setDateJournee(String date) {
		this.dateJournée.setText(date);
	}

	// Event Listener on Button.onAction
	@FXML
	public void afficherFrais2(ActionEvent event) {
		this.fmController.afficherFrais2(dateJournée.getText());
	}

	public AnchorPane getPage() {
		return this.pageFrais1;
	}

	public void setFmController(FraisMissionController fmController) {
		this.fmController = fmController;
	}
}
