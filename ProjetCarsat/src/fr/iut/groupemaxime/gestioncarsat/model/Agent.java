package fr.iut.groupemaxime.gestioncarsat.model;

public class Agent {
	private String nom;
	private String prenom;
	private int numCAPSSA;
	private Fonction fonction;
	private ResidenceAdministrative residenceAdmin;
	private UniteTravail uniteTravail;
	
	public Agent(String nom, String prenom, int numCAPSSA, Fonction fonction, ResidenceAdministrative residenceAdmin,
			UniteTravail uniteTravail) {
		this.nom = nom;
		this.prenom = prenom;
		this.numCAPSSA = numCAPSSA;
		this.fonction = fonction;
		this.residenceAdmin = residenceAdmin;
		this.uniteTravail = uniteTravail;
	}
	
	public String toString() {
		return this.nom + ","+ this.prenom + ","+ this.numCAPSSA + ","+ this.fonction + ","+  this.residenceAdmin + ","+ this.uniteTravail;
		
	}

}
