package fr.iut.groupemaxime.gestioncarsat.agent.model;

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

public class Options implements DocJson<Options> {
	String cheminFichiers;
	String cheminSignature;
	Agent agent;

	public Options(String cheminFichiers, String cheminSignature, Agent agent) {
		this.cheminFichiers = cheminFichiers;
		this.cheminSignature = cheminSignature;
		this.agent = agent;
	}

	public Options() {
		this(Constante.CHEMIN_FICHIERS_DEFAUT,null, new Agent());
	}
	
	@Override
	public void sauvegarderJson(String adresseFichier) {
		Gson gson = new GsonBuilder()
				  .setPrettyPrinting()
				  .create();
		String s = gson.toJson(this);
		FileWriter f;
		try {
			f = new FileWriter(new File(Constante.CHEMIN_OPTIONS));
			f.write(s);
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Options chargerJson(String adresseFichier) {
		Gson g = new Gson();
		Options options = new Options();
		InputStream is;
		if (fichierOptionsExiste()) {
			try {
				is = new FileInputStream(new File(Constante.CHEMIN_OPTIONS));
				// Creation du JsonReader depuis Json.
				JsonReader reader = Json.createReader(is);
				// Recuperer la structure JsonObject depuis le JsonReader.
				JsonObject objetJson = reader.readObject();
				reader.close();
				options = g.fromJson(objetJson.toString(), Options.class);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return options;
	}

	public static boolean fichierOptionsExiste() {
		boolean existe = false;
		File f = new File(Constante.CHEMIN_OPTIONS);
		if (f.isFile()) {
			existe = true;
		}
		return existe;
	}

	public String getCheminOM() {
		return this.cheminFichiers;
	}

	public void setCheminOM(String cheminOM) {
		this.cheminFichiers = cheminOM;
	}
	
	public Agent getAgent() {
		return this.agent;
	}
	
	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public String getCheminSignature() {
		return cheminSignature;
	}

	public void setCheminSignature(String cheminSignature) {
		this.cheminSignature = cheminSignature;
	}

}
