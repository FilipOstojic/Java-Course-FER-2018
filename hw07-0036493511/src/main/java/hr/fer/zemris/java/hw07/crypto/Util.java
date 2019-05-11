package hr.fer.zemris.java.hw07.crypto;


/**
 * Razred <code>Util</code> sadrži metode za međusobnu pretvorbu između polja
 * byte-ova i stringa.
 * 
 * @author Filip
 *
 */
public class Util {

	/**
	 * Metoda koja od Stringa radi polje byte-ova. Veličina slova kojoa predstavlja
	 * heksadekdsku znamenku nije važna (zanemaruje veličinu slova), ali je važno da
	 * je paran broj znakova u stringu.
	 * 
	 * @param keyText
	 *            text od kojeg treba napraviti polje byte-ova
	 * @return polje byte-ova
	 * @throws IllegalArgumentException
	 *             -ako text sadrži znakove koji ne pripadaju hexadekadskim
	 *             znamenkama ili ako je neparan broj znakova u stringu.
	 */
	public static byte[] hextobyte(String keyText) {
//		assertNotNull(keyText);
		if (keyText.trim().equals(""))
			return new byte[0];
		int len = keyText.length();
		boolean isHex = isHex(keyText);
		if (len % 2 != 0 || !isHex) {
			throw new IllegalArgumentException("The keyText has odd length or contains illegal characters.");
		}

		byte[] byteArray = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			byteArray[i / 2] = (byte) ((Character.digit(keyText.charAt(i), 16) << 4)
					+ Character.digit(keyText.charAt(i + 1), 16));
		}
		return byteArray;
	}

	/**
	 * Metoda od polja byte-ova radi string koji predstavlja te byte-eve kao
	 * heksadekadske znamenke.
	 * 
	 * @param byteArray
	 *            polje byte-ova
	 * @return string heksadekadskih znamenki
	 */
	public static String bytetohex(byte[] byteArray) {
		StringBuilder sb = new StringBuilder();
		for (byte b : byteArray) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	/**
	 * Metoda koja provjerava sadrži li string samo znakove jednake heksadekadskim
	 * znamenkama.
	 * 
	 * @param text
	 *            tekst koji se provjerava
	 * @return true ako sadrži samo heksadekadske znamenke, inače false
	 */
	private static boolean isHex(String text) {
		for (int i = 0, n = text.length(); i < n; i++) {
			char c = text.charAt(i);
			if (c < '0' || (c > '9' && c < 'A') || (c > 'F' && c < 'a') || c > 'f')
				return false;
		}
		return true;
	}
}
