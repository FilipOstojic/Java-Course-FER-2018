package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Razred <code>SmartScriptEngine</code>predstavlja glavni razred za oobilazak
 * čvorova i njegovih elemenata, nastalih parsiranjem te interpretiranje
 * njihovih funkcija.
 * 
 * @author Filip
 *
 */
public class SmartScriptEngine {
	/**
	 * Korijen svih čvorova.
	 */
	private DocumentNode documentNode;
	/**
	 * {@link RequestContext}
	 */
	private RequestContext requestContext;
	/**
	 * {@link Multistack}
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	/**
	 * Razred koji implementira oblikovni obrazac Visitor (sučelje
	 * {@link INodeVisitor}).
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				throw new RuntimeException();
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String variableName = node.getVariable().getName();
			int start = ((ElementConstantInteger) node.getStartExpression()).getValue();
			int end = ((ElementConstantInteger) node.getEndExpression()).getValue();
			int step = ((ElementConstantInteger) node.getStepExpression()).getValue();
			ValueWrapper first;
			int second;
			multistack.push(variableName, new ValueWrapper(start));
			if (multistack.peek(variableName).numCompare(end) <= 0) {
				first = multistack.peek(variableName);
				second = end;
			} else {
				first = new ValueWrapper(end);
				second = (int) multistack.peek(variableName).getValue();
			}

			while (first.numCompare(second) <= 0) {
				for (int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(visitor);
				}
				multistack.peek(variableName).add(step);
				first = multistack.peek(variableName);
			}

			multistack.pop(variableName);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Element[] elements = node.getElements();
			Stack<Object> stack = new Stack<>();

			for (Element elem : elements) {
				executeJob(stack, elem);
			}
			requestContextWrite(stack);
		}

		/**
		 * Ispisuje elemente koji su ostali u pomoćnom stogu. Ispis ide od elemenata s
		 * dna stoga prema vrhu stoga.
		 * 
		 * @param stack
		 *            pomoćni stog
		 */
		private void requestContextWrite(Stack<Object> stack) {
			List<Object> list = stack;

			for (int i = 0; i < list.size(); i++) {
				try {
					requestContext.write(list.get(i).toString());
				} catch (IOException e) {
					System.out.println("Problem occured while writing elements from stack.");
				}
			}
		}

		/**
		 * Pomoćna metoda koja ovisno o vrsti {@link Element}-a izvršava njegov posao.
		 * 
		 * @param stack
		 *            pomoćni sotg
		 * @param element
		 *            primjerak razreda {@link Element}
		 */
		private void executeJob(Stack<Object> stack, Element element) {
			if (element instanceof ElementConstantInteger) {
				stack.push(((ElementConstantInteger) element).getValue());
			} else if (element instanceof ElementConstantDouble) {
				stack.push(((ElementConstantDouble) element).getValue());
			} else if (element instanceof ElementString) {
				stack.push(((ElementString) element).getValue());
			} else if (element instanceof ElementVariable) {
				ValueWrapper variable = multistack.peek(((ElementVariable) element).getName());
				stack.push(variable.getValue());
			} else if (element instanceof ElementOperator) {
				calculateOperation(stack, (ElementOperator) element);
			} else if (element instanceof ElementFunction) {
				calculateFunction(stack, (ElementFunction) element);
			}
		}

		/**
		 * Metoda koja ovisno o imenu primjerku razreda {@link ElementFunction} definira
		 * njegovu zadaću, tj izvršava funkciju.
		 * 
		 * @param stack
		 *            pomoćni stog
		 * @param element
		 *            primjerak razreda {@link ElementFunction}
		 */
		private void calculateFunction(Stack<Object> stack, ElementFunction element) {
			switch (element.getName().substring(1)) {
			case "sin":
				stack.push(Math.sin(Math.toRadians(Double.parseDouble(stack.pop().toString()))));
				break;
			case "decfmt":
				DecimalFormat decimalFormat = new DecimalFormat((String) stack.pop());
				ValueWrapper wrapper = new ValueWrapper(stack.pop());
				String result = decimalFormat.format(Double.parseDouble(wrapper.getValue().toString()));
				stack.push(result);
				break;
			case "dup":
				stack.push(stack.peek());
				break;
			case "swap":
				Object top = stack.pop();
				Object second = stack.pop();
				stack.push(second);
				stack.push(top);
				break;
			case "setMimeType":
				requestContext.setMimeType((String) stack.pop());
				break;
			case "paramGet":
				XGetParam("param", stack);
				break;
			case "pparamGet":
				XGetParam("pparam", stack);
				break;
			case "tparamGet":
				XGetParam("tparam", stack);
				break;
			case "pparamSet":
				requestContext.setPersistentParameter(stack.pop().toString(), stack.pop().toString());
				break;
			case "tparamSet":
				requestContext.setTemporaryParameter(stack.pop().toString(), stack.pop().toString());
				break;
			case "pparamDel":
				requestContext.removePersistentParameter(stack.pop().toString());
				break;
			case "tparamDel":
				requestContext.removeTemporaryParameter(stack.pop().toString());
				break;
			}
		}

		/**
		 * Metoda dohvaća vrijednost iz neke od mapa: parameters, persistantParameters,
		 * temporaryParameters i stavlja ju na stog.
		 * 
		 * @param name
		 *            ime mape iz koje se dohvaća element
		 * @param stack
		 *            pomoćni stog
		 */
		private void XGetParam(String name, Stack<Object> stack) {
			Object defaultValue = stack.pop();
			String value = null;
			switch (name) {
			case "param":
				value = requestContext.getParameter((String) stack.pop());
				break;
			case "pparam":
				value = requestContext.getPersistentParameter((String) stack.pop());
				break;
			case "tparam":
				value = requestContext.getTemporaryParameter((String) stack.pop());
				break;
			}
			stack.push(value == null ? defaultValue : value);
		}

		/**
		 * Metoda izvršava jednu od operacija: zbrajanje, oduzimanje, dijeljenje ili
		 * množenje.
		 * 
		 * @param stack
		 *            pomoćni stog
		 * @param element
		 *            element
		 */
		private void calculateOperation(Stack<Object> stack, ElementOperator element) {
			ValueWrapper first = new ValueWrapper(stack.pop());
			ValueWrapper second = new ValueWrapper(stack.pop());

			switch (((ElementOperator) element).getSymbol()) {
			case "+":
				first.add(second.getValue());
				break;
			case "-":
				first.subtract(second.getValue());
				break;
			case "*":
				first.multiply(second.getValue());
				break;
			case "/":
				first.divide(second.getValue());
			}
			stack.push(first.getValue());
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}

	};

	/**
	 * Konstruktor.
	 * 
	 * @param documentNode
	 *            {@link DocumentNode}
	 * @param requestContext
	 *            {@link RequestContext}
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		Objects.requireNonNull(documentNode);
		Objects.requireNonNull(requestContext);
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Metoda pokreće {@link SmartScriptEngine}.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}