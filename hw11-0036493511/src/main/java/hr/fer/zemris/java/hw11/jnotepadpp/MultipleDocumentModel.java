package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Sučelje <code>MultipleDocumentModel</code> definira metode koje svaki razred
 * koji predstavlja više datoteka mora imati.
 * 
 * @author Filip
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	/**
	 * Stvara novi {@link SingleDocumentModel}.
	 * 
	 * @return {@link SingleDocumentModel}
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Vraća trenutno otvorenu datoteku.
	 * 
	 * @return {@link SingleDocumentModel}
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Očitava novu datoteku iz memorije računala
	 * 
	 * @param path
	 *            putanja do datoteke
	 * @return {@link SingleDocumentModel}
	 * 
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Pohranjuje datoteku ({@link SingleDocumentModel}) na određenu putanju.
	 * 
	 * @param model
	 *            {@link SingleDocumentModel}
	 * @param newPath
	 *            putanja do pohrane
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Zatvara (gasi) datoteku.
	 * 
	 * @param model
	 *            {@link SingleDocumentModel}
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Dodaju promatrače u listu.
	 * 
	 * @param l
	 *            {@link MultipleDocumentListener}
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Uklanja promatrača iz liste.
	 * 
	 * @param l
	 *            {@link MultipleDocumentListener}
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Vraća broj datoteka (dokumenata).
	 * 
	 * @return broj datoteka
	 */
	int getNumberOfDocuments();

	/**
	 * Vraća dokument za zadani indeks.
	 * 
	 * @param index
	 *            indkes datoteke
	 * @return {@link SingleDocumentModel}
	 */
	SingleDocumentModel getDocument(int index);
}
