package fr.iut.groupemaxime.gestioncarsat.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.iut.groupemaxime.gestioncarsat.MainApp;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;

public class MissionController {

	@FXML
	private FlowPane flowPane;

	@FXML
	private ToggleGroup typeOm;

	@FXML
	private ToggleGroup titre;

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

	@FXML
	private void initialize() {
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void passerAMoyenTransport() {
		this.mainApp.afficherFormMoyenTransport();
	}

	public void ordrePermanentRadioBtnSelectionne() {
		this.flowPane.getChildren().removeAll(labelDu, dateDebut, labelAu, dateFin);
	}

	
	public void ordrePonctuelRadioBtnSelectionne() {
		this.flowPane.getChildren().add(2, dateFin);
		this.flowPane.getChildren().add(2, labelAu);
		this.flowPane.getChildren().add(2, dateDebut);
		this.flowPane.getChildren().add(2, labelDu);

	}

}
