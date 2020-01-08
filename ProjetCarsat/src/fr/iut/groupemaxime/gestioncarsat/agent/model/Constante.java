package fr.iut.groupemaxime.gestioncarsat.agent.model;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import javafx.stage.FileChooser;

public class Constante {
	public static String CHEMIN_PDF = "target/PDF/";
	public static String EXTENSION_XML = ".xml";
	public static String EXTENSION_PDF = ".pdf";
	public static String CHEMIN_PDF_VIDE = "target/PDF/OM_vide.pdf";
	public static String CHEMIN_FICHIERS_DEFAUT = "target/OM/";
	public static String EXTENSION_JSON = ".json";

	public static String CHEMIN_OPTIONS = "target/options.json";
	public static String CHEMIN_IMAGES = "ressources/images/";
	public static final String CHEMIN_MAILS_EN_ATTENTE = "target/mails_en_attente.json";

	public static SimpleDateFormat FORMAT_DATE_TIRET = new SimpleDateFormat("dd-MM-yyyy");
	public static SimpleDateFormat FORMAT_DATE_SLASH = new SimpleDateFormat("dd/MM/yyyy");
	public static DateTimeFormatter FORMATTER_DATEPICKER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public static int TAILLE_NUMCAPSSA = 8;
	public static int TAILLE_CODE_ANALYTIQUE = 10;

	public static final int TAILLE_SIGNATURE = 60;
	public static final int SIGNATURE_AGENT_Y = 230;
	public static final int SIGNATURE_AGENT_X = 330;

	public static final FileChooser.ExtensionFilter IMAGE_FILTER = new FileChooser.ExtensionFilter(
			"Fichier image (.jpg .png .gif)", "*.jpg", "*.png", "*.gif");

	public static final String BACKGROUND_COLOR_MISSION_SELECTIONNE = "-fx-background-color: #D6D39D;";

	public static final String OBJET_DU_MAIL_DEFAUT = "Bonjour, \nVeuillez trouver ci-joint mon ordre de mission. \n\nRespectueusement.";
}
