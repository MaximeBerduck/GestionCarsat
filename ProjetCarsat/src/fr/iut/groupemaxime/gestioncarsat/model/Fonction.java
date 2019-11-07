package fr.iut.groupemaxime.gestioncarsat.model;

public class Fonction {
	private String fonction;
	private int coefficient;
	
	public Fonction(String fonction, int coefficient) {
		this.fonction = fonction;
		this.coefficient = coefficient;
	}
	
	public String toString() {
		return this.fonction+","+this.coefficient;
	}
}
