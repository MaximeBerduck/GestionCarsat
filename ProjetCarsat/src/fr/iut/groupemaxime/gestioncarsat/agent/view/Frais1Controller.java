package fr.iut.groupemaxime.gestioncarsat.agent.view;

import fr.iut.groupemaxime.gestioncarsat.agent.fraismission.model.FraisJournalier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

	private AnchorPane pageFrais1;

	private FraisMissionController fmController;

	// Event Listener on Button.onAction
	@FXML
	public void afficherFrais2(ActionEvent event) {
		this.fmController.afficherFrais2(dateJournee.getText());
	}

	public void modifierFraisJournalier(FraisJournalier fj) {
		// TODO
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

}
