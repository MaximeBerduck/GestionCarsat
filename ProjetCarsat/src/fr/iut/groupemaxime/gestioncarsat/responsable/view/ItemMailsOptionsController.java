package fr.iut.groupemaxime.gestioncarsat.responsable.view;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

public class ItemMailsOptionsController {
	@FXML
	private Label adresseMail;

	private OptionsResponsableController optionCtrl;

	@FXML
	public void supprimer() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation de suppression");
		alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cette adresse mail : ");
		alert.setContentText(this.adresseMail.getText());

		ButtonType buttonTypeOk = new ButtonType("Supprimer", ButtonData.OK_DONE);
		ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(buttonTypeCancel, buttonTypeOk);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOk) {
			optionCtrl.supprimerMail(this.adresseMail.getText());
		} else {
			// l'utilisateur appuie sur le bouton annuler ou ferme la fenetre de dialog
		}
	}

	public void setAdresseMailResponsable(String mailResponsable) {
		this.adresseMail.setText(mailResponsable);
	}

	public void setOptionCtrl(OptionsResponsableController optionCtrl) {
		this.optionCtrl = optionCtrl;
	}
}
