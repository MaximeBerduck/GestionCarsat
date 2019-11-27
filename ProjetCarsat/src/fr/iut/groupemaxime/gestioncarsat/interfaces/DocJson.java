package fr.iut.groupemaxime.gestioncarsat.interfaces;

public interface DocJson<T> {

	public void sauvegarderJson(T objet, String adresseFichier);

	public abstract T chargerJson(String adresseFichier);

}
