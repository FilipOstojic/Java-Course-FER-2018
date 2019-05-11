package hr.fer.zemris.java.hw16.jvdraw.model;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * Sučelje koje sadrži metode za s {@link GeometricalObject}. Implementacija :
 * {@link DocumentModel}.
 * 
 * @author Filip
 *
 */
public interface DrawingModel {
	/**
	 * Vraća broj likova iz liste.
	 * 
	 * @return broj likova iz liste
	 */
	public int getSize();

	/**
	 * Vraća objekt iz liste.
	 * 
	 * @param index
	 *            index
	 * @return objekt {@link GeometricalObject}
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Dodaje {@link GeometricalObject} u listu objekata.
	 * 
	 * @param object
	 *            {@link GeometricalObject}
	 */
	public void add(GeometricalObject object);

	/**
	 * Dodaje promatrača u listu promatrača.
	 * 
	 * @param l
	 *            {@link DrawingModelListener}
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Uklanja promatrača iz liste {@link DrawingModelListener}.
	 * 
	 * @param l
	 *            {@link DrawingModelListener}
	 */
	public void removeDrawingModelListener(DrawingModelListener l);

	/**
	 * Uklanja {@link GeometricalObject} iz liste.
	 * 
	 * @param object
	 *            {@link GeometricalObject}
	 */
	public void remove(GeometricalObject object);

	/**
	 * Pomiče za 1 mjesto gore/dolje {@link GeometricalObject} ovisno o offsetu (
	 * 0/1 ).
	 * 
	 * @param object
	 *            {@link GeometricalObject}
	 * @param offset
	 *            -1 ili 1
	 */
	public void changeOrder(GeometricalObject object, int offset);
}