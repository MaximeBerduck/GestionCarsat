package fr.iut.groupemaxime.gestioncarsat.model;

import java.sql.Time;
import java.util.Date;


public class MissionTemporaire extends Mission {
	private Date dateDebut;
	private Time heureDebut;
	private Date dateFin;
	private Time heureFin;
	private String motifDeplacement;
	private String lieuDeplacement;
	private String titre; //ne peut prendre uniquement la valeur fonctionHabituelle ou formation
	
	
	
	public MissionTemporaire(Date dateDebut, Time heureDebut, Date dateFin, Time heureFin, String motifDeplacement,
			String lieuDeplacement, String titre) {
		this.dateDebut = dateDebut;
		this.heureDebut = heureDebut;
		this.dateFin = dateFin;
		this.heureFin = heureFin;
		this.motifDeplacement = motifDeplacement;
		this.lieuDeplacement = lieuDeplacement;
		this.titre = titre;
	}
	
	public Date getDateDebut() {
		return dateDebut;
	}
	public Time getHeureDebut() {
		return heureDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public Time getHeureFin() {
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
	
	
}
