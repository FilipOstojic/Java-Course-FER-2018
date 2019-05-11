package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred <code>HexdumpShellCommand</code> predstavlja naredbu koja prima put do
 * datoteke kroz okolinu razreda <code>MyShell</code>. Na standardni izlaz
 * ispisuje hexdump datoteke.
 * 
 * @author Filip
 *
 */
public class HexdumpShellCommand implements ShellCommand {

	/**
	 * Ime datoteke.
	 */
	final String name = "hexdump";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = getElements(arguments);
		if (args.length != 1) {
			env.writeln("Expected 1 argument, recieved: " + args.length);
			return ShellStatus.CONTINUE;
		}
		writeHexDump(env, args[0]);
		return ShellStatus.CONTINUE;
	}

	/**
	 * Metoda čita 16 byte-ova te ispisuje: broj retka (zadnja nula, ispred nule,
	 * veličine 10), prvih 8 byte-ova, |, drugih 8 byte-ova, | te 16 znakova koje ti
	 * bytovi predstavljaju. Znakovi manji od 32 i veći od 127 se zamjenjuju točkom.
	 * Null se ne ispisuje.
	 * 
	 * @param env
	 *            okolina
	 * @param pathString
	 *            string puta
	 * @return ShellStatus
	 */
	private ShellStatus writeHexDump(Environment env, String pathString) {
		Path path = env.getCurrentDirectory().resolve(pathString).normalize();
		int counter = 0;
		if (!Files.exists(path)) {
			env.writeln("Path to file is invalid or file doesn't exist.");
			return ShellStatus.CONTINUE;
		}
		try (InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get(pathString)))) {
			StringBuilder sb = new StringBuilder();
			byte[] ibuf = new byte[16];
			while ((is.read(ibuf)) != -1) {
				String firstPart = "";
				for (int i = 0; i < ibuf.length / 2; i++) {
					if (ibuf[i] == 0x00) {
						firstPart += "   ";
					} else {
						firstPart += String.format("%02X ", ibuf[i]);
					}
				}
				String secondPart = "";
				for (int i = ibuf.length / 2; i < ibuf.length; i++) {
					if (ibuf[i] == 0x00) {
						secondPart += "   ";
					} else {
						secondPart += String.format("%02X ", ibuf[i]);
					}
				}
				String text = getText(ibuf);
				sb.append(String.format("%07d0: %s|%s | %s", counter, firstPart, secondPart, text));
				env.writeln(sb.toString());
				counter++;
				ibuf = new byte[16];
				sb = new StringBuilder();
			}
		} catch (IOException e) {
			env.writeln("Error occured while reading from file. Error description: IOException while reading file.");
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Metoda pretvara byte u char te vraća ukupni string na kraju.
	 * 
	 * @param ibuf
	 *            buffer
	 * @return string
	 */
	private String getText(byte[] ibuf) {
		StringBuilder sb = new StringBuilder();
		for (byte b : ibuf) {
			if (b == 0) {
				sb.append("");
			} else if (b < 32 || b > 127) {
				sb.append(".");
			} else {
				sb.append((char) b);
			}
		}
		return sb.toString();
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return separateText("the hexdump command expects a single argument: file name, and produces"
				+ " hex-output as illustrated below. On the right side of the image only a standard "
				+ "subset of characters is shown; for all other characters a '.' is printed instead "
				+ "(i.e. replace all bytes whose value is less than 32 or greater than 127 with '.').  e.g.   "
				+ "\"00000000: 6F 76 6F 20 6A 65 20 74 |65 73 74 6E 69 20 70 72  | ovo je testni pr\"");
	}

}
