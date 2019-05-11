package hr.fer.zemris.java.gui.charts;

/**
 * Razred <code>XYValue</code> predstavlja naziv svakog stupca i visinu stupaca.
 * 
 * @author Filip
 *
 */
public class XYValue {
	/**
	 * Naziv stupca.
	 */
	private int x;
	/**
	 * Visina stupca.
	 */
	private int y;

	/**
	 * Konstruktor.
	 * 
	 * @param x
	 *            naziv stupca
	 * @param y
	 *            visina stupca
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Metoda vraća naziv stupca.
	 * 
	 * @return naziv stupca
	 */
	public int getX() {
		return x;
	}

	/**
	 * Metoda vraća visinu stupca.
	 * 
	 * @return visina stupca
	 */
	public int getY() {
		return y;
	}

}
