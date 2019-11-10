package fr.iut.groupemaxime.gestioncarsat.model;

import java.io.File;
import java.sql.Time;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
		try {
			//Récupération d'une instance de la classe "DocumentBuilderFactory"
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			try {
				//Création d'un parseur
				final DocumentBuilder builder = factory.newDocumentBuilder();

				//Création du document
				final Document document = builder.newDocument();

				//Création de l'élément racine
				final Element om = document.createElement("OM");
				document.appendChild(om);

				exportAgent(document, om);
				
				if(this.mission instanceof MissionPermanent) {
					exportMissionPermanent(document, om);
				}
				else if(this.mission instanceof MissionTemporaire) {
					exportMissionTemporaire(document, om);
				}
				
				if(this.transport instanceof Avion) {
					exportAvion(document, om);
				}
				else if(this.transport instanceof Voiture) {
					exportVoiture(document, om);
				}
				else if(this.transport instanceof Train) {
					exportTrain(document, om);
				}

				//Affichage dans le document
				final TransformerFactory transformerFactory = TransformerFactory.newInstance();
				final Transformer transformer = transformerFactory.newTransformer();
				final DOMSource source = new DOMSource(document);
				final StreamResult sortie = new StreamResult(file);
				// final StreamResult result = new StreamResult(System.out);

				// prologue
				transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

				// formatage
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

				// sortie
				transformer.transform(source, sortie);
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

			}
		} finally {

		}
	}

	private void exportMissionPermanent(final Document document, final Element om) {
		final Element missionPermanent = document.createElement("missionPermanent");
		om.appendChild(missionPermanent);
	}

	private void exportMissionTemporaire(final Document document, final Element om) {
		MissionTemporaire missionTempo = (MissionTemporaire) this.mission;
		
		final Element missionTemporaire = document.createElement("missionTemporaire");
		om.appendChild(missionTemporaire);
		
		final Element dateDebut = document.createElement("dateDebut");
		dateDebut.appendChild(document.createTextNode(String.valueOf(missionTempo.getDateDebut())));
		
		final Element heureDebut = document.createElement("heureDebut");
		heureDebut.appendChild(document.createTextNode(String.valueOf(missionTempo.getHeureDebut())));
		
		final Element dateFin = document.createElement("dateFin");
		dateFin.appendChild(document.createTextNode(String.valueOf(missionTempo.getDateFin())));
		
		final Element heureFin = document.createElement("heureFin");
		heureFin.appendChild(document.createTextNode(String.valueOf(missionTempo.getHeureFin())));
		
		final Element motifDeplaElement = document.createElement("motifDeplacement");
		motifDeplaElement.appendChild(document.createTextNode(missionTempo.getMotifDeplacement()));
		
		final Element lieuDeplacement = document.createElement("lieuDeplacement");
		lieuDeplacement.appendChild(document.createTextNode(missionTempo.getLieuDeplacement()));
		
		final Element titre = document.createElement("titre");
		titre.appendChild(document.createTextNode(missionTempo.getTitre()));
		
		missionTemporaire.appendChild(dateDebut);
		missionTemporaire.appendChild(heureDebut);
		missionTemporaire.appendChild(dateFin);
		missionTemporaire.appendChild(heureFin);
		missionTemporaire.appendChild(motifDeplaElement);
		missionTemporaire.appendChild(lieuDeplacement);
		missionTemporaire.appendChild(titre);
	}

	private void exportAvion(final Document document, final Element om) {
		Avion avion1 = (Avion) this.transport;
		final Element avion = document.createElement("avion");
		om.appendChild(avion);
		
		final Element prisParCRAMCO = document.createElement("prisParCRAMCO");
		prisParCRAMCO.appendChild(document.createTextNode(avion1.getPrisParCRAMCO()));
		
		avion.appendChild(prisParCRAMCO);
	}

	private void exportVoiture(final Document document, final Element om) {
		Voiture voiture1 = (Voiture) this.transport;
		final Element voiture = document.createElement("voiture");
		om.appendChild(voiture);
		
		final Element typeVoiture = document.createElement("typeVoiture");
		typeVoiture.appendChild(document.createTextNode(voiture1.getTypeVoiture()));
		
		final Element immatriculation = document.createElement("immatriculation");
		immatriculation.appendChild(document.createTextNode(voiture1.getImmatriculation()));
		
		final Element nbrCV = document.createElement("nbrCV");
		nbrCV.appendChild(document.createTextNode(String.valueOf(voiture1.getNbrCV())));
		
		final Element appartenanceVehicule = document.createElement("appartenanceVehicule");
		appartenanceVehicule.appendChild(document.createTextNode(voiture1.getAppartenanceVehicule()));
		
		voiture.appendChild(typeVoiture);
		voiture.appendChild(immatriculation);
		voiture.appendChild(nbrCV);
		voiture.appendChild(appartenanceVehicule);
	}

	private void exportTrain(final Document document, final Element om) {
		Train train1 = (Train) this.transport;
		final Element train = document.createElement("train");
		om.appendChild(train);
		
		final Element classe = document.createElement("classe");
		classe.appendChild(document.createTextNode(train1.getClasse()));
		
		final Element prisParCRAMCO = document.createElement("prisParCRAMCO");
		prisParCRAMCO.appendChild(document.createTextNode(train1.getPrisParCRAMCO()));
		
		train.appendChild(classe);
		train.appendChild(prisParCRAMCO);
	}

	private void exportAgent(final Document document, final Element om) {
		final Element agent = document.createElement("Agent");
		om.appendChild(agent);

		final Element nom = document.createElement("nom");
		nom.appendChild(document.createTextNode(this.agent.getNom()));

		final Element prenom = document.createElement("prenom");
		prenom.appendChild(document.createTextNode(this.agent.getPrenom()));

		final Element numCAPSSA = document.createElement("numCAPSSA");
		numCAPSSA.appendChild(document.createTextNode(String.valueOf(this.agent.getNumCAPSSA())));

		final Element fonction = document.createElement("fonction");
		fonction.appendChild(document.createTextNode(this.agent.getFonction()));

		final Element residenceAdmin = document.createElement("residenceAdmin");
		residenceAdmin.appendChild(document.createTextNode(this.agent.getResidenceAdmin()));

		final Element uniteTravail = document.createElement("uniteTravail");
		uniteTravail.appendChild(document.createTextNode(this.agent.getUniteTravail()));

		final Element codeAnalytique = document.createElement("codeAnalytique");
		codeAnalytique.appendChild(document.createTextNode(String.valueOf(this.agent.getCodeAnalytique())));

		final Element coefficient = document.createElement("coefficient");
		coefficient.appendChild(document.createTextNode(String.valueOf(this.agent.getCoefficient())));

		agent.appendChild(nom);
		agent.appendChild(prenom);
		agent.appendChild(numCAPSSA);
		agent.appendChild(fonction);
		agent.appendChild(residenceAdmin);
		agent.appendChild(uniteTravail);
		agent.appendChild(codeAnalytique);
		agent.appendChild(coefficient);
	}

	public static void main(String[] args) {
		Agent agent = new Agent("Berduck", "Maxime", 12345, "progr", "DUT", "2A", 200, 123);
		Transport avion = new Avion("oui");
		Transport voiture = new Voiture("course", "11-qqq-44", 120, "vehiculePersonnel");
		Transport train = new Train("premiereClasse", "oui");
		Mission mission = new MissionTemporaire(new Date(),new Time(0, 0, 0), new Date(), new Time(0, 0, 0),"test", "Limoges", "Rien");
		OrdreMission OM = new OrdreMission(agent, mission, avion);
		OrdreMission OM1 = new OrdreMission(agent, mission, train);
		OrdreMission OM2 = new OrdreMission(agent, mission, voiture);
		
		OM.sauvegarder(new File("target/PDF/OM.xml"));
		OM1.sauvegarder(new File("target/PDF/OM1.xml"));
		OM2.sauvegarder(new File("target/PDF/OM2.xml"));

	}

}
