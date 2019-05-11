package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Razred <code>ForLoopNode</code> nasljeđuje razred <code>Element</code> te
 * predstavlja čvor koji sadrži varijablu te još tri elementa. FOR je tip tokena
 * kojim započinje ovaj čvor. Neprazan je i može imati djecu.
 * 
 * @author Filip
 *
 */
public class ForLoopNode extends Node {
	/**
	 * Varijabla koja čuva naziv prve varijable.
	 */
	private ElementVariable variable;
	/**
	 * Varijabla koja čuva vrijednos od cijelog broja/decimalnog broja/varijable.
	 */
	private Element startExpression;
	/**
	 * Varijabla koja čuva vrijednos od cijelog broja/decimalnog broja/varijable.
	 */
	private Element endExpression;
	/**
	 * Varijabla koja čuva vrijednos od cijelog broja/decimalnog broja/varijable ili
	 * je null.
	 */
	private Element stepExpression;

	/**
	 * Konstruktor koji prima ElementVariable i 3 Element-a.
	 * 
	 * @param variable
	 * @param startExpression
	 * @param endExpression
	 * @param stepExpression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Vraća vrijednost(ime) prve varijable.
	 * 
	 * @return
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Vraća vrijednost drugog elementa.
	 * 
	 * @return
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Vraća vrijednost trećeg elementa.
	 * 
	 * @return
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Vraća vrijednost zadnjeg elementa.
	 * 
	 * @return
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	/**
	 * Metoda prilagođena za ispis.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$ FOR " + variable.asText() + " ");
		if (startExpression instanceof ElementVariable || startExpression instanceof ElementConstantInteger
				|| startExpression instanceof ElementConstantDouble || startExpression instanceof ElementString) {
			sb.append(startExpression.asText() + " ");
		}
		if (endExpression instanceof ElementVariable || endExpression instanceof ElementConstantInteger
				|| endExpression instanceof ElementConstantDouble || endExpression instanceof ElementString) {
			sb.append(endExpression.asText() + " ");
		}
		if (stepExpression != null && (stepExpression instanceof ElementVariable
				|| stepExpression instanceof ElementConstantInteger || stepExpression instanceof ElementConstantDouble
				|| stepExpression instanceof ElementString)) {
			sb.append(stepExpression.asText() + " ");
		}
		sb.append("$}");
		return sb.toString();
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
}
