package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Razred <code>QueryFilter</code> predstavlja filter za filtriranje preimjeraka
 * razreda <code>StudentRecord</code>.
 * 
 * @author Filip
 *
 */
public class QueryFilter implements IFilter {
	/**
	 * Lista uvijeta koje svaki record mora zadovoljavati.
	 */
	private List<ConditionalExpression> expressions;

	/**
	 * Konstruktor, prima listu uvijeta.
	 * 
	 * @param expressions
	 *            lista uvijeta
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}
	
	@Override
	public boolean accepts(StudentRecord record) {
		for (int i = 0; i < expressions.size(); i++) {
			ConditionalExpression expr = expressions.get(i);

			if (!expr.getComparisonOperator().satisfied(expr.getFieldValueGetter().get(record), expr.getLiteral()))
				return false;
		}
		return true;
	}

}
