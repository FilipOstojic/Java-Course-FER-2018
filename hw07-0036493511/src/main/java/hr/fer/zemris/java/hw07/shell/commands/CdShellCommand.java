package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred <code>CdShellCommand</code> predstavlja naredbu koja kroz okolinu
 * razreda <code>MyShell</code> prima put do novog trenutnog direktorija.
 * 
 * @author Filip
 *
 */
public class CdShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	final String name = "cd";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = getElements(arguments);
		if (checkInput(env, args) != null) {
			return ShellStatus.CONTINUE;
		}
		if (args[0].equals("..")) {
			String absoulutePath = env.getCurrentDirectory().toAbsolutePath().toString();
			env.setCurrentDirectory(Paths.get(absoulutePath.substring(0, absoulutePath.lastIndexOf("\\"))));
		} else {
			Path newPath = env.getCurrentDirectory().resolve(Paths.get(args[0])).normalize();
			if (!new File(newPath.toString()).exists()) {
				env.writeln("Provided path does not exist.");
				return ShellStatus.CONTINUE;
			}
			env.setCurrentDirectory(newPath);
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
				"Command cd takes one argument (path to new current directory and stores it in Environment)."
						+ " Argument can be relative path which is relative to a path stored in Environment (current directory).");
	}

	/**
	 * Metoda provjerava ulaz.
	 * 
	 * @param env
	 *            okolina
	 * @param args
	 * @return object
	 */
	private Object checkInput(Environment env, String[] args) {
		if (args.length != 1) {
			env.writeln("CdShellCommand is expecting 1 argument, but recieved: " + args.length);
			return ShellStatus.CONTINUE;
		}
		return null;
	}

}
