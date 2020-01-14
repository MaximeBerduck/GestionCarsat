package fr.iut.groupemaxime.gestioncarsat.agent.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ItemResponsableOptionsController {
	@FXML
	private Label adresseMailResponsable;

	private OptionsController optionCtrl;

	@FXML
	public void supprimerResponsable() {
		// TODO
		optionCtrl.supprimerResponsable(this.adresseMailResponsable.getText());
	}

	public void setAdresseMailResponsable(String mailResponsable) {
		this.adresseMailResponsable.setText(mailResponsable);
	}

	public void setOptionCtrl(OptionsController optionCtrl) {
		this.optionCtrl = optionCtrl;
	}
}
