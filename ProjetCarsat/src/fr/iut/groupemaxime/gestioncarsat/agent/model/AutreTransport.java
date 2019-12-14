package fr.iut.groupemaxime.gestioncarsat.agent.model;

public class AutreTransport extends Transport {
	private String autreMoyenTransport;

	public AutreTransport(String autreMoyenTransport) {
		super("AutreTransport");
		this.autreMoyenTransport = autreMoyenTransport;
	}

	public String getAutreTransport() {
		return this.autreMoyenTransport;
	}

}
