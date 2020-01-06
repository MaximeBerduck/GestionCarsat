package fr.iut.groupemaxime.gestioncarsat.agent.form;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import fr.iut.groupemaxime.gestioncarsat.agent.model.Agent;
import fr.iut.groupemaxime.gestioncarsat.agent.model.AutreTransport;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Avion;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Constante;
import fr.iut.groupemaxime.gestioncarsat.agent.model.MissionPermanent;
import fr.iut.groupemaxime.gestioncarsat.agent.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.agent.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Train;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Transport;
import fr.iut.groupemaxime.gestioncarsat.agent.model.TypeMission;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Voiture;

public class PDF {
	private PDDocument modele;
	private PDAcroForm formulaire;
	private PDField champ;
	private String cheminFichier;

	public PDF(File source) throws IOException {
		this.modele = PDDocument.load(source);
		this.formulaire = this.modele.getDocumentCatalog().getAcroForm();
		this.champ = null;
		this.cheminFichier = null;
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

	public OrdreMission chargerPDFtoOM() {
		StringBuilder sb = new StringBuilder();
		String part;
		for (int i = 0; i < 8; i++) {
			part = this.formulaire.getField("numCAPSSA" + i).getValueAsString();
			sb.append(part);
		}
		int cappsa = Integer.valueOf(sb.toString());
		for (int i = 0; i < 8; i++) {
			part = this.formulaire.getField("codeAnalytique" + i).getValueAsString();
			sb.append(part);
		}
		int codeAnalytique = Integer.valueOf(sb.toString());
		Agent agent = new Agent(this.formulaire.getField("nomPrenom").getValueAsString().split(" ")[0],
				this.formulaire.getField("nomPrenom").getValueAsString().split(" ")[1], cappsa,
				this.formulaire.getField("fonction").getValueAsString(),
				this.formulaire.getField("residenceAdmin").getValueAsString(),
				this.formulaire.getField("uniteTravail").getValueAsString(),
				Integer.valueOf(this.formulaire.getField("coefficient").getValueAsString()), codeAnalytique);
		String titre;
		if (this.formulaire.getField("fonctionHabituelle").getValueAsString().equals("Yes")) {
			titre = "fonctionHabituelle";
		} else {
			titre = "formation";
		}
		TypeMission mission = new MissionTemporaire(this.formulaire.getField("dateOM").getValueAsString().split(" ")[1],
				this.formulaire.getField("dateOM").getValueAsString().split(" ")[2],
				this.formulaire.getField("dateOM").getValueAsString().split(" ")[4],
				this.formulaire.getField("dateOM").getValueAsString().split(" ")[5],
				this.formulaire.getField("motifDeplacement").getValueAsString(),
				this.formulaire.getField("lieuDeplacement").getValueAsString(), titre);
		Transport transport;
		if (this.formulaire.getField("train").getValueAsString().equals("Yes")) {
			String classe;
			if (this.formulaire.getField("deuxiemeClasse").getValueAsString().equals("Yes")) {
				classe = "deuxiemeClasse";
			} else {
				classe = "premiereClasse";
			}
			String cramco;
			if (this.formulaire.getField("oui").getValueAsString().equals("Yes")) {
				cramco = "Oui";
			} else {
				cramco = "Non";
			}
			transport = new Train(classe, cramco);
		} else if (this.formulaire.getField("avion").getValueAsString().equals("Yes")) {
			String cramco;
			if (this.formulaire.getField("oui").getValueAsString().equals("Yes")) {
				cramco = "Oui";
			} else {
				cramco = "Non";
			}
			transport = new Avion(cramco);
		} else if (this.formulaire.getField("voiture").getValueAsString().equals("Yes")) {
			String appartenance;
			if (this.formulaire.getField("vehiculePerso").getValueAsString().equals("Yes")) {
				appartenance = "vehiculePerso";
			}else {
				appartenance = "vehiculeService";
			}
			transport = new Voiture(this.formulaire.getField("typeVoiture").getValueAsString(),
					this.formulaire.getField("immatriculation").getValueAsString(),
					Integer.valueOf(this.formulaire.getField("nbrCV").getValueAsString()), appartenance);
		} else {
			transport = new AutreTransport(this.formulaire.getField("autreChamp").getValueAsString());
		}
		return new OrdreMission(agent, mission, transport);
	}

	public void sauvegarderPDF() throws IOException {
		this.modele.save(this.cheminFichier);
	}

	public void fermerPDF() throws IOException {
		this.modele.close();
	}

	public void remplirPDF(OrdreMission om) {

		if (null != om) {
			this.cheminFichier = om.getCheminDossier() + om.getNomOM() + Constante.EXTENSION_PDF;
			this.remplirChamp("nomPrenom", om.getAgent().getNom() + ' ' + om.getAgent().getPrenom());

			char[] numCapssa = String.valueOf(om.getAgent().getNumCAPSSA()).toCharArray();
			for (int i = 0; i < numCapssa.length; i++) {
				this.remplirChamp("numCAPSSA" + i, String.valueOf(numCapssa[i]));
			}

			this.remplirChamp("fonction", om.getAgent().getFonction());
			this.remplirChamp("residenceAdmin", om.getAgent().getResidenceAdmin());
			this.remplirChamp("uniteTravail", om.getAgent().getUniteTravail());

			char[] codeAnalytique = String.valueOf(om.getAgent().getCodeAnalytique()).toCharArray();
			for (int i = 0; i < codeAnalytique.length; i++) {
				this.remplirChamp("codeAnalytique" + i, String.valueOf(codeAnalytique[i]));
			}

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
			} else if (om.getTransport() instanceof Train) {
				Train train = (Train) om.getTransport();
				this.remplirChamp("train", "Yes");
				if ("premiereClasse".equals(train.getClasse())) {
					this.remplirChamp("premiereClasse", "Yes");
				} else {
					this.remplirChamp("deuxiemeClasse", "Yes");
				}
				if ("oui".equals(train.getPrisParCRAMCO())) {
					this.remplirChamp("oui", "Yes");
				} else if ("non".equals(train.getPrisParCRAMCO())) {
					this.remplirChamp("non", "Yes");
				} else {
					this.remplirChamp("autre", "Yes");
					this.remplirChamp("non", "Yes");
					this.remplirChamp("infos", train.getPrisParCRAMCO());
				}
			} else if (om.getTransport() instanceof Avion) {
				Avion avion = (Avion) om.getTransport();
				this.remplirChamp("avion", "Yes");
				if ("oui".equals(avion.getPrisParCRAMCO())) {
					this.remplirChamp("oui", "Yes");
				} else if ("non".equals(avion.getPrisParCRAMCO())) {
					this.remplirChamp("non", "Yes");
				}
			} else if (om.getTransport() instanceof AutreTransport) {
				this.remplirChamp("autre", "Yes");
				this.remplirChamp("autreChamp", ((AutreTransport) om.getTransport()).getAutreTransport());
			}
		}
	}

	public static void signerPDF(int x, int y, int taille, OrdreMission om, String signature) {
		String cheminFichier = om.getCheminDossier() + om.getNomOM() + Constante.EXTENSION_PDF;
		try {
			PDDocument pdf = PDDocument.load(new File(cheminFichier));
			PDPage page = pdf.getPage(0);

			PDImageXObject pdImage = PDImageXObject.createFromFile(signature, pdf);
			PDPageContentStream contentStream = new PDPageContentStream(pdf, page, AppendMode.APPEND, true);

			contentStream.drawImage(pdImage, x, y, taille, taille);
			contentStream.close();
			pdf.save(cheminFichier);
			pdf.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
