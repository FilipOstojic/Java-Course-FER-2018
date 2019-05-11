package hr.fer.zemris.java.hw05.db;

/**
 * Sučelje koje definira metodu koja će se implementirati za vraćanje određene
 * vrijednosti primjerka razreda <code>StudentRecord</code>.
 * 
 * @author Filip
 *
 */
public interface IFieldValueGetter {
	/**
	 * Metoda vraća određenu vrijednost record-a.
	 * 
	 * @param record
	 *            primjerak razreda <code>StudentRecord</code>
	 * @return vrijednost record-a (ovisno o implementaciji)
	 */
	public String get(StudentRecord record);
}
