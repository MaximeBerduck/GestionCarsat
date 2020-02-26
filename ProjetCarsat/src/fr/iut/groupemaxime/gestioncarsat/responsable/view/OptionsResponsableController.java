package fr.iut.groupemaxime.gestioncarsat.responsable.view;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import fr.iut.groupemaxime.gestioncarsat.responsable.ResponsableApp;
import fr.iut.groupemaxime.gestioncarsat.utils.Bibliotheque;
import fr.iut.groupemaxime.gestioncarsat.utils.Constante;
import fr.iut.groupemaxime.gestioncarsat.utils.Options;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OptionsResponsableController {
	@FXML
	private TextField textFieldCheminSignature;
	@FXML
	private TextField textFieldMailAgent;
	@FXML
	private Label domaineLabel;
	@FXML
	private VBox vboxMails;
	
	private ResponsableApp mainApp;
	private Options options;
	private Stage optionAgentStage;
	
	@FXML
	public void initialize() {
		this.textFieldCheminSignature.setDisable(true);
		this.domaineLabel.setText('@' + Constante.HOSTNAME);
	}
	
	@FXML
	public void choisirCheminSignature() {
		File signature = Bibliotheque.ouvrirFileChooser(Constante.IMAGE_FILTER);
		if (null != signature) {
			this.textFieldCheminSignature.setText(String.valueOf(signature));
			this.options.setCheminSignature(String.valueOf(signature));
		}
	}

	@FXML
	public void ajouterMail() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setHeaderText("Ajout d'un destinataire");
		dialog.setTitle("Ajouter un nouveau destinataire");
		dialog.setContentText("Veuillez saisir l'adresse mail à ajouter :");

		// Récupérer la valeur saisie
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			this.options.ajouterAutre(result.get());
			this.ajouterItemAutre(result.get());
		}
	}

	public void ajouterItemAutre(String adresseMail) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(OptionsResponsableController.class.getResource("ItemMailsOptions.fxml"));
			AnchorPane itemResponsable = loader.load();

			ItemMailsOptionsController itemMailsOptionsCtrl = loader.getController();
			itemMailsOptionsCtrl.setOptionCtrl(this);
			itemMailsOptionsCtrl.setAdresseMailResponsable(adresseMail);

			vboxMails.getChildren().add(itemResponsable);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void chargerMailsAutres() {
		this.vboxMails.getChildren().clear();
		if (!this.options.getMailsAutres().isEmpty()) {
			for (String res : this.options.getMailsAutres())
				this.ajouterItemAutre(res);
		}
	}

	public void chargerPage(ResponsableApp mainApp, Options options) {
		this.mainApp = mainApp;
		this.options = options;
		this.textFieldCheminSignature.setText(this.options.getCheminSignature());
		this.textFieldMailAgent.setText(this.options.getMailAgent());
	}
	
	public void fermerSecondaryStage() {
		this.optionAgentStage.close();
	}

	public void sauvegarder() {
		this.options.setMailAgent(this.textFieldMailAgent.getText());
		mainApp.setOptions(options);
		mainApp.fermerSecondaryStage();
	}

	public void supprimerMail(String adresseResponsable) {
		this.options.supprimerAutre(adresseResponsable);
		this.chargerMailsAutres();
	}

}
