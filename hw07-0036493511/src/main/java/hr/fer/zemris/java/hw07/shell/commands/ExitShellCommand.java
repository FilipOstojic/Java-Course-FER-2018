package hr.fer.zemris.java.hw07.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred <code>ExithellCommand</code> predstavlja naredbu koja prekida rad okoline
 * razreda <code>MyShell</code>. Na standardni izlaz ne ispisuje ništa.
 * 
 * @author Filip
 *
 */
public class ExitShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	final String name = "exit";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return separateText("Shell terminates when user gives exit command.");
	}

}
