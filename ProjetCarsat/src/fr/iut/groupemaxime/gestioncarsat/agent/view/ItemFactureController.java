package fr.iut.groupemaxime.gestioncarsat.agent.view;

import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.Facture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ItemFactureController {
	@FXML
	private Label montantFacture;
	@FXML
	private Label typeFacture;

	private Facture facture;
	private ListeFacturesController listeController;
	
	private AnchorPane itemFacture;

	// Event Listener on Button.onAction
	@FXML
	public void modifierFacture(ActionEvent event) {
		listeController.modifierFacture(this);
	}

	// Event Listener on Button.onAction
	@FXML
	public void supprimerFacture(ActionEvent event) {
		listeController.supprimerFacture(this);
	}

	public void setFacture(Facture facture) {
		this.montantFacture.setText(facture.getMontant() + "€");
		if ("Papier".equals(facture.getChemin()))
			this.typeFacture.setText("Papier");
		else
			this.typeFacture.setText("Numérique");
		this.facture = facture;
	}

	public void setMontantFacture(float montant) {
		this.montantFacture.setText(String.valueOf(montant) + "€");
	}

	public void setTypeFacture(String type) {
		this.typeFacture.setText(type);
	}

	public Facture getFacture() {
		return facture;
	}

	public void setListeController(ListeFacturesController listeController) {
		this.listeController = listeController;
	}

	public AnchorPane getItemFacture() {
		return itemFacture;
	}

	public void setItemFacture(AnchorPane itemFacture) {
		this.itemFacture = itemFacture;
	}
}
