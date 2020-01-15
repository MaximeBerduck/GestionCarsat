package fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model;

public class PlageHoraire {
	private int heureDeb;
	private int minDeb;
	private int heureFin;
	private int minFin;
	
	public PlageHoraire() {
		this.heureDeb= 0;
		this.minDeb = 00;
		this.heureDeb = 0;
		this.minFin = 00;
	}
	public int getHeureDeb() {
		return heureDeb;
	}
	public void setHeureDeb(int heureDeb) {
		this.heureDeb = heureDeb;
	}
	public int getMinDeb() {
		return minDeb;
	}
	public void setMinDeb(int minDeb) {
		this.minDeb = minDeb;
	}
	public int getHeureFin() {
		return heureFin;
	}
	public void setHeureFin(int heureFin) {
		this.heureFin = heureFin;
	}
	public int getMinFin() {
		return minFin;
	}
	public void setMinFin(int minFin) {
		this.minFin = minFin;
	}
}
