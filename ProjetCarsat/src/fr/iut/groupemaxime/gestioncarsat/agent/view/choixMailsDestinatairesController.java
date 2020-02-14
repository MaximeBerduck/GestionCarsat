package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.util.Optional;

import fr.iut.groupemaxime.gestioncarsat.utils.Options;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class choixMailsDestinatairesController {
	@FXML
	private ScrollPane scrollMails;
	@FXML
	private Button buttonAjouterDestinataire;
	@FXML
	private Button buttonValider;
	@FXML
	private VBox vboxMails;
	private Options options;
	private Stage fenetreChoix;
	String destinataire;

	public void initialize(Stage choixMailsDestinataires, Options options) {
		this.fenetreChoix = choixMailsDestinataires;
		this.options = options;
		this.destinataire = "";
	}

	// Event Listener on Button[#buttonAjouterDestinataire].onAction
	@FXML
	public void ajouterNewDestinataire(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Nouveau destinataire");
		dialog.setHeaderText("Saisie d'un nouveau destinataire");
		dialog.setContentText("Saisir l'adresse mail du destinataire :");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			CheckBox check = new CheckBox(result.get());
			vboxMails.getChildren().add(check);
			check.setSelected(true);
			options.ajouterResponsable(result.get());
			options.sauvegarder();
		}
	}

	// Event Listener on Button[#buttonValider].onAction
	@FXML
	public void validerMailsDesti(ActionEvent event) {
		String desti = "";
		for (Node node : vboxMails.getChildren()) {
			CheckBox check = (CheckBox) node;
			if (check.isSelected()) {
				desti += check.getText() + ',';
			}
		}
		if (desti.endsWith(","))
			desti = desti.substring(0, desti.length());
		fenetreChoix.close();
		this.destinataire = desti;
	}

	public void ajouterMails(String mail) {
		CheckBox check = new CheckBox(mail);
		vboxMails.getChildren().add(check);
	}

	public String getDestinaires() {
		return this.destinataire;
	}
}
