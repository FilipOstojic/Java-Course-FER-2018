package hr.fer.zemris.java.gui.calc;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import static java.lang.Math.E;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * Razred <code>Calculator</code> predstavlja grafičko korisničko sučelje
 * implementacije modela kalkulatora {@link CalcModelImpl}, tj. jednostavni
 * kalkulator.
 * 
 * @author Filip
 *
 */
public class Calculator extends JFrame {

	/** Serijska vrijednost UID. */
	private static final long serialVersionUID = 1L;
	/** Pretpostavljna x lokacija kalkulatora. */
	private static int DEFAULT_X = 100;
	/** Pretpostavljna y lokacija kalkulatora. */
	private static int DEFAULT_Y = 100;
	/** Pretpostavljna širina kalkulatora. */
	private static int DEFAULT_WIDTH = 850;
	/** Pretpostavljna visina kalkulatora. */
	private static int DEFAULT_HEIGHT = 620;
	/** Stog. */
	private Stack<Double> stack = new Stack<>();
	/** Glavni panel. */
	private JPanel panel;
	/** {@link CalcModelImpl} */
	private CalcModelImpl model;
	/** Zastavica koja govori je li uključen inver ili nije */
	private boolean isInvers;

	/**
	 * Konstruktor, bez argumenata.
	 */
	public Calculator() {
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		setLocation(DEFAULT_X, DEFAULT_Y);
		setTitle("Calculator");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		initGUI();
	}
	
	/**
	 * Inicijalizacija grafičkog korisničkog sučelja.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		panel = new JPanel(new CalcLayout(5));
		cp.add(panel);

		JLabel display = createDisplay();
		panel.add(display, "1,1");

		model = new CalcModelImpl();
		model.addCalcValueListener(model1 -> display.setText(model1.toString()));

		initDigitButtons();
		initBinaryOperators();
		initUnaryButtons();
		initStackButtons();
		initOtherButtons();
		createInvers();
	}

	/**
	 * Metoda inicijalizira gumbe za rad sa stogom.
	 */
	private void initStackButtons() {
		panel.add(stackButton("push"), "3,7");
		panel.add(stackButton("pop"), "4,7");
	}

	/**
	 * Metoda inicijalizira ostale gumbe.
	 */
	private void initOtherButtons() {
		panel.add(dotButton("."), "5,5");
		panel.add(swapButton("+/-"), "5,4");
		panel.add(equalsButton("="), "1,6");
		panel.add(resButton("res"), "2,7");
		panel.add(clrBbutton("clr"), "1,7");
	}

	/**
	 * Metoda inicijalizira gumbe koji vrše operacije nad jednim operandom.
	 */
	private void initUnaryButtons() {
		panel.add(unaryButton("sin", Math::sin, Math::asin), "2,2");
		panel.add(unaryButton("cos", Math::cos, Math::acos), "3,2");
		panel.add(unaryButton("tan", Math::tan, Math::atan), "4,2");
		panel.add(unaryButton("ctg", n -> 1 / Math.tan(n), n -> 1 / Math.atan(n)), "5,2");
		panel.add(unaryButton("1/x", n -> 1 / n, n -> 1 / n), "2,1");
		panel.add(unaryButton("ln", Math::log, n -> Math.pow(E, n)), "4,1");
		panel.add(unaryButton("log", Math::log10, n -> Math.pow(10, n)), "3,1");
	}

	/**
	 * Metoda inicijalizira gumbe koji vrše operacije nad dvama operandima.
	 */
	private void initBinaryOperators() {
		panel.add(binaryButton("+", (n1, n2) -> n1 + n2), "5,6");
		panel.add(binaryButton("-", (n1, n2) -> n1 - n2), "4,6");
		panel.add(binaryButton("*", (n1, n2) -> n1 * n2), "3,6");
		panel.add(binaryButton("/", (n1, n2) -> n1 / n2), "2,6");
		panel.add(binaryButton("x^n", (n1, n2) -> Math.pow(n1, n2), (n1, n2) -> Math.pow(n1, 1 / n2)), "5,1");
	}

	/**
	 * Metoda inicijalizira gumbe koji predstavljaju znamenke.
	 */
	private void initDigitButtons() {
		panel.add(digitButton("1"), "4,3");
		panel.add(digitButton("2"), "4,4");
		panel.add(digitButton("3"), "4,5");
		panel.add(digitButton("4"), "3,3");
		panel.add(digitButton("5"), "3,4");
		panel.add(digitButton("6"), "3,5");
		panel.add(digitButton("7"), "2,3");
		panel.add(digitButton("8"), "2,4");
		panel.add(digitButton("9"), "2,5");
		panel.add(digitButton("0"), "5,3");
	}

	/**
	 * Metoda stvara zaslon kalkulatora.
	 */
	private JLabel createDisplay() {
		JLabel display = new JLabel("0");
		display.setBackground(Color.decode("#ffd20c"));
		display.setHorizontalAlignment(JTextField.RIGHT);
		display.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		Font font = new Font("Arial", Font.BOLD, 25);
		display.setFont(font);
		display.setForeground(Color.decode("#191970"));
		display.setOpaque(true);
		return display;
	}

	/**
	 * Metoda kreira checkBox za odabir inverza.
	 */
	private void createInvers() {
		Checkbox invers = new Checkbox("inv", false);
		Font font = new Font("Arial", Font.PLAIN, 25);
		invers.setFont(font);
		invers.addItemListener(e -> {
			isInvers = !isInvers;
		});
		invers.setBackground(Color.decode("#2D95B8"));
		panel.add(invers, "5,7");
	}

	/**
	 * Metoda stvara gumb za rad nad stogom.
	 * 
	 * @param name
	 *            ime gumba
	 * @return gumb
	 */
	private JButton stackButton(String name) {
		JButton button = new JButton(name);
		initializeButton(button);
		button.addActionListener(e -> {
			try {
				if (name.equals("push")) {
					stack.push(model.getValue());
				} else {
					double popValue = stack.pop();
					model.clearAll();
					model.setValue(popValue);
				}
			} catch (Exception ex) {
				createMessageDialog("Stack is empty!");
			}
		});
		return button;
	}

	/**
	 * Metoda stavara gumb jednako.
	 * 
	 * @param name
	 *            ime gumba
	 * @return gumb
	 */
	private JButton equalsButton(String name) {
		JButton button = new JButton(name);
		initializeButton(button);
		button.addActionListener(e -> {
			try {
				if (!model.isActiveOperandSet() && model.getPendingBinaryOperation()==null) return;
				model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
				model.clearActiveOperand();
			} catch (Exception ex) {
				createMessageDialog(ex.getMessage());
			}
		});
		return button;
	}

	/**
	 * Metoda stavara clear gumb.
	 * 
	 * @param name
	 *            ime gumba
	 * @return gumb
	 */
	private JButton clrBbutton(String name) {
		JButton button = new JButton(name);
		initializeButton(button);
		button.addActionListener(e -> model.clear());
		return button;
	}

	/**
	 * Metoda stavra reset gumb.
	 * 
	 * @param name
	 *            ime gumba
	 * @return gumb
	 */
	private JButton resButton(String name) {
		JButton button = new JButton(name);
		initializeButton(button);
		button.addActionListener(e -> model.clearAll());
		return button;
	}

	/**
	 * Metoda stvara gumb za računske operacije nad jednim operandom.
	 * 
	 * @param name
	 *            ime gumba
	 * @param op
	 *            računska operacija ako nije uključen inverz
	 * @param opInv
	 *            računska operacija ako je uključen inverz
	 * @return gumb
	 */
	private JButton unaryButton(String name, DoubleUnaryOperator op, DoubleUnaryOperator opInv) {
		JButton button = new JButton(name);
		initializeButton(button);
		button.addActionListener(e -> {
			try {
				if (!model.isActiveOperandSet()) { //DODANO
					model.setActiveOperand(model.getValue()); 
				} 
				double val = isInvers == true ? opInv.applyAsDouble(model.getValue()) : op.applyAsDouble(model.getValue());
				model.setValue(val);
			} catch (Exception ex) {
				createMessageDialog(ex.getMessage());
			}
		});
		return button;
	}

	/**
	 * Metoda stvara gumb za računske operacije nad dvama operandima.
	 * 
	 * @param name
	 *            ime gumba
	 * @param op
	 *            računska operacija ako nije uključen inverz
	 * @param opInv
	 *            računska operacija ako je uključen inverz
	 * @return gumb
	 */
	private JButton binaryButton(String name, DoubleBinaryOperator op, DoubleBinaryOperator opInv) {
		JButton button = new JButton(name);
		initializeButton(button);
		button.addActionListener(e -> {
			try {
				DoubleBinaryOperator realOp = isInvers == true ? opInv : op;
				if (model.getPendingBinaryOperation() == null && !model.isActiveOperandSet()) {
					model.setActiveOperand(model.getValue());
					model.clear();
					model.setPendingBinaryOperation(realOp);
				} else if (model.getPendingBinaryOperation() != null && model.isActiveOperandSet()) {
					double newValue = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
					model.setActiveOperand(newValue);
					model.clear();
					model.setPendingBinaryOperation(realOp);
				} else if (model.getPendingBinaryOperation() != null && !model.isActiveOperandSet()) {
					model.setActiveOperand(model.getValue());
					model.clear();
					model.setPendingBinaryOperation(realOp);
				}
			} catch (Exception ex) {
				createMessageDialog("Invalid command!");
			}
		});
		return button;
	}

	/**
	 * Metoda stvara gumb za računske operacije nad dvama operandima.
	 * 
	 * @param name
	 *            ime gumba
	 * @param operator
	 *            računska operacija
	 * @return gumb
	 */
	private JButton binaryButton(String name, DoubleBinaryOperator operator) {
		JButton button = new JButton(name);
		initializeButton(button);
		button.addActionListener(e -> {
			if (model.getPendingBinaryOperation() == null && !model.isActiveOperandSet()) {
				model.setActiveOperand(model.getValue());
				model.clear();
				model.setPendingBinaryOperation(operator);
			} else if (model.getPendingBinaryOperation() != null && model.isActiveOperandSet()) {
				double newValue = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
				model.setActiveOperand(newValue);
				model.clear();
				model.setPendingBinaryOperation(operator);
			} else if (model.getPendingBinaryOperation() != null && !model.isActiveOperandSet()) {
				model.setActiveOperand(model.getValue());
				model.clear();
				model.setPendingBinaryOperation(operator);
			}
		});
		return button;
	}

	/**
	 * Metoda stvara gumb za zamjenu predznaka.
	 * 
	 * @param name
	 *            ime gumba
	 * @return gumb
	 */
	private JButton swapButton(String name) {
		JButton button = new JButton(name);
		initializeButton(button);
		button.addActionListener(e -> model.swapSign());
		return button;
	}

	/**
	 * Metoda stvara gumb koji predstavlja točku.
	 * 
	 * @param name
	 *            ime gumba
	 * @return gumb
	 */
	private JButton dotButton(String name) {
		JButton button = new JButton(name);
		initializeButton(button);
		button.addActionListener(e -> model.insertDecimalPoint());
		return button;
	}

	/**
	 * Metoda stvara gumb koji predstavlja znamenku.
	 * 
	 * @param name
	 *            ime gumba
	 * @return gumb
	 */
	private JButton digitButton(String name) {
		JButton button = new JButton(name);
		initializeButton(button);
		button.addActionListener(e -> model.insertDigit(Integer.parseInt(button.getActionCommand())));
		return button;
	}

	/**
	 * Metoda inicijalizira gumb (boja, fonr itd.).
	 * 
	 * @param button
	 *            gumb
	 */
	private void initializeButton(JButton button) {
		button.setBackground(Color.decode("#2D95B8"));
		Font font = new Font("Arial", Font.PLAIN, 25);
		button.setFont(font);
		button.setActionCommand(button.getName());
		button.setFocusPainted(false);
	}

	/**
	 * Metoda stvara novi prozor s opisom pogreške.
	 * 
	 * @param message
	 *            opis pogreške
	 */
	private void createMessageDialog(String message) {
		JOptionPane.showMessageDialog(this, "Invalid command!", "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Metoda koja se poziva kada se pokrene program.
	 * 
	 * @param args
	 *            argumenti komandne linije, ne očekuju se
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Calculator();
			frame.pack();
			frame.setVisible(true);
		});
	}

}
