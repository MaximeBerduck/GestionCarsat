package fr.iut.groupemaxime.gestioncarsat.agent.form;

import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



class Javamail {
public static void envoyerMail() {
	System.out.println("Vous allez envoyer un mail.");

    Properties props = new Properties();
    //Properties props = System.getProperties();

    
    
    props.put("mail.smtp.socketFactory.port", "587");
    props.put("mail.smtp.socketFactory.class",
            "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    
    //configuration pour envoyer un mail depuis et vers gmail
    //props.put("mail.smtp.host","smtp.gmail.com");
    
    //Configuration pour envoyer un mail depuis et vers outlook
    props.put("mail.smtp.host", "smtp-mail.outlook.com");
    
    props.put("mail.smtp.port", "587");

    
    

    Session session = Session.getDefaultInstance(props,
            new javax.mail.Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("gautier.mechaussie@etu.unilim.fr","Azerty19");
                

                }

            });
    try {
    	//Cr�ation du message
		Message message = new MimeMessage(session);

	    //Pi�ces jointes
	    File file=new File("D:\\Menu\\Documents\\1.pdf");
	    FileDataSource source = new FileDataSource(file);
	    DataHandler handler = new DataHandler(source);
	    MimeBodyPart fichier = new MimeBodyPart();
	   
	    fichier.setDataHandler(handler);
	    fichier.setFileName(source.getName());
	    
	    
	    MimeBodyPart content = new MimeBodyPart();
	    
	    content.setText("Bonjour, \n\n"
	    		+ "Veuillez trouver ci joint l'ordre de mission compl�t�. \n\n"
	    		+ "Cordialement.");
	    

	    
	    MimeMultipart mimeMultipart = new MimeMultipart();
	    
	    mimeMultipart.addBodyPart(content);
	    mimeMultipart.addBodyPart(fichier);
	   
	    
	    
	    message.setContent(mimeMultipart);
	    message.setSubject("OM compl�t�");
	        
	    //Destinataire et Envoyeur
		message.setFrom(new InternetAddress("gautier.mechaussie@etu.unilim.fr"));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("gautier.mechaussie@etu.unilim.fr"));
		
		//Est cens� faire l'accus� de reception setHeader
		message.setHeader("Disposition-Notification-To","gautier.mechaussie@etu.unilim.fr");
		message.setHeader("Return-Receipt-To","gautier.mechaussie@etu.unilim.fr");

        Transport.send(message);

        System.out.println("Le mail a �t� envoy� avec succ�s.");

    }catch (MessagingException e) {
        System.out.println("Une erreur s'est produite.");
        e.printStackTrace();
    }catch(Exception ex){
		Logger.getLogger(Javamail.class.getName()).log(Level.SEVERE,null,ex);
	}
}
}