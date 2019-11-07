package fr.iut.groupemaxime.gestioncarsat.model;

public class Train extends Transport {
	private String classe; //Ne peut prendre que les valeurs premiereClasse et deuxiemeClasse

	public Train(String classe) {
		super("train");
		if("premiereClasse".equals(classe) || "deuxiemeClasse".equals(classe)) {
			this.classe = classe;
		}
		else {
			System.out.println("erreur de saisie de la classe du train"); // erreur à générer
		}
	}

}
