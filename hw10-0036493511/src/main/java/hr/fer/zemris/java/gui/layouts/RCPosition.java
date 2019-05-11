package hr.fer.zemris.java.gui.layouts;

/**
 * Razred <code>RCPosition</code> čuva redak i stupac komponente u
 * {@link CalcLayout} razmještaju.
 * 
 * @author Filip
 *
 */
public class RCPosition {
	/**
	 * Redak.
	 */
	private int row;
	/**
	 * Stupac.
	 */
	private int column;

	/**
	 * Konstruktor.
	 * 
	 * @param row
	 *            redak
	 * @param column
	 *            stupac
	 */
	public RCPosition(int row, int column) {
		checkArguments(row, column);
		this.row = row;
		this.column = column;
	}

	/**
	 * Konstruktor.
	 * 
	 * @param rowsAndColumns
	 *            redak i stupac
	 */
	public RCPosition(String rowsAndColumns) {
		String[] chunks = rowsAndColumns.split(",");
		if (chunks.length != 2)
			throw new CalcLayoutException("Expected something like \"x,y\", was: " + rowsAndColumns);
		try {
			row = Integer.parseInt(chunks[0]);
			column = Integer.parseInt(chunks[1]);
			checkArguments(row, column);
			return;
		} catch (Exception e) {
			throw new CalcLayoutException("Input can not be parsed into Integer.");
		}
	}

	/**
	 * Metoda provjerava ispravnost unosa pozicije unutar {@link CalcLayout}
	 * razmještaja.
	 * 
	 * @param rows
	 *            redak
	 * @param columns
	 *            stupac
	 */
	private void checkArguments(int rows, int columns) {
		if (rows < 1 || rows > 5 || columns < 1 || columns > 7) {
			throw new CalcLayoutException("Wrong row/column.");
		}
		if (rows == 1 && (columns > 1 && columns < 6)) {
			throw new CalcLayoutException("Rows from (1,1) to (1,5) can be reached only with (1,1) position.");
		}
	}

	/**
	 * Metoda vraća redak.
	 * 
	 * @return redak
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Metoda vraća stupac.
	 * 
	 * @return stupac
	 */
	public int getColumn() {
		return column;
	}
}
