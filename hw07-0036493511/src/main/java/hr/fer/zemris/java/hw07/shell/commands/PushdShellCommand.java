package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred <code>PushdShellCommand</code> predstavlja naredbu koja prima put do
 * direktorija (koji predstavlja novi trenutni direktorij) kroz okolinu razreda
 * <code>MyShell</code>. Prije zamjene stari "trenutni direktorij" stavlja na
 * stog.
 * 
 * @author Filip
 *
 */
public class PushdShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	final String name = "pushd";

	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (env.getSharedData("cdstack") == null) {
			env.setSharedData("cdstack", new Stack<Path>());
		}
		Path path = env.getCurrentDirectory().resolve(arguments).normalize();
		if (!Files.exists(path) || !Files.isDirectory(path)) {
			env.writeln("This path does not exists or is not a directory.");
			return ShellStatus.CONTINUE;
		}
		((Stack<Path>) env.getSharedData("cdstack")).push(env.getCurrentDirectory());
		env.setCurrentDirectory(path);
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return separateText("Command pushd stores current directory on stack "
				+ "and takes one argument (path) whish becomes current directory."
				+ "Command creates stack (if does not already exist) in map sharedData"
				+ "using key: \"cdstack\" . If new path does not exist"
				+ "or is not a directory, exception massage will be printed and no change"
				+ "to the current directory will happened.");
	}

}
