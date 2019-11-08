package fr.iut.groupemaxime.gestioncarsat.form;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

public class PDF {
	private PDDocument modele;
	private PDAcroForm formulaire;
	private PDField champ;
	
	public PDF(File source) throws IOException {
		modele = PDDocument.load(source);
		formulaire = modele.getDocumentCatalog().getAcroForm();
		champ = null;
	}
	
	public void remplirChamp(String champ, String valeur) throws IOException {
		this.champ = formulaire.getField(champ);
		this.champ.setValue(valeur);
	}
	
	public void sauvegarderPDF() throws IOException {
		modele.save("target/PDF/Doc1modif.pdf");
	}
	
	public void fermerPDF() throws IOException {
		modele.close();
	}
	
	public static void main(String[] args) throws IOException {
		PDF om = new PDF(new File("target/PDF/Doc1.pdf"));
		om.remplirChamp("surname", "salut");
		om.sauvegarderPDF();
		om.fermerPDF();
	}
}
