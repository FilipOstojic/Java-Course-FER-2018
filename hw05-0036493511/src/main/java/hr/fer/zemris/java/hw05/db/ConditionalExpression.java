package hr.fer.zemris.java.hw05.db;

/**
 * Razred <code>ConditionalExpression</code> predstavlja jedan uvijet.
 *
 * @author Filip
 *
 */
public class ConditionalExpression {
	/**
	 * Varijabla čuva strategiju za odabir vrijednosti iz primjerka razreda
	 * <code>StudentRecord</code>.
	 */
	private IFieldValueGetter fieldValueGetter;
	/**
	 * Varijabla koja čuva string za usporedbu u strategiji iz razreda
	 * <code>comparisonOperator</code>.
	 */
	private String literal;
	/**
	 * Varijabla čuva strategiju iz razreda <code>comparisonOperator</code>.
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * Konstruktor, prima dvije strategije i string za usporedbu.
	 * 
	 * @param fieldValueGetter
	 *            strategija odabira vrijednosti
	 * @param literal
	 *            string za usporedbu
	 * @param comparisonOperator
	 *            strategija operatora za usporedbu
	 */
	public ConditionalExpression(IFieldValueGetter fieldValueGetter, String literal,
			IComparisonOperator comparisonOperator) {
		this.fieldValueGetter = fieldValueGetter;
		this.literal = literal;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Vraća strategiju odabira vrijednosti
	 * 
	 * @return strategija odabira vrijednosti
	 */
	public IFieldValueGetter getFieldValueGetter() {
		return fieldValueGetter;
	}

	/**
	 * Vraća string za usporedbu.
	 * 
	 * @return string za usporedbu
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Vraća strategiju operatora za usporedbu.
	 * 
	 * @return strategija operatora za usporedbu
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
}
