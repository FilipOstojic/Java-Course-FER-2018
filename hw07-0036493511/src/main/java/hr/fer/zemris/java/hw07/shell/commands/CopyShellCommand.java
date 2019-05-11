package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred <code>CopyShellCommand</code> predstavlja naredbu koja prima dva
 * argumenta. Prvi put do datoteke, a drugi put do foldera ili datoteke gdje se
 * želi kopirati datoteka. Argumente prima kroz okolinu razreda
 * <code>MyShell</code>.
 * 
 * @author Filip
 *
 */
public class CopyShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	final String name = "copy";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = getElements(arguments);
		if (checkInput(env, args) != null) {
			return ShellStatus.CONTINUE;
		}
		File file = new File(env.getCurrentDirectory().resolve(args[0]).normalize().toAbsolutePath().toString());
		File dir = new File(env.getCurrentDirectory().resolve(args[1]).normalize().toAbsolutePath().toString());
		if (dir.isDirectory()) {
			// provjerava jesu li direktorij i direktorij file-a isti direktorij
			if (dir.getAbsolutePath()
					.equals(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("\\")))) {
				File fileNew = new File(dir.getAbsolutePath() + "/" + file.getName());
				return override(env, file, fileNew);
			} else {
				File fileNew = new File(dir.getAbsolutePath() + "/" + file.getName());
				return copyFile(env, file, fileNew);
			}
		} else {
			if (dir.exists()) {
				return override(env, file, dir);
			} else {
				return copyFile(env, file, dir);
			}
		}
	}

	/**
	 * Metoda koja komunicira s korisnikom u slučaju istog imena dvije datoteke.
	 * Ispituje treba li se nadječat datoteka ili stvorit nova drugačijeg imena.
	 * 
	 * @param env
	 *            okolina
	 * @param file
	 *            file
	 * @param dir
	 *            direktorij
	 * @return ShellStatus
	 */
	private ShellStatus override(Environment env, File file, File dir) {
		env.writeln(dir.getName() + " already exists. Do You want to override it? [y|n]");
		String answer;
		do {
			answer = env.readLine();
			switch (answer) {
			case "y":
				env.writeln("The file is overrided.");
				return copyFile(env, file, dir);
			case "n":
				env.writeln("Created new copy.");
				String newFileName = dir.getAbsolutePath().replace(dir.getName(), "copy" + dir.getName());
				return copyFile(env, file, new File(newFileName));
			default:
				env.writeln("Wrong input. Possible answers: y (yes) or n (no).");
				answer = env.readLine();
			}
		} while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n"));
		return ShellStatus.CONTINUE;
	}

	/**
	 * Metoda kopira datoteku u zadani direktorij.
	 * 
	 * @param env
	 *            okolina
	 * @param file
	 *            datoteka
	 * @param dir
	 *            direktorij
	 * @return ShellStatus
	 */
	private ShellStatus copyFile(Environment env, File file, File dir) {
		try (InputStream is = new BufferedInputStream(Files.newInputStream(file.toPath()));
				OutputStream os = new BufferedOutputStream(Files.newOutputStream(dir.toPath()))) {
			byte[] ibuf = new byte[1024];
			int len;
			while ((len = is.read(ibuf)) != -1) {
				os.write(ibuf, 0, len);
			}
		} catch (IOException e) {
			env.writeln("Problem occured while reading/writing in/from file.");
		}
		return ShellStatus.CONTINUE;
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
	private ShellStatus checkInput(Environment env, String[] args) {
		if (args.length != 2) {
			env.writeln("CopyCommand is expecting 2 arguments, but recieved: " + args.length);
			return ShellStatus.CONTINUE;
		}
		File file = new File(env.getCurrentDirectory().resolve(args[0]).toAbsolutePath().toString());
		File dir = new File(env.getCurrentDirectory().resolve(args[1]).toAbsolutePath().toString());
		if (!file.isFile()) {
			env.writeln("First argument should be file.");
			return ShellStatus.CONTINUE;
		}
		if (dir.isDirectory()) {
			if (!dir.exists()) {
				env.writeln("Second argument does not exists (directory).");
				return ShellStatus.CONTINUE;
			}
		} else {
			String dirPath = dir.getAbsolutePath().substring(0, dir.getAbsolutePath().lastIndexOf("\\"));
			File dirPathPart = new File(dirPath);
			if (!dirPathPart.isDirectory()) {
				env.writeln("Path to file does not exist: " + dirPath);
				return ShellStatus.CONTINUE;
			}
		}
		return null;

	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return separateText("The copy command expects two arguments: source file name and destination"
				+ " file name (i.e. paths and names). Is destination file exists, MyShell will ask if "
				+ "is it allowed to overwrite it. Copy command must work only with files (no "
				+ "directories). If the second argument is directory, MyShell assumes that user wants"
				+ " to copy the original file into that directory using the original file name.");
	}

}
