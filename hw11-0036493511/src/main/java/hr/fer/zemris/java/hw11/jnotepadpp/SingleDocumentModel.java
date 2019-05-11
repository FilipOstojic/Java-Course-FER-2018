package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Sučelje <code>SingleDocumentModel</code> definira metode koje svaki razred
 * koji predstavlja datoteku mora imati.
 * 
 * @author Filip
 *
 */
public interface SingleDocumentModel {
	/**
	 * Vraća JTextArea od datoteke.
	 * 
	 * @return {@link JTextArea}
	 */
	JTextArea getTextComponent();

	/**
	 * Vraća put do datoteke ili null reeferencu ako datoteka nije pohranjena.
	 * 
	 * @return put do datoteke
	 */
	Path getFilePath();

	/**
	 * Postavlja put do datoteke.
	 * 
	 * @param path
	 *            putanja do datoteke
	 */
	void setFilePath(Path path);

	/**
	 * Provjerava je li datoteka modificirana.
	 * 
	 * @return true ako je datoteka modificirana, inače false
	 */
	boolean isModified();

	/**
	 * Postavlja datoteku na modificiranu/nemodificiranu.
	 * 
	 * @param modified
	 *            zastavica modificiranosti
	 */
	void setModified(boolean modified);

	/**
	 * Metoda dodaje promatrača u listu.
	 * 
	 * @param l
	 *            {@link SingleDocumentListener}
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Metoda uklanja promatrača iz liste.
	 * 
	 * @param l
	 *            {@link SingleDocumentListener}
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
