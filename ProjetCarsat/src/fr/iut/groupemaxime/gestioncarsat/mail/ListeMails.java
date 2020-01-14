package fr.iut.groupemaxime.gestioncarsat.mail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;

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
			try {
				message.writeTo(new FileOutputStream(new File(adresseDossier + message.toString())));
			} catch (IOException|MessagingException e) {
				//TODO
				e.printStackTrace();
			}
		}

	}

	public ListeMails chargerMails(String adresseDossier) {
		ListeMails liste = new ListeMails();
		if (dossierExiste(adresseDossier)) {
			File dossier = new File(adresseDossier);
			File[] mails = dossier.listFiles();
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
