package fr.iut.groupemaxime.gestioncarsat.model;

import java.sql.Date;
import java.sql.Time;

public class MissionTemporaire extends Mission {
	private Date dateDebut;
	private Time heureDebut;
	private Date dateFin;
	private Time heureFin;
	private String motifDeplacement;
	private String lieuDeplacement;
	private String titre; //ne peut prendre uniquement la valeur fonctionHabituelle ou formation
}
