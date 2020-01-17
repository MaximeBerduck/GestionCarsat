package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.sun.javafx.scene.traversal.Hueristic2D;

import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.MissionTemporaire;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

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

	@FXML
	private TextField heureDepart;

	@FXML
	private TextField minuteDepart;

	@FXML
	private TextField heureRetour;

	@FXML
	private TextField minuteRetour;

	private OrdreMissionController mainApp;

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
			erreur = getErreurDatesHeures() + getErreurMotifEtLieu();
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

	private String getErreurDatesHeures() {
		String erreur = "";

		if (null == dateDebut.getValue())
			erreur += "Le champ date de debut est vide !\n";

		if ("".equals(heureDepart.getText())) {
			erreur += "Le champs heure de départ est vide !\n";
		} else {
			try {
				if (Integer.valueOf(heureDepart.getText()) < 0 || Integer.valueOf(heureDepart.getText()) > 23) {
					erreur += "Le champs heure de départ est invalide !\n";
				}
			} catch (NumberFormatException e) {
				erreur += "Le champs heure de départ est invalide !\n";
			}

		}
		if ("".equals(minuteDepart.getText())) {
			erreur += "Le champs minute de départ est vide !\n";
		} else {
			try {
				if (Integer.valueOf(minuteDepart.getText()) < 0 || Integer.valueOf(minuteDepart.getText()) > 59) {
					erreur += "Le champs minute de départ est invalide !\n";
				}
			} catch (NumberFormatException e) {
				erreur += "Le champs heure de départ est invalide !\n";
			}

		}

		if (null == dateFin.getValue())
			erreur += "Le champ date de fin est vide !\n";
		if ("".equals(heureRetour.getText())) {
			erreur += "Le champs heure de retour est vide !\n";
		} else {
			try {
				if (Integer.valueOf(heureRetour.getText()) < 0 || Integer.valueOf(heureRetour.getText()) > 23) {
					erreur += "Le champs heure de retour est invalide !\n";
				}
			} catch (NumberFormatException e) {
				erreur += "Le champs heure de retour est invalide !\n";
			}

		}
		if ("".equals(minuteRetour.getText())) {
			erreur += "Le champs minute de retour est vide !\n";
		} else {
			try {
				if (Integer.valueOf(minuteRetour.getText()) < 0 || Integer.valueOf(minuteRetour.getText()) > 59) {
					erreur += "Le champs minute de retour est invalide !\n";
				}
			} catch (NumberFormatException e) {
				erreur += "Le champs minute de retour est invalide !\n";
			}

		}
		if (erreur.equals("")) {
			LocalDateTime depart = LocalDateTime.of(dateDebut.getValue().getYear(), dateDebut.getValue().getMonth(), dateDebut.getValue().getDayOfMonth(),Integer.valueOf(heureDepart.getText()), Integer.valueOf(minuteDepart.getText()));
			LocalDateTime retour = LocalDateTime.of(dateFin.getValue().getYear(), dateFin.getValue().getMonth(), dateFin.getValue().getDayOfMonth(),Integer.valueOf(heureRetour.getText()), Integer.valueOf(minuteRetour.getText()));
			if (depart.isAfter(retour)) {
				erreur = "L'heure de retour est avant l'heure de départ !\n";
			}
		}
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
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
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
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
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

	public TextField getHeureDepart() {
		return heureDepart;
	}

	public TextField getMinuteDepart() {
		return minuteDepart;
	}

	public TextField getHeureRetour() {
		return heureRetour;
	}

	public TextField getMinuteRetour() {
		return minuteRetour;
	}

	public void setChamps(MissionTemporaire mission) {
		this.dateDebut.setValue(LocalDate.parse(mission.getDateDebut(), DateTimeFormatter.ofPattern("d/M/yyyy")));
		this.onDateDebutModifier();
		
		this.heureDepart.setText(mission.getHeureDebut().substring(0,mission.getHeureDebut().lastIndexOf(":")));
		this.minuteDepart.setText(mission.getHeureDebut().substring(mission.getHeureDebut().lastIndexOf(":")+1));

		this.dateFin.setValue(LocalDate.parse(mission.getDateFin(), DateTimeFormatter.ofPattern("d/M/yyyy")));
		this.onDateFinModifier();

		this.heureRetour.setText(mission.getHeureFin().substring(0,mission.getHeureFin().lastIndexOf(":")));
		this.minuteRetour.setText(mission.getHeureFin().substring(mission.getHeureFin().lastIndexOf(":")+1));
		
		this.lieuDeplacementTextField.setText(mission.getLieuDeplacement());
		this.motifDeplacementTextField.setText(mission.getMotifDeplacement());
		if ("formation".equals(mission.getTitre()))
			this.formationRadioBtn.setSelected(true);

	}

}
