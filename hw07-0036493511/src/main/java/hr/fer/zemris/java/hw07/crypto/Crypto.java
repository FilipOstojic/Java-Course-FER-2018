package hr.fer.zemris.java.hw07.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import static hr.fer.zemris.java.hw07.crypto.Util.*;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Razred <code>Crypto</code> omogućava korisniku da šifrira / dešifrira zadanu
 * datoteku pomoću AES kripto algoritma i 128-bitnog ključa za šifriranje ili
 * izračunava i provjerava SHA-256 datotečni digest. Ova vrsta kriptografije
 * radi s binarnim podacima. Očekujju se argumenti: "checksha" te put do
 * datoteke čiji se digest provjerava ili "encrypt" te putovi do datoteka izvora
 * i ponora ili "decrypt" te putovi do datoteka izvora i ponora.
 * 
 * @author Filip
 *
 */
public class Crypto {

	/**
	 * Metoda, poziva se kada se pokrene program. Šifrira / dešifrira zadanu
	 * datoteku pomoću AES kripto algoritma i 128-bitnog ključa za šifriranje ili
	 * izračunava i provjerava SHA-256 datotečni digest
	 * 
	 * @param args
	 *            argumenti komandne linije
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		if (args.length == 2) {
			checkInputLine(args);
			System.out.format("Please provide expected sha-256 digest for %s: \n> ", args[1]);
			String keyText = sc.next();
			sc.close();
			if (sha(args[1]).equals(keyText)) {
				System.out.format("Digesting completed. Digest of %s matches expected digest.\n", args[1]);
				return;
			}
			System.out.format("Digesting completed. Digest of %s does not match the expected digest. Digest was: %s",
					args[1], sha(args[1]));
		} else if (args.length == 3) {
			boolean encrypt = checkInputLine(args).equals("encrypt") ? true : false;
			System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
			String keyText = sc.next();
			System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
			String ivText = sc.next();
			sc.close();

			SecretKeySpec keySpec = new SecretKeySpec(hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));

			try {
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
				crypt(args[1], args[2], cipher);
				System.out.format(
						(encrypt ? "Encription" : "Decryption") + " completed. Generated file %s based on file %s.",
						args[2], args[1]);
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Wrong number of arguments are provided. Exepcting 2 or 3, was: " + args.length);
		}

	}

	/**
	 * Metoda koja provjerava ispravnost i broj argumenata komandne linije.
	 * 
	 * @param arguments
	 *            argumenti komandne linije
	 * @return prvi argument
	 */
	private static String checkInputLine(String[] arguments) {
		switch (arguments.length) {
		case 2:
			if (!arguments[0].equals("checksha"))
				throw new IllegalArgumentException("First argument should be \"checksha\", was: " + arguments[0]);
			if (!Files.exists(Paths.get(arguments[1])))
				throw new IllegalArgumentException("No file found in : " + Paths.get(arguments[1]));
			return arguments[0];
		default:
			if (!arguments[0].equals("encrypt") && !arguments[0].equals("decrypt"))
				throw new IllegalArgumentException(
						"First argument should be \"encrypt\" or \"decrypt\", was: " + arguments[0]);
			if (!Files.exists(Paths.get(arguments[1])))
				throw new IllegalArgumentException("No file found in : " + Paths.get(arguments[1]));
			return arguments[0];
		}
	}

	/**
	 * Metoda zadužena za kriptiranje i dekriptiranje datoteka.
	 * 
	 * @param source
	 *            put do izvorne datoteke
	 * @param dest
	 *            put do kriptirane/dekriptirane datoteke
	 * @param cipher
	 */
	static private void crypt( String source, String dest,Cipher cipher) {
		try (FileInputStream in = new FileInputStream(source); FileOutputStream out = new FileOutputStream(dest)) {
			byte[] ibuf = new byte[1024];    //ili 4096
			int len;
			while ((len = in.read(ibuf)) != -1) {
				byte[] obuf = cipher.update(ibuf, 0, len);
				if (obuf != null)
					out.write(obuf);
			}
			byte[] obuf = cipher.doFinal();
			if (obuf != null)
				out.write(obuf);
		} catch (Exception e) {
			System.out.println("Error occured while reading or writing in file.");
			System.exit(1);
		}
	}

	/**
	 * Metoda koja izračunava SHA-256 datotečni digest zadane datoteke.
	 * 
	 * @param source
	 *            put do datoteke
	 * @return SHA-256 datotečni digest zadane datoteke
	 */
	private static String sha(String source) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(Files.readAllBytes(Paths.get(source)));
			return bytetohex(messageDigest.digest());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

}
