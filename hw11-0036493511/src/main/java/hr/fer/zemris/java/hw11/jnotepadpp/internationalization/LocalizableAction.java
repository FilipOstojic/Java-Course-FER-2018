package hr.fer.zemris.java.hw11.jnotepadpp.internationalization;

import javax.swing.AbstractAction;

/**
 * Razred <code>LocalizableAction</code> predstavlja akciju koja ovisi o jeziku,
 * te ažurira svoje podatke ovisno o lokalizacijskim postavkama (jeziku).
 * 
 * @author Filip
 *
 */
public abstract class LocalizableAction extends AbstractAction {
	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Ključ.
	 */
	private String key;
	/**
	 * Provider.
	 */
	private ILocalizationProvider provider;
	/**
	 * Promatrač na lokalizacijske postavke, ažurira podatke.
	 */
	private ILocalizationListener listener = new ILocalizationListener() {

		@Override
		public void localizationChanged() {

			try {
				putValue(NAME, provider.getString(key));
				putValue(SHORT_DESCRIPTION, provider.getString(key + ".sd"));
				putValue(MNEMONIC_KEY, Integer.decode(provider.getString(key + ".mk")));
			} catch (Exception ignorable) {
			}

		}
	};

	/**
	 * Konstruktor.
	 * 
	 * @param key
	 *            ključ
	 * @param provider
	 *            provider
	 */
	public LocalizableAction(String key, ILocalizationProvider provider) {
		this.key = key;
		this.provider = provider;
		try {
			putValue(NAME, provider.getString(key));
			putValue(SHORT_DESCRIPTION, provider.getString(key + ".sd"));
			putValue(MNEMONIC_KEY, Integer.decode(provider.getString(key + ".mk")));
		} catch (Exception ignorable) {
		}
		provider.addLocalizationListener(listener);
	}

}
