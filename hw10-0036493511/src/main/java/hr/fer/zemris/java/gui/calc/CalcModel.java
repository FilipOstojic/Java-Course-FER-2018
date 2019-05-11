package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * Sučelje <code>CalcModel</code> definira metode koje primjerci razreda koji
 * implementiraju sučelje moraju implementirati u kalkulator.
 * 
 * @author Filip
 *
 */
public interface CalcModel {
	/**
	 * Metoda dodaje promatrač u listu promatrača.
	 * 
	 * @param l
	 *            promatrač
	 */
	void addCalcValueListener(CalcValueListener l);

	/**
	 * Metoda uklanja promatrača iz liste promatrača.
	 * 
	 * @param l
	 *            promatrač
	 */
	void removeCalcValueListener(CalcValueListener l);

	String toString();

	/**
	 * Metoda vraća double reprezentaciju stringa sa zaslona kalkulatora.
	 * 
	 * @return broj na zaslonu kalkulatora
	 */
	double getValue();

	/**
	 * Metoda postavlja broj na zaslon kalkulatora.
	 * 
	 * @param value
	 *            broj
	 */
	void setValue(double value);

	/**
	 * Metoda briše broj na zaslonu.
	 */
	void clear();

	/**
	 * Metoda briše zaslon i zapamćene vrijednosti operanda i operatora.
	 */
	void clearAll();

	/**
	 * Metoda mijenja predznak broja na zaslonu.
	 */
	void swapSign();

	/**
	 * Metoda dodaje decimalnu točku.
	 */
	void insertDecimalPoint();

	/**
	 * Metoda dodaje znamenku na zaslon kalkulatora.
	 * 
	 * @param digit
	 *            znamenka
	 */
	void insertDigit(int digit);

	/**
	 * Metoda provjerava postoji li aktivni operand.
	 * 
	 * @return true ako postoji, inače false
	 */
	boolean isActiveOperandSet();

	/**
	 * Metoda vraća aktivni operand ili baca iznimku.
	 * 
	 * @throws IllegalStateException
	 *             ako aktivni operand ne postoji
	 * @return aktivni operand
	 */
	double getActiveOperand();

	/**
	 * Metoda postavlja aktivni operand.
	 * 
	 * @param activeOperand
	 *            aktivni operand
	 */
	void setActiveOperand(double activeOperand);

	/**
	 * Metoda briše aktivni operand.
	 */
	void clearActiveOperand();

	/**
	 * Metoda vraća zadnji operator.
	 * 
	 * @return zadnji operator
	 */
	DoubleBinaryOperator getPendingBinaryOperation();

	/**
	 * Metoda postavlja operator.
	 * 
	 * @param op
	 *            operator
	 */
	void setPendingBinaryOperation(DoubleBinaryOperator op);
}