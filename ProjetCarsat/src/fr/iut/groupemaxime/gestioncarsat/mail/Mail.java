package fr.iut.groupemaxime.gestioncarsat.mail;

import java.io.File;

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
		fichier.delete();
		if (dossier.listFiles().length == 0) {
			dossier.delete();
		}
	}
	
	@Override
	public String toString() {
		return this.path;
	}
	
	
}
