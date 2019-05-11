package hr.fer.zemris.java.hw03;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Razred <code>SmartScriptTester</code> je testni razred parsera. U komandnoj
 * liniji prima jedan String koji predstavlja putanju do .txt datoteke. Ispis bi
 * trebao biti isti kao u ulaznoj datoteci (razmaci ne moraju bit identični).
 * Ispisuje rezultat na standardni izlaz.
 * 
 * @author Filip
 *
 */
public class SmartScriptTester {

	public static void main(String[] args) throws IOException {
		String filepath = args[0];
		String docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); 

	}

	/**
	 * Metoda rekonstruira ulazni tekst iz korijena stabla.
	 * 
	 * @param node
	 * @return String tekst
	 */
	public static String createOriginalDocumentBody(Node node) {
		StringBuilder sb = new StringBuilder();
		int numberOfChildren = node.numberOfChildren();
		if (node instanceof DocumentNode) {
			for (int i = 0; i < numberOfChildren; i++) {
				Node tmpNode = node.getChild(i);
				sb.append(createOriginalDocumentBody(tmpNode));
			}
		} else if (node instanceof ForLoopNode) {
			sb.append(((ForLoopNode) node).toString());
			for (int i = 0; i < numberOfChildren; i++) {
				Node tmpNode = node.getChild(i);
				sb.append(createOriginalDocumentBody(tmpNode));
			}
			sb.append("{$END$}");
		} else if (node instanceof EchoNode) {
			sb.append(node.toString());
		} else if (node instanceof TextNode) {
			sb.append(((TextNode) node).getText());
		}
		return sb.toString();
	}

}
