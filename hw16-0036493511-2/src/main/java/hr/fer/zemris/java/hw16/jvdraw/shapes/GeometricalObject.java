package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.util.ArrayList;
import java.util.List;

/**
 * Predstavlja bazni razred svakog geometrijskog lika.
 * 
 * @author Filip
 *
 */
public abstract class GeometricalObject {
	/**
	 * Lista registriranih promatrača.
	 */
	List<GeometricalObjectListener> listeners = new ArrayList<>();

	/**
	 * Šalje svoju referencu {@link GeometricalObjectVisitor}-u.
	 * 
	 * @param v
	 *            {@link GeometricalObjectVisitor}
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * Stvara editor za {@link GeometricalObject}
	 * 
	 * @return {@link GeometricalObjectEditor}
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Dodaje promatrača u listu.
	 * 
	 * @param l
	 *            {@link GeometricalObjectListener}
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	};

	/**
	 * Uklanja promatrača iz liste.
	 * 
	 * @param l
	 *            {@link GeometricalObjectListener}
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	};
}
