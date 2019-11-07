package fr.iut.groupemaxime.gestioncarsat.model;

public class OrdreMission {
	private Agent agent;
	private Fonction fonction;
	private ResidenceAdministrative residenceAdmin;
	private UniteTravail uniteTravail;
	private Mission mission;
	private Transport transport;
	
	public OrdreMission(Agent agent, Fonction fonction, ResidenceAdministrative residenceAdmin,
			UniteTravail uniteTravail, Mission mission, Transport transport) {
		this.agent = agent;
		this.fonction = fonction;
		this.residenceAdmin = residenceAdmin;
		this.uniteTravail = uniteTravail;
		this.mission = mission;
		this.transport = transport;
	}

	
}
