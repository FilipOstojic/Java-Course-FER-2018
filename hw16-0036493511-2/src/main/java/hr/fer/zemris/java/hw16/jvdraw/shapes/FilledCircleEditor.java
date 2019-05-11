package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Predstavlja panel koji nudi opcije promjene svakog svojstva kružnice s
 * ispunom.
 * 
 * @author Filip
 *
 */
public class FilledCircleEditor extends GeometricalObjectEditor {
	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Prostor za unos X0.
	 */
	private JTextArea area1;
	/**
	 * Prostor za unos Y0.
	 */
	private JTextArea area2;
	/**
	 * Prostor za unos radijusa.
	 */
	private JTextArea area3;
	/**
	 * Prostor za unos crvene boje.
	 */
	private JTextArea area5;
	/**
	 * Prostor za unos zelene boje.
	 */
	private JTextArea area6;
	/**
	 * Prostor za unos plave boje.
	 */
	private JTextArea area7;
	/**
	 * Prostor za unos crvene boje.
	 */
	private JTextArea area8;
	/**
	 * Prostor za unos zelene boje.
	 */
	private JTextArea area9;
	/**
	 * Prostor za unos plave boje.
	 */
	private JTextArea area10;
	/**
	 * Kružnica koja se editira.
	 */
	private FilledCircle filledCircle;
	/**
	 * X0.
	 */
	private int x0;
	/**
	 * X1.
	 */
	private int y0;
	/**
	 * Radijus.
	 */
	private int radius;
	/**
	 * Crvena.
	 */
	private int r;
	/**
	 * Zelena.
	 */
	private int g;
	/**
	 * Plava.
	 */
	private int b;
	/**
	 * Crvena.
	 */
	private int r2;
	/**
	 * Zelena.
	 */
	private int g2;
	/**
	 * Plava.
	 */
	private int b2;

	/**
	 * Konstruktor.
	 * 
	 * @param line
	 *            linija koja se editira
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		this.filledCircle = filledCircle;

		JPanel first = new JPanel();
		JLabel startPointX = new JLabel("X0: ");
		first.add(startPointX);
		area1 = new JTextArea(String.valueOf(filledCircle.getCenter().x), 1, 3);
		first.add(area1);

		JPanel second = new JPanel();
		JLabel startPointY = new JLabel("Y0: ");
		first.add(startPointY);
		area2 = new JTextArea(String.valueOf(filledCircle.getCenter().y), 1, 3);
		first.add(area2);

		JPanel third = new JPanel();
		JLabel endPointX = new JLabel("Radius: ");
		first.add(endPointX);
		area3 = new JTextArea(String.valueOf(filledCircle.getRadius()), 1, 3);
		first.add(area3);

		JPanel fifth = new JPanel();
		JLabel red = new JLabel("fg red: ");
		first.add(red);
		area5 = new JTextArea(String.valueOf(filledCircle.getColor().getRed()), 1, 3);
		first.add(area5);

		JPanel sixth = new JPanel();
		JLabel green = new JLabel("fg green: ");
		first.add(green);
		area6 = new JTextArea(String.valueOf(filledCircle.getColor().getGreen()), 1, 3);
		first.add(area6);

		JPanel seventh = new JPanel();
		JLabel blue = new JLabel("fg blue: ");
		first.add(blue);
		area7 = new JTextArea(String.valueOf(filledCircle.getColor().getBlue()), 1, 3);
		first.add(area7);

		JPanel eight = new JPanel();
		JLabel red2 = new JLabel("bg red: ");
		first.add(red2);
		area8 = new JTextArea(String.valueOf(filledCircle.getFillColor().getRed()), 1, 3);
		first.add(area8);

		JPanel nineth = new JPanel();
		JLabel green2 = new JLabel("bg green: ");
		first.add(green2);
		area9 = new JTextArea(String.valueOf(filledCircle.getFillColor().getGreen()), 1, 3);
		first.add(area9);

		JPanel tenth = new JPanel();
		JLabel blue2 = new JLabel("bg blue: ");
		first.add(blue2);
		area10 = new JTextArea(String.valueOf(filledCircle.getFillColor().getBlue()), 1, 3);
		first.add(area10);

		add(first);
		add(second);
		add(third);
		add(fifth);
		add(sixth);
		add(seventh);
		add(eight);
		add(nineth);
		add(tenth);
	}

	@Override
	public void checkEditing() {
		try {
			String input1 = area1.getText();
			x0 = Integer.parseInt(input1);
			String input2 = area2.getText();
			y0 = Integer.parseInt(input2);
			String input3 = area3.getText();
			radius = Integer.parseInt(input3);
			String input5 = area5.getText();
			r = Integer.parseInt(input5);
			String input6 = area6.getText();
			g = Integer.parseInt(input6);
			String input7 = area7.getText();
			b = Integer.parseInt(input7);
			String input8 = area8.getText();
			r2 = Integer.parseInt(input8);
			String input9 = area9.getText();
			g2 = Integer.parseInt(input9);
			String input10 = area10.getText();
			b2 = Integer.parseInt(input10);

			if (!((r >= 0 && r <= 255) && (g >= 0 && g <= 255) && (b >= 0 && b <= 255))) {
				throw new RuntimeException("Foreground colors have to be from interval: [0,255].");
			}
			if (!((r2 >= 0 && r2 <= 255) && (g2 >= 0 && g2 <= 255) && (b2 >= 0 && b2 <= 255))) {
				throw new RuntimeException("Backgoround colors have to be from interval: [0,255].");
			}
			if (!(x0 >= 0 && y0 >= 0 && radius >= 0)) {
				throw new RuntimeException("The coordinates of the points and radius have to be positive.");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void acceptEditing() {
		filledCircle.editFilledCircle(
				new FilledCircle(new Point(x0, y0), radius, new Color(r, g, b), new Color(r2, g2, b2)));
		filledCircle.listeners.forEach(l -> l.geometricalObjectChanged(filledCircle));
	}

}
