package fr.iut.groupemaxime.gestioncarsat.utils;

public enum Couleur {
	Rouge("-fx-background-color: #f53c2f;"), Jaune("-fx-background-color: yellow;"), Orange("-fx-background-color: orange;"),
	Vert("-fx-background-color:green;"), Bleu("-fx-background-color:blue;");

	private String couleur;

	private Couleur(String couleur) {
		this.couleur = couleur;
	}

	public String getCouleur() {
		return this.couleur;
	}
}
