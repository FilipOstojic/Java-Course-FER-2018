package hr.fer.zemris.java.hw07.shell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sučelje <code>ShellCommand</code> definira metode koje svaki razred koji
 * predstavlja naredbu u rezredu <code>MyShell</code> mora definirati.
 * 
 * @author Filip
 *
 */
public interface ShellCommand {
	/**
	 * Metoda izvršava zadatak naredbe.
	 * 
	 * @param env
	 *            {@link Environment}
	 * @param arguments
	 *            agumenti naredbe
	 * @return {@link ShellStatus}
	 * @throws IOException
	 */
	ShellStatus executeCommand(Environment env, String arguments) throws IOException;

	/**
	 * Metoda vraća naziv komande (naredbe).
	 * 
	 * @return naziv naredbe
	 */
	String getCommandName();

	/**
	 * Metoda vraća opis naredbe (što ona radi i kolko argumenata očekuje).
	 * 
	 * @return opis naredbe u listi
	 */
	List<String> getCommandDescription();

	/**
	 * Metoda transformira ulazni tekst u listu s redovima dugačkim 50 znakova (+
	 * jedna riječ).
	 * 
	 * @param text
	 *            ulazni tekst
	 * @return tekst pretvoren u listu
	 */
	default List<String> separateText(String text) {
		List<String> description = new ArrayList<>();
		String[] array = text.split("\\s+");
		int countLength = 0;
		StringBuilder sb = new StringBuilder();
		for (String word : array) {
			if (countLength <= 50) {
				countLength += word.length() + 1;
				sb.append(word).append(" ");
			} else {
				description.add(sb.toString());
				sb = new StringBuilder().append(word).append(" ");
				countLength = word.length() + 1;
			}
		}
		description.add(sb.toString());
		return description;
	}

	/**
	 * Metoda koja iz ulaznog stringa argumenata vraća argumente u polju stringova.
	 * 
	 * @param text
	 *            ulazni argumenti
	 * @return polje argumenata
	 */
	default String[] getElements(String text) {
		text += " ";
		String[] args = new String[10];
		int numOfArgs = 0;
		int currentPosition = 0;
		int size = text.length();
		for (; currentPosition < size - 1;) {
			if (text.charAt(currentPosition) == '"') {
				currentPosition++;
				int finalPosition = text.indexOf('"', currentPosition);
				String arg = text.substring(currentPosition, finalPosition);
				args[numOfArgs++] = arg;
				currentPosition = finalPosition + 1;
				if (currentPosition < size - 1 && text.charAt(currentPosition) == ' ') {
					currentPosition++;
				}
			} else {
				int finalPosition = text.indexOf(' ', currentPosition);
				String arg = text.substring(currentPosition, finalPosition);
				args[numOfArgs++] = arg;
				currentPosition = finalPosition + 1;
			}
		}
		int len = 0;
		for (int i = 0; i < args.length; i++) {
			if (args[i] != null)
				len++;
		}
		return Arrays.copyOf(args, len);
	}
}
