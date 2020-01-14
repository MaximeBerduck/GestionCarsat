package fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model;

public class HoraireJournalier {
	private String date;
	
	private String transportUtiliseSurPlace;
	private String dureeDuTrajetSurPlace;

	
	
	public HoraireJournalier(String date) {
		this.date = date;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	public String getTransportUtiliseSurPlace() {
		return transportUtiliseSurPlace;
	}

	public void setTransportUtiliseSurPlace(String transportUtiliseSurPlace) {
		this.transportUtiliseSurPlace = transportUtiliseSurPlace;
	}

	public String getDureeDuTrajetSurPlace() {
		return dureeDuTrajetSurPlace;
	}

	public void setDureeDuTrajetSurPlace(String dureeDuTrajetSurPlace) {
		this.dureeDuTrajetSurPlace = dureeDuTrajetSurPlace;
	}
	
	
	

//	public int getHeureTravailEffectuees() {
//		return heureTravailEffectuees;
//	}
//
//	public void setHeureTravailEffectuees(int heureTravailEffectuees) {
//		this.heureTravailEffectuees = heureTravailEffectuees;
//	}

	
	
}
