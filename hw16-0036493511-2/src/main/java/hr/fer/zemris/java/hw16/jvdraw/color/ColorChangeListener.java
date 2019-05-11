package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

/**
 * Sučelje koje definira metodu koja se poziva svaki puta kada je došlo do
 * promjene boje.
 * 
 * @author Filip
 *
 */
public interface ColorChangeListener {
	/**
	 * Metodu koja se poziva svaki puta kada je došlo do promjene boje.
	 * 
	 * @param source
	 *            {@link IColorProvider}
	 * @param oldColor
	 *            prethodna boja
	 * @param newColor
	 *            nova boja
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}