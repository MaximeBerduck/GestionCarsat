package fr.iut.groupemaxime.gestioncarsat.model;

public abstract class Transport {
	private String typeTransport;
	
	public Transport(String typeTransport) {
		this.typeTransport = typeTransport;
	}
	
	public String getTypeTransport() {
		return this.typeTransport;
	}
}
