package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * Komponenta koja sadrži boju. Klik na komponentu omogućava izbor boje iz
 * lepeze boja.
 * 
 * @author Filip
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Širina i visina komponente.
	 */
	private static final int DIMENSION = 20;
	/**
	 * Vrijednost boje.
	 */
	private Color selectedColor;
	/**
	 * Lista registriranih promatrača.
	 */
	List<ColorChangeListener> listeners = new ArrayList<>();

	/**
	 * Konstruktor, postavlja inicijalnu boju.
	 * 
	 * @param selectedColor
	 *            inicijalna boja
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(null, "Choose a color", selectedColor);
				setSelectedColor(newColor == null ? selectedColor : newColor);
			}
		});
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(DIMENSION, DIMENSION);
	}

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(DIMENSION, DIMENSION);
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(DIMENSION, DIMENSION);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}

	/**
	 * Setter boje.
	 * 
	 * @param selectedColor
	 *            trenutna boja
	 */
	public void setSelectedColor(Color selectedColor) {

		for (ColorChangeListener listener : listeners) {
			listener.newColorSelected(this, this.selectedColor, selectedColor);
		}
		this.selectedColor = selectedColor;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(0, 0, DIMENSION, DIMENSION);
	}

}
