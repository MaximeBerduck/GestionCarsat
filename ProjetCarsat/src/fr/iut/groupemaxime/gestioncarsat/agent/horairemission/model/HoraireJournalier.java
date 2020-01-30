package fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model;

import java.util.ArrayDeque;

public class HoraireJournalier implements Comparable<HoraireJournalier> {
	private String date;
	
	private String transportUtiliseSurPlace;
	private String dureeDuTrajetSurPlaceHeure;
	private String dureeDuTrajetSurPlaceMin;
	private String observation;
	private ArrayDeque<PlageHoraire> plageHoraire;

	
	public HoraireJournalier(String date) {
		this.date = date;
		this.plageHoraire = new ArrayDeque<PlageHoraire>();
	}

	public void ajouterHoraire(PlageHoraire plageHoraire) {
		this.plageHoraire.addLast(plageHoraire);
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

	public String getDureeDuTrajetSurPlaceHeure() {
		return dureeDuTrajetSurPlaceHeure;
	}

	public void setDureeDuTrajetSurPlaceHeure(String dureeDuTrajetSurPlace) {
		this.dureeDuTrajetSurPlaceHeure = dureeDuTrajetSurPlace;
	}
	
	public String getDureeDuTrajetSurPlaceMin() {
		return dureeDuTrajetSurPlaceMin;
	}

	public void setDureeDuTrajetSurPlaceMin(String dureeDuTrajetSurPlaceMin) {
		this.dureeDuTrajetSurPlaceMin = dureeDuTrajetSurPlaceMin;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public ArrayDeque<PlageHoraire> getPlageHoraire() {
		return plageHoraire;
	}
	
	
	
}
