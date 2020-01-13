package fr.iut.groupemaxime.gestioncarsat.agent.view;

import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.Agent;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AgentController {

	
	@FXML
	private GridPane grilleTop;

	@FXML
	private TextField nomTextField;

	@FXML
	private TextField prenomTextField;

	@FXML
	private TextField fonctionTextField;

	@FXML
	private TextField residenceAdminTextField;

	@FXML
	private TextField uniteTavailTextField;

	@FXML
	private TextField codeAnalytiqueTextField;

	@FXML
	private TextField numCAPSSATextField;

	@FXML
	private TextField coefficientTextField;

	@FXML
	private Button suivantBtn;

	private OrdreMissionController mainApp;

	@FXML
	private void initialize() {

	}

	public void setMainApp(OrdreMissionController mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void passerATypeOM() {
		if (tousLesChampsValides())
			this.mainApp.afficherFormTypeOM();
	}

	private boolean tousLesChampsValides() {
		String erreur = "";
		if (nomTextField.getText() == null || nomTextField.getText().length() == 0)
			erreur += "Le champ nom est vide !\n";
		if (prenomTextField.getText() == null || prenomTextField.getText().length() == 0)
			erreur += "Le champ prenom est vide !\n";
		if (fonctionTextField.getText() == null || fonctionTextField.getText().length() == 0)
			erreur += "Le champ fonction est vide !\n";
		if (uniteTavailTextField.getText() == null || uniteTavailTextField.getText().length() == 0)
			erreur += "Le champ unite de travail est vide !\n";
		if (residenceAdminTextField.getText() == null || residenceAdminTextField.getText().length() == 0)
			erreur += "Le champ residence administrative est vide !\n";
		if (numCAPSSATextField.getText() == null || numCAPSSATextField.getText().length() == 0)
			erreur += "Le champ numero CAPSSA est vide !\n";
		else {
			try {
				Integer.parseInt(numCAPSSATextField.getText());
				if(numCAPSSATextField.getText().length() > Constante.TAILLE_NUMCAPSSA)
					erreur += "Le numéro CAPSSA ne doit pas dépasser " + Constante.TAILLE_NUMCAPSSA + " chiffres\n";
			} catch (NumberFormatException e) {
				erreur += "Le champ numero CAPSSA est invalide (entrez un nombre entier)!\n";
			}
		}
		if (coefficientTextField.getText() == null || coefficientTextField.getText().length() == 0)
			erreur += "Le champ coefficient est vide !\n";
		else {
			try {
				Integer.parseInt(coefficientTextField.getText());
			} catch (NumberFormatException e) {
				erreur += "Le champ coefficient est invalide (entrez un nombre entier)!\n";
			}
		}
		if (codeAnalytiqueTextField.getText() == null || codeAnalytiqueTextField.getText().length() == 0)
			erreur += "Le champ code analytique est vide !\n";
		else {
			try {
				Integer.parseInt(codeAnalytiqueTextField.getText());
				if(codeAnalytiqueTextField.getText().length() > Constante.TAILLE_CODE_ANALYTIQUE)
					erreur += "Le code analytique ne doit pas dépasser " + Constante.TAILLE_CODE_ANALYTIQUE + " chiffres\n";
			} catch (NumberFormatException e) {
				erreur += "Le champ code analytique est invalide (entrez un nombre entier)!\n";
			}
		}

		if (erreur.length() > 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Champs non Valides");
			alert.setHeaderText("Des champs ne sont pas valides");
			alert.setContentText(erreur);

			alert.showAndWait();
			return false;
		} else {
			return true;
		}

	}

	public TextField getNomTextField() {
		return nomTextField;
	}

	public TextField getPrenomTextField() {
		return prenomTextField;
	}

	public TextField getFonctionTextField() {
		return fonctionTextField;
	}

	public TextField getResidenceAdminTextField() {
		return residenceAdminTextField;
	}

	public TextField getUniteTavailTextField() {
		return uniteTavailTextField;
	}

	public TextField getCodeAnalytiqueTextField() {
		return codeAnalytiqueTextField;
	}

	public TextField getNumCAPSSATextField() {
		return numCAPSSATextField;
	}

	public TextField getCoefficientTextField() {
		return coefficientTextField;
	}
	
	

	public void setChamps(Agent agent) {
		if (-1 != agent.getNumCAPSSA()) {
			this.nomTextField.setText(agent.getNom());
			this.prenomTextField.setText(agent.getPrenom());
			this.fonctionTextField.setText(agent.getFonction());
			this.numCAPSSATextField.setText(String.valueOf(agent.getNumCAPSSA()));
			this.residenceAdminTextField.setText(agent.getResidenceAdmin());
			this.coefficientTextField.setText(String.valueOf(agent.getCoefficient()));
			this.uniteTavailTextField.setText(agent.getUniteTravail());
			this.codeAnalytiqueTextField.setText(String.valueOf(agent.getCodeAnalytique()));
		}
	}

}
