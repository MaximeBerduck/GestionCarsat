package fr.iut.groupemaxime.gestioncarsat.model;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Agent {
	private String nom;
	private String prenom;
	private int numCAPSSA;
	private String fonction;
	private int coefficient;
	private String residenceAdmin;
	private String uniteTravail;
	private int codeAnalytique;

	public Agent(String nom, String prenom, int numCAPSSA, String fonction, String residenceAdmin, String uniteTravail,
			int coefficient, int codeAnalytique) {
		this.nom = nom;
		this.prenom = prenom;
		this.numCAPSSA = numCAPSSA;
		this.fonction = fonction;
		this.residenceAdmin = residenceAdmin;
		this.uniteTravail = uniteTravail;
		this.codeAnalytique = codeAnalytique;
		this.coefficient = coefficient;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public int getNumCAPSSA() {
		return numCAPSSA;
	}

	public String getFonction() {
		return fonction;
	}

	public int getCoefficient() {
		return coefficient;
	}

	public String getResidenceAdmin() {
		return residenceAdmin;
	}

	public String getUniteTravail() {
		return uniteTravail;
	}

	public int getCodeAnalytique() {
		return codeAnalytique;
	}

	public String toString() {
		return this.nom + "," + this.prenom + "," + this.numCAPSSA + "," + this.fonction + "," + this.residenceAdmin
				+ "," + this.uniteTravail + "," + this.codeAnalytique + "," + this.coefficient;

	}

}
