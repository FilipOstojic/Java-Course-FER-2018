package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;

/**
 * Implementacija {@link Tool}-a za dužinu.
 * 
 * @author Filip
 *
 */
public class LineDrawer implements Tool {

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
	 * Konstruktor.
	 * 
	 * @param fgColorProvider
	 *            {@link IColorProvider} za boju obruba
	 * @param bgColorProvider
	 *            {@link IColorProvider} za boju pozadine
	 */
	public LineDrawer(IColorProvider fgColorProvider, DocumentModel model) {
		this.fgColorProvider = fgColorProvider;
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
		model.add(new Line(startPoint, endPoint, fgColorProvider.getCurrentColor()));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (flag) {
			endPoint = new Point(e.getX(), e.getY());
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
		g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
	}

}
