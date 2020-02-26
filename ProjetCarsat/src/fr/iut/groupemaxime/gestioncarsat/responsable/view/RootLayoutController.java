package fr.iut.groupemaxime.gestioncarsat.responsable.view;

import fr.iut.groupemaxime.gestioncarsat.responsable.ResponsableApp;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class RootLayoutController {
	private ResponsableApp mainApp;

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
	private VBox boxOM;

	@FXML
	private VBox boxFM;

	@FXML
	private VBox boxHT;

	@FXML
	private VBox boxParam;

	@FXML
	private VBox boxMenu;
	
	@FXML
	private GridPane gridRoot;
	
	@FXML
	private Label labelMissionSelectionnee;

	
	@FXML
	private void afficherOrdresMission() {
		if (mainApp.missionActiveIsNull()) {
			mainApp.alertChoisirMission();
		} else {
			this.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			this.ajouterStyleOM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			mainApp.demanderActionOM();
		}
	}
	
	@FXML
	private void afficherFraisMission() {
		if (mainApp.missionActiveIsNull()) {
			mainApp.alertChoisirMission();
		} else {
			this.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			this.ajouterStyleFM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			mainApp.demanderActionFM();
		}

	}
	
	@FXML
	private void afficherHorairesTravail() {
		// TODO Auto-generated method stub

	}

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
		this.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
		mainApp.modifierOptions();
	}
	
	public void setMainApp(ResponsableApp mainApp) {
		this.mainApp = mainApp;
	}
	
	public GridPane getGridRoot() {
		return this.gridRoot;
	}
	
	public void retirerStyleSurTousLesDocs(String style) {
		this.boxFM.setStyle(this.boxFM.getStyle().replace(style, ""));
		this.boxOM.setStyle(this.boxOM.getStyle().replace(style, ""));
		this.boxHT.setStyle(this.boxHT.getStyle().replace(style, ""));
	}

	// Style Frais de mission
	public void ajouterStyleFM(String style) {
		this.boxFM.setStyle(this.boxFM.getStyle() + style);
	}

	public void retirerStyleFM(String style) {
		this.boxFM.setStyle(this.boxFM.getStyle().replace(style, ""));
	}

	// Style Ordre de mission
	public void ajouterStyleOM(String style) {
		this.boxOM.setStyle(this.boxOM.getStyle() + style);
	}

	public void retirerStyleOM(String style) {
		this.boxOM.setStyle(this.boxOM.getStyle().replace(style, ""));
	}

	// Style Horaire de travail
	public void ajouterStyleHT(String style) {
		this.boxHT.setStyle(this.boxHT.getStyle() + style);
	}

	public void retirerStyleHT(String style) {
		this.boxHT.setStyle(this.boxHT.getStyle().replace(style, ""));
	}

	public void ajouterStyleOptions(String style) {
		this.boxParam.setStyle(this.boxParam.getStyle() + style);
	}

	public void retirerStyleOptions(String style) {
		this.boxParam.setStyle(this.boxParam.getStyle().replace(style, ""));
	}

	public void setLabelMissionSelectionnee(String om) {
		this.labelMissionSelectionnee.setText(om);
	}
}
