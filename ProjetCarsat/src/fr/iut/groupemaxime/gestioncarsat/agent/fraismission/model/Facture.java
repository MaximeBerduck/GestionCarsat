package fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model;

public class Facture {
	private float montant;
	private String chemin; //Chemin ou sinon "Papier" si facture au format papier
	
	public Facture(Float montant, String chemin) {
		this.montant = montant;
		this.chemin = chemin;
	}
	
	public Facture() {
		this(Float.valueOf(0),"");
	}

	public float getMontant() {
		return montant;
	}

	public void setMontant(float montant) {
		this.montant = montant;
	}

	public String getChemin() {
		return chemin;
	}

	public void setChemin(String chemin) {
		this.chemin = chemin;
	}
	
	
}
