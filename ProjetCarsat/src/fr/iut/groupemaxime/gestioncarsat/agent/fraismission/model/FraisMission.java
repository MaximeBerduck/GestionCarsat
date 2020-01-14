package fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.iut.groupemaxime.gestioncarsat.agent.form.PDF;
import fr.iut.groupemaxime.gestioncarsat.agent.interfaces.DocJson;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;

public class FraisMission implements DocJson<FraisMission> {
	private String adresseFichier;
	private String dateDebutMission;
	private String dateFinMission;
	private HashMap<String, FraisJournalier> fraisMission;

	public FraisMission(String adresseFichier, String dateDebutMission, String dateFinMission,
			HashMap<String, FraisJournalier> fraisMission) {
		this.adresseFichier = adresseFichier;
		this.dateDebutMission = dateDebutMission;
		this.dateFinMission = dateFinMission;
		this.fraisMission = fraisMission;
	}

	public FraisMission(String adresseFichier) {
		this(adresseFichier, null, null, new HashMap<String, FraisJournalier>());
	}

	@Override
	public void sauvegarderJson(String adresseFichier) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String s = gson.toJson(this);
		FileWriter f;
		try {
			f = new FileWriter(new File(adresseFichier));
			f.write(s);
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public FraisMission chargerJson(String adresseFichier) {
		Gson g = new Gson();
		FraisMission fraisMission = new FraisMission(adresseFichier);
		InputStream is;
		try {
			is = new FileInputStream(new File(adresseFichier));
			// Creation du JsonReader depuis Json.
			JsonReader reader = Json.createReader(is);
			// Recuperer la structure JsonObject depuis le JsonReader.
			JsonObject objetJson = reader.readObject();
			reader.close();
			fraisMission = g.fromJson(objetJson.toString(), FraisMission.class);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fraisMission;
	}

	public void genererPDF() {
		try {
			PDF pdf = new PDF(new File(Constante.CHEMIN_PDF_VIDE));
			pdf.remplirPdfFM(this);
			pdf.sauvegarderPDF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ajouterJournee(FraisJournalier fraisJournalier) {
		if (null != this.fraisMission.get(fraisJournalier))
			this.fraisMission.replace(fraisJournalier.getDate(), fraisJournalier);
		else
			this.fraisMission.put(fraisJournalier.getDate(), fraisJournalier);
	}

	public HashMap<String, FraisJournalier> getFraisMission() {
		return fraisMission;
	}

	public String getAdresseFichier() {
		return this.adresseFichier;
	}

	public String getDateDebutMission() {
		return dateDebutMission;
	}

	public void setDateDebutMission(String dateDebutMission) {
		this.dateDebutMission = dateDebutMission;
	}

	public String getDateFinMission() {
		return dateFinMission;
	}

	public void setDateFinMission(String dateFinMission) {
		this.dateFinMission = dateFinMission;
	}

}
