package fr.iut.groupemaxime.gestioncarsat.view;

import javafx.fxml.FXML;
import fr.iut.groupemaxime.gestioncarsat.MainApp;
import javafx.event.ActionEvent;

public class RootLayoutController {
	private MainApp mainApp;

	@FXML
	private void initialize() {

	}

	// Event Listener on MenuItem.onAction
	@FXML
	public void modifierOptions(ActionEvent event) {
		System.out.println(null == mainApp);
		mainApp.modifierOptions();
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp=mainApp;
	}
}
