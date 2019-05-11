package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
/**
 * Razred <code>CalcLayout</code> predstavlja razmještaj namjenjen 
 * kalkulatorima.
 * 
 * @author Filip
 *
 */
public class CalcLayout implements LayoutManager2 {
	/**
	 * Razmak između komponenti.
	 */
	private int spacing;
	/**
	 * Tablica komponenti.
	 */
	private Component[][] table;
	/**
	 * Podrazumjevani razmak.
	 */
	private static int DEFAULT_SPACING = 3;
	/**
	 * Minimalni razmak.
	 */
	private static int MINIMAL_SPACING = 0;
	/**
	 * Broj redova.
	 */
	private static int ROWS = 5;
	/**
	 * Broj stupaca.
	 */
	private static int COLUMNS = 7;

	/**
	 * Defaultni konstruktor.
	 */
	public CalcLayout() {
		this(DEFAULT_SPACING);
	}

	/**
	 * Konstruktor.
	 * 
	 * @param spacing
	 *            razmak između komponenti.
	 */
	public CalcLayout(int spacing) {
		if (spacing < MINIMAL_SPACING)
			throw new CalcLayoutException();
		this.spacing = spacing;
		table = new Component[ROWS][COLUMNS];
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets inset = parent.getInsets();
		int width = (parent.getWidth() - (COLUMNS - 1) * spacing) / COLUMNS;
		int height = (parent.getHeight() - (ROWS - 1) * spacing) / ROWS;

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				Component comp = table[i][j];
				if (comp == null) {
					continue;
				} else if (i == 0 && j == 0) {
					comp.setBounds(inset.left, inset.top, width * 5 + spacing * 4, height);
				} else if (i == 0 && (j >= 1 && j <= 4)) {
					continue;
				} else {
					comp.setBounds(inset.left + j * (width + spacing), inset.top + i * (height + spacing), width, height);
				}
			}
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getSize(parent, table, CalcLayoutSize.MINIMAL);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getSize(parent, table, CalcLayoutSize.PREFERRED);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getSize(target, table, CalcLayoutSize.MAXIMAL);
	}

	/**
	 * Metoda izračunava dimenzije komponente.
	 * 
	 * @param parent
	 *            komponenta roditelj
	 * @param table
	 *            tablica komponenti
	 * @param layoutSize
	 *            {@link CalcLayoutSize}
	 * @return dimenzije komponenti
	 */
	private Dimension getSize(Container parent, Component[][] table, CalcLayoutSize layoutSize) {
		Insets inset = parent.getInsets();
		Integer width = null;
		Integer height = null;

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (table[i][j] != null) {
					int tmpWidth = getDimension(table[i][j], layoutSize).width;
					int tmpHeight = getDimension(table[i][j], layoutSize).height;

					if (table[i][j] == null) {
						continue;
					}
					if (i == 0 && (j >= 0 && j <= 5)) {
						tmpWidth -= (4 * spacing);
						tmpWidth /= 5;
					}
					if (width == null || width < tmpWidth) {
						width = tmpWidth;
					}
					if (height == null || height < tmpHeight) {
						height = tmpHeight;
					}
				}
			}
		}
		return new Dimension(inset.left + COLUMNS * width + (COLUMNS - 1) * spacing + inset.right,
							 inset.top + ROWS * height + (ROWS - 1) * spacing + inset.bottom);
	}

	/**
	 * Metoda vraća dimenzije komponente.
	 * 
	 * @param comp
	 *            komponenta
	 * @param layoutSize
	 *            {@link CalcLayoutSize}
	 * @return dimenzije komponente
	 */
	private Dimension getDimension(Component comp, CalcLayoutSize layoutSize) {
		switch (layoutSize) {
		case MINIMAL:
			return comp.getMinimumSize() == null ? new Dimension(0, 0) : comp.getMinimumSize();
		case MAXIMAL:
			return comp.getMinimumSize() == null ? new Dimension(0, 0) : comp.getMaximumSize();
		default:
			return comp.getPreferredSize() == null ? new Dimension(0, 0) : comp.getPreferredSize();
		}
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (table[i][j] == comp)
					table[i][j] = null;
			}
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition compRCP;

		if (constraints instanceof String) {
			compRCP = new RCPosition((String) constraints);
		} else if (constraints instanceof RCPosition) {
			compRCP = (RCPosition) constraints;
		} else {
			throw new CalcLayoutException("Invalid RCPosition.");
		}

		int row = compRCP.getRow();
		int column = compRCP.getColumn();

		if (table[row - 1][column - 1] != null) {
			throw new CalcLayoutException(String.format("Component already exists: (%d,%d)", row - 1, column - 1));
		}

		table[row - 1][column - 1] = comp;
		if (row == 1 && column == 1) {
			for (int i = 1; i <= 4; i++) {
				table[0][i] = comp;
			}
		}
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	@Override
	public void invalidateLayout(Container target) {
	}

	/**
	 * Metoda vraća razmak između komponenti.
	 * 
	 * @return razmak između komponenti
	 */
	public int getSpacing() {
		return spacing;
	}
}
