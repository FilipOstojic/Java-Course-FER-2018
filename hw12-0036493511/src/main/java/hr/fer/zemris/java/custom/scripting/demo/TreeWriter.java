package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Razred <code>TreeWriter</code> za provjeru parsiranja i ispisa ulazne
 * datoteke. Koristi oblikovni obrazac Visitor. Na standardni izlaz ispusuje
 * parsirani dokument. Kao argument se očekuje putanja do datoteke koja se
 * parsira.
 * 
 * @author Filip
 *
 */
public class TreeWriter {
	/**
	 * Metoda koja s epoziva kada se pokrene program.
	 * 
	 * @param args
	 *            argumenti komandne linije
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Expected file path only.");
			System.exit(-1);
		}
		String filepath = args[0];
		String docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		}
		WriterVisitor visitor = new WriterVisitor();
		parser.getDocumentNode().accept(visitor);
	}

	/**
	 * Razred implementira sučelje <code>INodeVisitor</code> te nudi implementaciju
	 * njegovih metoda. Predstavlja oblikovni obrazac Visitor. Metode ispisuju
	 * sadržaj svakog čvora ovisno o njegovoj vrsti.
	 * 
	 * @author Filip
	 *
	 */
	static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.getText());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print(node);
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
			System.out.print("{$END$}");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print(node);
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}
	}
}
