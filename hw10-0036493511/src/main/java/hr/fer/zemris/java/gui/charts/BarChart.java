package hr.fer.zemris.java.gui.charts;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred <code>BarChart</code> sadrži informacije o dijagramu (maksimalna
 * vrijednost ordinate, minimalna vrijednost ordinate, vrijednosti dijagrama
 * itd. )
 * 
 * @author Filip
 *
 */
public class BarChart {

	/**
	 * Lista vrijednosti.
	 */
	private List<XYValue> list = new ArrayList<>();
	/**
	 * Opis apscise.
	 */
	private String xDescr;
	/**
	 * Opis ordinate.
	 */
	private String yDescr;
	/**
	 * Minimalna vrijednost ordinate.
	 */
	private int yMin;
	/**
	 * Maksimalna vrijednost ordinate.
	 */
	private int yMax;
	/**
	 * Razmak između dvije vrijednosti ordinate.
	 */
	private int yDelta;

	/**
	 * Konstruktor.
	 * 
	 * @param list
	 *            lista vrijednosti
	 * @param xDescr
	 *            opis apscise
	 * @param yDescr
	 *            opis ordinate
	 * @param yMin
	 *            minimalna vrijednost ordinate
	 * @param yMax
	 *            maksimalna vrijednost ordinate
	 * @param yDelta
	 *            razmak između dviju vrijednosti ordinate
	 */
	public BarChart(List<XYValue> list, String xDescr, String yDescr, int yMin, int yMax, int yDelta) {
		this.list = list;
		this.xDescr = xDescr;
		this.yDescr = yDescr;
		this.yMin = yMin;
		this.yMax = yMax;
		this.yDelta = setDelta(yMin, yMax, yDelta);
	}

	/**
	 * Pomoćna metoda za računanje delte.
	 * 
	 * @param yMin2
	 *            minimalna vrijednost ordinate
	 * @param yMax2
	 *            maksimalna vrijednost ordinate
	 * @param yDelta2
	 *            delta
	 * @return delta
	 */
	private int setDelta(int yMin2, int yMax2, int yDelta2) {
		while ((yMax2 - yMin2) % yDelta2 != 0) {
			yDelta2++;
		}
		return yDelta2;
	}

	/**
	 * Metoda vraća listu vrijednosti.
	 * 
	 * @return listu vrijednosti
	 */
	public List<XYValue> getList() {
		return list;
	}

	/**
	 * Metoda vraća opis apscise.
	 * 
	 * @return opis apscise
	 */
	public String getxDescr() {
		return xDescr;
	}

	/**
	 * Metoda vraća opis ordinate.
	 * 
	 * @return opis ordinate
	 */
	public String getyDescr() {
		return yDescr;
	}

	/**
	 * Metoda dohvaća minimum ordinate.
	 * 
	 * @return minimum ordinate
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * Metoda dohvaća maksimum ordinate.
	 * 
	 * @return maksimum ordinate
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Metoda dohvaća deltu ordinate.
	 * 
	 * @return delta ordinate
	 */
	public int getyDelta() {
		return yDelta;
	}

}
