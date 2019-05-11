package hr.fer.zemris.java.hw11.jnotepadpp.internationalization;

/**
 * Sučelje koje moraju implementirati svi razredi koji žele mijenjat svoje
 * postavke ovisno o lokalizaciji.
 * 
 * @author Filip
 *
 */
public interface ILocalizationProvider {
	/**
	 * Dodaje promatrača u listu.
	 * 
	 * @param listener
	 *            promatrač
	 */
	void addLocalizationListener(ILocalizationListener listener);

	/**
	 * Uklanja promatrača iz liste.
	 * 
	 * @param listener
	 *            promatrač
	 */
	void removeLocalizationListener(ILocalizationListener listener);

	/**
	 * Metoda vraća prijevod ovisno o predanom ključu.
	 * 
	 * @param key
	 *            ključ
	 * @return prijevod
	 */
	String getString(String key);
}
