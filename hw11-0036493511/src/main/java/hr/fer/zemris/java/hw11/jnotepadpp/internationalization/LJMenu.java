package hr.fer.zemris.java.hw11.jnotepadpp.internationalization;

import javax.swing.JMenu;

/**
 * Razred <code>LJMenu</code> predstavlja implementaciju menija ovisnu o
 * lokalizacijksim postavkama (o jeziku).
 * 
 * @author Filip
 *
 */
public class LJMenu extends JMenu {

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
	 * Promatrač, ažurira meinije.
	 */
	private ILocalizationListener listener = new ILocalizationListener() {

		@Override
		public void localizationChanged() {
			updateMenu();
		}
	};

	/**
	 * Konstruktor.
	 * 
	 * @param provider
	 *            lokacijski provider
	 * @param key
	 *            ključ prijevoda
	 */
	public LJMenu(ILocalizationProvider provider, String key) {
		this.key = key;
		this.provider = provider;
		updateMenu();
		provider.addLocalizationListener(listener);
	}

	/**
	 * Metoda za ažuriranje menija.
	 */
	private void updateMenu() {
		setText(provider.getString(key));
	}

}
