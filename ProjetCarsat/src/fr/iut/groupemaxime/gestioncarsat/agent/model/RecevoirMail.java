package fr.iut.groupemaxime.gestioncarsat.agent.model;

import java.io.File;
import java.io.IOException;
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

public class RecevoirMail {
	public static void recevoirEmail(String host, String user, String password, String folder) {
		try {
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
							File dossier = new File(folder + "responsable/");
							if (!dossier.exists()) {
								dossier.mkdir();
							}
							String nomDossier = part.getFileName().substring(0, part.getFileName().lastIndexOf('.'));
							nomDossier = nomDossier.substring(nomDossier.indexOf('_') + 1);
							dossier = new File(folder + "responsable/" + nomDossier);
							if (!dossier.exists()) {
								dossier.mkdir();
							}
							File file;
							part.saveFile(dossier.getAbsolutePath() + "/" + part.getFileName());
						}
					}
				} else {
					System.out.println("Text: " + message.getContent().toString());
				}
			}

			emailFolder.close(false);
			emailStore.close();

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
