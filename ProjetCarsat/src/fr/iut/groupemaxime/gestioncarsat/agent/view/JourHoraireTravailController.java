package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;

import fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model.HoraireJournalier;
import fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model.PlageHoraire;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class JourHoraireTravailController {

	@FXML
	private VBox listeHoraireVBox;

	@FXML
	private Label dateJournee;

	@FXML
	private TextField transportUtiliseSurPlace;

	@FXML
	private TextField observation;

	private LinkedList<ItemHoraireTravailController> listItemHtCtrl;
	private ListIterator<ItemHoraireTravailController> iteratorItemHt;

	@FXML
	private Button boutonValider;

	private AnchorPane pageHoraire;

	private HorairesTravailController htController;
	@FXML
	private HBox hboxTransport;

	@FXML
	public void initialize() {
		this.listItemHtCtrl = new LinkedList<ItemHoraireTravailController>();
		this.iteratorItemHt = listItemHtCtrl.listIterator();
		ScheduledService<Void> serviceBtnSuppression = new ScheduledService<Void>() {

			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {

					@Override
					protected Void call() {
						if (listItemHtCtrl.size() == 1) {
							listItemHtCtrl.get(0).changerDisableBtn(true);
						}
						if (listItemHtCtrl.size() == 2) {
							listItemHtCtrl.get(0).changerDisableBtn(false);
						}
						return null;
					}

				};
			}
		};
		ScheduledService<Void> serviceAffichageTransport = new ScheduledService<Void>() {

			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {

					@Override
					protected Void call() {
						boolean actif = false;
						for(ItemHoraireTravailController item : listItemHtCtrl) {
							if(item.transportIsSelected()) {
								actif = true;
							}
						}
						hboxTransport.setVisible(actif);
						return null;
					}

				};
			}
		};
		serviceBtnSuppression.setPeriod(Duration.ZERO);
		serviceBtnSuppression.start();
		serviceAffichageTransport.setPeriod(Duration.ZERO);
		serviceAffichageTransport.start();
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
			itemHtCtrl.setPage(item);
			itemHtCtrl.setJht(this);
			this.listeHoraireVBox.getChildren().add(item);
			this.listItemHtCtrl.add(itemHtCtrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ajouterItemFin(ItemHoraireTravailController item) {
		this.listeHoraireVBox.getChildren().add(item.getPage());
	}

	public void placerIterateur(ItemHoraireTravailController plage) {
		this.iteratorItemHt = listItemHtCtrl.listIterator();
		while (this.iteratorItemHt.hasNext() && !this.iteratorItemHt.next().equals(plage))
			;
	}

	public void ajoutHoraireApresPlage(ItemHoraireTravailController itemPlage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("ItemHoraireTravail.fxml"));
			VBox item = loader.load();
			ItemHoraireTravailController itemHtCtrl = loader.getController();
			itemHtCtrl.setPage(item);
			itemHtCtrl.setJht(this);
			placerIterateur(itemPlage);
			this.iteratorItemHt.add(itemHtCtrl);
			rafraichirListHoraire();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void rafraichirListHoraire() {
		listeHoraireVBox.getChildren().clear();
		for (ItemHoraireTravailController element : listItemHtCtrl) {
			this.ajouterItemFin(element);
		}
	}

	public void ajoutHoraire() {
		ajoutHoraire(new PlageHoraire());
	}

	public void supprimerHoraire(ItemHoraireTravailController item) {
		placerIterateur(item);
		if (this.listeHoraireVBox.getChildren().size() != 1) {
			this.iteratorItemHt.remove();
		}
		rafraichirListHoraire();
	}

	// Event Listener on Button.onAction
	@FXML
	public void validerJournee(ActionEvent event) {
		if (tousLesChampsValides()) {
			this.htController.sauvegarderJournee(this.dateJournee.getText());
			this.htController.afficherJourSuivant(this.dateJournee.getText());
		}

	}

	// Event Listener on Button.onAction
	@FXML
	public void sauvegarderHoraireTravail(ActionEvent event) {
		this.htController.sauvegarderHoraires();
	}

	public void modifierHoraireJournalier(HoraireJournalier hj) {
		if (null != hj.getTransportUtiliseSurPlace()) {
			this.setTransportUtiliseSurPlace(hj.getTransportUtiliseSurPlace().split(":")[0]);
		}

		if (null != hj.getObservation()) {
			this.setObservation(hj.getObservation().split(":")[0]);
		}

		for (PlageHoraire plage : hj.getPlageHoraire()) {
			this.ajoutHoraire(plage);
		}

	}

	public void setBoutonSuivantToSauvegarder() {
		this.boutonValider.setText("Valider");
		if (tousLesChampsValides()) {
			this.boutonValider.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					htController.sauvegarderJournee(dateJournee.getText());
					htController.sauvegarderHoraires();
				}
			});
		}
	}

	@FXML
	public void boutonSuivantSauvegarde(ActionEvent event) {
		if (tousLesChampsValides()) {
			this.boutonValider.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					htController.sauvegarderJournee(dateJournee.getText());
					htController.sauvegarderHoraires();
				}
			});
		}
	}

	public boolean tousLesChampsValides() {
		String erreur = "";
		int compteur = 0;
//		if(dureeDuTrajetSurPlace.getText()!=null || dureeDuTrajetSurPlace.getText().length()!=0) {
//			try {
//				Integer.parseInt(dureeDuTrajetSurPlace.getText());
//			} catch (NumberFormatException e) {
//				erreur += "Le champ duree de trajet est invalide (entrez un nombre entier)!\n";
//			}
//		}

		for (ItemHoraireTravailController item : this.listItemHtCtrl) {
			if (item.getHeure1Deb() != null || item.getHeure1Deb().length() != 0 || item.getHeure1Fin() != null
					|| item.getHeure1Fin().length() != 0 || item.getMin1Deb() != null || item.getMin1Deb().length() != 0
					|| item.getMin1Fin() != null || item.getMin1Fin().length() != 0) {
				try {
					Integer.parseInt(item.getHeure1Deb());
					Integer.parseInt(item.getHeure1Fin());
					Integer.parseInt(item.getMin1Deb());
					Integer.parseInt(item.getMin1Fin());
				} catch (NumberFormatException e) {
					if (compteur == 0) {
						erreur += "L'un des horaires de travail est invalide (entrez des nombres entiers)!\n";
						compteur++;
					}
				}
			}
		}
		if (erreur.length() > 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(htController.getPrimaryStage());
			alert.setTitle("Champs non Valides");
			alert.setHeaderText("Des champs ne sont pas valides");
			alert.setContentText(erreur);

			alert.showAndWait();
			return false;
		} else {
			return true;
		}
	}

	public String getObservation() {
		return observation.getText();
	}

	public void setObservation(String observation) {
		this.observation.setText(observation);
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
	}

	public VBox getListeHoraireVBox() {
		return listeHoraireVBox;
	}

	public void setListeHoraireVBox(VBox listeHoraireVBox) {
		this.listeHoraireVBox = listeHoraireVBox;
	}

	public LinkedList<ItemHoraireTravailController> getListItemHtCtrl() {
		return listItemHtCtrl;
	}

	public void setListItemHtCtrl(LinkedList<ItemHoraireTravailController> listItemHtCtrl) {
		this.listItemHtCtrl = listItemHtCtrl;
	}

}
