package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.IOException;
import java.util.HashSet;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.Facture;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListeFacturesController {
	@FXML
	private VBox listeFactures;
	private HashSet<ItemFactureController> listeItemFactures;

	private Stage stage;

	private int nbrMax; // Nombre maximum de factures possibles

	private ItemFactureController itemEnCoursModification;

	public void initialize() {
		this.listeItemFactures = new HashSet<ItemFactureController>();
	}

	// Event Listener on Button.onAction
	@FXML
	public void valider(ActionEvent event) {
		this.stage.close();
	}

	// Event Listener on Button.onAction
	@FXML
	public void ajouterNouvelleFacture() {
		if (this.listeItemFactures.size() < nbrMax) {
			ouvrirSaisieFacture(null);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Nombre de facture maximum atteint");
			alert.setContentText("Vous ne pouvez pas avoir plus de " + nbrMax
					+ " facture(s)/nqui correspond au nombre maximum de justificatif restant (maximum-forfait)");
			alert.showAndWait();
		}
	}
	
	public void modifierListeFacture(HashSet<Facture> listeFacture) {
		for(Facture facture : listeFacture) {
			this.ajouterFactureListe(facture);
		}
	}

	public void ouvrirSaisieFacture(Facture facture) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AgentApp.class.getResource("view/SaisieFacture.fxml"));
			AnchorPane saisieLayout = loader.load();
			Stage secondaryStage = new Stage();

			Scene scene = new Scene(saisieLayout);
			SaisieFactureController controllerSaisie = loader.getController();

			controllerSaisie.setListeController(this);
			controllerSaisie.retirerChemin();
			controllerSaisie.setStage(secondaryStage);

			if (null != facture) {
				controllerSaisie.setFacture(facture);
			}

			secondaryStage.setScene(scene);
			secondaryStage.setTitle("Paramètres");
			secondaryStage.initOwner(this.stage);
			secondaryStage.initModality(Modality.WINDOW_MODAL);
			secondaryStage.getIcons().add(new Image("file:" + Constante.CHEMIN_IMAGES + "logo.png"));

			secondaryStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void modifierFacture(ItemFactureController factureController) {
		this.itemEnCoursModification = factureController;
		ouvrirSaisieFacture(factureController.getFacture());
	}

	public void supprimerFacture(ItemFactureController factureController) {
		this.listeItemFactures.remove(factureController);
		this.listeFactures.getChildren().remove(factureController.getItemFacture());
	}

	public void ajouterFactureListe(Facture facture) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AgentApp.class.getResource("view/ItemFacture.fxml"));
			AnchorPane itemLayout = loader.load();

			ItemFactureController controllerItem = loader.getController();

			controllerItem.setListeController(this);
			controllerItem.setFacture(facture);
			controllerItem.setItemFacture(itemLayout);

			listeItemFactures.add(controllerItem);
			listeFactures.getChildren().add(itemLayout);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public HashSet<Facture> getFactures() {
		HashSet<Facture> liste = new HashSet<Facture>();
		for (ItemFactureController itemFacture : listeItemFactures) {
			Facture facture = itemFacture.getFacture();
			liste.add(facture);
		}
		return liste;
	}

	public void setStage(Stage secondaryStage) {
		this.stage = secondaryStage;
	}

	public void setNbrMax(int nbrMax) {
		this.nbrMax = nbrMax;
	}

	public void modifierItemFacture(Facture facture) {
		this.itemEnCoursModification.setFacture(facture);
	}
}
