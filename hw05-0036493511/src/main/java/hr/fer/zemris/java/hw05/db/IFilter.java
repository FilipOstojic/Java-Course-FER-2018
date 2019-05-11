package hr.fer.zemris.java.hw05.db;

/**
 * Sučelje koje definira metodu koja će se implementirati koja (ne)prihvaća
 * određeni record (primjerak razreda <code>StudentRecord</code>).
 * 
 * @author Filip
 *
 */
public interface IFilter {
	/**
	 * Metoda prihvaća record ako prolazi filter (uvijete iz liste) i vraća true, inače
	 * false.
	 * 
	 * @param record
	 *            primjerak razreda <code>StudentRecord</code>
	 * @return true ako record prolazi filter, inače false
	 */
	public boolean accepts(StudentRecord record);
}
