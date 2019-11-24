package fr.iut.groupemaxime.gestioncarsat.view;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.iut.groupemaxime.gestioncarsat.MainApp;
import fr.iut.groupemaxime.gestioncarsat.model.Mail;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MailController {
	
	@FXML 
	private TextField expediteurTextField;

	@FXML
	private TextField destinataireTextField;
	
	@FXML
	private TextField enCopieTextField;
	
	@FXML
	private TextField objetDuMail;
	
	@FXML
	private TextArea corpsDuMail;
	
	@FXML
	private Button btnPieceJointe;
	
	@FXML
	private Button btnEnvoyerMail;
	
	@FXML
	private Button btnAnnulerMail;
	
	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	
	private boolean adressesMailValides() {
		//verifi les mails en destinataires et en copie
		
		
		//regex permet de vérifier la composition des adresses mails saisies.
		// voir ce lien pour plus d'info ->        https://howtodoinjava.com/regex/java-regex-validate-email-address/
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(regex);
		Boolean mailCorrect=true;
		String erreur=""; 
		int verifMailExpediteur = 0;
		int verifSiMailDestIncorrect=0;
		int verifSiMailEnCopieIncorrect =0;
		
		Matcher matcher1 = pattern.matcher(expediteurTextField.getText());
		if (matcher1.matches()==false) {
			verifMailExpediteur = 1;
		}
		if (verifMailExpediteur == 1) {
			erreur+="Le mail de l'expediteur a été mail saisie !\n";
		}
 
		for(String email : destinataireTextField.getText().split(",")){ 
			//la méthode split() va permettre de vérifier chaque mail un par un, en optenant plusieurs String qui sont mis dans email 
			//qui aura permis de remplir destinataireTextField, en séparant les adresses par des ","
			Matcher matcher = pattern.matcher(email);
			//permet de verifier si une adresse mail des destinataires est inccorect.
			if (matcher.matches()==false) {
				verifSiMailDestIncorrect =1;
			}
		}
		
		if(verifSiMailDestIncorrect==1) {
			erreur+= "L'une des adresses mails du destinataires a été mal saisies !\n";
		}
		
		for(String email : enCopieTextField.getText().split(",")) {
			Matcher matcher = pattern.matcher(email);
			//permet de verifier si une adresse mail en copie est inccorect.
			if (matcher.matches()==false) {
				verifSiMailEnCopieIncorrect =1;
			}
		}
		
		if(verifSiMailEnCopieIncorrect==1) {
			erreur+="L'une des adresses mails en copie a été mal saisies !\n";
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
	
	public TextField getExpediteurTextField() {
		return expediteurTextField;
	}


	public void setExpediteurTextField(TextField expediteurTextField) {
		this.expediteurTextField = expediteurTextField;
	}
	
	public TextField getDestinataireTextField() {
		return destinataireTextField;
	}

	public void setDestinataireTextField(TextField destinataireTextField) {
		this.destinataireTextField = destinataireTextField;
	}

	public TextField getEnCopieTextField() {
		return enCopieTextField;
	}

	public void setEnCopieTextField(TextField enCopieTextField) {
		this.enCopieTextField = enCopieTextField;
	}

	public TextField getObjetDuMail() {
		return objetDuMail;
	}

	public void setObjetDuMail(TextField objetDuMail) {
		this.objetDuMail = objetDuMail;
	}

	public TextArea getCorpsDuMail() {
		return corpsDuMail;
	}

	public void setCorpsDuMail(TextArea corpsDuMail) {
		this.corpsDuMail = corpsDuMail;
	}
	
	public void setChamps(Mail mail) {
		this.expediteurTextField.setText(mail.getExpediteur());
		this.destinataireTextField.setText(mail.getDestinatairesEnString());
		this.enCopieTextField.setText(mail.getEnCopieEnString());
		this.objetDuMail.setText(mail.getObjetDuMail());
		this.corpsDuMail.setText(mail.getCorpsDuMail());
	}
}
