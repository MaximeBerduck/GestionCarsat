package fr.iut.groupemaxime.gestioncarsat.agent.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.iut.groupemaxime.gestioncarsat.agent.interfaces.DocJson;

public class ListeMails implements DocJson<ListeMails>{
	ArrayList<Mail> listeMails;
	
	public ListeMails(ArrayList<Mail> listeMails) {
		this.listeMails = listeMails;
	}
	
	public ListeMails() {
		this(new ArrayList<Mail>());
	}

	public void ajouterMail(Mail mail) {
		this.listeMails.add(mail);
	}
	
	public ArrayList<Mail> getListeMails(){
		return this.listeMails;
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
	public ListeMails chargerJson(String adresseFichier) {
		Gson g = new Gson();
		ListeMails listeMails = new ListeMails();
		InputStream is;
		if (fichierExiste(adresseFichier)) {
			try {
				is = new FileInputStream(new File(adresseFichier));
				// Creation du JsonReader depuis Json.
				JsonReader reader = Json.createReader(is);
				// Recuperer la structure JsonObject depuis le JsonReader.
				JsonObject objetJson = reader.readObject();
				reader.close();
				listeMails = g.fromJson(objetJson.toString(), ListeMails.class);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listeMails;
	}
	
	public static boolean fichierExiste(String adresseFichier) {
		boolean existe = false;
		File f = new File(adresseFichier);
		if (f.isFile()) {
			existe = true;
		}
		return existe;
	}
}
