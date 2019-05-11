package hr.fer.zemris.java.hw07.shell;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.SortedMap;

/**
 * Sučelje <code>Environment</code> definira metode koje implementacija okoline
 * razeda <code>MyShell</code> mora imati.
 * 
 * @author Filip
 *
 */
public interface Environment {
	/**
	 * Metoda je napravljena da čita jedan readak ako nema znaka za više redova, ako
	 * ima čita sve redove dok znak za više redaka izostane (MULTILINE).
	 * 
	 * @return unos u jednom retku
	 * @throws ShellIOException
	 */
	String readLine() throws ShellIOException;

	/**
	 * Metoda piše na standardni izlaz i ne prelazi u novi red.
	 * 
	 * @param text
	 *            tekst koji se želi ispisati
	 * @throws ShellIOException
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Metoda piše na standardni izlaz i prelazi u novi red.
	 * 
	 * @param text
	 *            tekst koji se želi ispisati
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Metoda vraća mapu kojoj su ključevi nazivi podržanih naredbi od razreda
	 * <code>MyShell</code>, a vrijednosti same naredbe.
	 * 
	 * @return mapa<nazivNaredbe, Naredba>
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Metoda vraća simbol koji predstavlja pisanje kroz više redova. Defaultna
	 * vrijednost: '\'.
	 * 
	 * @return znak pisanja kroz više redova
	 */
	Character getMultilineSymbol();

	/**
	 * Metoda postavlja simbol koji predstavlja znak pisanja kroz više redova.
	 * 
	 * @param symbol
	 *            simbol koji predstavlja znak pisanja kroz više redova
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Metoda vraća simbol koji predstavlja znak za prvu naredbu u razredu
	 * <code>MyShell</code>. Defaultna vrijednost: '>'.
	 * 
	 * @return znak za prvu naredbu u razredu <code>MyShell</code>
	 */
	Character getPromptSymbol();

	/**
	 * Metoda postavlja simbol za prvi redak svake naredbe.
	 * 
	 * @param symbol
	 *            simbol za prvi redak svake naredbe
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Metoda vraća simbol koji predstavlja znak za pisanje kroz više redova.
	 * Defaultna vrijednost: '|'.
	 * 
	 * @return znak za pisanje kroz više redova
	 */
	Character getMorelinesSymbol();

	/**
	 * Metoda vraća simbol koji predstavlja znak za pisanje kroz više redova.
	 * 
	 * @param symbol
	 *            znak za pisanje kroz više redova
	 */
	void setMorelinesSymbol(Character symbol);

	/**
	 * Metoda vraća put do trenutnog direktorija okoline.
	 * 
	 * @return out do trenutnog direktorija okoline
	 */
	Path getCurrentDirectory();

	/**
	 * Metoda postavlja trenutni direktorij okoline.
	 * 
	 * @param path
	 *            put do trenutnog direktorija
	 * @throws NoSuchFileException
	 *             ako put do direktorija ne postoji
	 */
	void setCurrentDirectory(Path path);

	/**
	 * Metoda omogućavaju da naredbe dijele/pamte određene podatke. Implementacija
	 * sučelja Environment za ovo koristi mapu.
	 * 
	 * @param key
	 * @return
	 */
	Object getSharedData(String key);

	/**
	 * Metoda dohvaća određene podatke. Ako se traži nepostojeći ključ, vraća null.
	 * 
	 * @param key
	 * @param value
	 */
	void setSharedData(String key, Object value);
}
