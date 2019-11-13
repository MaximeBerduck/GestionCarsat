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
				if (fichier.endsWith(".xml")) {
					OrdreMission om = OrdreMission
							.importer(new File(fichier.substring(0, fichier.length() - 4)));
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
			File fichier = new File(Constante.CHEMIN_OM + om.getFichier() + Constante.EXTENSION_XML);
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
