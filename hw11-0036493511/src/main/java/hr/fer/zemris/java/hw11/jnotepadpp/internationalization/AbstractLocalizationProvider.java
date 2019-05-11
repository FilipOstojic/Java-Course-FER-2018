package hr.fer.zemris.java.hw11.jnotepadpp.internationalization;

import java.util.ArrayList;
import java.util.List;

/**
 * Apstrakni razred predstavlja implementaciju {@link ILocalizationProvider}-a
 * te nudi implementaciju metdoda za rad sa registriranim preomatračima.
 * 
 * @author Filip
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	/**
	 * Lista promatrača.
	 */
	private List<ILocalizationListener> listeners;

	/**
	 * Konstruktor.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Metoda obavještava sve registrirane promatrače da je došlo do promjene.
	 */
	public void fire() {
		listeners.forEach(a -> a.localizationChanged());
	}

	/**
	 * Vraća listu promatrača.
	 * 
	 * @return lista promatrača
	 */
	public List<ILocalizationListener> getListeners() {
		return listeners;
	}

}
