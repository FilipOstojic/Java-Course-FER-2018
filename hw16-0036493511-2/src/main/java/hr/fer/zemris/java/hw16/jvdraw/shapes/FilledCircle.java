package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Point;

/**
 * Predstavlja geometrijski lik kružnicu s ispunom.
 * 
 * @author Filip
 *
 */
public class FilledCircle extends GeometricalObject {
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
	/**
	 * Ispuna kružnice.
	 */
	private Color fillColor;

	/**
	 * Konstruktor.
	 * 
	 * @param center
	 *            centar kružnice
	 * @param radius
	 *            radijus kružnice
	 * @param color
	 *            boja kružnice
	 * @param fillColor
	 *            ispuna kružnice
	 */
	public FilledCircle(Point center, int radius, Color color, Color fillColor) {
		this.center = center;
		this.radius = radius;
		this.color = color;
		this.fillColor = fillColor;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
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

	public Color getFillColor() {
		return fillColor;
	}

	@Override
	public String toString() {
		String color = Integer.toHexString(fillColor.getRGB()).substring(2).toUpperCase();
		return String.format("Filled circle (%d,%d), %d, #%s", center.x, center.y, radius, color);
	}

	public void editFilledCircle(FilledCircle filledCircle) {
		this.center = filledCircle.center;
		this.color = filledCircle.color;
		this.fillColor = filledCircle.fillColor;
		this.radius = filledCircle.radius;
	}

}
