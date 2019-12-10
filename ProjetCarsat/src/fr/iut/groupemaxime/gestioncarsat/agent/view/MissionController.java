package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import fr.iut.groupemaxime.gestioncarsat.agent.model.MissionTemporaire;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DateCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.LocalDateStringConverter;

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

	private OrdreMissionController mainApp;

	@FXML
	private Label labelDu;

	@FXML
	private Label labelAu;

	public void setMainApp(OrdreMissionController mainApp) {
		this.mainApp = mainApp;
	}

	public void retournerAInfoPerso() {
		this.mainApp.afficherFormInfoPerso();
	}

	public void passerAMoyenTransport() {
		String erreur;
		if (ordrePermanentRadioBtn.isSelected()) {
			erreur = getErreurMotifEtLieu();
		} else {
			erreur = getErreurDates() + getErreurMotifEtLieu();
		}
		if (erreur.length() > 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Champs non Valides");
			alert.setHeaderText("Des champs ne sont pas valides");
			alert.setContentText(erreur);

			alert.showAndWait();
		} else {
			this.mainApp.afficherFormMoyenTransport();
		}
	}

	private String getErreurDates() {
		String erreur = "";
		if (null == dateDebut.getValue())
			erreur += "Le champ date de debut est vide !\n";
		if (null == dateFin.getValue())
			erreur += "Le champ date de fin est vide !\n";
		return erreur;
	}

	private String getErreurMotifEtLieu() {
		String erreur = "";
		if (null == motifDeplacementTextField.getText() || 0 == motifDeplacementTextField.getText().length())
			erreur += "Le champ motif du deplacement est vide !\n";
		if (null == lieuDeplacementTextField.getText() || 0 == lieuDeplacementTextField.getText().length())
			erreur += "Le champ lieu du deplacement est vide !\n";
		return erreur;
	}

	public void ordrePermanentRadioBtnSelectionne() {
		if (this.pageVBox.getChildren().contains(missionPonctuelHBox))
			this.pageVBox.getChildren().remove(missionPonctuelHBox);
	}

	public void ordrePonctuelRadioBtnSelectionne() {
		if (!this.pageVBox.getChildren().contains(missionPonctuelHBox))
			this.pageVBox.getChildren().add(2, missionPonctuelHBox);
	}

	public void onDateDebutModifier() {
		Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						// Must call super
						super.updateItem(item, empty);

						if (item.isBefore(dateDebut.getValue())) {
							this.setDisable(true);
						}
					}
				};
			}
		};
		dateFin.setDayCellFactory(dayCellFactory);
	}

	public void onDateFinModifier() {
		Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						// Must call super
						super.updateItem(item, empty);

						if (item.isAfter(dateFin.getValue())) {
							this.setDisable(true);
						}
					}
				};
			}
		};
		dateDebut.setDayCellFactory(dayCellFactory);
	}

	public RadioButton getOrdrePermanentRadioBtn() {
		return ordrePermanentRadioBtn;
	}

	public RadioButton getOrdrePonctuelRadioBtn() {
		return ordrePonctuelRadioBtn;
	}

	public RadioButton getFonctionHabituelleRadioBtn() {
		return fonctionHabituelleRadioBtn;
	}

	public RadioButton getFormationRadioBtn() {
		return formationRadioBtn;
	}

	public TextField getMotifDeplacementTextField() {
		return motifDeplacementTextField;
	}

	public TextField getLieuDeplacementTextField() {
		return lieuDeplacementTextField;
	}

	public DatePicker getDateDebut() {
		return dateDebut;
	}

	public DatePicker getDateFin() {
		return dateFin;
	}

	public void setChamps(MissionTemporaire mission) {
		this.dateDebut.setValue(LocalDate.parse(mission.getDateDebut(), DateTimeFormatter.ofPattern("d/M/yyyy")));
		this.onDateDebutModifier();
		
		this.dateFin.setValue(LocalDate.parse(mission.getDateFin(), DateTimeFormatter.ofPattern("d/M/yyyy")));
		this.onDateFinModifier();
		
		this.lieuDeplacementTextField.setText(mission.getLieuDeplacement());
		this.motifDeplacementTextField.setText(mission.getMotifDeplacement());
		if ("formation".equals(mission.getTitre()))
			this.formationRadioBtn.setSelected(true);

	}

}
