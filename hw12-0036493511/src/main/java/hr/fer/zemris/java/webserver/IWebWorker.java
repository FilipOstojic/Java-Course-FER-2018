package hr.fer.zemris.java.webserver;

/**
 * Sučelje predstavlja webWorkera čije implementacije izvršavaju razne zadatke
 * vidljive korisniku.
 * 
 * @author Filip
 *
 */
public interface IWebWorker {
	/**
	 * Metoda obrađuje dobiveni kontekst i izvršava specifični zadatak.
	 * 
	 * @param context
	 *            {@link RequestContext}
	 * @throws Exception
	 */
	public void processRequest(RequestContext context) throws Exception;
}
