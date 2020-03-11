package fr.iut.groupemaxime.gestioncarsat.agent.view;

import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisJournalier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Frais1Controller {
	@FXML
	private Label dateJournee;
	@FXML
	private TextField heureDepart;
	@FXML
	private TextField minDepart;
	@FXML
	private TextField heureRetour;
	@FXML
	private TextField minRetour;
	@FXML
	private TextField nbrForfaitRepas;
	@FXML
	private TextField nbrJustificatifRepas;
	@FXML
	private TextField nbrForfaitDecouchers;
	@FXML
	private TextField nbrJustifDecouchers;
	@FXML
	private Button btnRetour;

	private AnchorPane pageFrais1;

	private FraisMissionController fmController;

	//Méthode qui vérifie le nombre de repas saisie. Si >2, return false + affiche erreur
	public boolean verifierNbrRepas() {
		// TODO Auto-generated method stub
		int n = Integer.parseInt(getNbrForfaitRepas());
		// TODO Ajouter nbr justifs
		if (n > 2) {
			this.dateJournee.setText("bonjour");

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Nombre de repas journalier trop élevé");
			alert.setContentText("Vous avez indiqué un nombre trop élevé de repas dans cette journée (2 au maximum).\n"
					+ " Veuillez corriger cette erreur.");

			alert.showAndWait();
			return false;
		}
		return true;

	}

	//Méthode qui vérifie le nombre de découcher saisie. Si >1, return false + affiche erreur
	public boolean verifierNbrDecoucher() {
		// TODO Auto-generated method stub
		int n = Integer.parseInt(getNbrForfaitDecouchers());
		// TODO Ajouter nbr justifs
		if (n > 2) {
			this.dateJournee.setText("bonjour");

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Nombre de repas journalier trop élevé");
			alert.setContentText("Vous avez indiqué un nombre trop élevé de découcher dans cette journée (1 au maximum).\n"
					+ " Veuillez corriger cette erreur.");

			alert.showAndWait();
			return false;
		}
		return true;
	}

	public boolean verifierSaisieCorrect() {
		if(verifierNbrRepas() && verifierNbrDecoucher())
			return true;
		else
			return false;
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void afficherFrais2(ActionEvent event) {
		if(verifierSaisieCorrect())
			this.fmController.afficherFrais2(dateJournee.getText());
	}

	public void modifierFraisJournalier(FraisJournalier fj) {
		if (null != fj.getHeureDepart()) {
			this.setHeureDepart(fj.getHeureDepart().split(":")[0]);
			this.setMinDepart(fj.getHeureDepart().split(":")[1]);
		}

		if (null != fj.getHeureRetour()) {
			this.setHeureRetour(fj.getHeureRetour().split(":")[0]);
			this.setMinRetour(fj.getHeureRetour().split(":")[1]);
		}

		this.setNbrForfaitRepas(String.valueOf(fj.getNbrRepasForfait()));
		this.setNbrJustificatifRepas(String.valueOf(fj.getNbrRepasJustif()));

		this.setNbrForfaitDecouchers(String.valueOf(fj.getNbrDecouchForfait()));
		this.setNbrJustifDecouchers(String.valueOf(fj.getNbrDecouchJustif()));

	}

	public void afficherJourSuivant() {
		if(verifierSaisieCorrect()) {
			this.fmController.sauvegarderJournee(this.dateJournee.getText());
			if (!this.fmController.jourEstLeDernier(this.getDateJournee()))
				this.fmController.afficherJourSuivant(this.dateJournee.getText());
			else
				this.fmController.getAgentApp().retourMenu();
		}
	}

	public void afficherJourAvant() {
		if(verifierSaisieCorrect()) {
			this.fmController.sauvegarderJournee(this.dateJournee.getText());
			this.fmController.afficherJourAvant(this.dateJournee.getText());
		}
	}

	public void setHeureDepart(String heureDepart) {
		this.heureDepart.setText(heureDepart);
	}

	public void setMinDepart(String minDepart) {
		this.minDepart.setText(minDepart);
	}

	public void setHeureRetour(String heureRetour) {
		this.heureRetour.setText(heureRetour);
	}

	public void setMinRetour(String minRetour) {
		this.minRetour.setText(minRetour);
	}

	public void setNbrForfaitRepas(String nbrForfaitRepas) {
		this.nbrForfaitRepas.setText(nbrForfaitRepas);
	}

	public void setNbrJustificatifRepas(String nbrJustificatifRepas) {
		this.nbrJustificatifRepas.setText(nbrJustificatifRepas);
	}

	public void setNbrForfaitDecouchers(String nbrForfaitDecouchers) {
		this.nbrForfaitDecouchers.setText(nbrForfaitDecouchers);
	}

	public void setNbrJustifDecouchers(String nbrJustifDecouchers) {
		this.nbrJustifDecouchers.setText(nbrJustifDecouchers);
	}

	public void setPageFrais1(AnchorPane pageFrais1) {
		this.pageFrais1 = pageFrais1;
	}

	public void setDateJournee(String date) {
		this.dateJournee.setText(date);
	}

	public void setFmController(FraisMissionController fmController) {
		this.fmController = fmController;
	}

	public AnchorPane getPage() {
		return this.pageFrais1;
	}

	public String getHeureDepart() {
		return heureDepart.getText();
	}

	public String getMinDepart() {
		return minDepart.getText();
	}

	public String getHeureRetour() {
		return heureRetour.getText();
	}

	public String getMinRetour() {
		return minRetour.getText();
	}

	public String getNbrForfaitRepas() {
		return nbrForfaitRepas.getText();
	}

	public String getNbrJustificatifRepas() {
		return nbrJustificatifRepas.getText();
	}

	public String getNbrForfaitDecouchers() {
		return nbrForfaitDecouchers.getText();
	}

	public String getNbrJustifDecouchers() {
		return nbrJustifDecouchers.getText();
	}

	public FraisMissionController getFmController() {
		return fmController;
	}

	public void retirerBtnRetour() {
		this.btnRetour.setVisible(false);
	}

	public String getDateJournee() {
		return this.dateJournee.getText();
	}

}
