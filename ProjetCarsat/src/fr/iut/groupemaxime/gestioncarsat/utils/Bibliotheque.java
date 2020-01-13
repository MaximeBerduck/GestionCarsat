package fr.iut.groupemaxime.gestioncarsat.utils;

import java.io.File;

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
}
