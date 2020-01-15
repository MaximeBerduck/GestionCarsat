package fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model;

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

import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisMission;
import fr.iut.groupemaxime.gestioncarsat.agent.interfaces.DocJson;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;

public class OrdreMission implements DocJson<OrdreMission> {

	private Agent agent;
	private TypeMission mission;
	private Transport transport;
	private String cheminDossier;
	private String nomOM;
	private boolean signatureAgent;
	private boolean envoye;

	public OrdreMission(Agent agent, TypeMission mission, Transport transport, String cheminDossier, String nomOM,
			boolean signatureAgent) {
		this.agent = agent;
		this.mission = mission;
		this.transport = transport;
		this.cheminDossier = cheminDossier;
		this.nomOM = nomOM;
		this.signatureAgent = signatureAgent;
		this.envoye = false;
	}

	public OrdreMission(Agent agent, TypeMission mission, Transport transport) {
		this(agent, mission, transport, null, null, false);
	}

	public OrdreMission() {
		this(null, null, null);
	}

	@Override
	public void sauvegarderJson(String cheminDossier) {
		if (null == this.nomOM) {
			this.nomOM = "OM" + '_' + this.getAgent().getNom() + '_'
					+ ((MissionTemporaire) this.getMission()).getLieuDeplacement().replace(" ", "_") + '_'
					+ ((MissionTemporaire) this.getMission()).getDates();
		}

		Gson gson = new GsonBuilder().registerTypeAdapter(TypeMission.class, new InterfaceAdapter())
				.registerTypeAdapter(Transport.class, new InterfaceAdapter()).setPrettyPrinting().create();
		String s = gson.toJson(this);
		FileWriter f;
		try {
			f = new FileWriter(new File(cheminDossier + this.getNomOM() + Constante.EXTENSION_JSON));
			f.write(s);
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public OrdreMission chargerJson(String adresseFichier) {
		Gson g = new Gson();
		Gson gson = new GsonBuilder().registerTypeAdapter(TypeMission.class, new InterfaceAdapter())
				.registerTypeAdapter(Transport.class, new InterfaceAdapter()).setPrettyPrinting().create();
		OrdreMission om = new OrdreMission();
		InputStream is;
		try {
			is = new FileInputStream(new File(adresseFichier));

			// Creation du JsonReader depuis Json.
			JsonReader reader = Json.createReader(is);

			// Recuperer la structure JsonObject depuis le JsonReader.
			JsonObject objetJson = reader.readObject();
			reader.close();
			om = gson.fromJson(objetJson.toString(), OrdreMission.class);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return om;
	}

	public Agent getAgent() {
		return agent;
	}

	public TypeMission getMission() {
		return mission;
	}

	public Transport getTransport() {
		return transport;
	}

	public String getCheminDossier() {
		return cheminDossier;
	}

	public void setCheminDossier(String cheminDossier) {
		this.cheminDossier = cheminDossier;
	}

	public String getNomOM() {
		return nomOM;
	}

	public void setNomOM(String nomOM) {
		this.nomOM = nomOM;
	}

	public boolean agentSigne() {
		return signatureAgent;
	}

	public void setSignatureAgent(boolean signatureAgent) {
		this.signatureAgent = signatureAgent;
	}

	public boolean estSigne() {
		return this.signatureAgent;
	}

	public boolean fmEstSigne() {
		FraisMission fm = new FraisMission(null);
		fm = fm.chargerJson(cheminDossier + nomOM.replace("OM_", "FM_") + Constante.EXTENSION_JSON);
		return fm.estSigne();
	}

	public String getEtat() {
		// TODO Auto-generated method stub
		return "eere";
	}

	public boolean estEnvoye() {
		return this.envoye;
	}

	public void setEnvoye(boolean envoye) {
		this.envoye = envoye;
	}
}
