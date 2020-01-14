package fr.iut.groupemaxime.gestioncarsat.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import fr.iut.groupemaxime.gestioncarsat.utils.Constante;

public class ListeMails {
	List<Message> listeMails;

	public ListeMails(List<Message> listeMails) {
		this.listeMails = listeMails;
	}

	public ListeMails() {
		this(new ArrayList<Message>());
	}

	public void ajouterMail(Message mail) {
		this.listeMails.add(mail);
	}

	public List<Message> getListeMails() {
		return this.listeMails;
	}

	public void sauvegarderMails(String adresseDossier) {
		File dossier = new File(adresseDossier);
		if (!dossier.exists()) {
			dossier.mkdir();
		}
		for (Message message : listeMails) {
			Mail.sauvegarderMail(message, Constante.CHEMIN_MAILS_EN_ATTENTE);
		}

	}

	public ListeMails chargerMails(String adresseDossier) {
		ListeMails liste = new ListeMails();
		if (dossierExiste(adresseDossier)) {
			File dossier = new File(adresseDossier);
			File[] mails = dossier.listFiles();
			Message msg;
			for (File file : mails) {
				FileInputStream is;
				try {
					is = new FileInputStream(file);
					msg = new MimeMessage(Session.getDefaultInstance(Mail.configurationSmtp()), is);
					is.close();
					Mail.envoyerMail(msg);
				} catch (MessagingException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return liste;
	}

	private static boolean dossierExiste(String adresseDossier) {
		boolean existe = false;
		File f = new File(adresseDossier);
		if (f.isDirectory()) {
			existe = true;
		}
		return existe;
	}
}
