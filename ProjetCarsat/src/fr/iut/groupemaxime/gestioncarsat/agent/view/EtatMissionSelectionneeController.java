package fr.iut.groupemaxime.gestioncarsat.agent.view;

import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.utils.Couleur;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EtatMissionSelectionneeController {
	@FXML
	private Label etatOM;
	@FXML
	private Label etatFM;
	@FXML
	private Label etatHT;
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

	public String choisirCouleur(String etat) {
		switch (etat) {
		case "Envoyé":
			return Couleur.Vert.getCouleur();
		case "Signé":
			return Couleur.Jaune.getCouleur();

		case "Non signé":
			return Couleur.Orange.getCouleur();

		case "En cours d'envoi":
			return Couleur.Bleu.getCouleur();

		case "Non rempli":
			return Couleur.Rouge.getCouleur();

		default:
			return null;
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
	}

	public void setEtatOM(String etat) {
		this.etatOM.setText(etat);
	}

	public void setEtatFM(String etat) {
		this.etatFM.setText(etat);
	}

	public void setEtatHT(String etat) {
		this.etatHT.setText(etat);
	}

	public void setCouleurOM(String couleur) {
		this.retirerCouleur(this.etatOM);
		this.etatOM.setStyle(this.etatOM.getStyle().concat(couleur));
	}

	public void setCouleurFM(String couleur) {
		this.retirerCouleur(this.etatFM);
		this.etatFM.setStyle(this.etatFM.getStyle().concat(couleur));

	}

	public void setCouleurHT(String couleur) {
		this.retirerCouleur(this.etatHT);
		this.etatHT.setStyle(this.etatHT.getStyle().concat(couleur));
	}

	public String getEtatOM() {
		return this.etatOM.getText();
	}

	public String getEtatFM() {
		return this.etatFM.getText();
	}

	public String getEtatHT() {
		return this.etatHT.getText();
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
