package fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisMission;
import fr.iut.groupemaxime.gestioncarsat.agent.interfaces.DocJson;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import fr.iut.groupemaxime.gestioncarsat.utils.EtatMission;

public class HoraireTravail implements DocJson<HoraireTravail> {

	private String adresseFichier;
	private String dateDebutMission;
	private String dateFinMission;
	private HashMap<String, HoraireJournalier> horaireJournalier;
	private EtatMission etat;

	public HoraireTravail(String adresseFichier, String dateDebutMission, String dateFinMission,
			HashMap<String, HoraireJournalier> horaireTravail) {
		this.adresseFichier = adresseFichier;
		this.dateDebutMission = dateDebutMission;
		this.dateFinMission = dateFinMission;
		this.horaireJournalier = horaireTravail;
		this.etat = EtatMission.NON_REMPLI;
	}

	public HoraireTravail(String adresseFichier) {
		this(adresseFichier, null, null, new HashMap<String, HoraireJournalier>());
	}

	@Override
	public void sauvegarderJson(String adresseFichier) {
		this.setEtat(EtatMission.NON_SIGNE);
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

	private void setEtat(EtatMission etat) {
		this.etat = etat;
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

	public void trierHoraireJournalier() { // pb dans la sauvegarde
		this.horaireJournalier = HoraireTravail.triAvecValeur(this.horaireJournalier);
	}

	public static HashMap<String, HoraireJournalier> triAvecValeur(HashMap<String, HoraireJournalier> map) {
		LinkedList<Map.Entry<String, HoraireJournalier>> list = new LinkedList<Map.Entry<String, HoraireJournalier>>(
				map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, HoraireJournalier>>() {
			@Override
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
		if (null != this.horaireJournalier.get(horaireJournalier)) {
			this.horaireJournalier.replace(horaireJournalier.getDate(), horaireJournalier);
		} else {
			this.horaireJournalier.put(horaireJournalier.getDate(), horaireJournalier);
		}
	}
	
	// Remplir le fichier excel 
		public void RemplirExcelHT()
		{
			try {
				FileInputStream excelFile = new FileInputStream(new File(Constante.CHEMIN_EXCEL_VIDE));
				Workbook workbook = new HSSFWorkbook(excelFile);
				Sheet dataSheet = workbook.getSheetAt(0);
				this.chargerJson(this.adresseFichier);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	public HashMap<String, HoraireJournalier> getHoraireTravail() {
		return horaireJournalier;
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

	public EtatMission getEtat() {
		return this.etat;
	}

}