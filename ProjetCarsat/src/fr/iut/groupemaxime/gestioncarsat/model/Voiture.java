package fr.iut.groupemaxime.gestioncarsat.model;

public class Voiture extends Transport {
	private String typeVoiture;
	private String immatriculation;
	private int nbrCV;
	private String vehicule; // peut prendre uniquement les valeurs vehiculeService et vehiculePerso

	public Voiture() {
		super("voiture");
	}

}
