package fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model;

import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisJournalier;

public class HoraireJournalier implements Comparable<HoraireJournalier> {
	private String date;
	
	private String transportUtiliseSurPlace;
	private String dureeDuTrajetSurPlace;

	public HoraireJournalier(String date) {
		this.date = date;
	}
	
	@Override
	public int compareTo(HoraireJournalier fj) {
		int compare = 0;
		Integer[] date1 = this.getDateTab();
		Integer[] date2 = fj.getDateTab();

		compare = date1[2].compareTo(date2[2]);
		if (compare != 0) {
			return compare;
		}

		compare = date1[1].compareTo(date2[1]);
		if (compare != 0) {
			return compare;
		}

		compare = date1[0].compareTo(date2[0]);
		if (compare != 0) {
			return compare;
		}

		return compare;
	}
	
	public Integer[] getDateTab() {
		String[] split = this.date.split("/");
		Integer date[] = new Integer[3];

		date[0] = Integer.parseInt(split[0]);
		date[1] = Integer.parseInt(split[1]);
		date[2] = Integer.parseInt(split[2]);

		return date;
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
	
}
