package fr.iut.groupemaxime.gestioncarsat.view;

import fr.iut.groupemaxime.gestioncarsat.MainApp;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RootLayoutController {
	private MainApp mainApp;

	@FXML
	private ImageView imageV;

	@FXML
	private void initialize() {
		this.imageV.setImage(new Image("file:target/images/Carsat_transparence.png"));
	}

	// Event Listener sur Btn Parametres
	@FXML
	public void modifierOptions() {
		mainApp.modifierOptions();
	}
	
	@FXML
	public void afficherFraisMission() {
		mainApp.afficherFraisMission();
	}
	
	@FXML
	public void afficherOrdresMission() {
		mainApp.afficherOrdresMission();
	}
	
	@FXML
	public void afficherHorairesTravail() {
		mainApp.afficherHorairesTravail();
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
