package fr.iut.groupemaxime.gestioncarsat.utils;

public enum EtatMission {
	ENVOYE("Envoyé"), SIGNE("Signé"), NON_SIGNE("Non signé"), EN_COURS_ENVOI("En cours d'envoi"),
	NON_REMPLI("Non rempli"), EN_COURS_SAISIE("En cours de saisie");

	private String etat;

	private EtatMission(String etat) {
		this.etat = etat;
	}

	public String getEtat() {
		return this.etat;
	}
}
