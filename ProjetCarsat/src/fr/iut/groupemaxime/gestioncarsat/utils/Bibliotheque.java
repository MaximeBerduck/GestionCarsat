package fr.iut.groupemaxime.gestioncarsat.utils;

import java.io.File;
import java.util.Date;

import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisMission;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Bibliotheque {
	public static boolean fichierExiste(String cheminFichier) {
		boolean existe = false;
		if (cheminFichier == null)
			return false;
		File f = new File(cheminFichier);
		if (f.isFile()) {
			existe = true;
		}
		return existe;
	}

	public static File ouvrirDirectoryChooser() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		Stage stage = new Stage();
		File dossier = directoryChooser.showDialog(stage);
		if (null != dossier)
			return new File(dossier.getAbsolutePath() + '/');
		else
			return null;
	}

	public static File ouvrirFileChooser(FileChooser.ExtensionFilter filter) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(filter);
		Stage stage = new Stage();
		File fichier = fileChooser.showOpenDialog(stage);
		if (null != fichier)
			return fichier;
		else
			return null;
	}

	public static boolean fichierFmMissionExiste(OrdreMission om) {
		File chemin = new File(om.getCheminDossier());
		String listeFichiers[] = {};
		listeFichiers = chemin.list();
		if (listeFichiers == null) {
			return false;
		} else {
			for (String fichier : listeFichiers) {
				if (fichier.endsWith(".json") && fichier.startsWith("FM_")) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean fichierFmEstEntier(FraisMission fm) {
		String dateFinMission = fm.getDateFinMission();
		for (String date : fm.getFraisMission().keySet()) {
			if (dateFinMission.equals(date))
				return true;
		}
		return false;
	}

	public static FraisMission recupererFmAvecOm(OrdreMission om) {
		FraisMission fm = new FraisMission(null);
		fm = fm.chargerJson(Bibliotheque.recupererCheminEtNomFichierFm(om));
		return fm;
	}

	public static boolean fichierHtMissionExiste(OrdreMission om) {
		File chemin = new File(om.getCheminDossier());
		String listeFichiers[] = {};
		listeFichiers = chemin.list();
		if (listeFichiers == null) {
			return false;
		} else {
			for (String fichier : listeFichiers) {
				if (fichier.endsWith(".json") && fichier.startsWith("HT_")) {
					return true;
				}
			}
		}
		return false;
	}

	public static String recupererCheminEtNomFichierFm(OrdreMission om) {
		File chemin = new File(om.getCheminDossier());
		String listeFichiers[] = {};
		listeFichiers = chemin.list();
		if (listeFichiers != null) {
			for (String fichier : listeFichiers) {
				if (fichier.endsWith(".json") && fichier.startsWith("FM_")) {
					return om.getCheminDossier() + fichier;
				}
			}
		}
		return null;
	}

	public static String recupererCheminEtNomFichierHt(OrdreMission om) {
		File chemin = new File(om.getCheminDossier());
		String listeFichiers[] = {};
		listeFichiers = chemin.list();
		if (listeFichiers != null) {
			for (String fichier : listeFichiers) {
				if (fichier.endsWith(".json") && fichier.startsWith("HT_")) {
					return om.getCheminDossier() + fichier;
				}
			}
		}
		return null;
	}

	public static String getDateAujourdhui() {
		return Constante.FORMAT_DATE_SLASH.format(new Date());
	}

	public static void setEtatFm(OrdreMission mission, EtatMission etat) {
		FraisMission fm = Bibliotheque.recupererFmAvecOm(mission);
		fm.setEtat(etat);
		fm.sauvegarderJson(Bibliotheque.recupererCheminEtNomFichierFm(mission));
		
	}

}
