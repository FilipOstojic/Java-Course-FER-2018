package hr.fer.zemris.java.hw11.jnotepadpp.internationalization;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton koji predstavlja lokalizacijski provider, koji čuva podatke o
 * jeziku te čita iz lokacijskih datoteka prijjevode.
 * 
 * @author Filip
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	/**
	 * Trenutni jezik koji se koristi.
	 */
	private String language;
	/**
	 * Bundle.
	 */
	private ResourceBundle bundle;
	/**
	 * Putanja do foldera s datotekama prijavoda.
	 */
	private static String BUNDLE_PATH = "hr.fer.zemris.java.hw11.jnotepadpp.prijevodi";
	/**
	 * Jedini primjerak razreda {@link LocalizationProvider}.
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();

	/**
	 * Konstruktor.
	 */
	private LocalizationProvider() {
		language = "en";
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(BUNDLE_PATH, locale);
	}

	/**
	 * Metoda vraća instancu ovog razreda.
	 * 
	 * @return instanca LocalizationProvider-a
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	/**
	 * Vraća jezik trenutni.
	 * 
	 * @return trenutni jezik
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Postavlja jezik.
	 * 
	 * @param language
	 *            jezik
	 */
	public void setLanguage(String language) {
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(BUNDLE_PATH, locale);
		fire();
	}

	/**
	 * Vraća bundle.
	 * 
	 * @return bundle.
	 */
	public ResourceBundle getBundle() {
		return bundle;
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

}
