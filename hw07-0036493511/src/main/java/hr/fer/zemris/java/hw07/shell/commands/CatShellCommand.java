package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred <code>CatShellCommand</code> predstavlja naredbu koja prima put do
 * datoteke (i opcionalno charset) kroz okolinu razreda <code>MyShell</code>. Na
 * standardni izlaz ispisuje sadržaj datoteke.
 * 
 * @author Filip
 *
 */
public class CatShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	final String name = "cat";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = getElements(arguments);
		if (args.length != 1 && args.length != 2) {
			env.writeln("CatCommand is expecting 1 or 2 argumments, recieved: " + args.length);
			return ShellStatus.CONTINUE;
		}
		if (args.length == 1) {
			return writeFileText(env, args[0], Charset.defaultCharset());
		}
		Charset charset;
		try {
			charset = Charset.forName(args[1]);
			return writeFileText(env, args[0], charset);
		} catch (Exception e) {
			env.writeln("Unsupported charset!");
			return ShellStatus.CONTINUE;
		}
	}

	/**
	 * Ispisuje tekst na standardni izlaz.
	 * 
	 * @param env
	 *            okolina
	 * @param pathString
	 *            put do datoteke
	 * @param charset
	 *            charset
	 * @return ShellStatus
	 */
	private ShellStatus writeFileText(Environment env, String pathString, Charset charset) {
		Path path = env.getCurrentDirectory().resolve(pathString).normalize();
		if (!Files.exists(path)) {
			env.writeln("Path to file is invalid or file doesn't exist.");
			return ShellStatus.CONTINUE;
		}
		try (InputStream is = new BufferedInputStream(Files.newInputStream(path))) {
			byte[] ibuf = new byte[1024];
			while ((is.read(ibuf)) != -1) {
				String r = new String(ibuf, charset);
				env.write(r);
			}
		} catch (IOException e) {
			env.writeln("Error occured while reading from file. Error description: IOException while reading file.");
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
				"Command cat takes one or two arguments. The first argument is path to some file and is mandatory. "
						+ "The second argument is charset name that should be used to interpret chars from bytes. If not "
						+ "provided, a default platform charset should be used. This command opens given file and writes "
						+ "its content to console.");
	}

}
