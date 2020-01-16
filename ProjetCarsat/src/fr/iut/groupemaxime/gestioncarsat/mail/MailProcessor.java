package fr.iut.groupemaxime.gestioncarsat.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.pop3.POP3Store;

import fr.iut.groupemaxime.gestioncarsat.agent.view.MailController;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import fr.iut.groupemaxime.gestioncarsat.utils.Options;

public class MailProcessor {
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
			emailFolder.open(Folder.READ_ONLY);

			Message[] messages = emailFolder.getMessages();
			for (Message message : messages) {
				enregistrerPieceJointe(message, folder + "responsable/");
				message.setFlag(Flags.Flag.DELETED, true);
			}
			emailFolder.close(true);
			emailStore.close();
		} catch (MessagingException e) {
			// TODO
			e.printStackTrace();
		}
	}

	public static void enregistrerPieceJointe(Message message, String cheminDossier) {
		try {
			String contentType = message.getContentType();

			if (contentType.contains("multipart")) {
				Multipart multiPart = (Multipart) message.getContent();

				File dossier;
				for (int j = 0; j < multiPart.getCount(); j++) {
					MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(j);
					if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
						String nomDossier = part.getFileName().substring(0, part.getFileName().lastIndexOf('.'));
						nomDossier = nomDossier.substring(nomDossier.indexOf('_') + 1);
						dossier = new File(cheminDossier + nomDossier);
						if (!dossier.exists()) {
							dossier.mkdirs();
						}
						part.saveFile(dossier.getAbsolutePath() + "/" + part.getFileName());
					}
				}
			}
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Properties configurationSmtp() {
		Properties props = new Properties();

		props.put("mail.smtp.starttls.enable", "true");

		props.put("mail.smtp.host", Constante.HOSTNAME);

		props.put("mail.smtp.port", "587");

		props.put("mail.smtp.auth", "true");

		return props;
	}

	public static Message configurationMessage(Session session, MailController mailCtrl) {
		Message message = new MimeMessage(session);
		try {
			// Piéces jointes
			File file = new File(mailCtrl.getMainApp().getMainApp().getMissionActive().getCheminDossier() + "/"
					+ mailCtrl.getMainApp().getMainApp().getMissionActive().getNomOM() + Constante.EXTENSION_PDF);
			FileDataSource source = new FileDataSource(file);
			DataHandler handler = new DataHandler(source);
			MimeBodyPart pieceJointe = new MimeBodyPart();
			pieceJointe.setDataHandler(handler);
			pieceJointe.setFileName(source.getName());

			MimeBodyPart texteMessage = new MimeBodyPart();

			texteMessage.setText(mailCtrl.getCorpsDuMail().getText());

			MimeMultipart contenuMessage = new MimeMultipart();

			contenuMessage.addBodyPart(texteMessage);
			contenuMessage.addBodyPart(pieceJointe);

			message.setContent(contenuMessage);
			message.setSubject(mailCtrl.getObjetDuMail().getText());

			message.setFrom(new InternetAddress(mailCtrl.getExpediteur().getText()));

			List<String> listDest = mailCtrl.getDestinatairesTab();

			String listDestEnUnString = String.join(", ", listDest);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(listDestEnUnString));

			List<String> listEnCopie = mailCtrl.getDestEnCopieTab();
			if (listEnCopie != null) {
				String listDestEnCopieEnUnString = String.join(", ", listEnCopie);
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(listDestEnCopieEnUnString));

			}
		} catch (MessagingException e) {

		}
		return message;
	}

	public static Message creerMail(MailController mailCtrl) {
		Properties props = configurationSmtp();
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailCtrl.getExpediteur().getText(), Constante.MOT_DE_PASSE);
			}
		});
		return configurationMessage(session, mailCtrl);

	}

	public static Message envoyerMail(Message message) {
		try {
			Transport.send(message);
		} catch (MessagingException e) {
			return message;
		}
		return null;

	}

	public static void sauvegarderMail(Mail mail, String cheminDossier) {
		Message message = mail.getMail();

		try {
			Multipart multiPart = (Multipart) message.getContent();

			File dossier;

			MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(Constante.PIECE_JOINTE_NUMERO_PART);
			String nomDossier = part.getFileName().substring(0, part.getFileName().lastIndexOf('.'));
			nomDossier = nomDossier.substring(nomDossier.indexOf('_') + 1);
			dossier = new File(cheminDossier + nomDossier);
			if (!dossier.exists()) {
				dossier.mkdirs();
			}
			File fichierMail = new File(dossier.getAbsolutePath() + "/"
					+ part.getFileName().substring(0, part.getFileName().lastIndexOf(".")) + Constante.EXTENSION_MAIL);
			FileOutputStream os = new FileOutputStream(fichierMail);
			message.writeTo(os);
			mail.setPath(fichierMail.getAbsolutePath());
			os.close();

		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Message chargerMail(File mail, Options options) {
		Properties props = configurationSmtp();
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(options.getMailAgent() + '@' + Constante.HOSTNAME,
						Constante.MOT_DE_PASSE);
			}
		});
		try (FileInputStream is = new FileInputStream(mail)) {
			return new MimeMessage(session, is);
		} catch (IOException | MessagingException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}

}
