package hr.fer.zemris.java.hw16.jvdraw.model;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * Predstavlja implementaciju {@link AbstractListModel} i
 * {@link DrawingModelListener} te služi kao lista {@link GeometricalObject}-a.
 * 
 * @author Filip
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * {@link DocumentModel}
	 */
	DocumentModel model;

	/**
	 * Konstruktor.
	 * 
	 * @param model
	 *            {@link DocumentModel}
	 */
	public DrawingObjectListModel(DocumentModel model) {
		this.model = model;
		model.addDrawingModelListener(this);
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(source, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(source, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
	}

}