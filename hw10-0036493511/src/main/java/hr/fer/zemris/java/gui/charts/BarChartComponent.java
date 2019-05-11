package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComponent;

/**
 * Razred <code>BarChartComponent</code> koji na temelju ulaznih podataka iz
 * datoteke crta novu komponentu - dijagram.
 * 
 * @author Filip
 *
 */
public class BarChartComponent extends JComponent {

	/**
	 * Serijska vrijednost UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Konstanta za vertikalni razmak.
	 */
	private static int OFFSET_V = 20;
	/**
	 * Konstanta za horizontalni razmak.
	 */
	private static int OFFSET_H = 30;
	/**
	 * Konstanta za vertikalni razmak između opisa i brojeva.
	 */
	private static int GAP_V = 30;
	/**
	 * Konstanta za horizontalni razmak između opisa i brojeva.
	 */
	private static int GAP_H = 20;
	/**
	 * Konstanta za veličinu strelice.
	 */
	private static int POLYGON = 5;
	/**
	 * Čuva vrijednosti fonta.
	 */
	private static FontMetrics fm;
	/**
	 * Lista vrijednosti.
	 */
	private List<XYValue> list;
	/**
	 * Opis apscise.
	 */
	private String xDescr;
	/**
	 * Opis ordinate.
	 */
	private String yDescr;
	/**
	 * Minimalna ordinata.
	 */
	private int yMin;
	/**
	 * Maksimalna ordinata.
	 */
	private int yMax;
	/**
	 * Razmak između ordinatnih vrijednosti.
	 */
	private int yDelta;
	/**
	 * Razmak između horizontalnih linija.
	 */
	private int lineDeltaX;
	/**
	 * Razmak između vertikalnih linija.
	 */
	private int lineDeltaY;

	/**
	 * Konstruktor.
	 * 
	 * @param barChart
	 *            {@link BarChart}
	 */
	public BarChartComponent(BarChart barChart) {
		list = barChart.getList();
		xDescr = barChart.getxDescr();
		yDescr = barChart.getyDescr();
		yMin = barChart.getyMin();
		yMax = barChart.getyMax();
		yDelta = barChart.getyDelta();
	}

	@Override
	protected void paintComponent(Graphics g) {
		fm = g.getFontMetrics();
		drawDescriptions(g);
		drawAxis(g);
		drawNumbersAndHorizontalLines(g);
		drawVerticalLines(g);
		drawGraph(g);
	}

	/**
	 * Metoda crta stupce dijagrama.
	 * 
	 * @param g
	 *            graphics
	 */
	private void drawGraph(Graphics g) {
		List<XYValue> xNames = getXNames(list);
		int xStart = OFFSET_H + GAP_H;

		for (int i = 1; i <= list.size(); i++) {
			int topLeftY = getHeight() - GAP_V - OFFSET_V - lineDeltaX * xNames.get(i - 1).getY();
			int topLeftX = xStart + ((i - 1) * lineDeltaY);

			g.setColor(Color.ORANGE);
			g.fillRect(topLeftX, topLeftY, lineDeltaY, lineDeltaX * xNames.get(i - 1).getY() - 1);
			g.setColor(Color.BLACK);
			g.drawRect(topLeftX, topLeftY, lineDeltaY, lineDeltaX * xNames.get(i - 1).getY() - 1);
		}
		g.setColor(Color.BLACK);
	}

	/**
	 * Metoda crta vertikalne linije i vrijednosti.
	 * 
	 * @param g
	 *            graphics
	 */
	private void drawVerticalLines(Graphics g) {
		lineDeltaY = xAxisLength() / list.size();
		int xStart = OFFSET_H + GAP_H;
		int yStart = getHeight() - OFFSET_V - GAP_V;
		int yEnd = OFFSET_V + GAP_V;

		List<XYValue> xNames = getXNames(list);

		for (int i = 1; i <= list.size(); i++) {
			int xValue = xStart + i * lineDeltaY;
			g.drawLine(xValue, yStart, xValue, yEnd);
			g.drawString(Integer.toString(xNames.get(i - 1).getX()), xValue - lineDeltaY / 2, yStart + GAP_V / 2);
		}
	}

	/**
	 * Metoda crta horizontalne linije i vrijednosti.
	 * 
	 * @param g
	 *            graphics
	 */
	private void drawNumbersAndHorizontalLines(Graphics g) {
		lineDeltaX = yAxisLength() / (yMax - yMin);
		int yStart = getHeight() - OFFSET_V - GAP_V;

		for (int i = yMin; i <= yMax; i += yDelta) {
			String number = Integer.toString(i);
			int yValue = yStart - i * lineDeltaX;
			g.drawString(number, OFFSET_H + GAP_H / 4, yValue + GAP_H / 4);
			if (i != yMin) {
				g.drawLine(OFFSET_H + GAP_H, yValue, getWidth() - OFFSET_H - GAP_H, yValue);
			}
		}
	}

	/**
	 * Metoda crta koordinatne osi.
	 * 
	 * @param g
	 *            graphics
	 */
	private void drawAxis(Graphics g) {
		g.drawLine(OFFSET_H + GAP_H, getHeight() - OFFSET_V - GAP_V, getWidth() - OFFSET_H, getHeight() - OFFSET_V - GAP_V);
		g.drawLine(OFFSET_H + GAP_H, getHeight() - OFFSET_V - GAP_V, OFFSET_H + GAP_H, OFFSET_V);
		
		g.fillPolygon(new int[] { 
						getWidth() - OFFSET_V + POLYGON, 
						getWidth() - OFFSET_V - 2 * POLYGON,
						getWidth() - OFFSET_V - 2 * POLYGON },
					  new int[] { 
						getHeight() - OFFSET_V - GAP_V, 
						getHeight() - OFFSET_V - GAP_V - POLYGON,
						getHeight() - OFFSET_V - GAP_V + POLYGON }, 3);
		g.fillPolygon(new int[] { 
						OFFSET_H + GAP_H, 
						OFFSET_H + GAP_H + POLYGON, 
						OFFSET_H + GAP_H - POLYGON },
					  new int[] { 
						OFFSET_V - POLYGON, 
						OFFSET_V + 2 * POLYGON, 
						OFFSET_V + 2 * POLYGON }, 3);
	}

	/**
	 * Metoda crta opise koordinatnih osi.
	 * 
	 * @param g
	 *            graphics
	 */
	private void drawDescriptions(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g.drawString(xDescr, ((getWidth() - fm.stringWidth(xDescr)) / 2), getHeight() - OFFSET_V);

		AffineTransform defaultAt = g2d.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);

		g.drawString(yDescr, -(getHeight() + 2 * fm.stringWidth(yDescr)) / 2, OFFSET_H);

		g2d.setTransform(defaultAt);
	}

	/**
	 * Metoda vraća sortirano polje vrijednosti {@link XYValue} sortiranih po x
	 * vrijednosti.
	 * 
	 * @param list2
	 *            lista vrijednosti
	 * @return sortirana lista vrijednosti
	 */
	private List<XYValue> getXNames(List<XYValue> list2) {
		return list2.stream()
					.sorted((o1, o2) -> Integer.compare(o1.getX(), o2.getX()))
					.collect(Collectors.toList());
	}

	/**
	 * Metoda vraća duljinu apscise.
	 * 
	 * @return duljina apscise
	 */
	private int yAxisLength() {
		return getHeight() - 2 * OFFSET_V - 2 * GAP_V;
	}

	/**
	 * Metoda vraća duljinu ordinate.
	 * 
	 * @return duljina ordinate
	 */
	private int xAxisLength() {
		return getWidth() - 2 * OFFSET_H - 2 * GAP_H;
	}
}
