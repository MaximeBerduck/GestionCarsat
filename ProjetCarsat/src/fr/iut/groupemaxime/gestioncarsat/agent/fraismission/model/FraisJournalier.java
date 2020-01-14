package fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model;

public class FraisJournalier {
	private String date;
	private String heureDepart;
	private String heureRetour;

	private int nbrRepasForfait;
	private int nbrRepasJustif;

	private int nbrDecouchForfait;
	private int nbrDecouchJustif;

	private String typeFraisTransport;
	private float montantFraisTransport;

	private float nbrKmVehiPerso;
	private float nbrKmVehiService;

	public FraisJournalier(String date) {
		this.date = date;
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

	public int getNbrDecouchForfait() {
		return nbrDecouchForfait;
	}

	public void setNbrDecouchForfait(int nbrDecouchForfait) {
		this.nbrDecouchForfait = nbrDecouchForfait;
	}

	public int getNbrDecouchJustif() {
		return nbrDecouchJustif;
	}

	public void setNbrDecouchJustif(int nbrDecouchJustif) {
		this.nbrDecouchJustif = nbrDecouchJustif;
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
}
