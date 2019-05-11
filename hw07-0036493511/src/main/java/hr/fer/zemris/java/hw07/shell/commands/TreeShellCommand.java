package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred <code>TreeShellCommand</code> predstavlja naredbu koja prima put do
 * datoteke kroz okolinu razreda <code>MyShell</code>. Na standardni izlaz
 * ispisuje stablastu strukturu direktorija i datoreka.
 * 
 * @author Filip
 *
 */
public class TreeShellCommand implements ShellCommand {

	/**
	 * Ime datoteke.
	 */
	final String name = "tree";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = getElements(arguments);
		if (checkInput(env, args) != null) {
			return ShellStatus.CONTINUE;
		}
		try {
			Path path = env.getCurrentDirectory().resolve(args[0]).normalize();
			recursion(env, new File(path.toAbsolutePath().toString()), 0);
		} catch (IOException e) {
			env.writeln("Error occured while reading from directory. Error description: " + e.getMessage());
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Metoda provjerava unos argumenata.
	 * 
	 * @param env
	 *            okolina
	 * @param args
	 *            polje argumenata
	 * @return ShellStatus.CONTINUE ako se dogodila greška inače null
	 */
	private Object checkInput(Environment env, String[] args) {
		if (args.length != 1) {
			env.writeln("TreeCommand is expecting 1 argument, but recieved: " + args.length);
			return ShellStatus.CONTINUE;
		}
		if (!Files.isDirectory(Paths.get(args[0]))) {
			env.writeln("Path to directory is invalid or directory doesn't exist.");
			return ShellStatus.CONTINUE;
		}
		return null;
	}

	/**
	 * Metoda rekurzivno obilazi zadano podstablo i ispisuje ga na standardni izlaz.
	 * 
	 * @param env
	 *            okolina
	 * @param file
	 *            put do direktorija podstabla
	 * @param level
	 *            dubina
	 * @throws IOException
	 */
	private static void recursion(Environment env, File file, int level) throws IOException {
		if (level == 0) {
			env.writeln(file.getCanonicalPath());
		} else {
			env.writeln(getSpaces(level, file) + file.getName());
		}
		File[] elements = file.listFiles();
		if (elements != null) {
			for (File child : elements) {
				if (child.isDirectory()) {
					recursion(env, child, level + 1);
				} else {
					env.writeln(getSpaces(level, child) + child.getName());
				}
			}
		}
	}

	/**
	 * Metoda vraća razmake za određenu dubinu datoteke u stablu.
	 * 
	 * @param level
	 *            dubbina
	 * @return razmaci
	 */
	private static String getSpaces(int level, File f) {
		String spaces = "";
		if (f.isDirectory()) {
			for (int i = 0; i < level - 1; i++) {
				spaces += "  ";
			}
		} else {
			for (int i = 0; i < level; i++) {
				spaces += "  ";
			}
		}
		return spaces;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return separateText("The tree command expects a single argument: directory name and prints a"
				+ " tree (each directory level shifts output two charatcers to the right).");
	}

}
