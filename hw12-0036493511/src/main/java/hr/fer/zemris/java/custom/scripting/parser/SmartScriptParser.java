package hr.fer.zemris.java.custom.scripting.parser;

import static hr.fer.zemris.java.custom.scripting.lexer.SmartTokenType.*;

import java.util.Arrays;

import hr.fer.zemris.java.custom.scripting.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Razred <code>SmartScriptParser</code> predstavlja Parser, a njegova je zadaća
 * provjerit ispravnost redosljeda ulaznih tokena. Prilikom nedozvoljenog
 * redosljeda baca <code>SmartScriptParserException</code>.
 * 
 * @author Filip
 *
 */
public class SmartScriptParser {

	/**
	 * Referenca na lekser.
	 */
	private SmartScriptLexer lexer;
	/**
	 * Referenca na stog.
	 */
	private ObjectStack stack;
	/**
	 * Referenca na documentNode.
	 */
	private DocumentNode documentNode;
	/**
	 * Referenca na token.
	 */
	private SmartToken token;
	/**
	 * Referenca na čvor koji je na vrhu stoga.
	 */
	private Node peekNode;

	/**
	 * Konstruktor, prima String vrijednost teksta. Inicijalizira lekser, stog i
	 * documentNode te ga stavlja na stog. Pokreće metodu za parsiranje.
	 * 
	 * @param documentBody
	 */
	public SmartScriptParser(String documentBody) {
		documentNode = new DocumentNode();
		lexer = new SmartScriptLexer(documentBody);
		stack = new ObjectStack();
		stack.push(documentNode);
		peekNode = (Node) stack.peek();
		try {
			this.startParsing(lexer);
		} catch (Exception e) {
			throw new SmartScriptParserException();
		}

	}

	/**
	 * Metoda pokreće parsiranje nad tokenima. Ako je token neprazan stavlja ga na
	 * stog. Gradi stablo tokena čiji je korijen documentNode.
	 * 
	 * @param lexer
	 */
	public void startParsing(SmartScriptLexer lexer) {
		while (true) {
			token = lexer.nextToken();
			if (token.getType() == EOF)
				break;
			if (token.getType() == TEXT) {
				String text = (String) token.getValue();
				TextNode textNode = new TextNode(text);
				peekNode = (Node) stack.peek();
				peekNode.addChildNode(textNode);
			} else if (token.getType() == TAG) {
				token = lexer.nextToken();
				if (token.getType() == FOR) {
					ElementVariable elementVariable = this.getElementVariable();
					Element[] forElements = this.getElementsFor();
					ForLoopNode forNode;
					if (forElements.length == 2) {
						forNode = new ForLoopNode(elementVariable, forElements[0], forElements[1], null);
					} else if (forElements.length == 3) {
						forNode = new ForLoopNode(elementVariable, forElements[0], forElements[1], forElements[2]);
					} else {
						throw new SmartScriptParserException("Number of elements should be 2 or 3.");
					}
					if (stack.peek() instanceof ForLoopNode) {
						peekNode = (ForLoopNode) stack.peek();
						peekNode.addChildNode(forNode);
						stack.push(forNode);
					} else if (stack.peek() instanceof DocumentNode) {
						peekNode = (DocumentNode) stack.peek();
						peekNode.addChildNode(forNode);
						stack.push(forNode);
					} else {
						throw new SmartScriptParserException(
								"Last element pushed on stack should be DocumentNode or ForLoopNode.");
					}
				} else if (token.getType() == END) {
					token = lexer.nextToken();
					if (token.getType() == CLOSINGTAG) {
						if (stack.size() > 1) {
							stack.pop();
						} else {
							throw new SmartScriptParserException(
									"You cannot pop if stack is empty or have only one element.");
						}
					} else {
						throw new SmartScriptParserException("After END should come CLOSINGTAG.");
					}
				} else if (token.getType() == SPECIAL) {
					Element[] echoElements = this.getElementsEcho();
					EchoNode echoNode = new EchoNode(echoElements);
					if (stack.peek() instanceof ForLoopNode) {
						peekNode = (ForLoopNode) stack.peek();
						peekNode.addChildNode(echoNode);
					} else if (stack.peek() instanceof DocumentNode) {
						peekNode = (DocumentNode) stack.peek();
						peekNode.addChildNode(echoNode);
					} else {
						throw new SmartScriptParserException(
								"Last element pushed on stack should be DocumentNode or ForLoopNode.");
					}
				} else {
					throw new SmartScriptParserException("Expecting token type FOR, END or SPECIAL.");
				}
			} else {
				throw new SmartScriptParserException("Expecting token type TEXT or TAG.");
			}
		}
	}

	/**
	 * Metoda vraća polje elemenata od 2 ili 3 elementa koje FOR smije primiti.
	 * (ElementString, ElementVariable, ElementConstantInteger,
	 * ElementConstantDouble).
	 * 
	 * @return polje elemenata
	 */
	private Element[] getElementsFor() {
		Element[] elements = this.getElementsEcho();

		if (!(elements[0] instanceof ElementFunction) && !(elements[1] instanceof ElementFunction)
				&& !(elements[0] instanceof ElementOperator) && !(elements[1] instanceof ElementOperator)) {
			if (elements[2] == null) {
				Element[] elementsFor = new Element[2];
				elementsFor = Arrays.copyOfRange(elements, 0, 2);
				return elementsFor;
			} else if (!(elements[2] instanceof ElementFunction) && !(elements[2] instanceof ElementOperator)
					&& elements[3] == null) {
				Element[] elementsFor = new Element[3];
				elementsFor = Arrays.copyOfRange(elements, 0, 3);
				return elementsFor;
			} else {
				throw new SmartScriptParserException(
						"Invalid arguments: FUNCTION or OPERATOR or wrong number of orguments.");
			}
		} else {
			throw new SmartScriptParserException("Invalid arguments: FUNCTION or OPERATOR.");
		}
	}

	/**
	 * Metoda vraća polje elemenata od 2 ili 3 elementa koje FOR smije primiti.
	 * (ElementString, ElementVariable, ElementConstantInteger,
	 * ElementConstantDouble, ElementOperator, ElementFunction).
	 * 
	 * @return polje elemenata
	 */
	private Element[] getElementsEcho() {
		token = lexer.nextToken();
		Element[] elements = new Element[100];
		int i = 0;
		while (token.getType() != CLOSINGTAG) {
			if (token.getType() == VARIABLE) {
				elements[i] = new ElementVariable((String) token.getValue());
				i++;
			} else if (token.getType() == STRING) {
				elements[i] = new ElementString((String) token.getValue());
				i++;
			} else if (token.getType() == DOUBLE) {
				elements[i] = new ElementConstantDouble((Double) token.getValue());
				i++;
			} else if (token.getType() == INTEGER) {
				elements[i] = new ElementConstantInteger((Integer) token.getValue());
				i++;
			} else if (token.getType() == FUNCTION) {
				elements[i] = new ElementFunction((String) token.getValue());
				i++;
			} else if (token.getType() == SmartTokenType.OPERATOR) {
				elements[i] = new ElementOperator((String) token.getValue());
				i++;
			} else {
				throw new SmartScriptParserException("Token types shouldn't be EOF, FOR, END.");
			}
			token = lexer.nextToken();
		}
		Element[] elementEcho = new Element[i+1];
		elementEcho = Arrays.copyOfRange(elements, 0, i+1);
		return elementEcho;
	}

	/**
	 * Ako je sljedeći token varijabla vraća ju, inače baca iznimku.
	 * 
	 * @return ElementVariable
	 */
	private ElementVariable getElementVariable() {
		token = lexer.nextToken();
		if (token.getType() == VARIABLE) {
			ElementVariable elemVar = new ElementVariable((String) token.getValue());
			return elemVar;
		} else {
			throw new SmartScriptParserException("In FOR tag next token type should be VARIABLE.");
		}
	}

	/**
	 * Metoda vraća korijen stabla.
	 * 
	 * @return
	 */
	public DocumentNode getDocumentNode() {
		return this.documentNode;
	}

}
