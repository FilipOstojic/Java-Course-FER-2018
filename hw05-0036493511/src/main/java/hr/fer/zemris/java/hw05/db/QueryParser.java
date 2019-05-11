package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Razred <code>QueryParser</code> predstavlja Parser, a njegova je zadaća
 * provjerit ispravnost redosljeda ulaznih tokena. Prilikom nedozvoljenog
 * redosljeda baca <code>IllegalArgumentException</code>.
 * 
 * @author Filip
 *
 */
public class QueryParser {
	/**
	 * Lista koja čuva tokene.
	 */
	private List<Token> tokens = new ArrayList<>();
	/**
	 * Lista koja čuva uvijete.
	 */
	private List<ConditionalExpression> expressions = new LinkedList<>();
	/**
	 * Lexer.
	 */
	private QueryLexer lexer;

	/**
	 * Konstruktor, prima query ti incijalizira lekser i pokreće metodu parsiranja i
	 * stvaranja uvijeta.
	 * 
	 * @param line
	 *            query
	 */
	public QueryParser(String line) {
		checkInput(line);
		lexer = new QueryLexer(line.trim());
		try {
			startParsing();
			makeExpressions();
		} catch (Exception e) {
		}
	}

	/**
	 * Pomoćna metoda koja provjerava input (query).
	 * 
	 * @param line
	 *            query
	 */
	private void checkInput(String line) {
		if (line.contains("query"))
			throw new IllegalArgumentException("Too many query commands recieved.");
		if (line.equals(""))
			throw new IllegalArgumentException("Input line is empty.");
	}

	/**
	 * Metoda koja od tokena stvara uvijete. Uvijete dodaje u listu uvijeta.
	 */
	private void makeExpressions() {
		for (int i = 0; i < tokens.size();) {
			IFieldValueGetter fieldValueGetter = getField(tokens.get(i));
			i++;
			IComparisonOperator comparisonOperator = getOperator(tokens.get(i));
			i++;
			String literal = tokens.get(i).getValue();
			i++;
			expressions.add(new ConditionalExpression(fieldValueGetter, literal, comparisonOperator));
		}
	}

	/**
	 * Metoda vraća određenu strategiju za biranje operatora, ovisno o ulaznom
	 * tokenu.
	 * 
	 * @param token
	 *            {@link Token}
	 * @return strategija operatora
	 */
	private IComparisonOperator getOperator(Token token) {
		if (token.getValue().equals("<")) {
			return ComparisonOperators.LESS;
		} else if (token.getValue().equals("<=")) {
			return ComparisonOperators.LESS_OR_EQUALS;
		} else if (token.getValue().equals(">")) {
			return ComparisonOperators.GREATER;
		} else if (token.getValue().equals(">=")) {
			return ComparisonOperators.GREATER_OR_EQUALS;
		} else if (token.getValue().equals("=")) {
			return ComparisonOperators.EQUALS;
		} else if (token.getValue().equals("!=")) {
			return ComparisonOperators.NOT_EQUALS;
		} else {
			return ComparisonOperators.LIKE;
		}
	}

	/**
	 * Metoda vraća određenu strategiju za biranje povratne vrijednosti record-a,
	 * ovisno o ulaznom tokenu.
	 * 
	 * @param token
	 *            {@link Token}
	 * @return strategija operatora
	 */
	private IFieldValueGetter getField(Token token) {
		if (token.getValue().equals("firstName")) {
			return FieldValueGetters.FIRST_NAME;
		} else if (token.getValue().equals("lastName")) {
			return FieldValueGetters.LAST_NAME;
		} else {
			return FieldValueGetters.JMBAG;
		}
	}

	/**
	 * Metoda pokreće parsiranje i gleda ispravnost redosljeda tokena. Dodaje tokene
	 * u listu tokena.
	 */
	private void startParsing() {
		while (true) {
			Token token = lexer.next();
			if (token.getType() == TokenType.EOF)
				break;
			if (token.getType() == TokenType.ATRIBUT) {
				tokens.add(token);
				token = lexer.next();
				if (token.getType() == TokenType.OPERATOR) {
					tokens.add(token);
					token = lexer.next();
					if ( token.getType() == TokenType.STRING) {
						tokens.add(token);
						token = lexer.next();
						if (token.getType() == TokenType.SEPARATOR) {
							continue;
						}
					} else {
						throw new IllegalArgumentException("Expected JMBAG or STRING, was: " + token.getType());
					}
				} else {
					throw new IllegalArgumentException("Expected OPERATOR, was: " + token.getType());
				}
			} else {
				throw new IllegalArgumentException(
						"Each expression should start with tokenType ATRIBUT, was: " + token.getType());
			}
		}
	}

	/**
	 * Metoda provjera je li ulazni query direktan ili nije.
	 * 
	 * @return true ako je ulazni query direktan, inače false
	 */
	public boolean isDirectQuery() {
		if (expressions.size() == 1) {
			ConditionalExpression expression = getQuery().get(0);
			if (expression.getFieldValueGetter() == FieldValueGetters.JMBAG 
					&& expression.getComparisonOperator() == ComparisonOperators.EQUALS) {
				try {
					Integer.parseInt(expression.getLiteral());
					return true;
				} catch (Exception e) {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * Metoda za direktne query-je vraća JMBAG, inače baca
	 * <code>IllegalArgumentException</code>.
	 * 
	 * @return za direktne query-je vraća JMBAG
	 */
	public String getQueriedJMBAG() {
		if (isDirectQuery()) {
			return getQuery().get(0).getLiteral();
		}
		throw new IllegalArgumentException("Query is not direct.");
	}

	/**
	 * Metoda vraća listu uvijeta.
	 * 
	 * @return lista uvijeta
	 */
	public List<ConditionalExpression> getQuery() {
		return expressions;
	}

}
