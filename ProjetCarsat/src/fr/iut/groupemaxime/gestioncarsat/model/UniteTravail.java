package fr.iut.groupemaxime.gestioncarsat.model;

public class UniteTravail {
	private int codeAnalytique;
	private String uniteTravail;
	
	public UniteTravail(int codeAnalytique, String uniteTravail) {
		this.codeAnalytique = codeAnalytique;
		this.uniteTravail = uniteTravail;
	}
	
	public String toString() {
		return this.codeAnalytique+","+this.uniteTravail;
	}
}
