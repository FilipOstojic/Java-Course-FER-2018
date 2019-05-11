package hr.fer.zemris.java.hw01;

import java.text.NumberFormat;
import java.util.Scanner;

/**
 * Razred <code>Rectangle</code> prima 2 pozitivna realna broja (preko komandne
 * linije ili preko standardnog ulaza) kao visinu i širinu pravokutnika za koje
 * računa površinu i opseg.
 * 
 * @author Filip Ostojić
 * @version 1.0
 */
public class Rectangle {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * 
	 * @param args
	 *            argumenti iz komandne linije (očekuju se unos 2 pozitivna realna
	 *            broja ili bez argumenata)
	 */
	public static void main(String[] args) {

		if (args.length == 2) {
			try {
				double width = NumberFormat.getInstance().parse(args[0]).doubleValue();
				double height = NumberFormat.getInstance().parse(args[1]).doubleValue();

				if (width > 0 && height > 0) {
					ispis(width, height);
				} else {
					System.out.println("Brojevi ne smiju biti negativni.");
				}

			} catch (Exception e) {
				System.out.println("Argumenti naredbenog retka nisu brojevi.");
			}
		}

		else if (args.length == 0) {

			double width = provjera("širinu");
			double height = provjera("visinu");

			ispis(width, height);
		}

		else {
			System.out.println("Pogrešan broj argumenata.");
		}
	}

	/**
	 * Metoda ispisuje na standardni izlaz poruku s izracunatim opsegom te povrsinom
	 * pravokutnika ovisno o sirini i visini. Argumenti su pozitivni realni brojevi.
	 * 
	 * @param width
	 * @param height
	 */
	public static void ispis(double width, double height) {
		double area = width * height;
		double perimeter = 2 * (width + height);
		System.out.format("Pravokutnik širine %.1f i visine %.1f ima " + "površinu %.1f te opseg %.1f.", width, height,
				area, perimeter);
	}

	/**
	 * Metoda prima String potreban za postavljanje upita, tj. onoga što pitamo
	 * korisnika da unese. Metoda provjerava korisnikov unos i ponavlja se sve dok
	 * se ne upiše pozitivan realan broj.
	 * 
	 * @param string
	 * @return pozitivan realan broj
	 */
	public static double provjera(String string) {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("Unesite " + string + " > ");
			String input = sc.next();

			try {
				double num = NumberFormat.getInstance().parse(input).doubleValue();

				if (num > 0) {
					return num;
				} else {
					System.out.println("Unijeli ste negativnu vrijednost ili nulu.");
				}
			} catch (Exception e) {
				System.out.println("'" + input + "' se ne može protumačiti kao broj.");
				continue;
			} finally {
				if (input.equals("visinu")) {
					sc.close();
				}
			}
		}
	}
}
