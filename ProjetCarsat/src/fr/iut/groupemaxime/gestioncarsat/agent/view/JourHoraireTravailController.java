package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.IOException;
import java.util.ArrayDeque;

import fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model.HoraireJournalier;
import fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model.PlageHoraire;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class JourHoraireTravailController {

	@FXML
	private VBox listeHoraireVBox;

	@FXML
	private Label dateJournee;

	@FXML
	private TextField transportUtiliseSurPlace;

	@FXML
	private TextField dureeDuTrajetSurPlace;

	private ArrayDeque<ItemHoraireTravailController> dequeItemHtCtrl;

	@FXML
	private Button boutonValider;

	private AnchorPane pageHoraire;

	private HorairesTravailController htController;

	@FXML
	public void initialize() {
		this.dequeItemHtCtrl = new ArrayDeque<ItemHoraireTravailController>();
		
	}

	public void ajoutHoraire(PlageHoraire plage) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("ItemHoraireTravail.fxml"));
			VBox item = loader.load();
			ItemHoraireTravailController itemHtCtrl = loader.getController();
			itemHtCtrl.setHeure1Deb(String.valueOf(plage.getHeureDeb()));
			itemHtCtrl.setHeure1Fin(String.valueOf(plage.getHeureFin()));
			itemHtCtrl.setMin1Deb(String.valueOf(plage.getMinDeb()));
			itemHtCtrl.setMin1Fin(String.valueOf(plage.getMinFin()));
			this.listeHoraireVBox.getChildren().add(item);
			dequeItemHtCtrl.addLast(itemHtCtrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ajoutHoraire() {
		ajoutHoraire(new PlageHoraire());
	}

	public void supprimerHoraire() {
		if (this.listeHoraireVBox.getChildren().size() != 1) {
			this.listeHoraireVBox.getChildren().remove(this.listeHoraireVBox.getChildren().size() - 1);
			this.dequeItemHtCtrl.removeLast();
		}

	}

	// Event Listener on Button.onAction
	@FXML
	public void validerJournee(ActionEvent event) {
		this.htController.sauvegarderJournee(this.dateJournee.getText());
		this.htController.afficherJourSuivant(this.dateJournee.getText());
	}

	// Event Listener on Button.onAction
	@FXML
	public void sauvegarderHoraireTravail(ActionEvent event) {
		this.htController.sauvegarderHoraires();
	}

	public void modifierHoraireJournalier(HoraireJournalier hj) {
		this.setTransportUtiliseSurPlace(String.valueOf(hj.getTransportUtiliseSurPlace()));
		this.setDureeDuTrajetSurPlace(String.valueOf(hj.getDureeDuTrajetSurPlace()));

		for (PlageHoraire plage : hj.getPlageHoraire()) {
			this.ajoutHoraire(plage);
		}
	
	}

	public void setBoutonSuivantToSauvegarder() {
		this.boutonValider.setText("Valider");
		this.boutonValider.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				htController.sauvegarderJournee(dateJournee.getText());
				htController.sauvegarderHoraires();
			}
		});
	}

	@FXML
	public void boutonSuivantSauvegarde(ActionEvent event) {
		this.boutonValider.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				htController.sauvegarderJournee(dateJournee.getText());
				htController.sauvegarderHoraires();
			}
		});
	}

	public void setDateJournee(String date) {
		this.dateJournee.setText(date);
	}

	public void setHtController(HorairesTravailController htController) {
		this.htController = htController;
	}

	public AnchorPane getPage() {
		return this.pageHoraire;
	}

	public void setPageHoraire(AnchorPane pageHoraire) {
		this.pageHoraire = pageHoraire;
	}

	public String getTransportUtiliseSurPlace() {
		return transportUtiliseSurPlace.getText();
	}

	public void setTransportUtiliseSurPlace(String transportUtiliseSurPlace) {
		this.transportUtiliseSurPlace.setText(transportUtiliseSurPlace);
		;
	}

	public String getDureeDuTrajetSurPlace() {
		return dureeDuTrajetSurPlace.getText();
	}

	public void setDureeDuTrajetSurPlace(String dureeDuTrajetSurPlace) {
		this.dureeDuTrajetSurPlace.setText(dureeDuTrajetSurPlace);
	}

	public ArrayDeque<ItemHoraireTravailController> getPlageHoraire() {
		return dequeItemHtCtrl;
	}

}
