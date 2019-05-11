package hr.fer.zemris.java.hw05.db;

/**
 * Razred <code>FieldValueGetters</code> nudi strategije implementacije
 * <code>IFieldValueGetter</code> sučelja.
 * 
 * @author Filip
 *
 */
public class FieldValueGetters {
	/**
	 * Strategija koja vraća firstName iz primjerka razreda <code>StudentRecord</code>.
	 */
	public static final IFieldValueGetter FIRST_NAME = (record) -> {
		return record.getFirstName();
	};
	/**
	 * Strategija koja vraća lastName iz primjerka razreda <code>StudentRecord</code>.
	 */
	public static final IFieldValueGetter LAST_NAME = (record) -> {
		return record.getLastName();
	};
	/**
	 * Strategija koja vraća jmbag iz primjerka razreda <code>StudentRecord</code>.
	 */
	public static final IFieldValueGetter JMBAG = (record) -> {
		return record.getJmbag();
	};
}
