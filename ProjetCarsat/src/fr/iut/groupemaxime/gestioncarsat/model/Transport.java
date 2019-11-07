package fr.iut.groupemaxime.gestioncarsat.model;

public abstract class Transport {
	private String nom;
	
	public Transport(String nom) {
		this.nom = nom;
	}
	
	public String toString() {
		return this.nom;
	}
}
