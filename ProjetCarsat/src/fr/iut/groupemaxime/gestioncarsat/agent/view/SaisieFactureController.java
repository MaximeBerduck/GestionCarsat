package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.File;

import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.Facture;
import fr.iut.groupemaxime.gestioncarsat.utils.Bibliotheque;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SaisieFactureController {
	@FXML
	private RadioButton radioPapier;
	@FXML
	private RadioButton radioNumerique;
	@FXML
	private TextField montantFacture;
	@FXML
	private VBox vboxChemin;
	@FXML
	private AnchorPane boxCheminFacture;
	@FXML
	private TextField cheminFacture;

	private Stage stage;
	private ListeFacturesController listeController;
	private boolean modification;

	public void initilize() {
		this.modification = false;
	}

	// Event Listener on RadioButton[#radioPapier].onAction
	@FXML
	public void retirerChemin() {
		this.vboxChemin.getChildren().remove(0);
	}

	// Event Listener on RadioButton[#radioNumerique].onAction
	@FXML
	public void afficherChemin() {
		this.vboxChemin.getChildren().add(boxCheminFacture);
	}

	// Event Listener on Button.onAction
	@FXML
	public void validerFacture() {
		if (checkSaisie()) {
			if (modification) {
				this.listeController.modifierItemFacture(this.getFacture());
			} else {
				this.listeController.ajouterFactureListe(this.getFacture());
			}
			stage.close();
		}
	}

	public boolean checkSaisie() {
		boolean valide = true;
		if ("".equals(montantFacture.getText())
				|| (this.radioNumerique.isSelected() && "".equals(this.cheminFacture.getText()))) {
			valide = false;
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Champ invalide");
			alert.setContentText("Au moins un champ n'est pas correctement saisi (vide ou information incorrecte).");
			alert.showAndWait();
		} else {
			try {
				Float.parseFloat(this.montantFacture.getText());
			} catch (Exception e) {
				valide = false;
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText("Champ invalide");
				alert.setContentText("Le montant saisi n'est pas un nombre.");
				alert.showAndWait();
			}
		}
		return valide;
	}

	public void setFacture(Facture facture) {
		this.modification = true;
		this.montantFacture.setText(String.valueOf(facture.getMontant()));
		if ("Papier" == facture.getChemin()) {
			this.radioPapier.setSelected(true);
		} else {
			this.radioNumerique.setSelected(true);
			this.afficherChemin();
			this.cheminFacture.setText(facture.getChemin());
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void annuler(ActionEvent event) {
		stage.close();
	}

	public void ouvrirChemin() {
		File fichier = Bibliotheque.ouvrirFileChooser(Constante.FACTURE_FILTER);
		if (fichier != null) {
			this.cheminFacture.setText(fichier.getAbsolutePath());
		}
	}

	public Facture getFacture() {
		Facture facture = new Facture();
		facture.setMontant(Float.valueOf(montantFacture.getText()));
		if (radioNumerique.isSelected())
			facture.setChemin(cheminFacture.getText());
		else
			facture.setChemin("Papier");
		return facture;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setListeController(ListeFacturesController listeController) {
		this.listeController = listeController;
	}
}
