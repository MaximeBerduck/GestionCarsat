package fr.iut.groupemaxime.gestioncarsat.model;

public class Voiture extends Transport {
	private String typeVoiture;
	private String immatriculation;
	private int nbrCV;
	private String appartenanceVehicule; // peut prendre uniquement les valeurs vehiculeService et vehiculePerso
	
	public Voiture(String typeVoiture, String immatriculation, int nbrCV, String appartenanceVehicule) {
		super("voiture");
		this.typeVoiture = typeVoiture;
		this.immatriculation = immatriculation;
		this.nbrCV = nbrCV;
		this.appartenanceVehicule = appartenanceVehicule;
	}

	public String getTypeVoiture() {
		return typeVoiture;
	}

	public String getImmatriculation() {
		return immatriculation;
	}

	public int getNbrCV() {
		return nbrCV;
	}

	public String getAppartenanceVehicule() {
		return appartenanceVehicule;
	}
	
	

}
