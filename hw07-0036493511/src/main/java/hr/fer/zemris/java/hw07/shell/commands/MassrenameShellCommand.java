package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.NameBuilder;
import hr.fer.zemris.java.hw07.shell.NameBuilderInfoImpl;
import hr.fer.zemris.java.hw07.shell.NameBuilderParser;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred <code>MassrenameShellCommand</code> predstavlja naredbu koja kroz
 * okolinu razreda <code>MyShell</code> prima 4 ili 5 argumenata: DIR1 DIR2 CMD
 * REGEX {EXPRESION}. Na standardni izlaz ispisuje sve datoteke koje
 * zadovoljavaju regex ili ih premješta/preimenuje u neki drugi direktorij.
 * 
 * @author Filip
 *
 */
public class MassrenameShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	final String name = "massrename";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws IOException {
		String[] args = getElements(arguments);
		if (checkArguments(env, args) == ShellStatus.CONTINUE) {
			return ShellStatus.CONTINUE;
		}
		Path source = env.getCurrentDirectory().resolve(args[0]);
		Path destination = env.getCurrentDirectory().resolve(args[1]);
		Pattern p = Pattern.compile(args[3], Pattern.UNICODE_CASE & Pattern.CASE_INSENSITIVE);
		switch (args[2]) {
		case "filter":
			filterMethod(env, source, p);
			break;
		case "groups":
			groupMethod(env, source, p);
			break;
		case "show":
			showMethod(env, source, p, args[4]);
			break;
		case "execute":
			executeMethod(env, source, destination, p, args[4]);
			break;
		default:
			env.writeln(
					"MassrenameShellCommand's third argument can be: filter, group, show, execute. Was: " + args[2]);
			break;
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Metoda premješta i opcionalno preimenuje sve datoteke iz izvorne putanje i
	 * premješta u odredišnu.
	 * 
	 * @param env
	 *            okolina
	 * @param source
	 *            putanja do izvornih datoteka
	 * @param destination
	 *            putanja do odredišnog direktorija
	 * @param p
	 *            pattern
	 * @param expression
	 *            izraz
	 * @throws IOException
	 */
	private void executeMethod(Environment env, Path source, Path destination, Pattern p, String expression)
			throws IOException {
		List<String> names = Files.list(source)
				.filter(path -> p.matcher(path.getFileName().toString()).matches())
				.map(path -> path.getFileName().toString())
				.collect(Collectors.toList());
		NameBuilderParser parser = new NameBuilderParser(expression);
		NameBuilder builder = parser.getNameBuilder();
		for (String name : names) {
			Matcher matcher = p.matcher(name);
			if (!matcher.find())
				continue;
			NameBuilderInfoImpl info = new NameBuilderInfoImpl(matcher);
			builder.execute(info);
			String newName = info.getStringBuilder().toString();
			Files.move(source.resolve(name), destination.resolve(newName), StandardCopyOption.REPLACE_EXISTING);
			env.writeln(source.resolve(name).normalize() + " => " + destination.resolve(newName).normalize());
		}
	}

	/**
	 * Metoda ispisuje sve datoteke koje zadovoljavaju regex i njihova nova imena
	 * pomoću izraza.
	 * 
	 * @param env
	 *            okolina
	 * @param source
	 *            putanja do izvorišnih datoteka
	 * @param p
	 *            pattern
	 * @param expression
	 *            izraz
	 * @throws IOException
	 */
	private void showMethod(Environment env, Path source, Pattern p, String expression) throws IOException {
		List<String> names = Files.list(source)
				.filter(path -> p.matcher(path.getFileName().toString()).matches())
				.map(path -> path.getFileName().toString())
				.collect(Collectors.toList());
		NameBuilderParser parser = new NameBuilderParser(expression);
		NameBuilder builder = parser.getNameBuilder();
		for (String name : names) {
			Matcher matcher = p.matcher(name);
			if (!matcher.find())
				continue;
			NameBuilderInfoImpl info = new NameBuilderInfoImpl(matcher);
			builder.execute(info);
			String newName = info.getStringBuilder().toString();
			env.writeln(name + " => " + newName);
		}
	}

	/**
	 * Metoda za pisanje grupa datoteka koje zadovoljavaju regex.
	 *
	 * @param env
	 *            okolina
	 * @param source
	 *            putanja do izvorišnih datoteka
	 * @param p
	 *            pattern
	 * @throws IOException
	 */
	private void groupMethod(Environment env, Path source, Pattern p) throws IOException {
		List<String> names = Files.list(source)
				.filter(path -> p.matcher(path.getFileName().toString()).matches())
				.map(path -> path.getFileName().toString())
				.collect(Collectors.toList());
		for (String name : names) {
			Matcher matcher = p.matcher(name);
			if (!matcher.find())
				continue;
			env.write(name + " ");
			for (int i = 0; i <= matcher.groupCount(); i++) {
				env.write(String.format("%d: %s ", i, matcher.group(i)));
			}
			env.writeln("");
		}
	}

	/**
	 * Metoda za filtriranje svih datoteka koje zadovoljavaju regex.
	 * 
	 * @param env
	 *            okolina
	 * @param source
	 *            putanja do izvorišnih datoteka
	 * @param p
	 *            pattern
	 * @throws IOException
	 */
	private void filterMethod(Environment env, Path source, Pattern p) throws IOException {
		Files.list(source)
			.filter(path -> p.matcher(path.getFileName().toString()).matches())
			.forEach(e -> env.writeln(e.getFileName().toString()));
	}

	/**
	 * Metoda provjerava broj i sadržaj argumenata.
	 * 
	 * @param env
	 *            okolina
	 * @param args
	 *            argumenti
	 * @return ShellStatus
	 */
	private ShellStatus checkArguments(Environment env, String[] args) {
		if (args.length != 4 && args.length != 5) {
			env.writeln("MassrenameShellCommand is expecting 4 or 5 argumments, recieved: " + args.length);
			return ShellStatus.CONTINUE;
		}
		Path source = env.getCurrentDirectory().resolve(args[0]);
		Path destination = env.getCurrentDirectory().resolve(args[1]);
		if (!Files.exists(source) || !Files.isDirectory(source)) {
			env.writeln("Path to source directory is invalid or directory doesn't exist.");
			return ShellStatus.CONTINUE;
		}
		if (!Files.exists(destination) || !Files.isDirectory(destination)) {
			env.writeln("Path to destination directory is invalid or directory doesn't exist.");
			return ShellStatus.CONTINUE;
		}
		return null;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return separateText(
				"Implementation of ShellCommand used to filter files of a certain folder. The"
				+ "command expects 4 or 5 arguments: massrename DIR1 DIR2 CMD REGEX {EXPRESION}. Based "
				+ "on the CMD subcommand we have 4 different executions. Filter-> for DIR1 writes out"
				+ "all files that match the REGEX. Groups-> for DIR1 writes out all groups for the "
				+ "files that match the REGEX. Show-> displays how the files that match the REGEX from "
				+ "DIR1 will be renamed based on EXPRESION. Execute-> Moves and renames.");
	}
}
