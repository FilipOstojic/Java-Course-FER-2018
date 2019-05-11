package hr.fer.zemris.java.hw11.jnotepadpp.internationalization;

/**
 * Sučelje <code>ILocalizationListener</code> predstavlja promatrača na
 * lokacijski provider.
 * 
 * @author Filip
 *
 */
public interface ILocalizationListener {
	/**
	 * Metoda se poziva kada se promjeni lokalizacija (jezik).
	 */
	void localizationChanged();
}
