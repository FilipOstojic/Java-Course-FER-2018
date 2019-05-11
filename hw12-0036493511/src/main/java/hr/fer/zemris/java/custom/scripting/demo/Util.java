package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Pomoćni razred. Sadrži jednu metodu za čitanje iz datoteke.
 * 
 * @author Filip
 *
 */
public class Util {

	/**
	 * Metoda čita datoteku i vraća njezin sadržaj kao String (tekst).
	 * 
	 * @param path
	 *            putanja do datoteke
	 * @return sadržaj datoteke
	 */
	public static String readFromDisk(String path) {
		try {
			return new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e) {
			System.out.println("Error occured while reading from file.");
			return null;
		}
	}
}
