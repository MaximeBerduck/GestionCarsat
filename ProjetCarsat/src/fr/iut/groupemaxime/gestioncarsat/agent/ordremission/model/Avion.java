package fr.iut.groupemaxime.gestioncarsat.agent.ordremission.model;

public class Avion extends Transport {
	private String prisParCRAMCO; //Ne peut prendre que les valeurs Oui, Non

	
	public Avion(String prisParCRAMCO) {
		super("avion");
		this.prisParCRAMCO = prisParCRAMCO;
	}
	
	public String getPrisParCRAMCO() {
		return this.prisParCRAMCO;
	}
}
