package fr.iut.groupemaxime.gestioncarsat.model;

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

public class Options {
	String cheminOM;

	public Options(String cheminOM) {
		this.cheminOM = cheminOM;
	}

	public Options() {
		this("");
	}

	public void sauvegarderOptions() {
		Gson g = new Gson();
		String s = g.toJson(this);
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

	public static Options chargerOptions() {
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
		return this.cheminOM;
	}

	public void setCheminOM(String cheminOM) {
		this.cheminOM = cheminOM;
	}

}
