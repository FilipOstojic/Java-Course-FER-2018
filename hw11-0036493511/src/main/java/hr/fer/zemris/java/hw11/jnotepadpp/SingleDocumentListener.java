package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Sučelje <code>SingleDocumentListener</code> sadrži metode koje opisuju
 * promatrače nad razredom {@link SingleDocumentModel}.
 * 
 * @author Filip
 *
 */
public interface SingleDocumentListener {
	/**
	 * Poziva se kada je neka datoteka promjenila svoju zastavicu modifikacije.
	 * 
	 * @param model
	 *            {@link SingleDocumentModel}
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Poziva se kada je neka datoteka promjenila svoju putanju.
	 * 
	 * @param model
	 *            {@link SingleDocumentModel}
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
