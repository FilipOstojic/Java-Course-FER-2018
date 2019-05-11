package hr.fer.zemris.java.hw07.shell.commands;

import java.util.List;
import java.util.Set;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.util.Map;

/**
 * Razred <code>HelpShellCommand</code> predstavlja naredbu koja prima ime
 * datoteke kroz okolinu razreda <code>MyShell</code> ili ne prima argumente.
 * Ako primi ime naredbe na standardni izlaz ispisuje opis naredbe, inače to
 * radi za sve naredbe.
 * 
 * @author Filip
 *
 */
public class HelpShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	final String name = "help";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = arguments.split("\\s+");
		if (args.length != 0 && args.length != 1) {
			env.writeln("HelpCommand expects 0 or 1 argument, recieved: " + args.length);
			return ShellStatus.CONTINUE;
		}
		if (argummentIsCommand(env, args)) {
			List<String> description = env.commands().get(args[0]).getCommandDescription();
			for (String line : description) {
				env.writeln(line);
			}
			return ShellStatus.CONTINUE;
		} else if (arguments.equals("")) {
			Map<String, ShellCommand> descriptions = env.commands();
			Set<String> nameOfCommands = env.commands().keySet();
			for (String name : nameOfCommands) {
				env.writeln("->" + name);
				List<String> description = descriptions.get(name).getCommandDescription();
				for (String line : description) {
					env.writeln("\t" + line);
				}
				env.writeln("");
			}
			return ShellStatus.CONTINUE;
		}
		env.writeln("Argument is not a command name.");
		return ShellStatus.CONTINUE;
	}

	private boolean argummentIsCommand(Environment env, String[] args) {
		Set<String> commandNames = env.commands().keySet();
		for (String name : commandNames) {
			if (args[0].equals(name))
				return true;
		}
		return false;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return separateText(
				"If started with no arguments, it must list names of all supported commands. If started with single argument, it must print name and the description of selected command (or print appropriate error message if no such command exists).");

	}

}
