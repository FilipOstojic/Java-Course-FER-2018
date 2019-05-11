package hr.fer.zemris.java.hw07.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred <code>SymbolShellCommand</code> predstavlja naredbu koja prima 1 ili 2
 * argumenta kroz okolinu razreda <code>MyShell</code>. Za prvi argument prima
 * PROMPT, MULTILINES ili MORELINES te ispisuje njihov znak. Kao drugi argument
 * može primiti novi znak koji će predstvljati PROMPT, MULTILINES ili MORELINES.
 * 
 * @author Filip
 *
 */
public class SymbolShellCommand implements ShellCommand {

	/**
	 * Ime datoteke.
	 */
	final String name = "symbol";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = arguments.split("\\s+");
		if (args.length != 1 && args.length != 2) {
			env.writeln("Wrong number of arguments has been provided, expecting 1 or 2.");
			return ShellStatus.CONTINUE;
		} else if (args.length == 1) {
			switch (args[0]) {
			case "PROMPT":
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
				break;
			case "MORELINES":
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
				break;
			case "MULTILINE":
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
				break;
			default:
				env.writeln("Unsupported word, expecting: MORELINES, MULTILINE or PROMPT.");
				return ShellStatus.CONTINUE;
			}
			return ShellStatus.CONTINUE;
		}
		if (args[1].length() != 1) {
			env.writeln("Expecting one Charcter, got: " + args[1]);
			return ShellStatus.CONTINUE;
		}
		Character c;
		switch (args[0]) {
		case "PROMPT":
			c = args[1].charAt(0);
			env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() + "' to " + c);
			env.setPromptSymbol(c);
			break;
		case "MORELINES":
			c = args[1].charAt(0);
			env.writeln("Symbol for MORELINES changed from '" + env.getMorelinesSymbol() + "' to " + c);
			env.setMorelinesSymbol(c);
			break;
		case "MULTILINE":
			c = args[1].charAt(0);
			env.writeln("Symbol for MULTILINE changed from '" + env.getMultilineSymbol() + "' to " + c);
			env.setMultilineSymbol(c);
			break;
		default:
			env.writeln("Unsupported word, expecting: MORELINES, MULTILINE or PROMPT.");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return separateText("Recieves one or two arguments. First is PROMPT, MULTILINES ili"
				+ " MORELINES and secon new character.");
	}

}
