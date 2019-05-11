package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred <code>LsShellCommand</code> predstavlja naredbu koja prima put do
 * direktorija kroz okolinu razreda <code>MyShell</code>. Na standardni izlaz
 * ispisuje sadržaj direktorija.
 * 
 * @author Filip
 *
 */
public class LsShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	final String name = "ls";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = getElements(arguments);
		if (args.length != 1) {
			env.writeln("LsCommand is expecting 1 argument, but recieved: " + args.length);
			return ShellStatus.CONTINUE;
		}
		Path path = env.getCurrentDirectory().resolve(args[0]).normalize();
		if (!Files.isDirectory(path)) {
			env.writeln("Path to directory is invalid or directory doesn't exist.");
			return ShellStatus.CONTINUE;
		}
		try {
			Set<Path> paths = Files.list(path).collect(Collectors.toSet());
			for (Path p : paths) {
				env.writeln(createPrintString(p));
			}
		} catch (IOException e) {
			env.writeln("Error occured while reading from directory. Error description: " + e.getMessage());
		}
		return ShellStatus.CONTINUE;
	}

	private String createPrintString(Path p) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(Files.isDirectory(p) ? "d" : "_");
		sb.append(Files.isReadable(p) ? "r" : "_");
		sb.append(Files.isDirectory(p) ? "w" : "_");
		sb.append(Files.isExecutable(p) ? "x" : "_").append(" ");
		sb.append(String.format("%10s", Files.size(p))).append(" ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BasicFileAttributeView faView = Files.getFileAttributeView(p, BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		sb.append(formattedDateTime);
		sb.append(" " + p.getFileName().toString());
		return sb.toString();
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return separateText("Command ls takes a single argument – directory – and writes a directory"
				+ " listing (not recursive). The output consists of 4 columns. First column indicates "
				+ "if current object is directory (d), readable (r), writable (w) and executable (x). "
				+ "Second column contains object size in bytes that is right aligned and occupies 10 "
				+ "characters. Follows file creation date/time and finally file name.");
	}

}
