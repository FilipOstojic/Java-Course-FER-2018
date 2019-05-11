package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Razred <code> Factorial </code> omogućava računanje faktorijela nenegativnog
 * cijelog broja manjeg od 20.
 * 
 * @author Filip Ostojić
 * @version 1.0
 */
public class Factorial {

	/**
	 * metoda koja se poziva prilikom pokretanja programa
	 * 
	 * @param args
	 *            argumenti iz komandne linije (ne očekuju se)
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("Unesite broj > ");
			String input = sc.next();

			if (input.equals("kraj")) {
				System.out.println("Doviđenja.");
				break;
			}

			try {
				int num = Integer.parseInt(input);

				if (num <= 20) {
					long rez = factorial(num);
					System.out.println(num + "! = " + rez);
				} else {
					System.out.println("'" + input + "' nije broj u dozvoljenom rasponu.");
				}
			} catch (Exception e) {
				if (e.getMessage().equals("factorial")) {
					System.out.println("'" + input + "' nije broj u dozvoljenom rasponu.");
				} else {
					System.out.println("'" + input + "' nije cijeli broj.");
				}
			}

		} while (true);

		sc.close();
	}

	/**
	 * Statička metoda koja rekurzivno izračunava faktorjelu cijelog broja.
	 * 
	 * @param num
	 *            nenegativni cijeli broj čiju faktorijelu treba izračunat
	 * 
	 * @return vraća faktorijelu unesenog broja (long)
	 * @throws Exception
	 *             pri unosu negativnog broja
	 * 
	 */
	public static long factorial(int num) throws Exception {
		if (num < 0) {
			throw new Exception("factorial");
		} else if (num == 1 || num == 0) {
			return 1;
		} else {
			return num * factorial(num - 1);
		}
	}
}
