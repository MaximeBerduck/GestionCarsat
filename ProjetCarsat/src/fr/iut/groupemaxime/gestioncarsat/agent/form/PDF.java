package fr.iut.groupemaxime.gestioncarsat.agent.form;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisJournalier;
import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisMission;
import fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model.HoraireTravail;
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
import fr.iut.groupemaxime.gestioncarsat.utils.Bibliotheque;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import fr.iut.groupemaxime.gestioncarsat.utils.Options;

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

	public void remplirPdfFM(FraisMission fm, Options options) throws IOException {
		// TODO Gérer les missions de plusieurs semaines
		this.remplirChamp("dateDebutMission", fm.getDateDebutMission());
		this.remplirChamp("dateFinMission", fm.getDateFinMission());
		Calendar c = Calendar.getInstance();
		int repasForfaitTotal = 0;
		int repasJustifTotal = 0;
		int decouchForfaitTotal = 0;
		int decouchJustifTotal = 0;
		float serviceTotal = 0;
		float persoTotal = 0;

		HashSet<String> typeAutreFrais = new HashSet<String>();
		float montantAutreFrais = 0;

		for (FraisJournalier fj : fm.getFraisMission().values()) {
			try {
				repasForfaitTotal += fj.getNbrRepasForfait();
				repasJustifTotal += fj.getMontantJustifRepas();
				decouchForfaitTotal += fj.getNbrDecouchForfait();
				decouchJustifTotal += fj.getMontantJustifDecouchers();
				serviceTotal += fj.getNbrKmVehiService();
				persoTotal += fj.getNbrKmVehiPerso();
				montantAutreFrais += fj.getMontantAutreFrais();
				if (null != fj.getTypeAutreFrais())
					typeAutreFrais.add(fj.getTypeAutreFrais());

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
					this.remplirChamp("repasJustifDimanche", String.valueOf(fj.getMontantJustifRepas() + "€"));

					this.remplirChamp("decouchForfaitDimanche", String.valueOf(fj.getNbrDecouchForfait()));
					this.remplirChamp("decouchJustifDimanche", String.valueOf(fj.getMontantJustifDecouchers() + "€"));

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
					this.remplirChamp("repasJustifLundi", String.valueOf(fj.getMontantJustifRepas() + "€"));
					this.remplirChamp("decouchForfaitLundi", String.valueOf(fj.getNbrDecouchForfait()));
					this.remplirChamp("decouchJustifLundi", String.valueOf(fj.getMontantJustifDecouchers() + "€"));

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
					this.remplirChamp("repasJustifMardi", String.valueOf(fj.getMontantJustifRepas()) + "€");
					this.remplirChamp("decouchForfaitMardi", String.valueOf(fj.getNbrDecouchForfait()));
					this.remplirChamp("decouchJustifMardi", String.valueOf(fj.getMontantJustifDecouchers() + "€"));

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
					this.remplirChamp("repasJustifMercredi", String.valueOf(fj.getMontantJustifRepas() + "€"));
					this.remplirChamp("decouchForfaitMercredi", String.valueOf(fj.getNbrDecouchForfait()));
					this.remplirChamp("decouchJustifMercredi", String.valueOf(fj.getMontantJustifDecouchers() + "€"));

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
					this.remplirChamp("repasJustifJeudi", String.valueOf(fj.getMontantJustifRepas() + "€"));
					this.remplirChamp("decouchForfaitJeudi", String.valueOf(fj.getNbrDecouchForfait()));
					this.remplirChamp("decouchJustifJeudi", String.valueOf(fj.getMontantJustifDecouchers() + "€"));

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
					this.remplirChamp("repasJustifVendredi", String.valueOf(fj.getMontantJustifRepas() + "€"));
					this.remplirChamp("decouchForfaitVendredi", String.valueOf(fj.getNbrDecouchForfait()));
					this.remplirChamp("decouchJustifVendredi", String.valueOf(fj.getMontantJustifDecouchers() + "€"));

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
					this.remplirChamp("repasJustifSamedi", String.valueOf(fj.getMontantJustifRepas() + "€"));
					this.remplirChamp("decouchForfaitSamedi", String.valueOf(fj.getNbrDecouchForfait()));
					this.remplirChamp("decouchJustifSamedi", String.valueOf(fj.getMontantJustifDecouchers() + "€"));

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

			this.remplirChamp("repasForfaitTotal", String.valueOf(repasForfaitTotal));
			this.remplirChamp("repasJustifTotal", String.valueOf(repasJustifTotal) + "€");
			this.remplirChamp("decouchForfaitTotal", String.valueOf(decouchForfaitTotal));
			this.remplirChamp("decouchJustifTotal", String.valueOf(decouchJustifTotal) + "€");
			this.remplirChamp("serviceTotal", String.valueOf(serviceTotal));
			this.remplirChamp("persoTotal", String.valueOf(persoTotal));
			String typeFrais = "";
			for (String i : typeAutreFrais) {
				typeFrais += i + ',';
			}
			if (typeFrais.length() > 1) {
				typeFrais = typeFrais.substring(0, typeFrais.length() - 1);
			}
			this.remplirChamp("typeAutreFrais", typeFrais);
			this.remplirChamp("montantAutreFrais", String.valueOf(montantAutreFrais) + "€");

		}

		this.cheminFichier = fm.getAdresseFichier().replace(".json", ".pdf").replace("FM_", "OM_");
		this.sauvegarderPDF();
	}

	public void signerPdfFM(FraisMission fm, Options options) throws IOException {
		this.remplirChamp("informationsOK", "Yes");
		this.remplirChamp("deduireFraisCheck", "Yes");
		this.remplirChamp("montantDeductionFrais", String.valueOf(fm.getMontantDeductionFrais()));
		this.remplirChamp("avanceCheck", "Yes");
		this.remplirChamp("montantAvance", String.valueOf(fm.getMontantAvance()));
		this.remplirChamp("repasCheck", "Yes");
		this.remplirChamp("nbrRepasMidiOfferts", String.valueOf(fm.getNbrRepasOffert()));
		this.ajouterDateSignatureFMAgent(fm.getDateSignature());
		this.sauvegarderPDF();
		this.signerPdfFM(Constante.SIGNATURE_AGENT_FM_X, Constante.SIGNATURE_AGENT_FM_Y, Constante.TAILLE_SIGNATURE_FM,
				fm, options.getCheminSignature());
		this.fermerPDF();
	}

	public void signerPdfFM(int x, int y, int taille, FraisMission fm, String signature) {
		String cheminFM = fm.getAdresseFichier().replace(".json", ".pdf").replace("FM_", "OM_");
		this.signerPdf(x, y, taille, cheminFM, signature, 1);
	}

	public static void signerPdfFmResponsable(int x, int y, int taille, String cheminPDF, String cheminSignature) {
		try {
			PDF pdf = new PDF(new File(cheminPDF));
			pdf.cheminFichier = cheminPDF;
			pdf.ajouterDateSignatureFMResponsable(Bibliotheque.getDateAujourdhui());
			pdf.sauvegarderPDF();
			pdf.modele.close();
			pdf.signerPdf(x, y, taille, cheminPDF, cheminSignature, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void signerPdf(int x, int y, int taille, String cheminPDF, String cheminSignature, int numPage) {
		try {
			PDDocument pdf = PDDocument.load(new File(cheminPDF));
			PDPage page = pdf.getPage(numPage);

			PDImageXObject pdImage = PDImageXObject.createFromFile(cheminSignature, pdf);
			PDPageContentStream contentStream = new PDPageContentStream(pdf, page, AppendMode.APPEND, true);

			contentStream.drawImage(pdImage, x, y, taille, taille);
			contentStream.close();
			pdf.save(cheminPDF);
			pdf.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void signerPDFOM(int x, int y, int taille, OrdreMission om, String signature) {
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

	public void ajouterDateSignatureOMAgent(String date) {

		this.remplirChamp("dateSigna", date);
	}

	public void ajouterDateSignatureFMAgent(String date) {

		this.remplirChamp("dateSignatureAgent", date);

	}

	public void ajouterDateSignatureFMResponsable(String date) {

		this.remplirChamp("dateSignResponsable", date);

	}

	public void ajouterDateSignatureOMResponsable(String date) {
		this.remplirChamp("dateSignResOM", date);
	}

	public void completerExcel(HoraireTravail ht) {
		try {
			FileInputStream excelFile = new FileInputStream(new File(Constante.CHEMIN_EXCEL_VIDE));
			Workbook workbook = new HSSFWorkbook(excelFile);
			Sheet dataSheet = workbook.getSheetAt(0);
			Cell cell = dataSheet.getRow(17).getCell(3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void signerPdfOmResponsable(int x, int y, int taille, String cheminPDF, String cheminSignature) {
		try {
			PDF pdf = new PDF(new File(cheminPDF));
			pdf.cheminFichier = cheminPDF;
			pdf.ajouterDateSignatureOMResponsable(Bibliotheque.getDateAujourdhui());
			pdf.sauvegarderPDF();
			pdf.modele.close();
			pdf.signerPdf(x, y, taille, cheminPDF, cheminSignature, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static boolean signerHTResponsable(String cheminXls, String cheminSignature) {

		Workbook workbook;
		FileInputStream excelFile;
		try {
			excelFile = new FileInputStream(new File(cheminXls));
			workbook = new HSSFWorkbook(excelFile);
			Sheet dataSheet = workbook.getSheetAt(0);
			int ligne = 15;
			while (!"Date et signature :".equals(dataSheet.getRow(ligne).getCell(1).getStringCellValue())) {
				ligne++;
			}
			dataSheet.getRow(ligne + 1).getCell(5).setCellValue(Bibliotheque.getDateAujourdhui());
			final FileInputStream stream = new FileInputStream(cheminSignature);
			final CreationHelper helper = workbook.getCreationHelper();
			final Drawing drawing = dataSheet.createDrawingPatriarch();

			final ClientAnchor anchor = helper.createClientAnchor();
			anchor.setAnchorType(ClientAnchor.MOVE_AND_RESIZE);

			final int pictureIndex = workbook.addPicture(IOUtils.toByteArray(stream), Workbook.PICTURE_TYPE_PNG);

			anchor.setCol1(7);
			anchor.setRow1(ligne - 1);
			anchor.setRow2(ligne + 2);
			anchor.setCol2(9);
			final Picture pict = drawing.createPicture(anchor, pictureIndex);

			FileOutputStream out = new FileOutputStream(new File(cheminXls));
			workbook.write(out);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
