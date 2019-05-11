package hr.fer.zemris.java.custom.scripting.lexer;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.zemris.java.custom.scripting.lexer.SmartToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartTokenType;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerException;

/**
 * Some basic tests for SmartScriptLexer.
 * 
 * @author Filip
 *
 */
public class SmartScriptLexerTest {

	@Test
	public void TestEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		SmartToken correctData[] = { new SmartToken(SmartTokenType.EOF, null) };
		checkTokenStream(lexer, correctData);
	}

	@Test
	public void TestText() {
		SmartScriptLexer lexer = new SmartScriptLexer("it is only text.");
		SmartToken correctData[] = { new SmartToken(SmartTokenType.TEXT, "it is only text.") };
		checkTokenStream(lexer, correctData);
	}

	@Test
	public void TestSpaces() {
		SmartScriptLexer lexer = new SmartScriptLexer("it is \n only test \r\n but with \t spaces \n");
		SmartToken correctData[] = {
				new SmartToken(SmartTokenType.TEXT, "it is \n only test \r\n but with \t spaces \n") };
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void TestNumbers() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ -13.33 1 1.0 -3 -9.8$}");
		SmartToken correctData[] = {
				new SmartToken(SmartTokenType.TAG, "{$"), new SmartToken(SmartTokenType.DOUBLE, -13.33),
				new SmartToken(SmartTokenType.INTEGER, 1), new SmartToken(SmartTokenType.DOUBLE, 1.0), 
				new SmartToken(SmartTokenType.INTEGER, -3), new SmartToken(SmartTokenType.DOUBLE, -9.8), 
				new SmartToken(SmartTokenType.CLOSINGTAG, "$}") };

		checkTokenStream(lexer, correctData);
	}
	

	@Test
	public void TestQuotationAndBrackets() {
		SmartScriptLexer lexer = new SmartScriptLexer("\\{this \" tests\" quotation \"marks\"}.");
		SmartToken correctData[] = { new SmartToken(SmartTokenType.TEXT, "{this \" tests\" quotation \"marks\"}.") };
		checkTokenStream(lexer, correctData);
	}

	@Test
	public void TestEmptyInTags() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptLexerState.TAGS);
		SmartToken correctData[] = { new SmartToken(SmartTokenType.EOF, null) };
		checkTokenStream(lexer, correctData);
	}

	@Test
	public void TestTags() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ = i $}");
		SmartToken correctData[] = { new SmartToken(SmartTokenType.TAG, "{$"),
				new SmartToken(SmartTokenType.SPECIAL, "="), new SmartToken(SmartTokenType.VARIABLE, "i"),
				new SmartToken(SmartTokenType.CLOSINGTAG, "$}") };
		checkTokenStream(lexer, correctData);
	}

	@Test(expected = SmartScriptLexerException.class)
	public void TestTextWithBackslashAloneAtEnd() {
		SmartScriptLexer lexer = new SmartScriptLexer("This is not the \\ same");
		SmartToken correctData[] = { new SmartToken(SmartTokenType.TEXT, "This is not the \\ same") };
		checkTokenStream(lexer, correctData);
	}

	@Test
	public void TestTagWithSpaces() {
		SmartScriptLexer lexer = new SmartScriptLexer("   {$ \n 10 122.5 $}");
		SmartToken correctData[] = { new SmartToken(SmartTokenType.TEXT, "   "),
				new SmartToken(SmartTokenType.TAG, "{$"), new SmartToken(SmartTokenType.INTEGER, 10),
				new SmartToken(SmartTokenType.DOUBLE, 122.5), new SmartToken(SmartTokenType.CLOSINGTAG, "$}") };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void TestTextAndTags() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ foR i 10 -5 $} \n cool");
		SmartToken correctData[] = {

				new SmartToken(SmartTokenType.TAG, "{$"), new SmartToken(SmartTokenType.FOR, "foR"),
				new SmartToken(SmartTokenType.VARIABLE, "i"), new SmartToken(SmartTokenType.INTEGER, 10),
				new SmartToken(SmartTokenType.INTEGER, -5), new SmartToken(SmartTokenType.CLOSINGTAG, "$}"),
				new SmartToken(SmartTokenType.TEXT, " \n cool") };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void TestEnd() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$END$}{$end$}");

		SmartToken correctData[] = {

				new SmartToken(SmartTokenType.TAG, "{$"), new SmartToken(SmartTokenType.END, "END"),
				new SmartToken(SmartTokenType.CLOSINGTAG, "$}"), new SmartToken(SmartTokenType.TAG, "{$"),
				new SmartToken(SmartTokenType.END, "end"), new SmartToken(SmartTokenType.CLOSINGTAG, "$}") };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void TestQuotationInTags() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR Ivo \"2.2\"* * $}");

		SmartToken correctData[] = {

				new SmartToken(SmartTokenType.TAG, "{$"), new SmartToken(SmartTokenType.FOR, "FOR"),
				new SmartToken(SmartTokenType.VARIABLE, "Ivo"), new SmartToken(SmartTokenType.STRING, "2.2"),
				new SmartToken(SmartTokenType.OPERATOR, "*"), new SmartToken(SmartTokenType.OPERATOR, "*"),
				new SmartToken(SmartTokenType.CLOSINGTAG, "$}") };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void TestFunction() {
		SmartScriptLexer lexer = new SmartScriptLexer(" @nijeFunkcija {$ @Funkcija $}");

		SmartToken correctData[] = { new SmartToken(SmartTokenType.TEXT, " @nijeFunkcija "),
				new SmartToken(SmartTokenType.TAG, "{$"), new SmartToken(SmartTokenType.FUNCTION, "@Funkcija"),
				new SmartToken(SmartTokenType.CLOSINGTAG, "$}") };

		checkTokenStream(lexer, correctData);
	}

	@Test(expected = SmartScriptLexerException.class)
	public void TestVariable() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$-asko0$}");

		SmartToken correctData[] = { new SmartToken(SmartTokenType.TAG, "{$"),
				new SmartToken(SmartTokenType.VARIABLE, "-asko0"), new SmartToken(SmartTokenType.CLOSINGTAG, "$}") };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void TestAll() {
		SmartScriptLexer lexer = new SmartScriptLexer("This is sample text.\r\n{$ FOR i 1 10 1 $}\r\n"
				+ " This is {$= i $}-th time this message is generated.\r\n{$END$}\r\n{$FOR i 0 10 2 $}\r\n"
				+ " sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n{$END$}");

		SmartToken correctData[] = {

				new SmartToken(SmartTokenType.TEXT, "This is sample text.\r\n"),
				new SmartToken(SmartTokenType.TAG, "{$"), new SmartToken(SmartTokenType.FOR, "FOR"),
				new SmartToken(SmartTokenType.VARIABLE, "i"), new SmartToken(SmartTokenType.INTEGER, 1),
				new SmartToken(SmartTokenType.INTEGER, 10), new SmartToken(SmartTokenType.INTEGER, 1),
				new SmartToken(SmartTokenType.CLOSINGTAG, "$}"), new SmartToken(SmartTokenType.TEXT, "\r\n This is "),
				new SmartToken(SmartTokenType.TAG, "{$"), new SmartToken(SmartTokenType.SPECIAL, "="),
				new SmartToken(SmartTokenType.VARIABLE, "i"), new SmartToken(SmartTokenType.CLOSINGTAG, "$}"),
				new SmartToken(SmartTokenType.TEXT, "-th time this message is generated.\r\n"),
				new SmartToken(SmartTokenType.TAG, "{$"), new SmartToken(SmartTokenType.END, "END"),
				new SmartToken(SmartTokenType.CLOSINGTAG, "$}"), new SmartToken(SmartTokenType.TEXT, "\r\n"),
				new SmartToken(SmartTokenType.TAG, "{$"), new SmartToken(SmartTokenType.FOR, "FOR"),
				new SmartToken(SmartTokenType.VARIABLE, "i"), new SmartToken(SmartTokenType.INTEGER, 0),
				new SmartToken(SmartTokenType.INTEGER, 10), new SmartToken(SmartTokenType.INTEGER, 2),
				new SmartToken(SmartTokenType.CLOSINGTAG, "$}"), new SmartToken(SmartTokenType.TEXT, "\r\n sin("),
				new SmartToken(SmartTokenType.TAG, "{$"), new SmartToken(SmartTokenType.SPECIAL, "="),
				new SmartToken(SmartTokenType.VARIABLE, "i"), new SmartToken(SmartTokenType.CLOSINGTAG, "$}"),
				new SmartToken(SmartTokenType.TEXT, "^2) = "), new SmartToken(SmartTokenType.TAG, "{$"),
				new SmartToken(SmartTokenType.SPECIAL, "="), new SmartToken(SmartTokenType.VARIABLE, "i"),
				new SmartToken(SmartTokenType.VARIABLE, "i"), new SmartToken(SmartTokenType.OPERATOR, "*"),
				new SmartToken(SmartTokenType.FUNCTION, "@sin"), new SmartToken(SmartTokenType.STRING, "0.000"),
				new SmartToken(SmartTokenType.FUNCTION, "@decfmt"), new SmartToken(SmartTokenType.CLOSINGTAG, "$}"),
				new SmartToken(SmartTokenType.TEXT, "\r\n"), new SmartToken(SmartTokenType.TAG, "{$"),
				new SmartToken(SmartTokenType.END, "END"), new SmartToken(SmartTokenType.CLOSINGTAG, "$}")

		};

		checkTokenStream(lexer, correctData);
	}

	private void checkTokenStream(SmartScriptLexer lexer, SmartToken[] correctData) {
		int counter = 1;
		for (SmartToken expected : correctData) {
			SmartToken actual = lexer.nextToken();
			String msg = "Checking token " + counter + ":";
			Assert.assertEquals(msg, expected.getType(), actual.getType());
			Assert.assertEquals(msg, expected.getValue(), actual.getValue());
			counter++;
		}
	}

}
