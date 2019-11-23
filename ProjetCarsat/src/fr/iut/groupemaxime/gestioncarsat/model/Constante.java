package fr.iut.groupemaxime.gestioncarsat.model;

import java.util.HashMap;

public class Constante {
	public static String CHEMIN_OM = "target/OM/";
	public static String CHEMIN_PDF = "target/PDF/";
	public static String EXTENSION_XML = ".xml";
	public static String EXTENSION_PDF = ".pdf";
	public static String CHEMIN_PDF_VIDE = "target/PDF/OM_vide.pdf";

	public static String[] CHAMPS_AGENT = { "nomPrenom", "numCAPSSA", "fonction", "residenceAdmin", "uniteTravail",
			"codeAnalytique", "coefficient" };
	private static HashMap<String,String> MAP_AGENT = new HashMap<String,String>();
	static
	{
		MAP_AGENT.put("nomPrenom","toto");
		MAP_AGENT.put("numCAPSSA","titi");
	}
	
	public static String CHEMIN_OPTIONS = "target/options.json";
}
