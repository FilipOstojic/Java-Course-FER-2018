package hr.fer.zemris.java.hw07.shell;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import hr.fer.zemris.java.hw07.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CharsetShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CptreeShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.DropdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.ListdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.MassrenameShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PopdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PushdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PwdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.RmtreeShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.TreeShellCommand;

/**
 * Razred <code>MyShell</code> predstavlja oblik komandne linije. Sadrži naredbe
 * za osnovnu funkcionalnost (naredbe ls, cat, mkdir, copy itd.) za rad s
 * datotečnim sustavom. Broj argumenata ovisi o naredbi.
 * 
 * @author Filip
 *
 */
public class MyShell {

	/**
	 * Metoda se poziva kada se pokrene program.
	 * 
	 * @param args
	 *            argumenti komande linije
	 */
	public static void main(String[] args) {
		SortedMap<String, ShellCommand> commands = initCommands(new TreeMap<>());
		EnviromentImpl env = new EnviromentImpl();

		ShellStatus status = ShellStatus.CONTINUE;
		ShellCommand command;

		env.writeln("Welcome to MyShell v 1.0");

		do {
			String line = env.readLine().trim();
			String commandName;
			String arguments;
			if (line.contains(" ")) {
				commandName = line.substring(0, line.indexOf(" "));
				arguments = line.substring(line.indexOf(" ") + 1, line.length()).trim();
			} else {
				commandName = line.substring(0, line.length());
				arguments = "";
			}
			arguments = arguments.replaceAll(" +", " ");
			command = commands.get(commandName);
			if (line.equals("")) {
				continue;
			} else if (command == null) {
				env.writeln("Wrong input, command does not exist: " + commandName);
			} else {
				try {
					status = command.executeCommand(env, arguments);
				} catch (IOException e) {
					env.writeln(e.getMessage());
				}
			}
		} while (status != ShellStatus.TERMINATE);
	}

	/**
	 * Pomoćna metoda za inicijalizaciju nepromjenjive mape koja sadrži elemente
	 * (ime naredbe i samu naredbu).
	 * 
	 * @param commands
	 *            mapa koja se treba inicijalizirati
	 * @return mapa s vrijednostima
	 */
	private static SortedMap<String, ShellCommand> initCommands(TreeMap<String, ShellCommand> commands) {
		commands.put("exit", new ExitShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("charset", new CharsetShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("pwd", new PwdShellCommand());
		commands.put("cd", new CdShellCommand());
		commands.put("pushd", new PushdShellCommand());
		commands.put("popd", new PopdShellCommand());
		commands.put("listd", new ListdShellCommand());
		commands.put("dropd", new DropdShellCommand());
		commands.put("rmtree", new RmtreeShellCommand());
		commands.put("cptree", new CptreeShellCommand());
		commands.put("massrename", new MassrenameShellCommand());
		return commands;
	}

	/**
	 * Razred <code>EnviromentImpl</code> predstavlja iplementaciju okoline razreda
	 * <code>MyShell</code>.
	 * 
	 * @author Filip
	 *
	 */
	static class EnviromentImpl implements Environment {

		private Character PROMPTSYMBOL = '>';
		private Character MULTILINNESSYMBOL = '|';
		private Character MORELINESSYMBOL = '\\';

		private Path currentDirectory = Paths.get(".").toAbsolutePath().normalize();
		private Map<String, Object> sharedData = new HashMap<>();

		Scanner sc = new Scanner(System.in);

		@Override
		public String readLine() throws ShellIOException {
			StringBuilder sb = new StringBuilder();
			write(PROMPTSYMBOL + " ");
			String line = sc.nextLine().trim();
			while (line.endsWith(MORELINESSYMBOL.toString())) {
				sb.append(line.substring(0, line.length() - 1));
				write(MULTILINNESSYMBOL + " ");
				line = sc.nextLine().trim();
			}
			sb.append(line.substring(0, line.length()));
			return sb.toString();
		}

		@Override
		public void write(String text) throws ShellIOException {
			System.out.print(text);
		}

		@Override
		public void writeln(String text) throws ShellIOException {
			System.out.println(text);
		}

		@Override
		public SortedMap<String, ShellCommand> commands() {
			return initCommands(new TreeMap<>());
		}

		@Override
		public Character getMultilineSymbol() {
			return this.MULTILINNESSYMBOL;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			MULTILINNESSYMBOL = symbol;
		}

		@Override
		public Character getPromptSymbol() {
			return this.PROMPTSYMBOL;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			PROMPTSYMBOL = symbol;
		}

		@Override
		public Character getMorelinesSymbol() {
			return this.MORELINESSYMBOL;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			MORELINESSYMBOL = symbol;
		}

		@Override
		public Path getCurrentDirectory() {
			return currentDirectory;
		}

		@Override
		public void setCurrentDirectory(Path path) {
			Objects.requireNonNull(path);
			if (!path.toFile().isDirectory()) {
				try {
					throw new NoSuchFileException("Path to new current directory does not exist.");
				} catch (NoSuchFileException e) {
					writeln(e.getMessage());
				}
			}
			this.currentDirectory = path.toAbsolutePath().normalize();
		}

		@Override
		public Object getSharedData(String key) {
			if (!sharedData.containsKey(key)) {
				return null;
			}
			return sharedData.get(key);
		}

		@Override
		public void setSharedData(String key, Object value) {
			if (key == null) {
				throw new IllegalArgumentException();
			}
			sharedData.put(key, value);
		}

	}
}
