package fr.iut.groupemaxime.gestioncarsat.form;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import fr.iut.groupemaxime.gestioncarsat.model.Agent;
import fr.iut.groupemaxime.gestioncarsat.model.Avion;
import fr.iut.groupemaxime.gestioncarsat.model.Mission;
import fr.iut.groupemaxime.gestioncarsat.model.MissionPermanent;
import fr.iut.groupemaxime.gestioncarsat.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.model.Train;
import fr.iut.groupemaxime.gestioncarsat.model.Transport;
import fr.iut.groupemaxime.gestioncarsat.model.Voiture;

public class PDF {
	private PDDocument modele;
	private PDAcroForm formulaire;
	private PDField champ;

	public PDF(File source) throws IOException {
		this.modele = PDDocument.load(source);
		this.formulaire = this.modele.getDocumentCatalog().getAcroForm();
		this.champ = null;
	}

	public void remplirChamp(String champ, String valeur) {
		this.champ = formulaire.getField(champ);
		try {
			this.champ.setValue(valeur);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sauvegarderPDF() throws IOException {
		this.modele.save("target/PDF/Doc1modif.pdf");
	}

	public void fermerPDF() throws IOException {
		this.modele.close();
	}

	public void remplirPDF(OrdreMission om) {
		if (null != om) {
			this.remplirChamp("nomPrenom", om.getAgent().getNom() + ' ' + om.getAgent().getPrenom());
			this.remplirChamp("numCAPSSA", String.valueOf(om.getAgent().getNumCAPSSA()));
			this.remplirChamp("fonction", om.getAgent().getFonction());
			this.remplirChamp("residenceAdmin", om.getAgent().getResidenceAdmin());
			this.remplirChamp("uniteTravail", om.getAgent().getUniteTravail());
			this.remplirChamp("codeAnalytique", String.valueOf(om.getAgent().getCodeAnalytique()));
			this.remplirChamp("coefficient", String.valueOf(om.getAgent().getCoefficient()));

			if (om.getMission() instanceof MissionPermanent) {
				MissionPermanent mission = (MissionPermanent) om.getMission();
				this.remplirChamp("ordrePermanent", "Yes");
			} else {
				MissionTemporaire mission = (MissionTemporaire) om.getMission();
				this.remplirChamp("ordrePonctuel", "Yes");
				this.remplirChamp("dateOM", "De " + mission.getDateDebut() + ' ' + mission.getHeureDebut() + " au "
						+ mission.getDateFin() + ' ' + mission.getHeureFin());
				this.remplirChamp("motifDeplacement", mission.getMotifDeplacement());
				this.remplirChamp("lieuDeplacement", mission.getLieuDeplacement());
				if ("fonctionHabituelle".equals(mission.getTitre())) {
					this.remplirChamp("fonctionHabituelle", "Yes");
				} else if ("formation".equals(mission.getTitre())) {
					this.remplirChamp("formation", "Yes");
				}
			}

			if (om.getTransport() instanceof Voiture) {
				Voiture voiture = (Voiture) om.getTransport();
				this.remplirChamp("voiture", "Yes");
				this.remplirChamp("typeVoiture", voiture.getTypeVoiture());
				this.remplirChamp("immatriculation", voiture.getImmatriculation());
				this.remplirChamp("nbrCV", String.valueOf(voiture.getNbrCV()));
				if ("vehiculeService".equals(voiture.getAppartenanceVehicule())) {
					this.remplirChamp("vehiculeService", "Yes");
				} else {
					this.remplirChamp("vehiculePerso", "Yes");
				}
			} else {
				if (om.getTransport() instanceof Train) {
					Train train = (Train) om.getTransport();
					this.remplirChamp("train", "Yes");
					if ("premiereClasse".equals(train.getClasse())) {
						this.remplirChamp("premiereClasse", "Yes");
					} else {
						this.remplirChamp("deuxiemeClasse", "Yes");
					}
					if("oui".equals(train.getPrisParCRAMCO())) {
						this.remplirChamp("oui", "Yes");
					}
					else if("non".equals(train.getPrisParCRAMCO())) {
						this.remplirChamp("non", "Yes");
					}
					else {
						this.remplirChamp("autre", "Yes");
						this.remplirChamp("non", "Yes");
						this.remplirChamp("infos", train.getPrisParCRAMCO());
					}
				}
				else {
					
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		PDF pdf = new PDF(new File("target/PDF/OM_vide.pdf"));
		Agent agent = new Agent("Berduck", "Maxime", 12345, "progr", "DUT", "2A", 200, 1234567891);
		Transport avion = new Avion("oui");
		Transport voiture = new Voiture("course", "11-qqq-44", 120, "vehiculePersonnel");
		Transport train = new Train("premiereClasse", "bonjour");
		Mission mission = new MissionTemporaire("12/12/2019", "12:12", "14/12/2019", "20:12", "test", "Limoges",
				"formation");
		OrdreMission OM = new OrdreMission(agent, mission, train);
		pdf.remplirPDF(OM);
		pdf.sauvegarderPDF();
		pdf.fermerPDF();
	}
}
