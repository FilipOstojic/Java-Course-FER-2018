package hr.fer.zemris.java.hw07.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred <code>PwdShellCommand</code> predstavlja naredbu koja kroz okolinu
 * razreda <code>MyShell</code> ne prima nikakav argument. Na standardni izlaz
 * ispisuje trenutni direktorij okoline.
 * 
 * @author Filip
 *
 */
public class PwdShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	final String name = "pwd";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.equals("")) {
			env.writeln("PwdShellCommand is expecting 0 arguments.");
			return ShellStatus.CONTINUE;
		}
		env.writeln(env.getCurrentDirectory().toString());
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return separateText(
				"Command pwd takes no arguments and writes absolute path of current directory stored in Environment.");
	}

}
