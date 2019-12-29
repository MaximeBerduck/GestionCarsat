package fr.iut.groupemaxime.gestioncarsat.app;

import fr.iut.groupemaxime.gestioncarsat.agent.AgentApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ChoixAppController {
	@FXML
	private Button buttonAgent;
	@FXML
	private Button buttonResponsable;
	
	private App app;
	
	public void setApp(App app) {
		this.app = app;
	}

	// Event Listener on Button[#buttonAgent].onAction
	@FXML
	public void ouvrirAppAgent(ActionEvent event) {
		try {
			app.agentAppOpen();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Event Listener on Button[#buttonResponsable].onAction
	@FXML
	public void ouvrirAppResponsable(ActionEvent event) {
		app.responsableAppOpen();
	}
}
