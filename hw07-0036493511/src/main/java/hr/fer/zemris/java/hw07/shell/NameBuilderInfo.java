package hr.fer.zemris.java.hw07.shell;

/**
 * Sučelje <code>NameBuilderInfo</code> definira metode koje
 * 
 * @author Filip
 *
 */
public interface NameBuilderInfo {

	/**
	 * Metoda vraća referencu na StringBuilder.
	 * 
	 * @return referenca na StringBuilder
	 */
	StringBuilder getStringBuilder();

	/**
	 * Metoda vraća string zamjenu za znakove koji su unutar ${ }.
	 * 
	 * @param index
	 *            indeks grupe
	 * @return string
	 */
	String getGroup(int index);
}
