package hr.fer.zemris.java.hw16.jvdraw.model;

/**
 * Sučelje koje sadrži metode za promatrača nad {@link DrawingModel}-om.
 * 
 * @author Filip
 *
 */
public interface DrawingModelListener {
	/**
	 * Poziva se kada je dodan element u listu.
	 * 
	 * @param source
	 *            {@link DrawingModel}
	 * @param index0
	 *            index u listi na koje elemente utječe promjena
	 * @param index1
	 *            index u listi na koje elemente utječe promjena
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Poziva se kada je uklonjen element iz liste.
	 * 
	 * @param source
	 *            {@link DrawingModel}
	 * @param index0
	 *            index u listi na koje elemente utječe promjena
	 * @param index1
	 *            index u listi na koje elemente utječe promjena
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);
	
	/**
	 * Poziva se kada je promjenjen element iz liste.
	 * 
	 * @param source
	 *            {@link DrawingModel}
	 * @param index0
	 *            index u listi na koje elemente utječe promjena
	 * @param index1
	 *            index u listi na koje elemente utječe promjena
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}