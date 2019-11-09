package fr.iut.groupemaxime.gestioncarsat.model;

public class Train extends Transport {
	private String classe; //Ne peut prendre que les valeurs premiereClasse et deuxiemeClasse
	private String prisParCRAMCO; //Ne peut prendre que les valeurs Oui, Non ou <autres>

	public Train(String classe, String prisParCRAMCO) {
		super("train");
		if("premiereClasse".equals(classe) || "deuxiemeClasse".equals(classe)) {
			this.classe = classe;
		}
		else {
			System.out.println("erreur de saisie de la classe du train"); // erreur à générer
		}
		this.prisParCRAMCO = prisParCRAMCO;
	}

	public String getClasse() {
		return classe;
	}

	public String getPrisParCRAMCO() {
		return prisParCRAMCO;
	}

}
