package fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.iut.groupemaxime.gestioncarsat.agent.interfaces.DocJson;
import fr.iut.groupemaxime.gestioncarsat.utils.Options;

public class FraisMission implements DocJson<FraisMission> {
	private String adresseFichier;
	private HashSet<FraisJournalier> fraisMission;

	public FraisMission(String adresseFichier, HashSet<FraisJournalier> fraisMission) {
		this.adresseFichier = adresseFichier;
		this.fraisMission = fraisMission;
	}

	public FraisMission(String adresseFichier) {
		this(adresseFichier, new HashSet<FraisJournalier>());
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

	public HashSet<FraisJournalier> getFraisMission() {
		return fraisMission;
	}

	public void ajouterJournee(FraisJournalier fraisJournalier) {
		this.fraisMission.add(fraisJournalier);
	}

	public String getAdresseFichier() {
		return this.adresseFichier;
	}

	public void retirerJournee(FraisJournalier fraisJournalier) {
		for (FraisJournalier fj : this.fraisMission) {
			if (fj.getDate().equals(fraisJournalier.getDate())) {
				this.fraisMission.remove(fj);
			}
		}
	}

}
