package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred <code>RmtreeShellCommand</code> predstavlja naredbu koja prima put do
 * direktorija kroz okolinu razreda <code>MyShell</code>. Naredba rekurzivno
 * briše direktrij i sav njegov sadržaj.
 * 
 * @author Filip
 *
 */
public class RmtreeShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	final String name = "rmtree";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = getElements(arguments);
		if (args.length != 1) {
			env.writeln("RmtreeShellCommand is expecting 1 argument, recieved: " + args.length);
			return ShellStatus.CONTINUE;
		}
		Path path = env.getCurrentDirectory().resolve(args[0]).normalize();
		if (!Files.exists(path) || !Files.isDirectory(path)) {
			env.writeln("Path to directory is invalid or directory doesn't exist.");
			return ShellStatus.CONTINUE;
		}
		try {
			Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
		} catch (IOException e) {
			env.writeln("Error happened while deleting directory tree.");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return separateText("Command rmtree takes one argument and it is path to the"
				+ " directory which should be removed (and all its files/directories recursively). "
				+ "If directory does not exist, exception message will be printed.");
	}

}
