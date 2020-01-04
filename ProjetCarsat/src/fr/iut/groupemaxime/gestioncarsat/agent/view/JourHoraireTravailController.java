package fr.iut.groupemaxime.gestioncarsat.agent.view;

import fr.iut.groupemaxime.gestioncarsat.agent.horaireModel.HoraireTravail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class JourHoraireTravailController {
	
	private int nbHoraire = 2;
	
	@FXML
	private VBox pageHoraire;
	private HorairesTravailController htController;
	
	@FXML 
	private Label dateJournee;
	
	@FXML
	private TextField heure1Deb;
	
	@FXML
	private HBox hbox1Horaire;

	@FXML
	private HBox hbox2Horaire;

	@FXML
	private HBox hbox3Horaire;

	@FXML
	private HBox hbox4Horaire;
	
	@FXML 
	private VBox bye;

	
	public void initialize() {
		if(nbHoraire==1) {
			this.pageHoraire.getChildren().removeAll(bye);
			if(!this.pageHoraire.getChildren().contains(hbox1Horaire)) {
				this.pageHoraire.getChildren().add(2, hbox1Horaire);
			}
		}
		if(nbHoraire==2) {
			this.pageHoraire.getChildren().removeAll(bye);
			if(!this.pageHoraire.getChildren().contains(hbox1Horaire) 
					&& !this.pageHoraire.getChildren().contains(hbox2Horaire)) {
				this.pageHoraire.getChildren().add(2, hbox1Horaire);
				this.pageHoraire.getChildren().add(3, hbox2Horaire);
			}
		}
		
		if(nbHoraire==3) {
			this.pageHoraire.getChildren().removeAll(bye);
			if(!this.pageHoraire.getChildren().contains(hbox1Horaire) 
					&& !this.pageHoraire.getChildren().contains(hbox2Horaire)
					&& !this.pageHoraire.getChildren().contains(hbox3Horaire)) {
				this.pageHoraire.getChildren().add(2, hbox1Horaire);
				this.pageHoraire.getChildren().add(3, hbox2Horaire);
				this.pageHoraire.getChildren().add(4, hbox3Horaire);
			}
		}
		
		if(nbHoraire==4) {
			this.pageHoraire.getChildren().removeAll(bye);
			if(!this.pageHoraire.getChildren().contains(hbox1Horaire) 
					&& !this.pageHoraire.getChildren().contains(hbox2Horaire)
					&& !this.pageHoraire.getChildren().contains(hbox3Horaire)
					&& !this.pageHoraire.getChildren().contains(hbox4Horaire)) {
				this.pageHoraire.getChildren().add(2, hbox1Horaire);
				this.pageHoraire.getChildren().add(3, hbox2Horaire);
				this.pageHoraire.getChildren().add(4, hbox3Horaire);
				this.pageHoraire.getChildren().add(5, hbox4Horaire);
			}
		}
		
		
	}
	
	public void setDateJournee(String date) {
		this.dateJournee.setText(date);
	}
	
	public VBox getPage() {
		return this.pageHoraire; 
	}

	public void setHtController(HorairesTravailController htController) {
		this.htController = htController;
	}

	public void validerHT() {
		
	}
	
	@FXML
	public void btnSuivantHoraire(ActionEvent event) {
		
	}
	
	@FXML
	public void btnAjouterHoraire(ActionEvent event) {
		if(nbHoraire==4) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(htController.getPrimaryStage());
			alert.setTitle("Suppression impossible");
			alert.setHeaderText("Suppression impossible");
			alert.setContentText("Vous ne pouvez pas ajouter plus d'horaires.");

			alert.showAndWait();
		}
		
		if(nbHoraire==3) {
			if(!this.pageHoraire.getChildren().contains(hbox4Horaire)) {
				this.pageHoraire.getChildren().add(5, hbox4Horaire);
			}
			nbHoraire+=1;
		}
		
		if(nbHoraire==2) {
			if(!this.pageHoraire.getChildren().contains(hbox3Horaire)) {
				this.pageHoraire.getChildren().add(4, hbox3Horaire);
			}
			nbHoraire+=1;
		}
		
		if(nbHoraire==1) {
			if(!this.pageHoraire.getChildren().contains(hbox2Horaire)) {
				this.pageHoraire.getChildren().add(3, hbox2Horaire);
			}
			nbHoraire+=1;
		}	
		
		
		
	}
	
	@FXML
	public void btnSupprimerHoraire(ActionEvent event) {
		
		if (nbHoraire==1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(htController.getPrimaryStage());
			alert.setTitle("Suppression impossible");
			alert.setHeaderText("Suppression impossible");
			alert.setContentText("Vous ne pouvez pas supprimer tous les champs d'horaires.");

			alert.showAndWait();
		}
		
		if (nbHoraire == 2 ) {
			this.pageHoraire.getChildren().removeAll(bye, hbox1Horaire, hbox2Horaire,hbox3Horaire, hbox4Horaire);
			if(!this.pageHoraire.getChildren().contains(hbox1Horaire)) {
				this.pageHoraire.getChildren().add(2, hbox1Horaire);
			}
			nbHoraire-=1;
		}
		
		if(nbHoraire == 3) {
			this.pageHoraire.getChildren().removeAll(bye, hbox1Horaire, hbox2Horaire,hbox3Horaire, hbox4Horaire);
			if(!this.pageHoraire.getChildren().contains(hbox1Horaire) 
					&& !this.pageHoraire.getChildren().contains(hbox2Horaire)) {
				this.pageHoraire.getChildren().add(2, hbox1Horaire);
				this.pageHoraire.getChildren().add(3, hbox2Horaire);
			}
			
			nbHoraire-=1;
		}
		
		if(nbHoraire == 4) {
			this.pageHoraire.getChildren().removeAll(bye, hbox1Horaire, hbox2Horaire,hbox3Horaire, hbox4Horaire);
			if(!this.pageHoraire.getChildren().contains(hbox1Horaire) 
					&& !this.pageHoraire.getChildren().contains(hbox2Horaire)
					&& !this.pageHoraire.getChildren().contains(hbox3Horaire)) {
				this.pageHoraire.getChildren().add(2, hbox1Horaire);
				this.pageHoraire.getChildren().add(3, hbox2Horaire);
				this.pageHoraire.getChildren().add(4, hbox3Horaire);
			}
			nbHoraire-=1;
		}
		
		
	}
	
	public void setChamps(HoraireTravail horaire) {
		
	}
	
	
}
