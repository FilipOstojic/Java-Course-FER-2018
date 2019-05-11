package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred <code>CptreeShellCommand</code> predstavlja naredbu koja prima dva
 * puta do direktorija kroz okolinu razreda <code>MyShell</code>.Naredba
 * rekurzivno kopira sadržaj prvog direktorija u drugi (zadan drugim
 * argumentom).
 * 
 * @author Filip
 *
 */
public class CptreeShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	final String name = "cptree";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = getElements(arguments);
		if (args.length != 1 && args.length != 2) {
			env.writeln("Cptree command is expecting 2 argumments, recieved: " + args.length);
			return ShellStatus.CONTINUE;
		}
		Path source = env.getCurrentDirectory().resolve(args[0]);
		Path destination = env.getCurrentDirectory().resolve(args[1]);
		if (!Files.exists(source) || !Files.isDirectory(source)) {
			env.writeln("Path to source directory is invalid or directory doesn't exist.");
			return ShellStatus.CONTINUE;
		}
		if (Files.exists(destination) && Files.isDirectory(destination)) {
			File destinationNew = new File(
					destination.toAbsolutePath().toString() + "\\" + source.getFileName().toString());
			copyFolder(env, source.toFile(), destinationNew);
			return ShellStatus.CONTINUE;
		}
		String destinationString = destination.toAbsolutePath().toString();
		Path newDestination = Paths.get(destinationString.substring(0, destinationString.lastIndexOf("\\")));
		if (Files.exists(newDestination) && Files.isDirectory(newDestination)) {
			copyFolder(env, source.toFile(), destination.toFile());
			return ShellStatus.CONTINUE;
		}
		env.writeln("Destination directory does not exist.");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return separateText("Command cptree takes two arguments. The first argument is "
				+ "a path to the directory which should be copied recursively into the destination"
				+ "directory which is the second argument (path as well). If paths do not exist,"
				+ "exception message will be printed.");
	}

	public void copyFolder(Environment env, File src, File dest) {
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}
			String files[] = src.list();
			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				copyFolder(env, srcFile, destFile);
			}
		} else {
			try (InputStream in = new FileInputStream(src); OutputStream out = new FileOutputStream(dest)) {
				byte[] buffer = new byte[1024];
				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
			} catch (Exception e) {
				env.writeln("Error occured while copying directory tree.");
			}
		}
	}

}
