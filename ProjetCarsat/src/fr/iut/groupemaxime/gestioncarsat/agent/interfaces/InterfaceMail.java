package fr.iut.groupemaxime.gestioncarsat.agent.interfaces;

import java.io.File;
import java.util.List;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public interface InterfaceMail {
	public TextArea getCorpsDuMail();
	public File[] getPiecesJointes();
	public TextField getObjetDuMail();
	public TextField getExpediteur();
	public List<String> getDestinatairesTab();
	public List<String> getDestEnCopieTab();

	
}
