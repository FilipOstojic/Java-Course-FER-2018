package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Point;

/**
 * Predstavlja geometrijski lik kružnicu.
 * 
 * @author Filip
 *
 */
public class Circle extends GeometricalObject {
	/**
	 * Centar kružnice.
	 */
	private Point center;
	/**
	 * Radijus kružnice.
	 */
	private int radius;
	/**
	 * Boja kružnice.
	 */
	private Color color;

	public Circle(Point center, int radius, Color color) {
		this.center = center;
		this.radius = radius;
		this.color = color;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}

	public Point getCenter() {
		return center;
	}

	public int getRadius() {
		return radius;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public String toString() {
		return String.format("Circle (%d,%d), %d", center.x, center.y, radius);
	}

	/**
	 * Ažurira kružnicu.
	 * 
	 * @param circle
	 *            kružnica
	 */
	public void editCircle(Circle circle) {
		this.center = circle.center;
		this.color = circle.color;
		this.radius = circle.radius;
	}
}
