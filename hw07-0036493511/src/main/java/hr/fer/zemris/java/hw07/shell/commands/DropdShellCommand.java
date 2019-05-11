package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred <code>DropdShellCommand</code> predstavlja naredbu koja ne prima niti
 * jedan argument kroz okolinu razreda <code>MyShell</code>. Miče zadnju stazu
 * koja je stavljena na stog (ako takava postoji) te trenutni direktorij ostaje
 * ne promjenjen.
 * 
 * @author Filip
 *
 */
public class DropdShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	final String name = "dropd";

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
		stack.pop();
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return separateText("Command dropd takes zero arguments and removes last path pushed on stack (if exists),"
				+ " otherwise prints exception message.");
	}

}
