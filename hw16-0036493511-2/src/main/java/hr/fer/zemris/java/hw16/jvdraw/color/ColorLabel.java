package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * Razred <code>ColorLabel</code> predstavlja labelu koja se nalazi na dnu
 * prozora. Zapisuje podatke o bojama, npr: <br>
 * Foreground color: (255, 10, 210), background color: (128, 128, 0) <br>
 * 
 * @author Filip
 *
 */
public class ColorLabel extends JLabel implements ColorChangeListener {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * {@link IColorProvider} za boju obruba.
	 */
	private IColorProvider fgColorProvider;
	/**
	 * {@link IColorProvider} za boju pozadine.
	 */
	private IColorProvider bgColorProvider;

	/**
	 * Konstruktor.
	 * 
	 * @param fgColorProvider
	 *            {@link IColorProvider} za boju obruba
	 * @param bgColorProvider
	 *            {@link IColorProvider} za boju pozadine
	 */
	public ColorLabel(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
		fgColorProvider.addColorChangeListener(this);
		bgColorProvider.addColorChangeListener(this);
		newColorSelected(fgColorProvider, null, fgColorProvider.getCurrentColor());
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		Color fg = fgColorProvider.getCurrentColor();
		Color bg = bgColorProvider.getCurrentColor();

		if (source == fgColorProvider) {
			fg = newColor;
		} else if (source == bgColorProvider) {
			bg = newColor;
		}

		String text = String.format("Foreground color: (%d, %d, %d), background color: (%d, %d, %d).", fg.getRed(),
				fg.getGreen(), fg.getBlue(), bg.getRed(), bg.getGreen(), bg.getBlue());

		this.setText(text);
	}

}
