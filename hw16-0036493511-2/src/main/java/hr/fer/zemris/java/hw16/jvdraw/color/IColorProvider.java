package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

/**
 * Sučelje koje implementira {@link JColorArea} (komponenta s bojama).
 * 
 * @author Filip
 *
 */
public interface IColorProvider {
	/**
	 * Vraća trenutnu boju.
	 * 
	 * @return trenutna boja
	 */
	public Color getCurrentColor();

	/**
	 * Dodaje promatrača u listu.
	 * 
	 * @param l
	 *            {@link ColorChangeListener}
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Uklanja promatrača iz liste.
	 * 
	 * @param l
	 *            {@link ColorChangeListener}
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}
