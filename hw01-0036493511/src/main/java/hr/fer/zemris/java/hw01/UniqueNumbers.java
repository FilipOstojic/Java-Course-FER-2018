package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Razred <code> UniqueNumbers</code> omogućava unos cijelih brojeva u binarno
 * sortirano stablo te se ponaša kao skup: prihvaća samo različite brojeve. Unos
 * završava kada korisnik unese riječ "kraj" te sljedi ispis unesenih brojeva po
 * veličini (uzlazno i silazno).
 * 
 * 
 * @author Filip Ostojić
 * @version 1.0
 */
public class UniqueNumbers {

	/**
	 * Metoda koja se poziva kada se pokrene program.
	 * 
	 * @param args
	 *            argumenti komandne linije
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		TreeNode head = null;

		while (true) {
			System.out.println("Unesite broj > ");
			String input = sc.next();

			if (input.equals("kraj")) {
				System.out.print("Ispis od najmanjeg: ");
				printInorder(head);
				System.out.println("");
				System.out.print("Ispis od najvećeg: ");
				printInorderReversed(head);
				break;
			} else {
				try {
					int element = Integer.parseInt(input);
					if (containsValue(head, element)) {
						System.out.println("Broj već postoji. Preskačem.");
					} else {
						head = addNode(head, element);
						System.out.println("Dodano.");
					}
				} catch (Exception e) {
					System.out.println("'" + input + "' nije cijeli broj.");
				}
			}
		}
		sc.close();
	}

	/**
	 * Statička metoda,koja rekurzijom na standardni izlaz ispisuje brojeve stabla
	 * od najvećeg do najmanjeg
	 * 
	 * @param head
	 *            korijen stabla (TreeNode)
	 */
	public static void printInorderReversed(TreeNode head) {
		if (head != null) {
			printInorderReversed(head.right);
			System.out.print(head.value + " ");
			printInorderReversed(head.left);
		}
	}

	/**
	 * Statička metoda,koja rekurzijom na standardni izlaz ispisuje brojeve stabla
	 * od najmanjeg do najvećeg
	 * 
	 * @param head
	 *            korijen stabla (TreeNode)
	 */
	public static void printInorder(TreeNode head) {
		if (head != null) {
			printInorder(head.left);
			System.out.print(head.value + " ");
			printInorder(head.right);
		}
	}

	/**
	 * Statička metoda koja rekurzijom dodaje novi čvor u sortirano binarno stablo.
	 * 
	 * @param head
	 *            korijen stabla (TreeNode)
	 * @param num
	 *            cijeli broj koji se želi unijeti (int)
	 * @return vraća korijen stabla i podstabla (TreeNode)
	 */
	public static TreeNode addNode(TreeNode head, int num) {
		TreeNode tmp = head;
		if (tmp == null)
			tmp = new TreeNode(num);
		else {
			if (tmp.value < num)
				tmp.right = addNode(head.right, num);
			else
				tmp.left = addNode(head.left, num);
		}
		return tmp;
	}

	/**
	 * Rekurzivna statička metoda koja vraća veličinu stabla.
	 * 
	 * @param head
	 *            korijen stabla (TreeNode)
	 * @return veličina stabla (int)
	 */
	public static int treeSize(TreeNode head) {
		if (head == null) {
			return 0;
		} else {
			return treeSize(head.left) + 1 + treeSize(head.right);
		}
	}

	/**
	 * Statička metoda koja pretražuje element u stablu.
	 * 
	 * @param head
	 *            korijen stabla (TreeNode)
	 * @param element
	 *            broj koji provjeravamo (int)
	 * @return vraća 'true' ako element postoji u stablu te 'false' ako element ne
	 *         postoji
	 */
	public static boolean containsValue(TreeNode head, int element) {
		if (head == null) {
			return false;
		} else if (head.value == element) {
			return true;
		} else if (head.value > element) {
			return containsValue(head.left, element);
		} else {
			return containsValue(head.right, element);
		}
	}
}

/**
 * Pomoćni razred kojim je definiran čvor stabla. Sadrži tri članske varijable:
 * left i right, te value koji je tipa int.
 * 
 * @author Filip Ostojić
 *
 */
class TreeNode {
	int value;
	TreeNode left;
	TreeNode right;

	public TreeNode(int value) {
		this.value = value;
		this.left = null;
		this.right = null;
	}
}