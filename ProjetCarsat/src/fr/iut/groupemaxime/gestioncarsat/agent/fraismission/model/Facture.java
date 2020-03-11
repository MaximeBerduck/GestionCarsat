package fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model;

public class Facture {
	private float montant;
	private String chemin;
	
	public void Facture(Float montant, String chemin) {
		this.montant = montant;
		this.chemin = chemin;
	}
}
