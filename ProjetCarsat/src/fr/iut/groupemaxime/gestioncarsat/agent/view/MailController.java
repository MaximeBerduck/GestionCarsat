package fr.iut.groupemaxime.gestioncarsat.agent.view;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import fr.iut.groupemaxime.gestioncarsat.agent.model.Constante;
import fr.iut.groupemaxime.gestioncarsat.agent.model.Mail;
import fr.iut.groupemaxime.gestioncarsat.agent.model.OrdreMission;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class MailController {
	
	@FXML 
	private TextField expediteur;

	@FXML
	private TextField destinataires;
	
	@FXML
	private TextField destEnCopie;

	@FXML
	private TextField objetDuMail;
	
	@FXML
	private TextField corpsDuMail;
	
	private OrdreMission om;
	
	private OrdreMissionController mainApp;
	
	public void setMainApp(OrdreMissionController mainApp) {
		this.mainApp = mainApp;
	}
	
	public Properties configurationSmtp() {
		Properties props = new Properties();
	    
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
	    
	    return props;
	}
	
	public Message configurationMessage(Session session) {
		//cr�ation du message
		Message message = new MimeMessage(session);
		try {
		    //Pi�ces jointes
		    File file=new File(Constante.CHEMIN_PDF_VIDE); //Constante.CHEMIN_PDF + om.getFichier() + Constante.EXTENSION_PDF Je n'ai pas utilis� le chemin en commentaire car il bugait sur mon pc
		    
		    FileDataSource source = new FileDataSource(file);
		    DataHandler handler = new DataHandler(source);
		    MimeBodyPart fichier = new MimeBodyPart();
		   
		    fichier.setDataHandler(handler);
		    fichier.setFileName(source.getName());
		    
		    
		    MimeBodyPart content = new MimeBodyPart();
		    
		    content.setText(corpsDuMail.getText());
		    

		    
		    MimeMultipart mimeMultipart = new MimeMultipart();
		    
		    mimeMultipart.addBodyPart(content);
		    mimeMultipart.addBodyPart(fichier);
		   
		    
		    
		    message.setContent(mimeMultipart);
		    message.setSubject(objetDuMail.getText());
		        
		    //Expediteur et destinataires
			message.setFrom(new InternetAddress(expediteur.getText()));
			
			//R�cup�ration de la liste des destinataires
			List<String> listDest = getDestinatairesTab();
			//On transforme notre list<String> en un String pour n'avoir qu'une ligne : message.setRecepients
			String listDestEnUnString = String.join(", ",listDest);
			//Ajout des destinataires du message
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(listDestEnUnString));
			
			//r�cup�ration de la liste en copie
			List<String> listEnCopie = getDestEnCopieTab();
			String listDestEnCopieEnUnString = String.join(", ", listEnCopie);
			//Ajout des dest en copie du message 
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(listDestEnCopieEnUnString));
			
			//Est cens� faire l'accus� de reception setHeader
			message.setHeader("Disposition-Notification-To",expediteur.getText());
			message.setHeader("Return-Receipt-To",expediteur.getText());
			
		}catch(Exception ex){
			Logger.getLogger(MailController.class.getName()).log(Level.SEVERE,null,ex);
		}
		return message;
	}
	
	@FXML
	public void envoyerMail(ActionEvent event) {
	    Properties props = configurationSmtp();
	    Session session = Session.getDefaultInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(expediteur.getText(),"Azerty19");//ICI IL FAUDRA REFLECHIR POUR LA SAISIE DU MDP POUR LE MAIL DE L'EXPEDITEUR
	                }
	            });
	    
	    try {
	    	Message message = configurationMessage(session);
	    	if(adressesMailValides()) {
	    		Transport.send(message);
	    		Alert alert = new Alert(AlertType.INFORMATION);
	    		alert.setTitle("Etat d'envoi du mail");
	    		alert.setHeaderText("Regardez, une information importante est pr�sente.");
	    		alert.setContentText("Le mail a �t� envoy� avec succ�s.");

	    		alert.showAndWait();
	    	}
	    }catch (MessagingException e) {
	    	Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("Etat d'envoi du mail");
	    	alert.setHeaderText("Oups une erreur est survenue.");
	    	alert.setContentText("Veuillez v�rifier que les adresses ont �t� saisies correctement");

	    	alert.showAndWait();
	        e.printStackTrace();
	    }
	}
	
	
	private boolean adressesMailValides() {
		//verif les mails en destinataires, en expediteur et en copie si une adresse mail en copie est saisie 
		
		
		//regex permet de v�rifier la composition des adresses mails saisies.
		// voir ce lien pour plus d'info ->        https://howtodoinjava.com/regex/java-regex-validate-email-address/
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(regex);
		Boolean mailCorrect=true;
		String erreur=""; 
		
		int verifMailExpediteur = 0;
		
		
		//j'utilise des List<String> a la place de String[] car mon �l�ment n'a pas de taille d�fini � l'avance
		List<String> listDest = getDestinatairesTab();
		ListIterator<String> itDest = listDest.listIterator();
		int verifSiMailDestIncorrect=0;
		
		List<String> listEnCopie = getDestEnCopieTab();
		if(listEnCopie!=null) {
			ListIterator<String> itEnCopie = listEnCopie.listIterator();
			int verifSiMailEnCopieIncorrect =0;
			
			while(itEnCopie.hasNext()) {
				Matcher matcher = pattern.matcher(itEnCopie.next());
				if(!matcher.matches())
					verifSiMailEnCopieIncorrect = 1;
			}
				
			if(verifSiMailEnCopieIncorrect==1) {
				erreur+="L'une au moins des adresses mails en copie a �t� mal saisie !\n";
			}
		}
		
		
		Matcher matcher1= pattern.matcher(expediteur.getText());
		if (matcher1.matches()==false) {
			verifMailExpediteur = 1;
		}
		if (verifMailExpediteur == 1) {
			erreur+="Le mail de l'expediteur a �t� mail saisie !\n";
		}
		
		while(itDest.hasNext()) {
			Matcher matcher = pattern.matcher(itDest.next());
			//verif si adresse mail d'un dest est incorrect
			if(!matcher.matches())
				verifSiMailDestIncorrect = 1;
		}
		if(verifSiMailDestIncorrect==1) {
			erreur+= "L'une au moins des adresses mails du destinataire a �t� mal saisie !\n";
		}
		
	
		
		
		if(erreur.length()>0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Champs non valides");
			alert.setHeaderText("Des champs ne sont pas valides");
			alert.setContentText(erreur);
			
			alert.showAndWait();
			mailCorrect=false;
		}
		return mailCorrect;
	}
	
	
	public void setChamps(Mail mail) {
		this.expediteur.setText(mail.getExpediteur());
		this.destinataires.setText(mail.getDestinatairesEnString());
		this.destEnCopie.setText(mail.getEnCopieEnString());
		this.objetDuMail.setText(mail.getObjetDuMail());
		this.corpsDuMail.setText(mail.getCorpsDuMail());
	}
	
	public TextField getExpediteur() {
		return expediteur;
	}

	public void setExpediteur(String expediteur) {
		this.expediteur.setText(expediteur);
	}

	public TextField getDestinataires() {
		return destinataires;
	}
	
	public List<String> getDestinatairesTab() {
		List<String> listDest = new ArrayList<String>();
		//la m�thode split permet de lister les destinataires un par un s'ils sont s�par�s par une ","
		for(String email : destinataires.getText().split(",")) {
			listDest.add(email);
		}
		return listDest;
	}

	public void setDestinataires(String destinataires) {
		this.destinataires.setText(destinataires);
	}

	public TextField getDestEnCopie() {
		return destEnCopie;
	}
	
	
	public List<String> getDestEnCopieTab() {
		List<String> listEnCopie = null; //new ArrayList<String>();
		//la m�thode split permet de lister les destinataires un par un s'ils sont s�par�s par une ","
		if(!destEnCopie.getText().equals("")){
			listEnCopie = new ArrayList<String>();
			for(String email : destEnCopie.getText().split(",")) {
				listEnCopie.add(email);
			}
		}
		return listEnCopie;
	}

	public void setDestEnCopie(TextField destEnCopie) {
		this.destEnCopie = destEnCopie;
	}

	public TextField getObjetDuMail() {
		return objetDuMail;
	}

	public void setObjetDuMail(TextField objetDuMail) {
		this.objetDuMail = objetDuMail;
	}

	public TextField getCorpsDuMail() {
		return corpsDuMail;
	}

	public void setCorpsDuMail(TextField corpsDuMail) {
		this.corpsDuMail = corpsDuMail;
	}

	public OrdreMissionController getMainApp() {
		return mainApp;
	}
}
