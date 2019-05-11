package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred <code>CharsetShellCommand</code> predstavlja naredbu koja kroz okolinu
 * razreda <code>MyShell</code> ne prima nikakav argument. Na standardni izlaz
 * ispisuje sve trenutno dostupne charset-e.
 * 
 * @author Filip
 *
 */
public class CharsetShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	final String name = "charset";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.equals("")) {
			env.writeln("CharsetCommand is expecting 0 arguments.");
			return ShellStatus.CONTINUE;
		}
		Set<String> availableCharsets = Charset.availableCharsets().keySet();
		for (String charset : availableCharsets) {
			env.writeln(charset);
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return separateText(
				"Command charsets takes no arguments and lists names of supported charsets for your Java platform.");
	}

}
