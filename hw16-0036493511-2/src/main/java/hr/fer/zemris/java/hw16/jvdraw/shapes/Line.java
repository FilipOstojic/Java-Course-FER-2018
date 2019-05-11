package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Point;

/**
 * Predstavlja dužinu.
 * 
 * @author Filip
 *
 */
public class Line extends GeometricalObject {
	/**
	 * Početna točka.
	 */
	private Point startPoint;
	/**
	 * Krajnja točka.
	 */
	private Point endPoint;
	/**
	 * Boja.
	 */
	private Color color;

	/**
	 * Konstruktor.
	 * 
	 * @param startPoint
	 *            početna točkka
	 * @param endPoint
	 *            završna točka
	 * @param color
	 *            boja
	 */
	public Line(Point startPoint, Point endPoint, Color color) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.color = color;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}

	/**
	 * Getter za početnu točku.
	 * 
	 * @returnpočetna točka
	 */
	public Point getStartPoint() {
		return startPoint;
	}

	/**
	 * Getter za završnu točku.
	 * 
	 * @return završna točka
	 */
	public Point getEndPoint() {
		return endPoint;
	}

	/**
	 * Getter za boju.
	 * 
	 * @return boja
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Ažurira dužinu.
	 * 
	 * @param line
	 *            dužina
	 */
	public void editLine(Line line) {
		this.startPoint = line.startPoint;
		this.endPoint = line.endPoint;
		this.color = line.getColor();
	}

	@Override
	public String toString() {
		return String.format("Line (%d,%d)-(%d,%d)", startPoint.x, startPoint.y, endPoint.x, endPoint.y);
	}
}
