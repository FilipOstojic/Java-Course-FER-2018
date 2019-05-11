package hr.fer.zemris.java.hw16.jvdraw.model;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObjectListener;

/**
 * Implementacija {@link DrawingModel}-a i {@link GeometricalObjectListener}-a.
 * Sadrži listu objekata iz canvasa i listu promatrača.
 * 
 * @author Filip
 *
 */
public class DocumentModel implements DrawingModel, GeometricalObjectListener {
	/**
	 * Lista geometrijskih objekata.
	 */
	List<GeometricalObject> objects = new ArrayList<>();
	/**
	 * Lista registriranih promatrača.
	 */
	List<DrawingModelListener> listeners = new ArrayList<>();

	/**
	 * Konstruktor.
	 */
	public DocumentModel() {

	}

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(object);
		listeners.forEach(l -> l.objectsAdded(this, getSize() - 1, getSize() - 1));
		object.addGeometricalObjectListener(this);
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		listeners.forEach(l -> l.objectsChanged(this, objects.indexOf(o), objects.indexOf(o)));
	}

	@Override
	public void remove(GeometricalObject o) {
		listeners.forEach(l -> l.objectsRemoved(this, objects.indexOf(o), objects.indexOf(o)));
		objects.remove(o);
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int newIndex = objects.indexOf(object) + offset;
		if (newIndex > objects.size() - 1 || newIndex < 0)
			return;

		GeometricalObject secondObject = objects.get(newIndex);
		objects.set(objects.indexOf(object), secondObject);
		objects.set(newIndex, object);

		listeners.forEach(l -> l.objectsChanged(this, 0, objects.size() - 1));
	}

}
