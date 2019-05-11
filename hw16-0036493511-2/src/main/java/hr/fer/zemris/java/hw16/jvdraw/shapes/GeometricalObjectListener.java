package hr.fer.zemris.java.hw16.jvdraw.shapes;

/**
 * Predstavlja sučelje koje promatrači promjena nad {@link GeometricalObject}
 * trebaju implementirati.
 * 
 * @author Filip
 *
 */
public interface GeometricalObjectListener {
	/**
	 * Obavještava sve promatrače da je došlo do promjene.
	 * 
	 * @param o
	 *            {@link GeometricalObject}
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}