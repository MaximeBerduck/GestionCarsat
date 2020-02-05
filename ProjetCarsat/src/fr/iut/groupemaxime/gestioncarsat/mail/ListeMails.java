package fr.iut.groupemaxime.gestioncarsat.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import fr.iut.groupemaxime.gestioncarsat.utils.Options;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ListeMails {
	List<Mail> mails;

	public ListeMails(List<Mail> listeMails) {
		this.mails = listeMails;
	}

	public ListeMails() {
		this(new ArrayList<>());
	}

	public void ajouterMail(Mail mail) {
		this.mails.add(mail);
		MailProcessor.sauvegarderMail(mail, Constante.CHEMIN_MAILS_EN_ATTENTE);
	}

	public List<Mail> getListeMails() {
		return this.mails;
	}

	public void iterationMails() {
		StringBuilder erreur = new StringBuilder();
		List<Mail> aSuppr = new ArrayList<>();
		for (Mail mail : mails) {
			if (null == MailProcessor.envoyerMail(mail.getMail())) {
				if (mail.isSauvegarde()) {
					mail.supprimer();
				}
				aSuppr.add(mail);
			} else {
				if (!mail.isSauvegarde()) {
					MailProcessor.sauvegarderMail(mail, Constante.CHEMIN_MAILS_EN_ATTENTE);
				}
			}
		}
		this.mails.removeAll(aSuppr);
	}

	public void chargerMails(String cheminMailsEnAttente, Options options) {
		List<Mail> mailsCharges = new ArrayList<>();
		File dossierMails = new File(cheminMailsEnAttente);
		if (dossierMails.isDirectory()) {
			File[] contenuDossierMails = dossierMails.listFiles();
			if (contenuDossierMails != null) {
				for (File dossier : contenuDossierMails) {
					File[] contenuDossier = dossier.listFiles();
					if (contenuDossier != null) {
						for (File mail : contenuDossier) {
							Mail nouveauMail = new Mail(MailProcessor.chargerMail(mail, options));
							if (nouveauMail.getMail() != null) {
								nouveauMail.setPath(mail.getAbsolutePath());
								mailsCharges.add(nouveauMail);
							}
						}
					}
				}
			}
		}
		this.mails = mailsCharges;
	}
}
