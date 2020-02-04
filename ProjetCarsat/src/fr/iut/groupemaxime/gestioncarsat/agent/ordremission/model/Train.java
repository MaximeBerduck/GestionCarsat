package fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model;

public class Train extends Transport {
	private String classe; //Ne peut prendre que les valeurs premiereClasse et deuxiemeClasse
	private String prisParCRAMCO; //Ne peut prendre que les valeurs Oui ou Non

	public Train(String classe, String prisParCRAMCO) {
		super("train");
		if("premiereClasse".equals(classe) || "deuxiemeClasse".equals(classe)) {
			this.classe = classe;
		}
		else {
			//TODO erreur a generer
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
