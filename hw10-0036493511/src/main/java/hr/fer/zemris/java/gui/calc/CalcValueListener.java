package hr.fer.zemris.java.gui.calc;

/**
 * Sučelje <code>CalcValueListener</code> definira metodu koja obavještava
 * promatrače o promjeni.
 * 
 * @author Filip
 *
 */
public interface CalcValueListener {
	/**
	 * Metodu koja obavještava promatrače o promjeni.
	 * 
	 * @param model
	 *            {@link CalcModel}
	 */
	void valueChanged(CalcModel model);
}