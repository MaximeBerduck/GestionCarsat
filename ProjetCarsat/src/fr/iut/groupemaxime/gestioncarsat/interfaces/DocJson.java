package fr.iut.groupemaxime.gestioncarsat.interfaces;

public interface DocJson<T> {

	public void sauvegarderJson(String adresseFichier);

	public abstract T chargerJson(String adresseFichier);

}
