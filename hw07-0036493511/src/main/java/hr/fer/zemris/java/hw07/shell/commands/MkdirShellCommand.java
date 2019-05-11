package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred <code>MkdirShellCommand</code> predstavlja naredbu koja prima put do
 * datoteke kroz okolinu razreda <code>MyShell</code>. Stvara potrebnu strukturu
 * direktorija. Na standardni izlaz ne ispisuje ništa.
 * 
 * @author Filip
 *
 */
public class MkdirShellCommand implements ShellCommand {

	/**
	 * Ime datoteke.
	 */
	final String name = "mkdir";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = getElements(arguments);
		if (checkInput(env, args) != null) {
			return ShellStatus.CONTINUE;
		}
		Path path = env.getCurrentDirectory().resolve(args[0]).normalize();
		File file = new File(path.toAbsolutePath().toString());
		file.mkdirs();
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		return separateText("The mkdir command takes a single argument: directory name, and creates "
				+ "the appropriate directory structure.");
	}

	/**
	 * Metoda provjerava ulaz.
	 * 
	 * @param env
	 *            okolina
	 * @param args
	 * @return
	 */
	private Object checkInput(Environment env, String[] args) {
		if (args.length != 1) {
			env.writeln("TreeCommand is expecting 1 argument, but recieved: " + args.length);
			return ShellStatus.CONTINUE;
		}
		return null;
	}
}
