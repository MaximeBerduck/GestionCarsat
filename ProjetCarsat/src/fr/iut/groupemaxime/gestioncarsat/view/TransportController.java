package fr.iut.groupemaxime.gestioncarsat.view;

import fr.iut.groupemaxime.gestioncarsat.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TransportController {
	private MainApp mainApp;
	// menu choix moyen transport
	@FXML
	private RadioButton voitureRadioBtn;

	@FXML
	private RadioButton trainRadioBtn;

	@FXML
	private RadioButton avionRadioBtn;

	// train detail
	@FXML
	private HBox trainClasseHBox;

	@FXML
	private RadioButton train1ereClasseRadioBtn;

	@FXML
	private RadioButton train2emeClasseRadioBtn;

	// choix cramco
	@FXML
	private VBox cramcoVBox;

	@FXML
	private RadioButton cramcoOuiRadioBtn;

	@FXML
	private RadioButton cramcoNonRadioBtn;

	@FXML
	private HBox cramcoHBox;
	
	@FXML
	private RadioButton cramcoAutreRadioBtn;

	@FXML
	private TextField cramcoAutreTextField;

	// details voiture
	@FXML
	private GridPane detailsVoiture;

	@FXML
	private RadioButton vehiculeServiceRadioBtn;

	@FXML
	private RadioButton vehiculePersoRadioBtn;

	@FXML
	private TextField typeVoitureTextField;

	@FXML
	private TextField immatriculationTextField;

	@FXML
	private TextField nbrCVTextField;

	@FXML
	private VBox page;

	public void initialize() {
		this.page.getChildren().removeAll(trainClasseHBox, cramcoVBox);
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	public void retournerATypeOM() {
		this.mainApp.afficherFormTypeOM();
	}
	
	public void validerOM() {
		this.mainApp.validerOrdreMission();
	}

	public void AvionSelectionne() {
		this.page.getChildren().removeAll(detailsVoiture, trainClasseHBox);
		if (!this.page.getChildren().contains(cramcoVBox))
			this.page.getChildren().add(2, cramcoVBox);

	}

	public void TrainSelectionne() {
		this.page.getChildren().remove(detailsVoiture);
		if (!this.page.getChildren().contains(cramcoVBox))
			this.page.getChildren().add(2, cramcoVBox);
		this.page.getChildren().add(2, trainClasseHBox);
	}

	public void VoitureSelectionne() {
		this.page.getChildren().removeAll(cramcoVBox, trainClasseHBox);
		this.page.getChildren().add(2, detailsVoiture);
	}
	
	public void ouiOuNonSelectionne() {
		if (this.cramcoHBox.getChildren().contains(cramcoAutreTextField)) {
			this.cramcoHBox.getChildren().remove(cramcoAutreTextField);
		}		
	}
	
	public void autreSelectionne() {
		this.cramcoHBox.getChildren().add(cramcoAutreTextField);

	}

	public RadioButton getVoitureRadioBtn() {
		return voitureRadioBtn;
	}

	public RadioButton getTrainRadioBtn() {
		return trainRadioBtn;
	}

	public RadioButton getAvionRadioBtn() {
		return avionRadioBtn;
	}

	public HBox getTrainClasseHBox() {
		return trainClasseHBox;
	}

	public RadioButton getTrain1ereClasseRadioBtn() {
		return train1ereClasseRadioBtn;
	}

	public RadioButton getTrain2emeClasseRadioBtn() {
		return train2emeClasseRadioBtn;
	}

	public RadioButton getCramcoOuiRadioBtn() {
		return cramcoOuiRadioBtn;
	}

	public RadioButton getCramcoNonRadioBtn() {
		return cramcoNonRadioBtn;
	}

	public RadioButton getCramcoAutreRadioBtn() {
		return cramcoAutreRadioBtn;
	}

	public TextField getCramcoAutreTextField() {
		return cramcoAutreTextField;
	}

	public RadioButton getVehiculeServiceRadioBtn() {
		return vehiculeServiceRadioBtn;
	}

	public RadioButton getVehiculePersoRadioBtn() {
		return vehiculePersoRadioBtn;
	}

	public TextField getTypeVoitureTextField() {
		return typeVoitureTextField;
	}

	public TextField getImmatriculationTextField() {
		return immatriculationTextField;
	}

	public TextField getNbrCVTextField() {
		return nbrCVTextField;
	}
	
}
