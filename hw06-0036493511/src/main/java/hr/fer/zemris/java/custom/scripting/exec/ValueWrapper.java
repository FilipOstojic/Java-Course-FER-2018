package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Razred <code>ValueWrapper</code> predstavlja omotač primjeraka razreda
 * String, Integer i Double te null referenci. Njihovu vrijedost čuvaju, dok se
 * ne pozovu računske operacije kada se pretvaraju u Integer ili Double (ili se
 * baca iznimka ako pretvorba nije moguća).
 * 
 * @author Filip
 *
 */
public class ValueWrapper {
	/**
	 * Vrijednost omotača.
	 */
	private Object value;

	/**
	 * Konstruktor, prima vrijednost koju omata.
	 * 
	 * @param value
	 *            vrijednost koju omata
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Metoda, vraća vrijednost subjekta.
	 * 
	 * @return vrijednost subjekta
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Meotda postavlja vrijednost omotača.
	 * 
	 * @param value
	 *            nova vrijednost omotača
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Pomoćna metoda koja izračunava zadanu operaciju nad omotačem ovisno o
	 * argumentu c.
	 * 
	 * @param o
	 *            argument računske operacije
	 * @param c
	 *            znak računske operacije ('+', '-', '*' ili '/')
	 */
	private void calculate(Object o, char c) {
		checkArgumentType(o);
		value = getType(value);
		Number number = getType(o);
		if (value instanceof Integer && number instanceof Integer) {
			setValue(getOperation(value, number, c, true));
			return;
		}
		setValue(getOperation(value, number, c, false));
	}

	/**
	 * Pomoćna metoda koja određuje koju operaciju napraviti i kojeg je tipa konačno
	 * riješenje.
	 * 
	 * @param value2
	 *            trenutna vrijednost omotača
	 * @param number
	 *            argument računske operacije
	 * @param c
	 *            znak za određivanje računske operacije
	 * @param areIntegers
	 *            zastavica, true ako su oba argumenta Integeri, inače false
	 * @return rezultat operacije
	 */
	private Number getOperation(Object value2, Number number, char c, boolean areIntegers) {
		if (areIntegers) {
			switch (c) {
			case '+':
				return ((Number) value2).intValue() + number.intValue();
			case '-':
				return ((Number) value2).intValue() - number.intValue();
			case '*':
				return ((Number) value2).intValue() * number.intValue();
			case '/':
				checkArgumentZero(number);
				return ((Number) value2).intValue() / number.intValue();
			}
		}
		switch (c) {
		case '+':
			return ((Number) value2).doubleValue() + number.doubleValue();
		case '-':
			return ((Number) value2).doubleValue() - number.doubleValue();
		case '*':
			return ((Number) value2).doubleValue() * number.doubleValue();
		case '/':
			checkArgumentZero(number);
			return ((Number) value2).doubleValue() / number.doubleValue();
		default:
			throw new IllegalArgumentException("Wrong operator: " + c);
		}
	}
	
	/**
	 * Metoda koja obavlja računsku operaciju zbrajanja nad omotačem. Prihvatljivi
	 * argumenti su primjerci razreda String, Double i Integer te null referenca.
	 * 
	 * @param incValue
	 *            argument zbrajanja (pribrojnik)
	 */
	public void add(Object incValue) {
		calculate(incValue, '+');
	}

	/**
	 * Metoda koja obavlja računsku operaciju oduzimanja nad omotačem. Prihvatljivi
	 * argumenti su primjerci razreda String, Double i Integer te null referenca.
	 * 
	 * @param decValue
	 *            argument oduzimanja (umanjitelj)
	 */
	public void subtract(Object decValue) {
		calculate(decValue, '-');
	}

	/**
	 * Metoda koja obavlja računsku operaciju množenja nad omotačem. Prihvatljivi
	 * argumenti su primjerci razreda String, Double i Integer te null referenca.
	 * 
	 * @param mulValue
	 *            argument množenja
	 */
	public void multiply(Object mulValue) {
		calculate(mulValue, '*');
	}

	/**
	 * Metoda koja obavlja računsku operaciju dijeljenja nad omotačem. Prihvatljivi
	 * argumenti su primjerci razreda String, Double i Integer te null referenca.
	 * 
	 * @param divValue
	 *            argument dijeljenja (dijelitelj)
	 */
	public void divide(Object divValue) {
		calculate(divValue, '/');
	}

	/**
	 * Metoda provjerava je li argument jednak nuli.
	 * 
	 * @throws IllegalArgumentException
	 *             ako je argument jednak nuli
	 * @param argument
	 *            broji za koji se provjerava je li jednak nuli
	 */
	private void checkArgumentZero(Number argument) {
		if (argument instanceof Integer) {
			if ((Integer) argument == 0) {
				throw new IllegalArgumentException("Division by zero is forbidden.");
			}
		} else if ((Double) argument == 0) {
			throw new IllegalArgumentException("Division by zero is forbidden.");
		}
	}

	/**
	 * Metoda uspoređuje vrijednost omotača i predani argument.
	 * 
	 * @param withValue
	 *            element s kojim se uspoređuje
	 * @return broj manji od 0 ako je vrijednost omotača manja od argumenta, broj
	 *         veći od nula ako je veći od argumenta inače nula
	 */
	public int numCompare(Object withValue) {
		checkArgumentType(withValue);
		if (value == withValue)
			return 0;
		value = getType(value);
		withValue = getType(withValue);
		if (withValue instanceof Integer && value instanceof Integer) {
			return Integer.compare((Integer) withValue, (Integer) value);
		}
		return Double.compare((Double) withValue, (Double) value);
	}

	/**
	 * Metoda provjerava je li dani argument instanca razreda String, Integer ili
	 * double te ako je null referenca.
	 * 
	 * @throws IllegalArgumentException
	 *             ako argument nije instanca razreda String, Integer ili double te
	 *             ako nije null referenca
	 * @param incValue
	 *            argument provjere
	 */
	private void checkArgumentType(Object argument) {
		if (!(argument instanceof String || argument instanceof Integer || argument instanceof Double
				|| argument == null)) {
			throw new IllegalArgumentException(
					"Allowed values for argument are: null and instances of Integer, Double and String classes, was: "
							+ argument.getClass().toString());
		}
	}

	/**
	 * Metoda ovisno o tipu argumenta radi parsiranje ili castanje kako bi iz iz
	 * argumenta dobila broj koji vraća.
	 * 
	 * @throws IllegalArgumentException
	 *             ako argument nije instaca razreda Integer, Double ili String
	 *             (koji se može parsirati u broj) ili null referenca.
	 * @param value
	 *            vrijednost
	 * @return broj
	 */
	private Number getType(Object value) {
		if (value == null) {
			return (Integer) 0;
		} else if (value instanceof Double) {
			return (Double) value;
		} else if (value instanceof Integer) {
			return (Integer) value;
		}
		try {
			if (((String) value).contains("E") || ((String) value).contains(".")) {
				double result = Double.parseDouble((String) value);
				return result;
			}
			int result = Integer.parseInt((String) value);
			return result;
		} catch (Exception e) {
			throw new IllegalArgumentException("String value can not be parsed into a number: " + value);
		}
	}
}
