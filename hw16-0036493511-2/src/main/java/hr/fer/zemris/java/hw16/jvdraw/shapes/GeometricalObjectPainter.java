package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Predsavlja implementaciju {@link GeometricalObjectVisitor}-a. Crta
 * komponentu.
 * 
 * @author Filip
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {
	/**
	 * Grafička komponenta.
	 */
	private Graphics2D g2d;

	/**
	 * Konstruktor.
	 * 
	 * @param g2d
	 *            grafička komponenta
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}

	@Override
	public void visit(Line line) {
		Point start = line.getStartPoint();
		Point end = line.getEndPoint();

		g2d.setColor(line.getColor());
		g2d.drawLine(start.x, start.y, end.x, end.y);
	}

	@Override
	public void visit(Circle circle) {
		Point center = circle.getCenter();
		int radius = circle.getRadius();

		g2d.setColor(circle.getColor());
		g2d.drawOval(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		Point center = filledCircle.getCenter();
		int radius = filledCircle.getRadius();

		g2d.setColor(filledCircle.getColor());
		g2d.drawOval(center.x - radius, center.y - radius, 2 * radius, 2 * radius);

		g2d.setColor(filledCircle.getFillColor());
		g2d.fillOval(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
	}

}
