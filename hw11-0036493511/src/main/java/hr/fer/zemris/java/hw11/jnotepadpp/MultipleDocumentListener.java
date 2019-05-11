package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Sučelje <code>MultipleDocumentListener</code> sadrži metode za razrede koji
 * čuvaju više dokumenata odjednom.
 * 
 * @author Filip
 *
 */
public interface MultipleDocumentListener {
	/**
	 * Metoda se poziva kada se promjeni trenutni dokument koji je u fokusu.
	 * 
	 * @param previousModel
	 *            prozor koji je bio prethodno u fokusu
	 * @param currentModel
	 *            prozor koji je trenutno u fokusu
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Metoda se poziva kada se doda novi dokument u razred.
	 * 
	 * @param model
	 *            novi model
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Metoda koja se poziva kada se ukloni dokument iz razreda.
	 * 
	 * @param model
	 *            uklonjeni dokument
	 */
	void documentRemoved(SingleDocumentModel model);

}
