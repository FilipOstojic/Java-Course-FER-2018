package hr.fer.zemris.java.hw11.jnotepadpp.internationalization;

/**
 * 
 * @author Filip
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	/**
	 * Zastavica, je li most povezan s prozorom.
	 */
	private boolean connected;
	/**
	 * Obavještava sve promatrače da je došlo do promjene.
	 */
	private ILocalizationListener listener = () -> fire();
	/**
	 * Lokalizacijski provider.
	 */
	private ILocalizationProvider parent;

	/**
	 * Konstruktor.
	 * 
	 * @param provider
	 *            provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		parent = provider;
	}

	/**
	 * Vraća promatrača.
	 * 
	 * @return promatrač.
	 */
	public ILocalizationListener getListener() {
		return listener;
	}

	/**
	 * Vraća lokacijski provider.
	 * 
	 * @return lokalizacijski provider
	 */
	public ILocalizationProvider getParent() {
		return parent;
	}

	public boolean isConnected() {
		return connected == true;
	}

	/**
	 * Metoda obavještava sve promatrače registrirane nad lokalizacijksim providrom
	 * da je došlo do promijene. Registrira promatrača.
	 */
	public void connect() {
		if (!isConnected()) {
			connected = true;
			parent.addLocalizationListener(listener);
			fire();
		}
	}

	/**
	 * Metoda "odspaja" most od lokalizacijskog providera te uklanja promatrača.
	 */
	public void disconnect() {
		if (isConnected()) {
			connected = false;
			parent.removeLocalizationListener(listener);
		}
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

}
