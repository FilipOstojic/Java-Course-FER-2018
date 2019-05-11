package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Definira metode koje svako stanje moa implementirati. Metode se pozivaju na
 * akciju miša.
 * 
 * @author Filip
 *
 */
public interface Tool {
	/**
	 * Poziva se na lijevi klik miša.
	 * 
	 * @param e
	 *            {@link Graphics2D}
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * Poziva se na otpuštanje lijevog klika miša.
	 * 
	 * @param e
	 *            {@link Graphics2D}
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * Poziva se na lijevi klik miša.
	 * 
	 * @param e
	 *            {@link Graphics2D}
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * Poziva se na pomak miša.
	 * 
	 * @param e
	 *            {@link Graphics2D}
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * Poziva se na pomak miša dok je stisnut lijevi gumb miša.
	 * 
	 * @param e
	 *            {@link Graphics2D}
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Crta međufaze likova. Između prvog i drugog klika miša.
	 * 
	 * @param g2d
	 *            {@link Graphics2D}
	 */
	public void paint(Graphics2D g2d);
}