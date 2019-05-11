package hr.fer.zemris.java.hw05.db;

/**
 * Razred <code>ComparisonOperators</code> nudi strategije implementacije
 * <code>IComparisonOperator</code> sučelja.
 * 
 * @author Filip
 *
 */
public class ComparisonOperators {
	/**
	 * Strategija koja vraća true ako je prvi string manji od drugog (usporedba
	 * komparatorom).
	 */
	public static final IComparisonOperator LESS = (s1, s2) -> {
		return s1.compareTo(s2) < 0;
	};
	/**
	 * Strategija koja vraća true ako je prvi string manji ili jednak od drugog
	 * (usporedba komparatorom).
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (s1, s2) -> {
		return s1.compareTo(s2) <= 0;
	};
	/**
	 * Strategija koja vraća true ako je prvi string veći od drugog (usporedba
	 * komparatorom).
	 */
	public static final IComparisonOperator GREATER = (s1, s2) -> {
		return s1.compareTo(s2) > 0;
	};
	/**
	 * Strategija koja vraća true ako je prvi string veći ili jednak od drugog
	 * (usporedba komparatorom).
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (s1, s2) -> {
		return s1.compareTo(s2) >= 0;
	};
	/**
	 * Strategija koja vraća true ako je prvi string jednak drugom (usporedba
	 * komparatorom).
	 */
	public static final IComparisonOperator EQUALS = (s1, s2) -> {
		return s1.compareTo(s2) == 0;
	};
	/**
	 * Strategija koja vraća true ako prvi string nije jednak drugom (usporedba
	 * komparatorom).
	 */
	public static final IComparisonOperator NOT_EQUALS = (s1, s2) -> {
		return s1.compareTo(s2) != 0;

	};
	/**
	 * Strategija koja vraća true ako mogu prvi i drugi string biti jednaki na način
	 * da * u drugom stringu predstavlja bilo koje slovo ili ništa, a ostala slova
	 * moraju biti na istim pozicijama.
	 */
	public static final IComparisonOperator LIKE = (s1, s2) -> {
		String tmp = s2;
		if (tmp.length() - tmp.replace("*", "").length() > 1)
			throw new IllegalArgumentException("More than one wildcard * occured.");
		if (s2.startsWith("*")) {
			String pattern = s2.replace("*", "");
			if (s1.endsWith(pattern))
				return true;
			return false;
		} else if (s2.endsWith("*")) {
			String pattern = s2.replace("*", "");
			if (s1.startsWith(pattern))
				return true;
			return false;
		} else if (s2.contains("*")) {
			String[] chunks = s2.split("\\*");
			if (s1.startsWith(chunks[0]) && s1.endsWith(chunks[1])
					&& s1.length() >= (chunks[0].length() + chunks[1].length()))
				return true;
			return false;
		} else {
			if (s1.equals(s2))
				return true;
			return false;
		}
	};
}
