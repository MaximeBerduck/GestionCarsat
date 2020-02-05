package fr.iut.groupemaxime.gestioncarsat.utils;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import javafx.stage.FileChooser;

public class Constante {
	public static String CHEMIN_PDF = "target/PDF/";
	public static String EXTENSION_XML = ".xml";
	public static String EXTENSION_PDF = ".pdf";
	public static String EXTENSION_MAIL = ".eml";
	public static String CHEMIN_PDF_VIDE = "target/PDF/OM_vide.pdf";
	public static String CHEMIN_FICHIERS_DEFAUT = "target/OM/";
	public static String EXTENSION_JSON = ".json";
	public static String CHEMIN_XLS_VIDE = "target/PDF/HT_vide.xls";
	public static String EXTENSION_XLS = ".xls";

	public static String CHEMIN_OPTIONS = "target/options.json";
	public static String CHEMIN_IMAGES = "ressources/images/";
	public static final String CHEMIN_MAILS_EN_ATTENTE = "target/mailsEnAttente/";
	public static final String CHEMIN_CARRE_BLANC = "target/blanc.jpg";
	
	public static String CHEMIN_EXCEL_VIDE = "target/PDF/HT_vide.xls";
	public static int FIN_LIGNE_EXCEL = 21;
	public static int DEBUT_LIGNE_EXCEL = 14;

	public static SimpleDateFormat FORMAT_DATE_TIRET = new SimpleDateFormat("dd-MM-yyyy");
	public static SimpleDateFormat FORMAT_DATE_SLASH = new SimpleDateFormat("dd/MM/yyyy");
	public static DateTimeFormatter FORMATTER_DATEPICKER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public static int TAILLE_NUMCAPSSA = 8;
	public static int TAILLE_CODE_ANALYTIQUE = 10;

	public static final int TAILLE_SIGNATURE = 60;
	public static final int SIGNATURE_AGENT_OM_Y = 230;
	public static final int SIGNATURE_AGENT_OM_X = 330;
	public static final int TAILLE_SIGNATURE_FM = 30;
	public static final int SIGNATURE_AGENT_FM_X = 430;
	public static final int SIGNATURE_AGENT_FM_Y = 425;

	public static final FileChooser.ExtensionFilter IMAGE_FILTER = new FileChooser.ExtensionFilter(
			"Fichier image (.jpg .png .gif)", "*.jpg", "*.png", "*.gif");

	public static final String BACKGROUND_COLOR_MISSION_SELECTIONNE = "-fx-background-color: #ff651c;";

	public static final String CORPS_DU_MAIL_DEFAUT = "Bonjour, \nVeuillez trouver ci-joint mon ordre de mission. \n\nCordialement.";
	public static final String OBJET_DU_MAIL_DEFAUT = "Ordre de mission";

	public static final String HOSTNAME = "groupemaxime.ddns.net";
	public static final String MOT_DE_PASSE = "root";

	public static final String TITRE_MODIF_OM = "Modification d'un ordre de mission";
	public static final String TITRE_MODIF_FM = "Modification des frais mission";
	public static final String TITRE_MODIF_HT = "Modification des horaires de travail";
	public static final String TITRE_SIGNER_FM = "Valider les informations des frais de missions";
}
