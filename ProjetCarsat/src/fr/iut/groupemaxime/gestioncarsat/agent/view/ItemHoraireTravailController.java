package fr.iut.groupemaxime.gestioncarsat.agent.view;

import java.io.IOException;

import fr.iut.groupemaxime.gestioncarsat.agent.horairemission.model.PlageHoraire;
import fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model.OrdreMission;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ItemHoraireTravailController {
	@FXML
	private Button btnSupprimer;
	@FXML
	private VBox itemHoraire;

	@FXML
	private TextField heure1Deb;
	@FXML
	private TextField min1Deb;
	@FXML
	private TextField heure1Fin;

	JourHoraireTravailController jht;
	
	@FXML
	private TextField min1Fin;


	private VBox page;

	public void ajoutHoraire() {
		this.jht.ajoutHoraireApresPlage(this);
		
	}
	
	public void supprimerHoraire() {
		this.jht.supprimerHoraire(this);
	}

	public JourHoraireTravailController getJht() {
		return jht;
	}

	public void setJht(JourHoraireTravailController jht) {
		this.jht = jht;
	}

	public VBox getPage() {
		return page;
	}

	public void setPage(VBox item) {
		this.page = item;
	}
	
	public String getHeure1Deb() {
		return heure1Deb.getText();
	}

	public void setHeure1Deb(String heure1Deb) {
		this.heure1Deb.setText(heure1Deb);
	}

	public String getMin1Deb() {
		return min1Deb.getText();
	}

	public void setMin1Deb(String min1Deb) {
		this.min1Deb.setText(min1Deb);
		;
	}

	public String getHeure1Fin() {
		return heure1Fin.getText();
	}

	public void setHeure1Fin(String heure1Fin) {
		this.heure1Fin.setText(heure1Fin);
		;
	}

	public String getMin1Fin() {
		return min1Fin.getText();
	}

	public void setMin1Fin(String min1Fin) {
		this.min1Fin.setText(min1Fin);
		;
	}

	public void changerDisableBtn(boolean b) {
		this.btnSupprimer.setDisable(b);
	}
	
	

}
