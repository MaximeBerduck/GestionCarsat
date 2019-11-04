package fr.iut.groupemaxime.gestioncarsat.view;

import fr.iut.groupemaxime.gestioncarsat.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class OrdreMissionInfoPersoController {

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

	private MainApp mainApp;

	@FXML
	private void initialize() {

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void passerATypeOM() {
		if (unDesChampsNEstPasRempli()) {
			Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Champs non remplis");
            alert.setHeaderText("Des champs ne sont pas remplis");
            alert.setContentText("Remplissez tous les champs avant de pouvoir continuer");
            
            alert.showAndWait();
		} else if(true){
			
		} else {
			this.mainApp.afficherFormTypeOM();
		}
	}

	private boolean unDesChampsNEstPasRempli() {
		return "".equals(nomTextField.getText()) 
				|| "".equals(prenomTextField.getText())
				|| "".equals(fonctionTextField.getText()) 
				|| "".equals(residenceAdminTextField.getText())
				|| "".equals(uniteTavailTextField.getText()) 
				|| "".equals(codeAnalytiqueTextField.getText())
				|| "".equals(numCAPSSATextField.getText()) 
				|| "".equals(coefficientTextField.getText());
	}

}
