package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.IOException;
import java.util.HashSet;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.Facture;
import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisJournalier;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
	private Label montantJustifRepas;
	@FXML
	private TextField nbrForfaitDecouchers;
	@FXML
	private Label montantJustifDecouchers;
	@FXML
	private Button btnRetour;

	private AnchorPane pageFrais1;

	private FraisMissionController fmController;

	private HashSet<Facture> justificatifRepas;
	private HashSet<Facture> justificatifDecoucher;

	private AgentApp agentApp;

	public void initialize() {
		this.justificatifRepas = new HashSet<Facture>();
		this.justificatifDecoucher = new HashSet<Facture>();
	}

	// Méthode qui vérifie le nombre de repas saisie. Si >2, return false + affiche
	// erreur
	public boolean verifierNbrRepas() {
		int n = Integer.parseInt(getNbrForfaitRepas()) + this.justificatifRepas.size();
		if (n > Constante.NBR_REPAS_JOURNALIER) {
			this.dateJournee.setText("bonjour");

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Nombre de repas journalier trop élevé");
			alert.setContentText("Vous avez indiqué un nombre trop élevé de repas dans cette journée ("
					+ Constante.NBR_REPAS_JOURNALIER + "au maximum).\n" + " Veuillez corriger cette erreur.");

			alert.showAndWait();
			return false;
		}
		return true;

	}

	// Méthode qui vérifie le nombre de découcher saisie. Si >1, return false +
	// affiche erreur
	public boolean verifierNbrDecoucher() {
		int n = Integer.parseInt(getNbrForfaitDecouchers()) + this.justificatifDecoucher.size();
		if (n > Constante.NBR_DECOUCHER_JOURNALIER) {
			this.dateJournee.setText("bonjour");

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Nombre de découcher journalier trop élevé");
			alert.setContentText("Vous avez indiqué un nombre trop élevé de découcher dans cette journée ("
					+ Constante.NBR_DECOUCHER_JOURNALIER + "au maximum).\n" + " Veuillez corriger cette erreur.");

			alert.showAndWait();
			return false;
		}
		return true;
	}

	public boolean verifierSaisieCorrect() {
		if (verifierNbrRepas() && verifierNbrDecoucher())
			return true;
		else
			return false;
	}

	// Event Listener on Button.onAction
	@FXML
	public void afficherFrais2(ActionEvent event) {
		if (verifierSaisieCorrect())
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
		this.justificatifRepas = fj.getJustificatifRepas();
		this.majJustifRepas();

		this.setNbrForfaitDecouchers(String.valueOf(fj.getNbrDecouchForfait()));
		// TODO setMontantJustifDecouchers
		this.justificatifDecoucher = fj.getJustificatifDecoucher();
		majJustifDecoucher();

	}

	public void afficherJourSuivant() {
		if (verifierSaisieCorrect()) {
			this.fmController.sauvegarderJournee(this.dateJournee.getText());
			if (!this.fmController.jourEstLeDernier(this.getDateJournee()))
				this.fmController.afficherJourSuivant(this.dateJournee.getText());
			else
				this.fmController.getAgentApp().retourMenu();
		}
	}

	public void afficherJourAvant() {
		if (verifierSaisieCorrect()) {
			this.fmController.sauvegarderJournee(this.dateJournee.getText());
			this.fmController.afficherJourAvant(this.dateJournee.getText());
		}
	}

	public void modifierJustificatifDecouchers() {
		if (!"".equals(this.nbrForfaitDecouchers.getText())
				&& Integer.parseInt(this.nbrForfaitDecouchers.getText()) >= Constante.NBR_DECOUCHER_JOURNALIER) {
			// Si le nombre de repas déclaré est déjà le maximum
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Nombre de découcher journalié trop élevé");
			alert.setContentText("Vous souhaitez indiquer un nombre trop élevé de découcher dans cette journée ("
					+ Constante.NBR_DECOUCHER_JOURNALIER + " au maximum).\n");
			alert.showAndWait();
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(AgentApp.class.getResource("view/ListeFactures.fxml"));
				AnchorPane saisieLayout = loader.load();
				Stage secondaryStage = new Stage();

				Scene scene = new Scene(saisieLayout);
				secondaryStage = new Stage();
				ListeFacturesController controllerListe = loader.getController();
				controllerListe.setStage(secondaryStage);

				controllerListe.setNbrMax(
						Constante.NBR_DECOUCHER_JOURNALIER - Integer.parseInt(this.nbrForfaitDecouchers.getText()));

				secondaryStage.setScene(scene);

				secondaryStage.setTitle("Justificatifs");
				secondaryStage.initOwner(this.agentApp.getPrimaryStage());
				secondaryStage.initModality(Modality.WINDOW_MODAL);
				secondaryStage.getIcons().add(new Image("file:" + Constante.CHEMIN_IMAGES + "logo.png"));

				if (this.justificatifDecoucher.size() != 0)
					controllerListe.modifierListeFacture(justificatifDecoucher);

				secondaryStage.showAndWait();

				this.justificatifDecoucher = controllerListe.getFactures();
				this.majJustifDecoucher();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void majJustifDecoucher() {
		float montant = 0;
		for (Facture facture : this.justificatifDecoucher) {
			montant += facture.getMontant();
		}
		this.montantJustifDecouchers.setText(String.valueOf(montant) + "€");
	}

	public void modifierJustificatifsRepas() {
		if (!"".equals(this.nbrForfaitRepas.getText())
				&& Integer.parseInt(this.nbrForfaitRepas.getText()) >= Constante.NBR_REPAS_JOURNALIER) {
			// Si le nombre de repas déclaré est déjà le maximum
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Nombre de repas journalié trop élevé");
			alert.setContentText("Vous souhaitez indiquer un nombre trop élevé de repas dans cette journée ("
					+ Constante.NBR_REPAS_JOURNALIER + " au maximum).\n");
			alert.showAndWait();
		} else {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(AgentApp.class.getResource("view/ListeFactures.fxml"));
				AnchorPane saisieLayout = loader.load();
				Stage secondaryStage = new Stage();

				Scene scene = new Scene(saisieLayout);
				secondaryStage = new Stage();
				ListeFacturesController controllerListe = loader.getController();
				controllerListe.setStage(secondaryStage);
				controllerListe
						.setNbrMax(Constante.NBR_REPAS_JOURNALIER - Integer.parseInt(this.nbrForfaitRepas.getText()));

				secondaryStage.setScene(scene);

				secondaryStage.setTitle("Justificatifs");
				secondaryStage.initOwner(this.agentApp.getPrimaryStage());
				secondaryStage.initModality(Modality.WINDOW_MODAL);
				secondaryStage.getIcons().add(new Image("file:" + Constante.CHEMIN_IMAGES + "logo.png"));

				if (this.justificatifRepas.size() != 0)
					controllerListe.modifierListeFacture(justificatifRepas);

				secondaryStage.showAndWait();

				this.justificatifRepas = controllerListe.getFactures();
				this.majJustifRepas();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void majJustifRepas() {
		float montant = 0;
		for (Facture facture : this.justificatifRepas) {
			montant += facture.getMontant();
		}
		this.montantJustifRepas.setText(String.valueOf(montant) + "€");
	}

	public String getMontantJustifRepas() {
		return montantJustifRepas.getText().replace("€", "");
	}

	public void setMontantJustifRepas(String montantJustifRepas) {
		this.montantJustifRepas.setText(montantJustifRepas);
	}

	public String getMontantJustifDecouchers() {
		return montantJustifDecouchers.getText();
	}

	public void setMontantJustifDecouchers(String montantJustifDecouchers) {
		this.montantJustifDecouchers.setText(montantJustifDecouchers);
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

	public void setNbrForfaitDecouchers(String nbrForfaitDecouchers) {
		this.nbrForfaitDecouchers.setText(nbrForfaitDecouchers);
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

	public String getNbrForfaitDecouchers() {
		return nbrForfaitDecouchers.getText();
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

	public void setAgentApp(AgentApp agentApp) {
		this.agentApp = agentApp;
	}

	public HashSet<Facture> getJustificatifRepas() {
		return justificatifRepas;
	}

	public void setJustificatifRepas(HashSet<Facture> justificatifRepas) {
		this.justificatifRepas = justificatifRepas;
	}

	public HashSet<Facture> getJustificatifDecoucher() {
		return justificatifDecoucher;
	}

	public void setJustificatifDecoucher(HashSet<Facture> justificatifDecoucher) {
		this.justificatifDecoucher = justificatifDecoucher;
	}

}
