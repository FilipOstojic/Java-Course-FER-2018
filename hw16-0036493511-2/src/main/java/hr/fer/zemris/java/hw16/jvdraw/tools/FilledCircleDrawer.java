package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;

/**
 * Implementacija {@link Tool}-a za kružnicu s ispunom.
 * 
 * @author Filip
 *
 */
public class FilledCircleDrawer implements Tool {
	/**
	 * {@link IColorProvider} za boju obruba.
	 */
	private IColorProvider fgColorProvider;
	/**
	 * {@link DocumentModel} model.
	 */
	private DocumentModel model;
	/**
	 * Brojač klikova.
	 */
	private int clickCounter = 0;
	/**
	 * Početna točka.
	 */
	private Point startPoint;
	/**
	 * Krajnja točka.
	 */
	private Point endPoint;
	/**
	 * Zastavica za crtanje.
	 */
	private boolean flag;
	/**
	 * Radijus kružnice.
	 */
	private int radius;
	/**
	 * {@link IColorProvider} za boju pozadine.
	 */
	private IColorProvider bgColorProvider;

	/**
	 * Konstruktor.
	 * 
	 * @param fgColorProvider
	 *            {@link IColorProvider} za boju obruba
	 * @param bgColorProvider
	 *            {@link IColorProvider} za boju pozadine
	 * @param model
	 *            {@link DocumentModel}
	 */
	public FilledCircleDrawer(IColorProvider fgColorProvider, IColorProvider bgColorProvider, DocumentModel model) {
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
		this.model = model;
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		clickCounter++;
		Point point = new Point(e.getX(), e.getY());

		if (clickCounter % 2 == 1) {
			startPoint = point;
			flag = true;
			return;
		}

		flag = false;
		endPoint = point;
		radius = calculateRadius();
		model.add(new FilledCircle(startPoint, radius, fgColorProvider.getCurrentColor(),
				bgColorProvider.getCurrentColor()));
	}

	/**
	 * Pomoćna metoda koja na temelju početne i završne točke, račun radijus
	 * kružnice.
	 * 
	 * @return radijus
	 */
	private int calculateRadius() {
		radius = (int) Math.sqrt(Math.pow(startPoint.x - endPoint.x, 2) + Math.pow(startPoint.y - endPoint.y, 2));
		return radius;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (flag) {
			endPoint = new Point(e.getX(), e.getY());
			radius = calculateRadius();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void paint(Graphics2D g2d) {
		if (!flag)
			return;
		g2d.setColor(fgColorProvider.getCurrentColor());
		g2d.drawOval(startPoint.x - radius, startPoint.y - radius, 2 * radius, 2 * radius);
		g2d.setColor(bgColorProvider.getCurrentColor());
		g2d.fillOval(startPoint.x - radius, startPoint.y - radius, 2 * radius, 2 * radius);
	}

}
