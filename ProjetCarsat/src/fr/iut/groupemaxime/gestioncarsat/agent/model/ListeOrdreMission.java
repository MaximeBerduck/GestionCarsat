package fr.iut.groupemaxime.gestioncarsat.agent.model;

import java.io.File;
import java.util.ArrayList;

public class ListeOrdreMission {
	private ArrayList<OrdreMission> listeOM;

	public ListeOrdreMission() {
		this.listeOM = new ArrayList<OrdreMission>();
	}

	public void chargerOM(File file) {
		File listeDossiers[] = {};
		listeDossiers = file.listFiles();
		if (listeDossiers == null) {
			System.out.println("pas de fichier à charger");
		} else {
			for (File dossier : listeDossiers) {
				if (dossier.isDirectory()) {
					for (String fichier : dossier.list()) {
						if (fichier.endsWith(".json")) {
							OrdreMission om = new OrdreMission();
							om = om.chargerJson(dossier.toString() + '/' + fichier);
							listeOM.add(om);
						}
					}
				}
			}
		}
	}

	public void ajouterOM(OrdreMission om) {
		if (null != om) {
			this.listeOM.add(om);
		}
	}

	public void supprimerOM(OrdreMission om) {
		if (null != om) {
			File fichier = new File(om.getCheminDossier() + Constante.EXTENSION_XML);
			fichier.delete();
			fichier = new File(Constante.CHEMIN_PDF + om.getCheminDossier() + Constante.EXTENSION_PDF);
			fichier.delete();
			this.listeOM.remove(om);
		}
	}

	public ArrayList<OrdreMission> getListeOM() {
		return listeOM;
	}
}