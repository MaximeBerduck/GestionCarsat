package fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisJournalier;
import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisMission;
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
		horaireTravail.trierHoraireJournalier();
		return horaireTravail;
	}
	
	public void trierHoraireJournalier() {
		this.horaireTravail = this.triAvecValeur(this.horaireTravail);
	}
	
	public static HashMap<String, HoraireJournalier> triAvecValeur(HashMap<String, HoraireJournalier> map) {
		LinkedList<Map.Entry<String, HoraireJournalier>> list = new LinkedList<Map.Entry<String, HoraireJournalier>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, HoraireJournalier>>() {
			public int compare(Map.Entry<String, HoraireJournalier> o1, Map.Entry<String, HoraireJournalier> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		HashMap<String, HoraireJournalier> map_apres = new LinkedHashMap<String, HoraireJournalier>();
		for (Map.Entry<String, HoraireJournalier> entry : list)
			map_apres.put(entry.getKey(), entry.getValue());
		return map_apres;
	}
	
	public void ajouterJournee(HoraireJournalier horaireJournalier) {
		if (null != this.horaireTravail.get(horaireJournalier))
			this.horaireTravail.replace(horaireJournalier.getDate(), horaireJournalier);
		else
			this.horaireTravail.put(horaireJournalier.getDate(), horaireJournalier);
	}
	
	public HashMap<String, HoraireJournalier> getHoraireTravail() {
		return horaireTravail;
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