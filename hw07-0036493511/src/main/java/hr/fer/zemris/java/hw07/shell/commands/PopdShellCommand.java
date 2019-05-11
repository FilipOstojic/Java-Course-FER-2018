package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred <code>PopdShellCommands</code> predstavlja naredbu koja ne prima niti
 * jedan argument kroz okolinu razreda <code>MyShell</code>. Miče zadnju stazu
 * koja je stavljena na stog te ju postavlja kao trenutni direktorij (ako takava
 * postoji).
 * 
 * @author Filip
 *
 */
public class PopdShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	final String name = "popd";

	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
		if (!arguments.equals("")) {
			env.writeln("Command is expecting 0 arguments.");
			return ShellStatus.CONTINUE;
		}
		if (stack.isEmpty()) {
			env.writeln("The stack is empty.");
			return ShellStatus.CONTINUE;
		}
		Path path = stack.pop();
		if (Files.exists(path)) {
			env.setCurrentDirectory(path);
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return separateText("Command popd takes no arguments. It removes the last path"
				+ " that was pushed on the stack and makes it current directory. If stack"
				+ " is empty, exception message is printed, but no changes with current directory" + " happens.");
	}

}
