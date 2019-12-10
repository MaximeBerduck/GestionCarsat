package fr.iut.groupemaxime.gestioncarsat.agent.interfaces;

public interface DocJson<T> {

	public void sauvegarderJson(String adresseFichier);

	public abstract T chargerJson(String adresseFichier);

}
