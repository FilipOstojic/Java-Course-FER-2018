package hr.fer.zemris.java.hw11.jnotepadpp.internationalization;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Razred <code>FormLocalizationProvider</code> predstavlja most (poveznicu)
 * između prozora dijelova s internacijonalizacijom.
 * 
 * @author Filip
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
	/**
	 * Konstruktor.
	 * 
	 * @param provider
	 *            ILocalizationProvider
	 * @param frame
	 *            prozor
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}

}
