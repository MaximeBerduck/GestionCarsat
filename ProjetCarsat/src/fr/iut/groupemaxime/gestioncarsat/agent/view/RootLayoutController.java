package fr.iut.groupemaxime.gestioncarsat.agent.view;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.Agent;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class RootLayoutController {
	private AgentApp agentApp;

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
	private Label prenomNomAgent;

	@FXML
	private void initialize() {
		this.banniereCarsat.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "banniereCarsat.png"));
		this.imageParametre.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "parametres.png"));
		this.imageHT.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "horaires.png"));
		this.imageFM.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "frais.png"));
		this.imageOM.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "ordre.png"));
		this.prenomNomAgent.setText("Options de l'agent incomplet.\nRendez-vous dans les options.");
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

	// Event Listener sur Btn Parametres
	@FXML
	public void modifierOptions() {
		this.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
		agentApp.modifierOptions();
	}

	@FXML
	public void afficherFraisMission() {
		if (agentApp.missionActiveIsNull()) {
			agentApp.alertChoisirMission();
		} else {
			this.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			this.ajouterStyleFM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			agentApp.demanderActionFM();
		}
	}

	@FXML
	public void afficherOrdresMission() {
		if (agentApp.missionActiveIsNull()) {
			agentApp.alertChoisirMission();
		} else {
			this.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			this.ajouterStyleOM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			agentApp.demanderActionOM();
		}
	}

	@FXML
	public void afficherHorairesTravail() {
		if (agentApp.missionActiveIsNull()) {
			agentApp.alertChoisirMission();
		} else {
			this.retirerStyleSurTousLesDocs(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			this.ajouterStyleHT(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			agentApp.demanderActionHT();
		}
	}

	public void setPrenomNomAgent() {
		Agent agent = this.agentApp.getOptions().getAgent();
		if (null != agent && !"".equals(agent.getPrenom()) && !"".equals(agent.getNom())) {
			this.prenomNomAgent.setText(agent.getPrenom() + " " + agent.getNom().toUpperCase());
		}
	}

	public void setLabelMissionSelectionnee(String om) {
		this.labelMissionSelectionnee.setText(om);
	}

	public void setAgentApp(AgentApp agentApp) {
		this.agentApp = agentApp;
	}

	public GridPane getGridRoot() {
		return this.gridRoot;
	}
}
