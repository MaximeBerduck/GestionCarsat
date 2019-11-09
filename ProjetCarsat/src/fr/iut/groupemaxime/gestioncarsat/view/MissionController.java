package fr.iut.groupemaxime.gestioncarsat.view;

import fr.iut.groupemaxime.gestioncarsat.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MissionController {

	@FXML
	private VBox pageVBox;

	@FXML
	private HBox missionPonctuelHBox;

	@FXML
	private ToggleGroup typeOmToggleG;

	@FXML
	private ToggleGroup titreToggleG;

	@FXML
	private RadioButton ordrePermanentRadioBtn;

	@FXML
	private RadioButton ordrePonctuelRadioBtn;

	@FXML
	private RadioButton fonctionHabituelleRadioBtn;

	@FXML
	private RadioButton formationRadioBtn;

	@FXML
	private TextField motifDeplacementTextField;

	@FXML
	private TextField lieuDeplacementTextField;

	@FXML
	private DatePicker dateDebut;

	@FXML
	private DatePicker dateFin;

	private MainApp mainApp;

	@FXML
	private Label labelDu;

	@FXML
	private Label labelAu;


	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void passerAMoyenTransport() {
		this.mainApp.afficherFormMoyenTransport();
	}

	public void ordrePermanentRadioBtnSelectionne() {
		if (this.pageVBox.getChildren().contains(missionPonctuelHBox))
			this.pageVBox.getChildren().remove(missionPonctuelHBox);
	}

	public void ordrePonctuelRadioBtnSelectionne() {
		if (!this.pageVBox.getChildren().contains(missionPonctuelHBox))
			this.pageVBox.getChildren().add(2, missionPonctuelHBox);
	}

}
