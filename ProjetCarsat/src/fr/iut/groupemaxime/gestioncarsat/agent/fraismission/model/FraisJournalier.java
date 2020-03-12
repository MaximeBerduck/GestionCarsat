package fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model;

import java.util.HashSet;

public class FraisJournalier implements Comparable<FraisJournalier> {
	private String date;
	private String heureDepart;
	private String heureRetour;

	private int nbrRepasForfait;
	private int nbrRepasJustif;

	private String typeFraisTransport;
	private float montantFraisTransport;

	private float nbrKmVehiPerso;
	private float nbrKmVehiService;

	private String typeAutreFrais;
	private float montantAutreFrais;

	private HashSet<Facture> justificatifRepas;
	private HashSet<Facture> justificatifDecoucher;

	public FraisJournalier(String date) {
		this.date = date;
		this.justificatifDecoucher = new HashSet<Facture>();
		this.justificatifRepas = new HashSet<Facture>();
	}

	@Override
	public int compareTo(FraisJournalier fj) {
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

	public String getHeureDepart() {
		return heureDepart;
	}

	public void setHeureDepart(String heureDepart) {
		this.heureDepart = heureDepart;
	}

	public String getHeureRetour() {
		return heureRetour;
	}

	public void setHeureRetour(String heureRetour) {
		this.heureRetour = heureRetour;
	}

	public int getNbrRepasForfait() {
		return nbrRepasForfait;
	}

	public void setNbrRepasForfait(int nbrRepasForfait) {
		this.nbrRepasForfait = nbrRepasForfait;
	}

	public int getNbrRepasJustif() {
		return nbrRepasJustif;
	}

	public void setNbrRepasJustif(int nbrRepasJustif) {
		this.nbrRepasJustif = nbrRepasJustif;
	}

	public String getTypeFraisTransport() {
		return typeFraisTransport;
	}

	public void setTypeFraisTransport(String typeFraisTransport) {
		this.typeFraisTransport = typeFraisTransport;
	}

	public float getMontantFraisTransport() {
		return montantFraisTransport;
	}

	public void setMontantFraisTransport(float montantFraisTransport) {
		this.montantFraisTransport = montantFraisTransport;
	}

	public float getNbrKmVehiPerso() {
		return nbrKmVehiPerso;
	}

	public void setNbrKmVehiPerso(float nbrKmVehiPerso) {
		this.nbrKmVehiPerso = nbrKmVehiPerso;
	}

	public float getNbrKmVehiService() {
		return nbrKmVehiService;
	}

	public void setNbrKmVehiService(float nbrKmVehiService) {
		this.nbrKmVehiService = nbrKmVehiService;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTypeAutreFrais() {
		return typeAutreFrais;
	}

	public void setTypeAutreFrais(String typeAutreFrais) {
		this.typeAutreFrais = typeAutreFrais;
	}

	public float getMontantAutreFrais() {
		return montantAutreFrais;
	}

	public void setMontantAutreFrais(float montantAutreFrais) {
		this.montantAutreFrais = montantAutreFrais;
	}

	public HashSet<Facture> getJustificatifRepas() {
		return justificatifRepas;
	}

	public void setJustificatifRepas(HashSet<Facture> justificatifRepas) {
		this.justificatifRepas = justificatifRepas;
	}

	public HashSet<Facture> getJustificatifDecoucher() {
		return justificatifDecoucher;
	}

	public void setJustificatifDecoucher(HashSet<Facture> justificatifDecoucher) {
		this.justificatifDecoucher = justificatifDecoucher;
	}

	public float getMontantJustifRepas() {
		float n = 0;
		for (Facture facture : this.justificatifRepas)
			n += facture.getMontant();
		return n;
	}

	public float getMontantJustifDecouchers() {
		float n = 0;
		for (Facture facture : this.justificatifDecoucher)
			n += facture.getMontant();
		return n;
	}

}
