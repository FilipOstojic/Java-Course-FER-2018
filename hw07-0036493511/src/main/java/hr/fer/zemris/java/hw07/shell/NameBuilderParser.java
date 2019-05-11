package hr.fer.zemris.java.hw07.shell;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Razred <code>NameBuilderParser</code> parsira zadani izraz te stavra
 * primjerke <code>NameBuilder</code>-a te iih stavlja u listu. Prilikom
 * pogreške javlja korisniku grešku.
 * 
 * @author Filip
 *
 */
public class NameBuilderParser {
	/**
	 * Lista <code>NameBuilder</code>-a.
	 */
	private List<NameBuilder> builders = new ArrayList<>();
	/**
	 * Trenutni znak u polju.
	 */
	private int currentIndex;
	/**
	 * Duljina izraza.
	 */
	int size;
	/**
	 * Polje znakova izraza.
	 */
	char[] charArray;

	/**
	 * Konstruktor, prima izraz.
	 * 
	 * @param expression
	 *            izraz
	 */
	public NameBuilderParser(String expression) {
		Objects.requireNonNull(expression);
		currentIndex = 0;
		charArray = expression.toCharArray();
		size = expression.length();
		startParsing(expression);
	}

	/**
	 * Metoda koja parsira zadani izraz te u listu dodaje objekte tipa
	 * <code>NameBuilder</code>.
	 * 
	 * @param expression
	 *            izraz
	 */
	private void startParsing(String expression) {
		for (; currentIndex < size;) {
			StringBuilder sb = new StringBuilder();
			if (charArray[currentIndex] != '$') {
				while (charArray[currentIndex] != '$') {
					sb.append(charArray[currentIndex++]);
					if (currentIndex == size)
						break;
				}
				builders.add(new StringNameBuilder(sb.toString()));
			} else if (checkNext("${", 1)) {
				while (charArray[currentIndex] != '}') {
					if (Character.isDigit(charArray[currentIndex]) || charArray[currentIndex] == ',') {
						sb.append(charArray[currentIndex++]);
					} else if (Character.isWhitespace(charArray[currentIndex])) {
						skipWhitespaces();
					} else {
						throw new IllegalAccessError(
								"Expression can contain only digits, comma and spaces (not between numbers).");
					}
				}
				checkedInput(sb.toString());
				builders.add(new GroupNameBuilder(sb.toString()));
				currentIndex++;
			} else {
				throw new IllegalArgumentException("Wrong input.");
			}

		}

	}

	/**
	 * Pomoćna metoda čija je prvenstveno zadaća provjera unnosa i dojavljvanje
	 * greški ako naiđe na neku. Inače ne vraća ništa.
	 * 
	 * @param input
	 *            unos
	 */
	private void checkedInput(String input) {
		String[] chunks = input.toString().split(",");
		if (chunks.length == 1) {
			if (chunks[0].trim().contains(" ")) {
				throw new IllegalAccessError("Expression has space(s) between numbers.");
			}
		} else if (chunks.length == 2) {
			if (chunks[0].trim().contains(" ") || chunks[1].trim().contains(" ")) {
				throw new IllegalAccessError("Expression has space(s) between numbers.");
			}
		} else {
			throw new IllegalAccessError("Expression has more than one commas.");
		}
	}

	/**
	 * Metoda preskače praznine u polju.
	 */
	private void skipWhitespaces() {
		while (Character.isWhitespace(charArray[currentIndex])) {
			currentIndex++;
		}
	}

	/**
	 * Pomoćna metoda koja provjerava postoji li određen broj znakova u budućnosti i
	 * jesu li oni jednaki očekivanom.
	 * 
	 * @param expected
	 *            očekivani string
	 * @param len
	 *            broj znakova za koje se gledaju postoje li unaprijed
	 * 
	 * @return true ako određeni broj znakova postoji i jednak je očekivanom, inače
	 *         false
	 * 
	 */
	private boolean checkNext(String expected, int len) {
		if (currentIndex + len < size) {
			int end = currentIndex + len;
			String actual = "";
			for (; currentIndex <= end; currentIndex++) {
				actual += charArray[currentIndex];
			}
			return expected.equalsIgnoreCase(actual);
		}
		return false;
	}

	/**
	 * Metoda stvara primjerak razreda <code>MainNameBuilder</code>.
	 * 
	 * @return {@linkplain MainNameBuilder}
	 */
	public NameBuilder getNameBuilder() {
		return new MainNameBuilder(builders);
	}

}
