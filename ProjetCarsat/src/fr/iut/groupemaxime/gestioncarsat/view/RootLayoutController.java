package fr.iut.groupemaxime.gestioncarsat.view;

import fr.iut.groupemaxime.gestioncarsat.MainApp;
import fr.iut.groupemaxime.gestioncarsat.model.Constante;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RootLayoutController {
	private MainApp mainApp;

	@FXML
	private ImageView banniereCarsat;

	@FXML
	private ImageView imageParametre;

	@FXML
	private ImageView imageOM;

	@FXML
	private ImageView imageFM;

	@FXML
	private ImageView imageHT;

	@FXML
	private void initialize() {
		this.banniereCarsat.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "banniereCarsat.png"));
		this.imageParametre.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "parametres.png"));
		this.imageHT.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "horaires.png"));
		this.imageFM.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "frais.png"));
		this.imageOM.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "ordre.png"));

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
