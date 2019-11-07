package fr.iut.groupemaxime.gestioncarsat.model;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class OrdreMission {
	private Agent agent;

	private Mission mission;
	private Transport transport;
	
	public OrdreMission(Agent agent, Mission mission, Transport transport) {
		this.agent = agent;
		this.mission = mission;
		this.transport = transport;
	}

	public void sauvegarder(File file) {
		//FileOutputStream fichier = null;
		DataOutputStream dos = null;
		try {
			//fichier = new FileOutputStream(file);
			//fichier.write(this.toString());
			dos = new DataOutputStream(
					new BufferedOutputStream(
							new FileOutputStream(file)));
			dos.writeChars(this.toString());
			
		} catch (FileNotFoundException e) {
			// erreur de l'ouverture du fichier
			e.printStackTrace();
		} catch (IOException e) {
	         // erreur d'écriture ou de lecture
	         e.printStackTrace();
		}
		finally {
			if(dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
		            e.printStackTrace();
		        }
			}
		}
	}
	
	public String toString() {
		String contenu = this.agent.toString() + this.transport.toString();
		return contenu;
	}

	public static void main(String[] args) {
		Fonction fonction = new Fonction("progr", 200);
		ResidenceAdministrative residenceAdmin = new ResidenceAdministrative("DUT");
		UniteTravail uniteTravail = new UniteTravail(123, "2A");
		Agent agent = new Agent("Berduck", "Maxime", 12345, fonction,residenceAdmin, uniteTravail);
		Transport avion = new Avion();
		OrdreMission OM = new OrdreMission(agent,null, avion);
		OM.sauvegarder(new File("target/PDF/OM1.txt"));
	}
	
}
