package fr.iut.groupemaxime.gestioncarsat.agent.model;

import java.io.File;
import java.util.Arrays;

public class Mail {
	private String expediteur;
	private String[] destinataires;
	private String[] enCopie;
	private String objetDuMail;
	private String corpsDuMail;
	private File fileEnPieceJointe;
	
	
	public Mail(String expe, String[] destinataires, String[] enCopie, String objetDuMail, String corpsDuMail, File fileEnPJ) {
		this.expediteur=expe;
		this.destinataires= destinataires;
		this.enCopie=enCopie;
		this.objetDuMail=objetDuMail;
		this.corpsDuMail=corpsDuMail;
		this.fileEnPieceJointe=fileEnPJ;
	}
	
	public String getExpediteur() {
		return expediteur;
	}

	public void setExpediteur(String expediteur) {
		this.expediteur = expediteur;
	}
	public String[] getDestinataires(){
		return this.destinataires;
	}
	
	public String getDestinatairesEnString() {
		return Arrays.toString(destinataires);
	}
	
	public void setDestinataires(String[] destinataires) {
		this.destinataires = destinataires;
	}

	public String[] getEnCopie() {
		return this.enCopie;
	}
	
	public String getEnCopieEnString(){
		return Arrays.toString(enCopie);
	}

	public void setEnCopie(String[] enCopie) {
		this.enCopie = enCopie;
	}

	public String getObjetDuMail() {
		return objetDuMail;
	}

	public void setObjetDuMail(String objetDuMail) {
		this.objetDuMail = objetDuMail;
	}

	public String getCorpsDuMail() {
		return corpsDuMail;
	}

	public void setCorpsDuMail(String corpsDuMail) {
		this.corpsDuMail = corpsDuMail;
	}

	public File getFileEnPieceJointe() {
		return fileEnPieceJointe;
	}

	public void setFileEnPieceJointe(File fileEnPieceJointe) {
		this.fileEnPieceJointe = fileEnPieceJointe;
	}

	public String toString() {
		return "Mail [expediteur=" + expediteur + ", destinataires=" + Arrays.toString(destinataires) + ", enCopie="
				+ Arrays.toString(enCopie) + ", objetDuMail=" + objetDuMail + ", corpsDuMail=" + corpsDuMail
				+ ", fileEnPieceJointe=" + fileEnPieceJointe + "]";
	}

	

}
