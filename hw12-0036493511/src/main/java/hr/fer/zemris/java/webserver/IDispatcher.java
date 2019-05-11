package hr.fer.zemris.java.webserver;

/**
 * Sučelje <code>IDispatcher</code> predstavlja otpremitelja korisnikovog
 * zahtjeva.
 * 
 * @author Filip
 *
 */
public interface IDispatcher {
	/**
	 * Ovisno o dobivenoj putanji (čiji sadržaj analizira) šalje zahtjev na pripadnu
	 * daljnju obradu.
	 * 
	 * @param urlPath
	 *            putanja
	 * @throws Exception
	 *             ako se dogodi greška pri otpremanju zahtjeva
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
