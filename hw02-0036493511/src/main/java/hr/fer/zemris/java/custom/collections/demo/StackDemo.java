package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Razred <code>StackDemo</code> prima 1 argument preko komandne linije u postfixnoj reprezentaciji.
 * Izračunava rezultat zadanih računskih operacija cijelih brojeva.
 * Podržane operacije: +, -, *, /, %.
 * @author Filip Ostojić
 *
 */
public class StackDemo {
	
	/**
	 * Metoda main, izvodi se prilikom pokretanja programa.
	 * @param args argumenti komandne linije
	 */
	public static void main(String[] args) {
		try {
			if(args.length!=1) {
				throw new IllegalArgumentException("The number of provided arguments is not 1!");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println("The program will close.");
			System.exit(1);
		}
		
		ObjectStack stack = new ObjectStack();
		String[] array = args[0].trim().split("\\s+");               
		
		stack.clear();
		for (String string : array) {
			if(isOperand(string)) {
				stack.push(Integer.parseInt(string));
				continue;
			} else {
				int secondNumber = (int) stack.pop();
				int firstNumber = (int) stack.pop();
				
				if(string.equals("+")) {
					stack.push(firstNumber + secondNumber);
				} else if(string.equals("-")) {
					stack.push(firstNumber - secondNumber);
				} else if(string.equals("/")) {
					if (secondNumber == 0) {
						try {
							throw new IllegalArgumentException("Dividing with 0 is not allowed.");
						} catch (IllegalArgumentException e) {
							System.err.println(e.getMessage());
							System.err.println("The program will close.");
							System.exit(1);						}
					} else {
						stack.push(firstNumber / secondNumber);
					}
				} else if(string.equals("*")) {
					stack.push(firstNumber * secondNumber);
				} else if(string.equals("%")) {
					stack.push(firstNumber % secondNumber);
				} else {
					throw new IllegalArgumentException("Unsupported value: "+string+".");
				}
			}
		}
		if(stack.size()!=1) {
			System.err.println("The stack is incorrectly implemented. ");
		} else {
			System.out.println(stack.pop());
		}
	}
	
	/**
	 * Metoda provjerava je li uneseni string cijeli broj ili nije.
	 * @param string
	 * @return true ako je uneseni String cijeli broj, inače false
	 */
	public static boolean isOperand(String string) {  
		try {  
			Integer.parseInt(string);
		} catch(NumberFormatException nfe) {  
			return false;  
		}  
		return true;  
	}
}
