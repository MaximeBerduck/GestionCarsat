package fr.iut.groupemaxime.gestioncarsat.mail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.mail.Message;

public class Mail {
	private Message mail;
	private String path;
	
	public Mail(Message mail) {
		this.mail = mail;
		this.path = "";
	}

	public boolean isSauvegarde() {
		return !path.equals("");
	}

	public Message getMail() {
		return mail;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}

	public void supprimer() {
		File fichier = new File(path);
		File dossier = fichier.getParentFile();
		try {
			Files.delete(fichier.toPath());
			Files.delete(dossier.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return this.path;
	}
	
	
}
