package fr.iut.groupemaxime.gestioncarsat.responsable.view;

import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import fr.iut.groupemaxime.gestioncarsat.utils.Couleur;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class EtatMissionResponsableController {
	@FXML
	private Label saisieEnCoursOM;
	@FXML
	private Label signeOM;
	@FXML
	private Label envoiEnCoursOM;
	@FXML
	private Label envoiOM;

	@FXML
	private Label saisieEnCoursFM;
	@FXML
	private Label signeFM;

	@FXML
	private Label saisieEnCoursHT;
	@FXML
	private Label signeHT;
	
	@FXML
	private Label nomPrenom;
	@FXML
	private Label lieuMission;
	@FXML
	private Label motifMission;
	@FXML
	private Label dateDebutMission;
	@FXML
	private Label dateFinMission;
	@FXML
	private Label heureDebut;
	@FXML
	private Label heureFin;

	@FXML
	private ImageView saisieOM;
	@FXML
	private ImageView signatureOM;
	@FXML
	private ImageView coursEnvoiOM;
	@FXML
	private ImageView envoyeOM;

	@FXML
	private ImageView saisieFM;
	@FXML
	private ImageView signatureFM;

	@FXML
	private ImageView saisieHT;
	@FXML
	private ImageView signatureHT;
	
	@FXML
	private Label envoiEnCoursHTFM;
	@FXML
	private Label envoiHTFM;
	@FXML
	private ImageView imageEnvoiHTFM;
	@FXML
	private ImageView imageEnvoiEnCoursHTFM;

	@FXML
	public void initialize() {
		this.saisieOM.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "saisie.png"));
		this.signatureOM.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "signature.png"));
		this.coursEnvoiOM.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "envoiEnCours.png"));
		this.envoyeOM.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "envoi.png"));

		this.saisieFM.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "saisie.png"));
		this.signatureFM.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "signature.png"));

		this.saisieHT.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "saisie.png"));
		this.signatureHT.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "signature.png"));
		this.imageEnvoiEnCoursHTFM.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "envoiEnCours.png"));
		this.imageEnvoiHTFM.setImage(new Image("file:" + Constante.CHEMIN_IMAGES + "envoi.png"));

	}

	// Style Etat OM
	public void ajouterStyleSaisieEnCoursOM(String style) {
		this.saisieEnCoursOM.setStyle(this.saisieEnCoursOM.getStyle() + style);
	}

	public void ajouterStyleSigneOM(String style) {
		this.saisieEnCoursOM.setStyle(this.saisieEnCoursHT.getStyle() + style);
		this.signeOM.setStyle(this.signeOM.getStyle() + style);
	}

	public void ajouterStyleEnvoiEnCoursOM(String style) {
		this.ajouterStyleSigneOM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
		this.envoiEnCoursOM.setStyle(this.envoiEnCoursOM.getStyle() + style);
	}

	public void ajouterStyleEnvoyeOM(String style) {
		this.ajouterStyleEnvoiEnCoursOM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
		this.envoiOM.setStyle(this.saisieEnCoursOM.getStyle() + style);
	}

	// Style Etat FM
	public void ajouterStyleSaisieEnCoursFM(String style) {
		this.saisieEnCoursFM.setStyle(this.saisieEnCoursFM.getStyle() + style);
	}

	public void ajouterStyleSigneFM(String style) {
		this.saisieEnCoursFM.setStyle(this.saisieEnCoursHT.getStyle() + style);
		this.signeFM.setStyle(this.signeFM.getStyle() + style);
	}

	// Style Etat HTFM
	public void ajouterStyleEnvoiEnCoursHTFM(String style) {
		this.ajouterStyleSigneHT(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
		this.ajouterStyleSigneFM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
		this.envoiEnCoursHTFM.setStyle(this.envoiEnCoursHTFM.getStyle() + style);
	}

	public void ajouterStyleEnvoyeHTFM(String style) {
		this.ajouterStyleEnvoiEnCoursHTFM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
		this.envoiHTFM.setStyle(this.envoiHTFM.getStyle() + style);
	}

	// Style Etat HT
	public void ajouterStyleSaisieEnCoursHT(String style) {
		this.saisieEnCoursHT.setStyle(this.saisieEnCoursHT.getStyle() + style);
	}

	public void ajouterStyleSigneHT(String style) {
		this.saisieEnCoursHT.setStyle(this.saisieEnCoursHT.getStyle() + style);
		this.signeHT.setStyle(this.signeHT.getStyle() + style);
	}

	public void choisirCouleurOM(String etat) {
		switch (etat) {
		case "Envoyé":
			this.ajouterStyleEnvoyeOM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			break;
		case "Signé":
			this.ajouterStyleSigneOM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			break;
		case "En cours d'envoi":
			this.ajouterStyleEnvoiEnCoursOM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			break;
		case "Non recu":
			this.ajouterStyleSaisieEnCoursOM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			break;
		default:

		}
	}

	public void choisirCouleurFM(String etat) {
		switch (etat) {
		case "Envoyé":
			this.ajouterStyleEnvoyeHTFM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			break;
		case "Signé":
			this.ajouterStyleSigneFM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			break;
		case "En cours d'envoi":
			this.ajouterStyleEnvoiEnCoursHTFM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			break;
		case "En cours de saisie":
			this.ajouterStyleSaisieEnCoursFM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			break;
		case "Non signé":
			this.ajouterStyleSaisieEnCoursFM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			break;
		default:
		}
	}

	public void choisirCouleurHT(String etat) {
		switch (etat) {
		case "Envoyé":
			this.ajouterStyleEnvoyeHTFM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			break;
		case "Signé":
			this.ajouterStyleSigneHT(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			break;
		case "En cours d'envoi":
			this.ajouterStyleEnvoiEnCoursHTFM(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			break;
		case "En cours de saisie":
			this.ajouterStyleSaisieEnCoursHT(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			break;
		case "Non signé":
			this.ajouterStyleSaisieEnCoursHT(Constante.BACKGROUND_COLOR_MISSION_SELECTIONNE);
			break;
		default:
		}
	}

	private void retirerCouleur(Label label) {
		for (Couleur couleur : Couleur.values()) {
			label.getStyle().replace(couleur.getCouleur(), "");
		}
	}

	public void modifierInfosMission(OrdreMission om) {
		MissionTemporaire mission = (MissionTemporaire) om.getMission();
		this.setLieuMission(mission.getLieuDeplacement());
		this.setMotifMission(mission.getMotifDeplacement());
		this.setDateDebutMission(mission.getDateDebut());
		this.setDateFinMission(mission.getDateFin());
		this.setHeureDebutMission(mission.getHeureDebut());
		this.setHeureFinMission(mission.getHeureFin());
		this.setNomPrenom(om.getAgent().getNom() +" "+om.getAgent().getPrenom());
	}

	private void setNomPrenom(String nomprenom) {
		this.nomPrenom.setText(nomprenom);
		
	}

	public Label getSaisieEnCoursOM() {
		return saisieEnCoursOM;
	}

	public void setSaisieEnCoursOM(Label saisieEnCoursOM) {
		this.saisieEnCoursOM = saisieEnCoursOM;
	}

	public void setLieuMission(String lieu) {
		this.lieuMission.setText(lieu);
	}

	public void setMotifMission(String motif) {
		this.motifMission.setText(motif);
	}

	public void setDateDebutMission(String dateDebut) {
		this.dateDebutMission.setText(dateDebut);
	}

	public void setDateFinMission(String dateFin) {
		this.dateFinMission.setText(dateFin);
	}

	public void setHeureDebutMission(String heureDebut) {
		this.heureDebut.setText(heureDebut);
	}

	public void setHeureFinMission(String heureFin) {
		this.heureFin.setText(heureFin);
	}
}
