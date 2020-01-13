package fr.iut.groupemaxime.gestioncarsat.agent.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class Frais2Controller {
	@FXML
	private Label dateJournee;
	@FXML
	private TextField typeFraisTransport;
	@FXML
	private TextField montantFraisTransport;
	@FXML
	private VBox nbrKilometreLayout;
	@FXML
	private AnchorPane vehiculeServiceLayout;
	@FXML
	private TextField nbrKmService;
	@FXML
	private AnchorPane vehiculePersonnelLayout;
	@FXML
	private TextField nbrKmPerso;
	@FXML
	private Button boutonValider;

	private AnchorPane pageFrais2;
	private FraisMissionController fmController;

	public void setPageFrais2(AnchorPane pageFrais2) {
		this.pageFrais2 = pageFrais2;
	}

	public void setFmController(FraisMissionController fmController) {
		this.fmController = fmController;
	}

	// Event Listener on Button.onAction
	@FXML
	public void validerJournee(ActionEvent event) {
		this.fmController.afficherJourSuivant(this.dateJournee.getText());
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void sauvegarderFraisMission(ActionEvent event) {
		this.fmController.sauvegarderFrais();
	}

	// Event Listener on Button.onAction
	@FXML
	public void retourFrais1(ActionEvent event) {
		this.fmController.retourFrais1(this.dateJournee.getText());
	}
	
	public void setBoutonSuivantToSauvegarder() {
		this.boutonValider.setText("Valider");
		this.boutonValider.setOnAction(new EventHandler<ActionEvent>() 
		{
		    @Override public void handle(ActionEvent e) 
		    {
				fmController.sauvegarderFrais();
		    }
		});
	}

	public void setDateJournee(String jour) {
		this.dateJournee.setText(jour);
	}

	public AnchorPane getPage() {
		return this.pageFrais2;
	}

	public VBox getNbrKilometreLayout() {
		return nbrKilometreLayout;
	}

	public AnchorPane getVehiculeServiceLayout() {
		return this.vehiculeServiceLayout;
	}

	public AnchorPane getVehiculePersoLayout() {
		return this.vehiculePersonnelLayout;
	}

	public String getNbrKmService() {
		return nbrKmService.getText();
	}

	public String getNbrKmPerso() {
		return nbrKmPerso.getText();
	}

	public String getTypeFraisTransport() {
		return typeFraisTransport.getText();
	}

	public String getMontantFraisTransport() {
		return montantFraisTransport.getText();
	}

}
