package fr.iut.groupemaxime.gestioncarsat.agent.view;

import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisJournalier;
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
	@FXML
	private Button boutonQuitter;
	@FXML
	private TextField typeAutreFrais;
	@FXML
	private TextField montantAutreFrais;

	private AnchorPane pageFrais2;
	private FraisMissionController fmController;

	public void setPageFrais2(AnchorPane pageFrais2) {
		this.pageFrais2 = pageFrais2;
	}

	public void setFmController(FraisMissionController fmController) {
		this.fmController = fmController;
	}

	public void setTypeAutreFrais(String typeAutreFrais) {
		this.typeAutreFrais.setText(typeAutreFrais);
	}

	public void setMontantAutreFrais(String montantAutreFrais) {
		this.montantAutreFrais.setText(montantAutreFrais);
	}

	public String getTypeAutreFrais() {
		return this.typeAutreFrais.getText();
	}

	public String getMontantAutreFrais() {
		return this.montantAutreFrais.getText();
	}

	// Event Listener on Button.onAction
	@FXML
	public void validerJournee(ActionEvent event) {
		this.fmController.sauvegarderJournee(this.dateJournee.getText());
		this.fmController.afficherJourSuivant(this.dateJournee.getText());
	}

	// Event Listener on Button.onAction
	@FXML
	public void sauvegarderFraisMission() {
		this.fmController.sauvegarderJournee(this.dateJournee.getText());
		this.fmController.sauvegarderFrais();
		this.fmController.getAgentApp().retirerDocActif();
	}

	// Event Listener on Button.onAction
	@FXML
	public void retourFrais1() {
		this.fmController.retourFrais1(this.dateJournee.getText());
	}

	public void modifierFraisJournalier(FraisJournalier fj) {
		this.setTypeFraisTransport(fj.getTypeFraisTransport());
		this.setMontantFraisTransport(String.valueOf(fj.getMontantFraisTransport()));

		this.setNbrKmService(String.valueOf(fj.getNbrKmVehiService()));
		this.setNbrKmPerso(String.valueOf(fj.getNbrKmVehiPerso()));
	}

	public void setBoutonSuivantToSauvegarder() {
		this.boutonQuitter.setVisible(false);

		this.boutonValider.setText("Valider");
		this.boutonValider.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				sauvegarderFraisMission();
			}
		});

		// this.boutonQuitter.setVisible(false);
	}

	public void setTypeFraisTransport(String typeFraisTransport) {
		this.typeFraisTransport.setText(typeFraisTransport);
	}

	public void setMontantFraisTransport(String montantFraisTransport) {
		this.montantFraisTransport.setText(montantFraisTransport);
	}

	public void setNbrKmService(String nbrKmService) {
		this.nbrKmService.setText(nbrKmService);
	}

	public void setNbrKmPerso(String nbrKmPerso) {
		this.nbrKmPerso.setText(nbrKmPerso);
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
