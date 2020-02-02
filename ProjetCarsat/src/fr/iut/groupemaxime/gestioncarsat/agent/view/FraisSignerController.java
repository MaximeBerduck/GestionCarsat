package fr.iut.groupemaxime.gestioncarsat.agent.view;

import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class FraisSignerController {
	@FXML
	private CheckBox informationsCheck;
	@FXML
	private CheckBox deduireFraisCheck;
	@FXML
	private TextField montantDeductionFrais;
	@FXML
	private CheckBox avanceCheck;
	@FXML
	private TextField montantAvance;
	@FXML
	private CheckBox repasCheck;
	@FXML
	private TextField nbrRepasMidiOfferts;

	private FraisMissionController fmCtrl;
	private OrdreMission mission;

	// Event Listener on Button.onAction
	@FXML
	public void validerInformations(ActionEvent event) {
		float montantDeductionFrais = 0;
		float montantAvance = 0;
		int nbrRepasMidiOfferts = 0;

		if (!informationsCheck.isSelected()) {
			this.afficherErreur();
		} else if (!deduireFraisCheck.isSelected()) {
			this.afficherErreur();
		} else if (!avanceCheck.isSelected()) {
			this.afficherErreur();
		} else if (!repasCheck.isSelected()) {
			this.afficherErreur();
		} else {
			try {
				montantDeductionFrais = Float.parseFloat(this.montantDeductionFrais.getText());
				montantAvance = Float.parseFloat(this.montantAvance.getText());
				nbrRepasMidiOfferts = Integer.parseInt(this.nbrRepasMidiOfferts.getText());

				fmCtrl.signerFM(this.mission, montantDeductionFrais, montantAvance, nbrRepasMidiOfferts);
			} catch (NumberFormatException e) {
				this.afficherErreur();
			}
		}
	}

	public void afficherErreur() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erreur");
		alert.setHeaderText("Vous ne pouvez pas valider les informations");
		alert.setContentText("Toutes les cases doivent être cochées et les montants doivent être des nombres!");

		alert.showAndWait();
	}

	public void setFmCtrl(FraisMissionController fmCtrl) {
		this.fmCtrl = fmCtrl;
	}

	public void setMission(OrdreMission mission) {
		this.mission = mission;
	}
}
