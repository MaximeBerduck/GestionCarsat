package fr.iut.groupemaxime.gestioncarsat.model;

import java.io.File;
import java.util.ArrayList;

public class ListeOrdreMission {
	private ArrayList<OrdreMission> listeOM;

	public ListeOrdreMission() {
		this.listeOM = new ArrayList<OrdreMission>();
	}
	
	public void chargerOM(File file) {
		String liste[] = file.list();
		for(String fichier : liste) {
			OrdreMission om = OrdreMission.importer(new File(file.toString() + '/' + fichier));
			listeOM.add(om);
		}
	}
	
	public ArrayList<OrdreMission> getListeOM() {
		return listeOM;
	}

	public static void main(String[] args) {
		ListeOrdreMission liste = new ListeOrdreMission();
		liste.chargerOM(new File("target/OM/"));
	}
	
}
