package fr.iut.groupemaxime.gestioncarsat.model;

import java.io.File;
import java.util.ArrayList;

public class ListeOrdreMission {
	private ArrayList<OrdreMission> listeOM;

	public ListeOrdreMission() {
		this.listeOM = new ArrayList<OrdreMission>();
	}

	public void chargerOM(File file) {
		String liste[] = {};
		liste = file.list();
		if (liste == null) {
			System.out.println("pas de fichier à charger");
		} else {
			for (String fichier : liste) {
				if (fichier.endsWith(".json")) {
					OrdreMission om = new OrdreMission();
					om = om.chargerJson(file.toString() + '/' + fichier);
					listeOM.add(om);
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
			File fichier = new File(Constante.CHEMIN_OM_DEFAUT + om.getFichier() + Constante.EXTENSION_XML);
			fichier.delete();
			fichier = new File(Constante.CHEMIN_PDF + om.getFichier() + Constante.EXTENSION_PDF);
			fichier.delete();
			this.listeOM.remove(om);
		}
	}

	public ArrayList<OrdreMission> getListeOM() {
		return listeOM;
	}
}
