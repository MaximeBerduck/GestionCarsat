package fr.iut.groupemaxime.gestioncarsat.responsable.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Session;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisMission;
import fr.iut.groupemaxime.gestioncarsat.agent.interfaces.InterfaceMail;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import fr.iut.groupemaxime.gestioncarsat.mail.Mail;
import fr.iut.groupemaxime.gestioncarsat.mail.MailProcessor;
import fr.iut.groupemaxime.gestioncarsat.responsable.ResponsableApp;
import fr.iut.groupemaxime.gestioncarsat.utils.Bibliotheque;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import fr.iut.groupemaxime.gestioncarsat.utils.EtatMission;
import fr.iut.groupemaxime.gestioncarsat.utils.Options;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MailController implements InterfaceMail {

	@FXML
	private TextField expediteur;

	@FXML
	private TextField destinataires;

	@FXML
	private TextField destEnCopie;

	@FXML
	private TextField objetDuMail;

	@FXML
	private TextArea corpsDuMail;

	private File[] piecesJointes;

	private ResponsableApp mainApp;

	public void setMainApp(ResponsableApp mainApp) {
		this.mainApp = mainApp;
	}

	public void chargerOptions() {
		Options options = this.mainApp.getOptions();
		this.corpsDuMail.setText(options.getCorpsDuMail());
		this.objetDuMail.setText(Constante.OBJET_DU_MAIL_DEFAUT);
	}

	@FXML
	public void envoyerMail(ActionEvent event) {
		if (adressesMailValides()) {
			if (piecesJointes.length == 2) {
				mainApp.getEtatMissionActive().setFm(EtatMission.EN_COURS_ENVOI);
				mainApp.getEtatMissionActive().setHt(EtatMission.EN_COURS_ENVOI);
				mainApp.getEtatMissionActive().sauvegarderJson();
			} else {
				mainApp.getEtatMissionActive().setOm(EtatMission.EN_COURS_ENVOI);
				mainApp.getEtatMissionActive().sauvegarderJson();
			}
			mainApp.getMailsEnAttente().ajouterMail(new Mail(MailProcessor.creerMail(this)));
			mainApp.getMailsEnAttente().chargerMails(Constante.CHEMIN_MAILS_EN_ATTENTE, mainApp.getOptions());
			mainApp.retourMenu();
			mainApp.getServiceEnvoiMail().restart();
		}
	}

	private boolean adressesMailValides() {
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(regex);
		boolean mailCorrect = true;
		String erreur = "";

		int verifMailExpediteur = 0;

		List<String> listDest = getDestinatairesTab();
		ListIterator<String> itDest = listDest.listIterator();
		int verifSiMailDestIncorrect = 0;

		List<String> listEnCopie = getDestEnCopieTab();
		if (listEnCopie != null) {
			ListIterator<String> itEnCopie = listEnCopie.listIterator();
			int verifSiMailEnCopieIncorrect = 0;

			while (itEnCopie.hasNext()) {
				Matcher matcher = pattern.matcher(itEnCopie.next());
				if (!matcher.matches())
					verifSiMailEnCopieIncorrect = 1;
			}

			if (verifSiMailEnCopieIncorrect == 1) {
				erreur += "L'une au moins des adresses mails en copie a été mal saisie !\n";
			}
		}

		Matcher matcher1 = pattern.matcher(expediteur.getText());
		if (!matcher1.matches()) {
			verifMailExpediteur = 1;
		}
		if (verifMailExpediteur == 1) {
			erreur += "Le mail de l'expediteur a été mail saisie !\n";
		}

		while (itDest.hasNext()) {
			Matcher matcher = pattern.matcher(itDest.next());
			if (!matcher.matches())
				verifSiMailDestIncorrect = 1;
		}
		if (verifSiMailDestIncorrect == 1) {
			erreur += "L'une au moins des adresses mails du destinataire a été mal saisie !\n";
		}

		if (erreur.length() > 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Champs non valides");
			alert.setHeaderText("Des champs ne sont pas valides");
			alert.setContentText(erreur);

			alert.showAndWait();
			mailCorrect = false;
		}
		return mailCorrect;
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
		List<String> listDest = new ArrayList<>();
		listDest.addAll(Arrays.asList(destinataires.getText().split(",")));
		return listDest;
	}

	public void setDestinataires(String destinataires) {
		this.destinataires.setText(destinataires);
	}

	public TextField getDestEnCopie() {
		return destEnCopie;
	}

	public List<String> getDestEnCopieTab() {
		List<String> listEnCopie = null;
		if (!destEnCopie.getText().equals("")) {
			listEnCopie = new ArrayList<>();
			listEnCopie.addAll(Arrays.asList(destEnCopie.getText().split(",")));
		}
		return listEnCopie;
	}

	public void setDestEnCopie(TextField destEnCopie) {
		this.destEnCopie = destEnCopie;
	}

	public TextField getObjetDuMail() {
		return objetDuMail;
	}

	public void setObjetDuMail(String objetDuMail) {
		this.objetDuMail.setText(objetDuMail);
	}

	public TextArea getCorpsDuMail() {
		return corpsDuMail;
	}

	public void setCorpsDuMail(String corpsDuMail) {
		this.corpsDuMail.setText(corpsDuMail);
	}

	public ResponsableApp getMainApp() {
		return mainApp;
	}

	public void setPiecesJointes(File[] fichiers) {
		this.piecesJointes = fichiers;

	}

	public File[] getPiecesJointes() {
		return this.piecesJointes;
	}
}
