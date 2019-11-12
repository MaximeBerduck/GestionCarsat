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
	
	public void ajouterOM(OrdreMission om) {
		if(null != om) {
			this.listeOM.add(om);
		}
	}
	
	public void supprimerOM(OrdreMission om){
		if(null != om) {
			this.listeOM.remove(om);
		}
	}
	
	public ArrayList<OrdreMission> getListeOM() {
		return listeOM;
	}
}