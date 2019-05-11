package hr.fer.zemris.java.hw05.db;

/**
 * Sučelje koje definira metodu koja će se implementirati za usporedbu dva
 * stringa.
 * 
 * @author Filip
 *
 */
public interface IComparisonOperator {
	/**
	 * Metoda za usporedbu dva stringa.
	 * 
	 * @param value1
	 *            prvi string
	 * @param value2
	 *            drugi string
	 * @return true ako stringovi zadovoljavaju uvijet strategije, inače false
	 */
	public boolean satisfied(String value1, String value2);
}
