package fr.iut.groupemaxime.gestioncarsat.agent.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SaisieMailController {
	@FXML
	private TextField mailAgent;
	@FXML
	private Label hostname;
	@FXML
	private Button buttonValider;
	@FXML
	private Button boutonAnnuler;
	private Stage fenetre;

	// Event Listener on Button[#buttonValider].onAction
	@FXML
	public void valider(ActionEvent event) {
		// TODO Autogenerated
		if (null == getMailAgent()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Vous n'avez pas saisi votre adresse.");
			alert.setContentText("Veuillez saisir votre adresse mail avant de confirmer.");

			alert.showAndWait();
		} else {
			fenetre.close();
		}
	}

	// Event Listener on Button[#boutonAnnuler].onAction
	@FXML
	public void annuler(ActionEvent event) {
		// TODO Autogenerated
		fenetre.close();
	}

	public String getMailAgent() {
		return this.mailAgent.getText();
	}

	public void setStage(Stage fenetre) {
		this.fenetre = fenetre;
	}

	public void setHostname(String hostname) {
		this.hostname.setText("@" + hostname);
	}
}
