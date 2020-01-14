package fr.iut.groupemaxime.gestioncarsat.mail;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Mail {
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
			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				String contentType = message.getContentType();

				if (contentType.contains("multipart")) {
					Multipart multiPart = (Multipart) message.getContent();

					for (int j = 0; j < multiPart.getCount(); j++) {
						MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(j);
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
				message.setFlag(Flags.Flag.DELETED, true);
			}
			emailFolder.close(true);
			emailStore.close();
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		}
	}

	private static Properties configurationSmtp() {
		Properties props = new Properties();

		props.put("mail.smtp.starttls.enable", "true");

		props.put("mail.smtp.host", Constante.HOSTNAME);

		props.put("mail.smtp.port", "587");

		props.put("mail.smtp.auth", "true");

		return props;
	}

	private static Message configurationMessage(Session session, MailController mailCtrl) {
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

	public static boolean envoyerMail(MailController mailCtrl) {
		Properties props = configurationSmtp();
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailCtrl.getExpediteur().getText(), Constante.MOT_DE_PASSE);
			}
		});
		try {
			Transport.send(configurationMessage(session, mailCtrl));
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}

	}

}
