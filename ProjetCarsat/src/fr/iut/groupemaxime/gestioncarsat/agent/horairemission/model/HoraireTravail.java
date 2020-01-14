package fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.iut.groupemaxime.gestioncarsat.agent.interfaces.DocJson;

public class HoraireTravail implements DocJson<HoraireTravail>{
	
	private String adresseFichier;
	private String dateDebutMission;
	private String dateFinMission;
	private HashMap<String, HoraireJournalier> horaireTravail;
	
	
	public HoraireTravail(String adresseFichier, String dateDebutMission, String dateFinMission,HashMap<String, HoraireJournalier> horaireTravail) {
		this.adresseFichier = adresseFichier;
		this.dateDebutMission = dateDebutMission;
		this.dateFinMission = dateFinMission;
		this.horaireTravail = horaireTravail;
	}
	
	public HoraireTravail(String adresseFichier) {
		this(adresseFichier, null, null, new HashMap<String, HoraireJournalier>());
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
	public HoraireTravail chargerJson(String adresseFichier) {
		Gson g = new Gson();
		HoraireTravail horaireTravail = new HoraireTravail(adresseFichier);
		InputStream is;
		try {
			is = new FileInputStream(new File(adresseFichier));
			// Creation du JsonReader depuis Json.
			JsonReader reader = Json.createReader(is);
			// Recuperer la structure JsonObject depuis le JsonReader.
			JsonObject objetJson = reader.readObject();
			reader.close();
			horaireTravail = g.fromJson(objetJson.toString(), HoraireTravail.class);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return horaireTravail;
	}
	
	public HashMap<String, HoraireJournalier> getHoraireTravail() {
		return horaireTravail;
	}
	
	public void ajouterJournee(HoraireJournalier horaireJournalier) {
		if (null != this.horaireTravail.get(horaireJournalier))
			this.horaireTravail.replace(horaireJournalier.getDate(), horaireJournalier);
		else
			this.horaireTravail.put(horaireJournalier.getDate(), horaireJournalier);
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