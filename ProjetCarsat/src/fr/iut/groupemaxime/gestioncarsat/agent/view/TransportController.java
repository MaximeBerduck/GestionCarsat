package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.File;
import java.util.Optional;

import fr.iut.groupemaxime.gestioncarsat.agent.model.Avion;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Bibliotheque;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Constante;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Train;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Transport;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Voiture;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TransportController {
	private OrdreMissionController mainApp;
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
	@FXML
	private Button boutonSigner;

	private boolean signatureAgent = false;

	public void initialize() {
		this.page.getChildren().removeAll(trainClasseHBox, cramcoVBox);
	}

	public void setMainApp(OrdreMissionController mainApp) {
		this.mainApp = mainApp;
	}

	public void retournerATypeOM() {
		this.mainApp.afficherFormTypeOM();
	}

	public void validerOM() {
		String erreur = "";
		if (avionRadioBtn.isSelected()) {
			erreur = getErreurAvion();
		} else if (voitureRadioBtn.isSelected()) {
			erreur = getErreurVoiture();
		} else {
			erreur = getErreurTrain();
		}
		if (erreur.length() > 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Champs non Valides");
			alert.setHeaderText("Des champs ne sont pas valides");
			alert.setContentText(erreur);

			alert.showAndWait();
		} else {
			this.mainApp.validerOrdreMission();
		}
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
		if (!this.page.getChildren().contains(detailsVoiture))
			this.page.getChildren().add(2, detailsVoiture);
	}

	public String getErreurTrain() {
		String erreur = "";
		if (!train1ereClasseRadioBtn.isSelected() && !train2emeClasseRadioBtn.isSelected()) {
			erreur += "Vous n'avez pas sélectionné la classe de votre billet! \n";
		}
		erreur += getErreurCRAMCO();
		return erreur;
	}

	public String getErreurCRAMCO() {
		String erreur = "";
		if (!cramcoNonRadioBtn.isSelected() && !cramcoOuiRadioBtn.isSelected()) {
			erreur += "Vous n'avez pas dit si la C.R.A.M.C.O a pris votre billet ! \n";
		}
		return erreur;
	}

	public String getErreurVoiture() {
		String erreur = "";
		if (null == typeVoitureTextField.getText() || 0 == typeVoitureTextField.getText().length()) {
			erreur += "Vous n'avez pas indiqué le type de la voiture! \n";
		}
		if (null == immatriculationTextField.getText() || 0 == immatriculationTextField.getText().length()) {
			erreur += "Vous n'avez pas indiqué le numéro d'immatriculation de la voiture!\n";
		}
		if (null == nbrCVTextField.getText() || 0 == nbrCVTextField.getText().length()) {
			erreur += "Vous n'avez pas indiqué le nombre de cheveaux de la voiture!\n";
		} else {
			try {
				Integer.parseInt(nbrCVTextField.getText());
			} catch (NumberFormatException e) {
				erreur += "Le champ nombre de CV est invalide (entrez un nombre entier)!\n";
			}
		}
		if (!vehiculePersoRadioBtn.isSelected() && !vehiculeServiceRadioBtn.isSelected()) {
			erreur += "Vous n'avez pas indiqué à qui appartient la voiture";
		}
		return erreur;
	}

	public String getErreurAvion() {
		String erreur = "";
		erreur += getErreurCRAMCO();
		return erreur;
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

	public void setChamps(Transport transport) {
		if (transport instanceof Avion) {
			this.avionRadioBtn.setSelected(true);
			this.AvionSelectionne();
			if ("oui".equals(((Avion) transport).getPrisParCRAMCO())) {
				this.cramcoOuiRadioBtn.setSelected(true);
			} else {
				this.cramcoNonRadioBtn.setSelected(true);
			}
		} else if (transport instanceof Train) {
			this.trainRadioBtn.setSelected(true);
			this.TrainSelectionne();
			if ("premiereClasse".equals(((Train) transport).getClasse())) {
				this.train1ereClasseRadioBtn.setSelected(true);
			} else {
				this.train2emeClasseRadioBtn.setSelected(true);
			}
			if ("oui".equals(((Train) transport).getPrisParCRAMCO())) {
				this.cramcoOuiRadioBtn.setSelected(true);
			} else {
				this.cramcoNonRadioBtn.setSelected(true);
			}
		} else {
			this.voitureRadioBtn.setSelected(true);
			this.VoitureSelectionne();
			this.typeVoitureTextField.setText(((Voiture) transport).getTypeVoiture());
			this.nbrCVTextField.setText(String.valueOf(((Voiture) transport).getNbrCV()));
			this.immatriculationTextField.setText(((Voiture) transport).getImmatriculation());
			if ("vehiculeService".equals(((Voiture) transport).getAppartenanceVehicule())) {
				this.vehiculeServiceRadioBtn.setSelected(true);
			} else {
				this.vehiculePersoRadioBtn.setSelected(true);
			}

		}

	}

	@FXML
	public void signerOM() {
		if (Bibliotheque.fichierExiste(this.mainApp.getOptions().getCheminSignature())) {
			this.signatureAgent = true;
			this.boutonSigner.setVisible(false);
		}
		else {
			TextInputDialog dialog = new TextInputDialog("");
			dialog.setTitle("Signature non renseignée");
			dialog.setHeaderText("Vous n'avez pas encore renseigné le chemin vers votre signature.");
			
			GridPane pageAlert = new GridPane();
			TextField tfCheminSignature = new TextField();
			tfCheminSignature.setPromptText("Chemin vers votre signature");
			
			Button boutonCheminSignature = new Button();
			boutonCheminSignature.setText("...");
			boutonCheminSignature.setOnAction(new EventHandler<ActionEvent>() {
				 
			    @Override
			    public void handle(ActionEvent event) {
			    	File cheminSignature = Bibliotheque.ouvrirFileChooser(Constante.IMAGE_FILTER);
			    	if(null != cheminSignature) {
			    		tfCheminSignature.setText(cheminSignature.toString());
			    	}
			    }
			});
			
			pageAlert.add(new Label("Chemin vers votre signature : "), 0, 0);
			pageAlert.add(tfCheminSignature, 1, 0);
			pageAlert.add(boutonCheminSignature,2,0);
			
			dialog.getDialogPane().setContent(pageAlert);
			dialog.showAndWait();
			
			if("".equals(tfCheminSignature.getText())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur chemin signature");
				alert.setHeaderText("Vous n'avez pas saisi l'adresse vers votre signature !");
				alert.showAndWait();
			}
			else {
				this.mainApp.getOptions().setCheminSignature(tfCheminSignature.getText());
				this.mainApp.getOptions().sauvegarderJson(Constante.CHEMIN_OPTIONS);
				this.signatureAgent = true;
				this.boutonSigner.setVisible(false);
			}
		}
	}

	public boolean agentSigne() {
		return this.signatureAgent;
	}
}
