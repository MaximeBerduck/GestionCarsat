package fr.iut.groupemaxime.gestioncarsat.model;

import java.io.File;
import java.io.IOException;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class OrdreMission {

	private Agent agent;
	private Mission mission;
	private Transport transport;
	private String fichier;

	public OrdreMission(Agent agent, Mission mission, Transport transport, String fichier) {
		this.agent = agent;
		this.mission = mission;
		this.transport = transport;
		this.fichier = fichier;
	}

	public OrdreMission(Agent agent, Mission mission, Transport transport) {
		this(agent, mission, transport, null);
	}

	public void sauvegarder(File file) {
		try {
			// Récupération d'une instance de la classe "DocumentBuilderFactory"
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			try {
				// Création d'un parseur
				final DocumentBuilder builder = factory.newDocumentBuilder();

				// Création du document
				final Document document = builder.newDocument();

				// Création de l'élément racine
				final Element om = document.createElement("OM");
				document.appendChild(om);

				exportAgent(document, om);

				if (this.mission instanceof MissionPermanent) {
					exportMissionPermanent(document, om);
				} else if (this.mission instanceof MissionTemporaire) {
					exportMissionTemporaire(document, om);
				}

				if (this.transport instanceof Avion) {
					exportAvion(document, om);
				} else if (this.transport instanceof Voiture) {
					exportVoiture(document, om);
				} else if (this.transport instanceof Train) {
					exportTrain(document, om);
				}

				// Affichage dans le document
				final TransformerFactory transformerFactory = TransformerFactory.newInstance();
				final Transformer transformer = transformerFactory.newTransformer();
				final DOMSource source = new DOMSource(document);
				final StreamResult sortie = new StreamResult(
						new File(Constante.CHEMIN_OM_DEFAUT + file.toString() + Constante.EXTENSION_XML));
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
				this.fichier = file.toString();
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

	public static OrdreMission importer(File file) {
		Agent agentOM = null;
		Transport transportOM = null;
		OrdreMission om = null;
		Mission missionOM = null;
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();
			try {
				final Document document = builder.parse(Constante.CHEMIN_OM_DEFAUT + file + Constante.EXTENSION_XML);

				// Importation de l'agent
				agentOM = importerAgent(agentOM, document);

				// Importation de la missionTemporaire si temporaire
				missionOM = importerMissionTemporaire(missionOM, document);

				// Importation de la missionPermanent si permanente
				missionOM = importerMissionPermanent(missionOM, document);

				// Importation du Train si le transport est le train
				transportOM = importerTrain(transportOM, document);

				// Importation de la Voiture si le transport est la voiture
				transportOM = importerVoiture(transportOM, document);

				// Importation de l'Avion si le transport est l'avion
				transportOM = importerAvion(transportOM, document);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		om = new OrdreMission(agentOM, missionOM, transportOM, file.toString());
		return om;
	}

	private static Transport importerAvion(Transport transportOM, final Document document) {
		final NodeList avion = document.getElementsByTagName("avion");
		for (int i = 0; i < avion.getLength(); i++) {
			Node node = avion.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {

				// Récupère l'élément
				Element avionElement = (Element) node;

				// Objet MissionTemporaire
				transportOM = new Avion(avionElement.getElementsByTagName("prisParCRAMCO").item(0).getTextContent());
			}
		}
		return transportOM;
	}

	private static Transport importerVoiture(Transport transportOM, final Document document) {
		final NodeList voiture = document.getElementsByTagName("voiture");
		for (int i = 0; i < voiture.getLength(); i++) {
			Node node = voiture.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {

				// Récupère l'élément
				Element voitureElement = (Element) node;

				// Objet MissionTemporaire
				transportOM = new Voiture(voitureElement.getElementsByTagName("typeVoiture").item(0).getTextContent(),
						voitureElement.getElementsByTagName("immatriculation").item(0).getTextContent(),
						Integer.parseInt(voitureElement.getElementsByTagName("nbrCV").item(0).getTextContent()),
						voitureElement.getElementsByTagName("appartenanceVehicule").item(0).getTextContent());
			}
		}
		return transportOM;
	}

	private static Transport importerTrain(Transport transportOM, final Document document) {
		final NodeList train = document.getElementsByTagName("train");
		for (int i = 0; i < train.getLength(); i++) {
			Node node = train.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {

				// Récupère l'élément
				Element trainElement = (Element) node;

				// Objet MissionTemporaire
				transportOM = new Train(trainElement.getElementsByTagName("classe").item(0).getTextContent(),
						trainElement.getElementsByTagName("prisParCRAMCO").item(0).getTextContent());
			}
		}
		return transportOM;
	}

	private static Mission importerMissionPermanent(Mission missionOM, final Document document) {
		final NodeList missionPerma = document.getElementsByTagName("missionPermanent");
		for (int i = 0; i < missionPerma.getLength(); i++) {
			Node node = missionPerma.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {

				// Récupère l'élément
				Element missionElement = (Element) node;

				// Objet MissionTemporaire
				missionOM = new MissionPermanent();
			}
		}
		return missionOM;
	}

	private static Mission importerMissionTemporaire(Mission missionOM, final Document document) {
		final NodeList missionTempo = document.getElementsByTagName("missionTemporaire");
		for (int i = 0; i < missionTempo.getLength(); i++) {
			Node node = missionTempo.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {

				// Récupère l'élément
				Element missionElement = (Element) node;

				// Objet MissionTemporaire
				missionOM = new MissionTemporaire(
						missionElement.getElementsByTagName("dateDebut").item(0).getTextContent(),
						missionElement.getElementsByTagName("heureDebut").item(0).getTextContent(),
						missionElement.getElementsByTagName("dateFin").item(0).getTextContent(),
						missionElement.getElementsByTagName("heureFin").item(0).getTextContent(),
						missionElement.getElementsByTagName("motifDeplacement").item(0).getTextContent(),
						missionElement.getElementsByTagName("lieuDeplacement").item(0).getTextContent(),
						missionElement.getElementsByTagName("titre").item(0).getTextContent());
			}
		}
		return missionOM;
	}

	private static Agent importerAgent(Agent agentOM, final Document document) {
		final NodeList agent = document.getElementsByTagName("Agent");
		for (int i = 0; i < agent.getLength(); i++) {
			Node node = agent.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {

				// Récupère l'élément
				Element agentElement = (Element) node;

				// Objet Agent
				agentOM = new Agent(agentElement.getElementsByTagName("nom").item(0).getTextContent(),
						agentElement.getElementsByTagName("prenom").item(0).getTextContent(),
						Integer.parseInt(agentElement.getElementsByTagName("numCAPSSA").item(0).getTextContent()),
						agentElement.getElementsByTagName("fonction").item(0).getTextContent(),
						agentElement.getElementsByTagName("residenceAdmin").item(0).getTextContent(),
						agentElement.getElementsByTagName("uniteTravail").item(0).getTextContent(),
						Integer.parseInt(agentElement.getElementsByTagName("coefficient").item(0).getTextContent()),
						Integer.parseInt(agentElement.getElementsByTagName("codeAnalytique").item(0).getTextContent()));
			}
		}
		return agentOM;
	}

	public Agent getAgent() {
		return agent;
	}

	public Mission getMission() {
		return mission;
	}

	public Transport getTransport() {
		return transport;
	}

	public String getFichier() {
		return fichier;
	}
}
