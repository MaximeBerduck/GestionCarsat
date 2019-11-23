package fr.iut.groupemaxime.gestioncarsat.model;

import java.io.File;
import java.util.Arrays;

public class Mail {
	private String expediteur;
	private static String[] destinataires; //il y a des types static devant ces 2 variables car sinon je n'arrivais pas a les utiliser dans le mailController
	private static String[] enCopie;
	private String objetDuMail;
	private String corpsDuMail;
	private File fileEnPieceJointe;
	
	
	public Mail(String expe, String[] destinataires, String[] enCopie, String objetDuMail, String corpsDuMail, File fileEnPJ) {
		this.expediteur=expe;
		Mail.destinataires= destinataires;
		Mail.enCopie=enCopie;
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
	
	public static String[] getDestinataires(){
		return destinataires;
	}
	
	public String getDestinatairesEnString() {
		return Arrays.toString(destinataires);
	}
	
	public void setDestinataires(String[] destinataires) {
		Mail.destinataires = destinataires;
	}

	public static String[] getEnCopie() {
		return enCopie;
	}
	
	public String getEnCopieEnString(){
		return Arrays.toString(enCopie);
	}

	public void setEnCopie(String[] enCopie) {
		Mail.enCopie = enCopie;
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
