package hr.fer.zemris.java.hw11.jnotepadpp.internationalization;

import javax.swing.JLabel;

/**
 * Razred <code>LJLabel</code> predstavlja implementaciju labele koja je ovisna
 * o lokalizacijskim postavkama (o jeziku).
 * 
 * @author Filip
 *
 */
public class LJLabel extends JLabel {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Ključ za prijevod.
	 */
	private String key;
	/**
	 * Lokalizacijski provider.
	 */
	private ILocalizationProvider provider;
	/**
	 * Promatrač koji ažurira labele.
	 */
	private ILocalizationListener listener = new ILocalizationListener() {

		@Override
		public void localizationChanged() {
			updateLabel();
		}
	};

	/**
	 * Konstruktor.
	 * 
	 * @param provider
	 *            lokalizacijski providr
	 * @param key
	 *            ključ za prijevod
	 */
	public LJLabel(ILocalizationProvider provider, String key) {
		this.key = key;
		this.provider = provider;
		updateLabel();
		provider.addLocalizationListener(listener);
	}

	/**
	 * Metoda koja ažurira vrijednosti labele ovisno o jeziku.
	 */
	private void updateLabel() {
		setText(provider.getString(key));
	}

}
