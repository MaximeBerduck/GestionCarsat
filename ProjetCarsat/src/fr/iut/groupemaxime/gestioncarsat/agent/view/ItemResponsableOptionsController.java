package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

public class ItemResponsableOptionsController {
	@FXML
	private Label adresseMailResponsable;

	private OptionsController optionCtrl;

	@FXML
	public void supprimerResponsable() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation de suppression");
		alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cette adresse mail : ");
		alert.setContentText(this.adresseMailResponsable.getText());

		ButtonType buttonTypeOk = new ButtonType("Supprimer", ButtonData.OK_DONE);
		ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(buttonTypeCancel, buttonTypeOk);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOk) {
			optionCtrl.supprimerResponsable(this.adresseMailResponsable.getText());
		} else {
			// l'utilisateur appuie sur le bouton annuler ou ferme la fenetre de dialog
		}
	}

	public void setAdresseMailResponsable(String mailResponsable) {
		this.adresseMailResponsable.setText(mailResponsable);
	}

	public void setOptionCtrl(OptionsController optionCtrl) {
		this.optionCtrl = optionCtrl;
	}
}
