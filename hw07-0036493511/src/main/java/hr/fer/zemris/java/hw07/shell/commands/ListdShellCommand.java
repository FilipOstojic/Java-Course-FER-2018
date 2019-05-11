package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred <code>ListdShellCommands</code> predstavlja naredbu koja ne prima niti
 * jedan argument kroz okolinu razreda <code>MyShell</code>. Naredba ispisuje
 * sve staze sa stoga u terminal, počev od one koja je zadnja dodana.
 * 
 * @author Filip
 *
 */
public class ListdShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	final String name = "listd";

	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Stack<Path> stack = (Stack<Path>) ((Stack<Path>) env.getSharedData("cdstack")).clone();
		if (!arguments.equals("")) {
			env.writeln("Command is expecting 0 arguments.");
			return ShellStatus.CONTINUE;
		}
		if (stack.isEmpty()) {
			env.writeln("The stack contains no directories.");
			return ShellStatus.CONTINUE;
		}
		while(!stack.isEmpty()) {
			env.writeln(stack.pop().toString());
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
				"Command listd takes no arguments and prints all paths that are"
				+ " stored in stack (from top to bottom)");
	}

}
