package fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.iut.groupemaxime.gestioncarsat.agent.form.PDF;
import fr.iut.groupemaxime.gestioncarsat.agent.interfaces.DocJson;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import fr.iut.groupemaxime.gestioncarsat.utils.Options;

public class FraisMission implements DocJson<FraisMission> {
	private String adresseFichier;
	private String dateDebutMission;
	private String dateFinMission;
	private HashMap<String, FraisJournalier> fraisMission;
	private String typeAutreFrais;
	private float montantAutreFrais;
	private boolean estSigne;
	private String dateSignature;
	private float montantDeductionFrais;
	private float montantAvance;
	private int nbrRepasOffert;

	public FraisMission(String adresseFichier, String dateDebutMission, String dateFinMission,
			HashMap<String, FraisJournalier> fraisMission, String typeAutreFrais, float montantAutreFrais,
			boolean estSigne, String dateSignature, float montantDeductionFrais, float montantAvance,
			int nbrRepasOffert) {
		this.adresseFichier = adresseFichier;
		this.dateDebutMission = dateDebutMission;
		this.dateFinMission = dateFinMission;
		this.fraisMission = fraisMission;
	}

	public FraisMission(String adresseFichier) {
		this(adresseFichier, null, null, new HashMap<String, FraisJournalier>(), null, 0, false, null, 0, 0, 0);
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
		fraisMission.trierFraisJournalier();
		return fraisMission;
	}

	public void trierFraisJournalier() {
		this.fraisMission = FraisMission.triAvecValeur(this.fraisMission);
	}

	public void genererPDF(Options options) {
		try {
			PDF pdf = new PDF(new File(Constante.CHEMIN_PDF_VIDE));
			pdf.remplirPdfFM(this, options);
			pdf.sauvegarderPDF();
			if (this.estSigne) {
				pdf.signerPdfFM(this, options);
			}
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

	public boolean estSigne() {
		return this.estSigne;
	}

	public void signerFMAgent(float montantDeductionFrais, float montantAvance, int nbrRepasOffert) {
		this.estSigne = true;
		this.dateSignature = Constante.FORMAT_DATE_SLASH.format(new Date());
		this.montantDeductionFrais = montantDeductionFrais;
		this.montantAvance = montantAvance;
		this.nbrRepasOffert = nbrRepasOffert;
	}

	public String getTypeAutreFrais() {
		return typeAutreFrais;
	}

	public void setTypeAutreFrais(String typeAutreFrais) {
		this.typeAutreFrais = typeAutreFrais;
	}

	public float getMontantAutreFrais() {
		return montantAutreFrais;
	}

	public void setMontantAutreFrais(float montantAutreFrais) {
		this.montantAutreFrais = montantAutreFrais;
	}

	public float getMontantDeductionFrais() {
		return montantDeductionFrais;
	}

	public void setMontantDeductionFrais(float montantDeductionFrais) {
		this.montantDeductionFrais = montantDeductionFrais;
	}

	public float getMontantAvance() {
		return montantAvance;
	}

	public void setMontantAvance(float montantAvance) {
		this.montantAvance = montantAvance;
	}

	public int getNbrRepasOffert() {
		return nbrRepasOffert;
	}

	public void setNbrRepasOffert(int nbrRepasOffert) {
		this.nbrRepasOffert = nbrRepasOffert;
	}

	public String getDateSignature() {
		return dateSignature;
	}

	public void setAdresseFichier(String adresseFichier) {
		this.adresseFichier = adresseFichier;
	}

	public void setFraisMission(HashMap<String, FraisJournalier> fraisMission) {
		this.fraisMission = fraisMission;
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

	public static HashMap<String, FraisJournalier> triAvecValeur(HashMap<String, FraisJournalier> map) {
		LinkedList<Map.Entry<String, FraisJournalier>> list = new LinkedList<Map.Entry<String, FraisJournalier>>(
				map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, FraisJournalier>>() {
			@Override
			public int compare(Map.Entry<String, FraisJournalier> o1, Map.Entry<String, FraisJournalier> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		HashMap<String, FraisJournalier> map_apres = new LinkedHashMap<String, FraisJournalier>();
		for (Map.Entry<String, FraisJournalier> entry : list)
			map_apres.put(entry.getKey(), entry.getValue());
		return map_apres;
	}
}
