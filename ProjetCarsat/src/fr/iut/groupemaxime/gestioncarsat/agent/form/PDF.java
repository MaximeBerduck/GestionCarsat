package fr.iut.groupemaxime.gestioncarsat.agent.form;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisJournalier;
import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisMission;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.Agent;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.AutreTransport;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.Avion;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.MissionPermanent;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.MissionTemporaire;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.Train;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.Transport;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.TypeMission;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.Voiture;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;

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
			} else {
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

	public void remplirPdfFM(FraisMission fm) throws IOException {

		this.remplirChamp("dateDebutMission", fm.getDateDebutMission());
		this.remplirChamp("dateFinMission", fm.getDateFinMission());
		Calendar c = Calendar.getInstance();

		for (FraisJournalier fj : fm.getFraisMission().values()) {
			try {
				c.setTime(Constante.FORMAT_DATE_SLASH.parse(fj.getDate()));

				switch (c.getTime().getDay()) {
				// Dimanche
				case 0:
					if (null != fj.getHeureDepart()) {
						this.remplirChamp("dateDepartDimanche", fj.getDate());
						this.remplirChamp("heureDepartDimanche", fj.getHeureDepart());
					}
					if (null != fj.getHeureRetour()) {
						this.remplirChamp("dateRetourDimanche", fj.getDate());
						this.remplirChamp("heureRetourDimanche", fj.getHeureRetour());
					}

					this.remplirChamp("repasForfaitDimanche", String.valueOf(fj.getNbrRepasForfait()));
					this.remplirChamp("repasJustifDimanche", String.valueOf(fj.getNbrRepasJustif()));
					this.remplirChamp("decouchForfaitDimanche", String.valueOf(fj.getNbrDecouchForfait()));
					this.remplirChamp("decouchJustifDimanche", String.valueOf(fj.getNbrDecouchJustif()));

					// TODO
					this.remplirChamp("motifDimanche", "");
					this.remplirChamp("lieuDimanche", "");

					this.remplirChamp("typeDimanche", fj.getTypeFraisTransport());
					this.remplirChamp("fraisDimanche", String.valueOf(fj.getMontantFraisTransport()));

					this.remplirChamp("serviceDimanche", String.valueOf(fj.getNbrKmVehiService()));
					this.remplirChamp("persoDimanche", String.valueOf(fj.getNbrKmVehiPerso()));
					break;
				// Lundi
				case 1:
					if (null != fj.getHeureDepart()) {
						this.remplirChamp("dateDepartLundi", fj.getDate());
						this.remplirChamp("heureDepartLundi", fj.getHeureDepart());
					}
					if (null != fj.getHeureRetour()) {
						this.remplirChamp("dateRetourLundi", fj.getDate());
						this.remplirChamp("heureRetourLundi", fj.getHeureRetour());
					}

					this.remplirChamp("repasForfaitLundi", String.valueOf(fj.getNbrRepasForfait()));
					this.remplirChamp("repasJustifLundi", String.valueOf(fj.getNbrRepasJustif()));
					this.remplirChamp("decouchForfaitLundi", String.valueOf(fj.getNbrDecouchForfait()));
					this.remplirChamp("decouchJustifLundi", String.valueOf(fj.getNbrDecouchJustif()));

					// TODO
					this.remplirChamp("motifLundi", "");
					this.remplirChamp("lieuLundi", "");

					this.remplirChamp("typeLundi", fj.getTypeFraisTransport());
					this.remplirChamp("fraisLundi", String.valueOf(fj.getMontantFraisTransport()));

					this.remplirChamp("serviceLundi", String.valueOf(fj.getNbrKmVehiService()));
					this.remplirChamp("persoLundi", String.valueOf(fj.getNbrKmVehiPerso()));
					break;
				// Mardi
				case 2:
					if (null != fj.getHeureDepart()) {
						this.remplirChamp("dateDepartMardi", fj.getDate());
						this.remplirChamp("heureDepartMardi", fj.getHeureDepart());
					}
					if (null != fj.getHeureRetour()) {
						this.remplirChamp("dateRetourMardi", fj.getDate());
						this.remplirChamp("heureRetourMardi", fj.getHeureRetour());
					}

					this.remplirChamp("repasForfaitMardi", String.valueOf(fj.getNbrRepasForfait()));
					this.remplirChamp("repasJustifMardi", String.valueOf(fj.getNbrRepasJustif()));
					this.remplirChamp("decouchForfaitMardi", String.valueOf(fj.getNbrDecouchForfait()));
					this.remplirChamp("decouchJustifMardi", String.valueOf(fj.getNbrDecouchJustif()));

					// TODO
					this.remplirChamp("motifMardi", "");
					this.remplirChamp("lieuMardi", "");

					this.remplirChamp("typeMardi", fj.getTypeFraisTransport());
					this.remplirChamp("fraisMardi", String.valueOf(fj.getMontantFraisTransport()));

					this.remplirChamp("serviceMardi", String.valueOf(fj.getNbrKmVehiService()));
					this.remplirChamp("persoMardi", String.valueOf(fj.getNbrKmVehiPerso()));
					break;
				// Mercredi
				case 3:
					if (null != fj.getHeureDepart()) {
						this.remplirChamp("dateDepartMercredi", fj.getDate());
						this.remplirChamp("heureDepartMercredi", fj.getHeureDepart());
					}
					if (null != fj.getHeureRetour()) {
						this.remplirChamp("dateRetourMercredi", fj.getDate());
						this.remplirChamp("heureRetourMercredi", fj.getHeureRetour());
					}

					this.remplirChamp("repasForfaitMercredi", String.valueOf(fj.getNbrRepasForfait()));
					this.remplirChamp("repasJustifMercredi", String.valueOf(fj.getNbrRepasJustif()));
					this.remplirChamp("decouchForfaitMercredi", String.valueOf(fj.getNbrDecouchForfait()));
					this.remplirChamp("decouchJustifMercredi", String.valueOf(fj.getNbrDecouchJustif()));

					// TODO
					this.remplirChamp("motifMercredi", "");
					this.remplirChamp("lieuMercredi", "");

					this.remplirChamp("typeMercredi", fj.getTypeFraisTransport());
					this.remplirChamp("fraisMercredi", String.valueOf(fj.getMontantFraisTransport()));

					this.remplirChamp("serviceMercredi", String.valueOf(fj.getNbrKmVehiService()));
					this.remplirChamp("persoMercredi", String.valueOf(fj.getNbrKmVehiPerso()));
					break;
				// Jeudi
				case 4:
					if (null != fj.getHeureDepart()) {
						this.remplirChamp("dateDepartJeudi", fj.getDate());
						this.remplirChamp("heureDepartJeudi", fj.getHeureDepart());
					}
					if (null != fj.getHeureRetour()) {
						this.remplirChamp("dateRetourJeudi", fj.getDate());
						this.remplirChamp("heureRetourJeudi", fj.getHeureRetour());
					}

					this.remplirChamp("repasForfaitJeudi", String.valueOf(fj.getNbrRepasForfait()));
					this.remplirChamp("repasJustifJeudi", String.valueOf(fj.getNbrRepasJustif()));
					this.remplirChamp("decouchForfaitJeudi", String.valueOf(fj.getNbrDecouchForfait()));
					this.remplirChamp("decouchJustifJeudi", String.valueOf(fj.getNbrDecouchJustif()));

					// TODO
					this.remplirChamp("motifJeudi", "");
					this.remplirChamp("lieuJeudi", "");

					this.remplirChamp("typeJeudi", fj.getTypeFraisTransport());
					this.remplirChamp("fraisJeudi", String.valueOf(fj.getMontantFraisTransport()));

					this.remplirChamp("serviceJeudi", String.valueOf(fj.getNbrKmVehiService()));
					this.remplirChamp("persoJeudi", String.valueOf(fj.getNbrKmVehiPerso()));
					break;
				// Vendredi
				case 5:
					if (null != fj.getHeureDepart()) {
						this.remplirChamp("dateDepartVendredi", fj.getDate());
						this.remplirChamp("heureDepartVendredi", fj.getHeureDepart());
					}
					if (null != fj.getHeureRetour()) {
						this.remplirChamp("dateRetourVendredi", fj.getDate());
						this.remplirChamp("heureRetourVendredi", fj.getHeureRetour());
					}

					this.remplirChamp("repasForfaitVendredi", String.valueOf(fj.getNbrRepasForfait()));
					this.remplirChamp("repasJustifVendredi", String.valueOf(fj.getNbrRepasJustif()));
					this.remplirChamp("decouchForfaitVendredi", String.valueOf(fj.getNbrDecouchForfait()));
					this.remplirChamp("decouchJustifVendredi", String.valueOf(fj.getNbrDecouchJustif()));

					// TODO
					this.remplirChamp("motifVendredi", "");
					this.remplirChamp("lieuVendredi", "");

					this.remplirChamp("typeVendredi", fj.getTypeFraisTransport());
					this.remplirChamp("fraisVendredi", String.valueOf(fj.getMontantFraisTransport()));

					this.remplirChamp("serviceVendredi", String.valueOf(fj.getNbrKmVehiService()));
					this.remplirChamp("persoVendredi", String.valueOf(fj.getNbrKmVehiPerso()));
					break;
				// Samedi
				case 6:
					if (null != fj.getHeureDepart()) {
						this.remplirChamp("dateDepartSamedi", fj.getDate());
						this.remplirChamp("heureDepartSamedi", fj.getHeureDepart());
					}
					if (null != fj.getHeureRetour()) {
						this.remplirChamp("dateRetourSamedi", fj.getDate());
						this.remplirChamp("heureRetourSamedi", fj.getHeureRetour());
					}

					this.remplirChamp("repasForfaitSamedi", String.valueOf(fj.getNbrRepasForfait()));
					this.remplirChamp("repasJustifSamedi", String.valueOf(fj.getNbrRepasJustif()));
					this.remplirChamp("decouchForfaitSamedi", String.valueOf(fj.getNbrDecouchForfait()));
					this.remplirChamp("decouchJustifSamedi", String.valueOf(fj.getNbrDecouchJustif()));

					// TODO
					this.remplirChamp("motifSamedi", "");
					this.remplirChamp("lieuSamedi", "");

					this.remplirChamp("typeSamedi", fj.getTypeFraisTransport());
					this.remplirChamp("fraisSamedi", String.valueOf(fj.getMontantFraisTransport()));

					this.remplirChamp("serviceSamedi", String.valueOf(fj.getNbrKmVehiService()));
					this.remplirChamp("persoSamedi", String.valueOf(fj.getNbrKmVehiPerso()));
					break;

				default:
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		this.cheminFichier = fm.getAdresseFichier().replace(".json", ".pdf");
		this.sauvegarderPDF();
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
