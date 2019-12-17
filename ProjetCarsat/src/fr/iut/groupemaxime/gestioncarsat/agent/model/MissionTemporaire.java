package fr.iut.groupemaxime.gestioncarsat.agent.model;

import java.util.Date;

public class MissionTemporaire extends TypeMission {
	private String dateDebut; // Format dd/mm/aaaa
	private String heureDebut; // Format hh:mm
	private String dateFin; // Format dd/mm/aaaa
	private String heureFin; // Format hh:mm
	private String motifDeplacement;
	private String lieuDeplacement;
	private String titre; // ne peut prendre uniquement la valeur fonctionHabituelle ou formation

	public MissionTemporaire(String dateDebut, String heureDebut, String dateFin, String heureFin,
			String motifDeplacement, String lieuDeplacement, String titre) {
		super("MissionTemporaire");
		this.dateDebut = dateDebut;
		this.heureDebut = heureDebut;
		this.dateFin = dateFin;
		this.heureFin = heureFin;
		this.motifDeplacement = motifDeplacement;
		this.lieuDeplacement = lieuDeplacement;
		this.titre = titre;
	}

	public String getDateDebut() {
		return dateDebut;
	}

	public String getHeureDebut() {
		return heureDebut;
	}

	public String getDateFin() {
		return dateFin;
	}

	public String getHeureFin() {
		return heureFin;
	}

	public String getMotifDeplacement() {
		return motifDeplacement;
	}

	public String getLieuDeplacement() {
		return lieuDeplacement;
	}

	public String getTitre() {
		return titre;
	}

	@Override
	public String toString() {
		return "MissionTemporaire [dateDebut=" + dateDebut + ", heureDebut=" + heureDebut + ", dateFin=" + dateFin
				+ ", heureFin=" + heureFin + ", motifDeplacement=" + motifDeplacement + ", lieuDeplacement="
				+ lieuDeplacement + ", titre=" + titre + "]";
	}

	public String getDates() {
		return ((this.dateDebut + '-' + this.dateFin).replace('/', '-'));
	}

}
