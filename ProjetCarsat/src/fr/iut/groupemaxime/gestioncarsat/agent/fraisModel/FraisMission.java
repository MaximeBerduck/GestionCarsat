package fr.iut.groupemaxime.gestioncarsat.agent.fraisModel;

import java.util.HashSet;

import fr.iut.groupemaxime.gestioncarsat.agent.interfaces.DocJson;

public class FraisMission implements DocJson<FraisMission> {
	private String nomFichier;
	private String cheminDossier;
	private HashSet<FraisJournalier> fraisMission;

	public FraisMission(String nomFichier, String cheminDossier, HashSet<FraisJournalier> fraisMission) {
		this.nomFichier = nomFichier;
		this.cheminDossier = cheminDossier;
		this.fraisMission = fraisMission;
	}

	public FraisMission(String nomFichier, String cheminDossier) {
		this(nomFichier, cheminDossier, new HashSet<FraisJournalier>());
	}

	@Override
	public void sauvegarderJson(String adresseFichier) {
		// TODO Auto-generated method stub

	}

	@Override
	public FraisMission chargerJson(String adresseFichier) {
		// TODO Auto-generated method stub
		return null;
	}

	public HashSet<FraisJournalier> getFraisMission() {
		return fraisMission;
	}
}
