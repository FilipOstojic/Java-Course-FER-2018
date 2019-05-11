package hr.fer.zemris.java.hw16.jvdraw.model;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;

public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Način rada.
	 */
	private Tool state;
	/**
	 * {@link DocumentModel}
	 */
	private DocumentModel model;

	/**
	 * Konstruktor.
	 * 
	 * @param model
	 *            {@link JDrawingCanvas}
	 */
	public JDrawingCanvas(DocumentModel model) {
		this.model = model;
		model.addDrawingModelListener(this);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		GeometricalObjectPainter visitor = new GeometricalObjectPainter((Graphics2D) g);
		for (int i = 0, end = model.objects.size(); i < end; i++) {
			model.getObject(i).accept(visitor);
		}
		state.paint((Graphics2D) g);
	}

	/**
	 * Postavlja trenutni {@link Tool}
	 * 
	 * @param state
	 *            {@link Tool}
	 */
	public void setState(Tool state) {
		this.state = state;
	}

	/**
	 * Vraća trenutni {@link Tool}
	 * 
	 * @return {@link Tool}
	 */
	public Tool getState() {
		return state;
	}
}
