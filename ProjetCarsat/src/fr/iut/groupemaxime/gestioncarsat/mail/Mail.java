package fr.iut.groupemaxime.gestioncarsat.mail;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;

import com.sun.mail.pop3.POP3Store;

public class Mail {
	private String expediteur;
	private String[] destinataires;
	private String[] enCopie;
	private String objetDuMail;
	private String corpsDuMail;
	private File fileEnPieceJointe;

	public Mail(String expe, String[] destinataires, String[] enCopie, String objetDuMail, String corpsDuMail,
			File fileEnPJ) {
		this.expediteur = expe;
		this.destinataires = destinataires;
		this.enCopie = enCopie;
		this.objetDuMail = objetDuMail;
		this.corpsDuMail = corpsDuMail;
		this.fileEnPieceJointe = fileEnPJ;
	}

	public String getExpediteur() {
		return expediteur;
	}

	public void setExpediteur(String expediteur) {
		this.expediteur = expediteur;
	}

	public String[] getDestinataires() {
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

	public String getEnCopieEnString() {
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

	public static void recevoirEmail(String host, String user, String password, String folder) {
		try {
			File dossier = new File(folder + "responsable/");
			if (!dossier.exists()) {
				dossier.mkdir();
			}
			Properties properties = new Properties();
			properties.put("mail.pop3.host", host);
			Session emailSession = Session.getDefaultInstance(properties);

			POP3Store emailStore = (POP3Store) emailSession.getStore("pop3");
			emailStore.connect(user, password);

			Folder emailFolder = emailStore.getFolder("INBOX");
			emailFolder.open(Folder.READ_WRITE);

			Message[] messages = emailFolder.getMessages();
			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				String contentType = message.getContentType();

				if (contentType.contains("multipart")) {
					Multipart multiPart = (Multipart) message.getContent();

					for (int i1 = 0; i1 < multiPart.getCount(); i1++) {
						MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i1);
						if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {

							String nomDossier = part.getFileName().substring(0, part.getFileName().lastIndexOf('.'));
							nomDossier = nomDossier.substring(nomDossier.indexOf('_') + 1);
							dossier = new File(folder + "responsable/" + nomDossier);
							if (!dossier.exists()) {
								dossier.mkdir();
							}
							part.saveFile(dossier.getAbsolutePath() + "/" + part.getFileName());
						}
					}
				}

				emailFolder.close(false);
				emailStore.close();
			}

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
