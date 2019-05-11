package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * Predsavlja implementaciju {@link GeometricalObjectVisitor}-a. Računa najmanji
 * pravokutnik potreban da se obuhvate sve komponente slike.
 * 
 * @author Filip
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {
	/**
	 * Najveći X.
	 */
	private int maxX = 0;
	/**
	 * Najveći Y.
	 */
	private int maxY = 0;
	/**
	 * Najmanji X.
	 */
	private int minX = Integer.MAX_VALUE;
	/**
	 * Najmanji Y.
	 */
	private int minY = Integer.MAX_VALUE;
	/**
	 * X vrijednosti.
	 */
	List<Integer> xValues = new ArrayList<>();
	/**
	 * Y vrijednosti.
	 */
	List<Integer> yValues = new ArrayList<>();

	@Override
	public void visit(Line line) {
		extractPoints(line);
	}

	/**
	 * Razvrstava koordinate točaka u liste.
	 * 
	 * @param object
	 *            {@link GeometricalObject}
	 */
	private void extractPoints(GeometricalObject object) {

		if (object instanceof Line) {
			xValues.add((int) ((Line) object).getStartPoint().getX());
			yValues.add((int) ((Line) object).getStartPoint().getY());
			xValues.add((int) ((Line) object).getEndPoint().getX());
			yValues.add((int) ((Line) object).getEndPoint().getY());
		} else if (object instanceof Circle) {
			Point center = ((Circle) object).getCenter();
			int radius = ((Circle) object).getRadius();

			xValues.add((int) center.getX() + radius);
			xValues.add((int) center.getX() - radius);
			yValues.add((int) center.getY() + radius);
			yValues.add((int) center.getY() - radius);
		} else if (object instanceof FilledCircle) {
			Point center = ((FilledCircle) object).getCenter();
			int radius = ((FilledCircle) object).getRadius();

			xValues.add((int) center.getX() + radius);
			xValues.add((int) center.getX() - radius);
			yValues.add((int) center.getY() + radius);
			yValues.add((int) center.getY() - radius);
		}
	}

	@Override
	public void visit(Circle circle) {
		extractPoints(circle);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		extractPoints(filledCircle);
	}

	/**
	 * Vraća najmanji pravokutnik potreban da se obuhvate sve komponente slike.
	 * 
	 * @return najmanji pravokutnik potreban da se obuhvate sve komponente slike.
	 */
	public Rectangle getBoundingBox() {
		for (Integer value : xValues) {
			if (value > maxX) {
				maxX = value;
			}
			if (value < minX) {
				minX = value;
			}
		}
		for (Integer value : yValues) {
			if (value > maxY) {
				maxY = value;
			}
			if (value < minY) {
				minY = value;
			}
		}

		return new Rectangle(minX, minY, maxX - minX, maxY - minY);
	}
}
