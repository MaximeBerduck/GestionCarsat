package fr.iut.groupemaxime.gestioncarsat.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.iut.groupemaxime.gestioncarsat.agent.interfaces.DocJson;

public class EtatsResponsable implements DocJson<EtatsResponsable> {
	private String chemin;
	private EtatMission om;
	private EtatMission fm;
	private EtatMission ht;

	
	public EtatsResponsable(String chemin) {
		this.chemin = chemin;
		this.om = EtatMission.NON_SIGNE;
		this.fm = EtatMission.NON_RECU;
		this.ht = EtatMission.NON_RECU;

	}

	public String getNom() {
		return chemin;
	}

	public void setNom(String nom) {
		this.chemin = nom;
	}

	public EtatMission getOm() {
		return om;
	}

	public void setOm(EtatMission om) {
		this.om = om;
	}

	public EtatMission getFm() {
		return fm;
	}

	public void setFm(EtatMission fm) {
		this.fm = fm;
	}

	public EtatMission getHt() {
		return ht;
	}

	public void setHt(EtatMission ht) {
		this.ht = ht;
	}
	
	public void sauvegarderJson() {
		sauvegarderJson(this.chemin);
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
	public EtatsResponsable chargerJson(String adresseFichier) {
		Gson g = new Gson();
		EtatsResponsable etatsResponsable = new EtatsResponsable(adresseFichier);
		InputStream is;
		try {
			is = new FileInputStream(new File(adresseFichier));
			// Creation du JsonReader depuis Json.
			JsonReader reader = Json.createReader(is);
			// Recuperer la structure JsonObject depuis le JsonReader.
			JsonObject objetJson = reader.readObject();
			reader.close();
			etatsResponsable = g.fromJson(objetJson.toString(), EtatsResponsable.class);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return etatsResponsable;
	}

	@Override
	public String toString() {
		return "Nom : " + this.chemin+"\nOm : "+this.om+"\nFm : "+this.fm+"\nHt : "+this.ht;
	}
}
