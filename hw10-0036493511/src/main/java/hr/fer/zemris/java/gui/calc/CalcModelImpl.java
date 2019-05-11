package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.layouts.CalcLayoutException;

/**
 * Razred <code>CalcModeImpl</code> predstavlja implementaciju modela
 * kalkulatora {@link CalcModel}.
 * 
 * @author Filip
 *
 */
public class CalcModelImpl implements CalcModel {
	/** Zapis na zaslonu kalkulatora. **/
	private String input;
	/** Lista promatrača. **/
	private List<CalcValueListener> listeners;
	/** Aktivni operand. **/
	private double activeOperand;
	/** Zadnji operator. **/
	private DoubleBinaryOperator pendingOperation;
	/** Konstanta za točku. **/
	private static String DOT = ".";
	/** Konstanta za nulu. **/
	private static String ZERO = "0";
	/** Konstanta za minus i nulu. **/
	private static String NEGATIVE_ZERO = "-0";

	/**
	 * Konstruktor, bez argumenata.
	 */
	public CalcModelImpl() {
		listeners = new ArrayList<>();
		activeOperand = Double.NaN;
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}

	@Override
	public double getValue() {
		return input == null ? 0.0 : Double.parseDouble(input);
	}

	@Override
	public void setValue(double value) {
		if (!Double.isNaN(value) && Double.isFinite(value)) {
			input = Double.toString(value);
			notifyAllListeners();
			return;
		}
		throw new CalcLayoutException("Operation is not defined for that argument! ");
	}

	@Override
	public void clear() {
		input = null;
		notifyAllListeners();
	}

	@Override
	public void clearAll() {
		clear();
		clearActiveOperand();
		pendingOperation = null;
		notifyAllListeners();
	}

	@Override
	public void swapSign() {
		if (input != null) {
			if (!input.startsWith("-")) {
				input = "-" + input;
			} else {
				input = input.substring(1, input.length());
			}
			notifyAllListeners();
		}
	}

	@Override
	public void insertDecimalPoint() {
		if (input == null) {
			input = ZERO + DOT;
		} else if (!input.contains(DOT)) {
			input = input + DOT;
		}
		notifyAllListeners();
	}

	@Override
	public void insertDigit(int digit) {
		if (input == null) {
			input = Integer.toString(digit);
		} else if ((input.equals(ZERO) || input.equals(NEGATIVE_ZERO)) && digit == 0) {
			return;
		} else {
			if (input.equals(ZERO) || input.equals(NEGATIVE_ZERO)) {
				input = input.replaceFirst(ZERO, Integer.toString(digit));
				return;
			}
			input += Integer.toString(digit);
		}
		if (Double.parseDouble(input) > Double.MAX_VALUE) {
			input = input.substring(0, input.length() - 1);
		}
		notifyAllListeners();
	}

	@Override
	public boolean isActiveOperandSet() {
		if (Double.isNaN(activeOperand))
			return false;
		return true;
	}

	@Override
	public double getActiveOperand() {
		if (!isActiveOperandSet())
			throw new IllegalStateException("Active operand is not defined.");
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		notifyAllListeners();
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = Double.NaN;
		notifyAllListeners();
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
	}

	/**
	 * Metoda obavještava sve registrirane promatrače da je došlo do promjene.
	 */
	private void notifyAllListeners() {
		for (CalcValueListener listener : listeners) {
			listener.valueChanged(this);
		}
	}

	@Override
	public String toString() {
		if (input == null)
			return ZERO;
		if (input.startsWith("-") && Double.parseDouble(input) == 0.0)
			return NEGATIVE_ZERO;
		if (Double.parseDouble(input) == 0.0 && !input.contains(DOT))
			return ZERO;
		return input;
	}
}
