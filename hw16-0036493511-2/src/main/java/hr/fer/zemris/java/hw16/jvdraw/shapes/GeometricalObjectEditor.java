package hr.fer.zemris.java.hw16.jvdraw.shapes;

import javax.swing.JPanel;

/**
 * Sučelje koje svaki editor treba nasljediti. Sadrži metode za provjeru unosa i
 * ažuriranje.
 * 
 * @author Filip
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {
	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Provjerava ispravnost unesenih podataka.
	 */
	public abstract void checkEditing();

	/**
	 * Ažurira {@link GeometricalObject}
	 */
	public abstract void acceptEditing();
}