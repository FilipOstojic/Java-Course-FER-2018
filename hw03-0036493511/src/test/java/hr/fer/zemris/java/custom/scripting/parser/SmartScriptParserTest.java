package hr.fer.zemris.java.custom.scripting.parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Nekoliko osnovnih testova za SmartScriptParser
 * 
 * @author Filip
 *
 */
public class SmartScriptParserTest {

	@Test
	public void testChildren() {
		String text = loader("testOsnovni.txt");
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(text);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();

		Assert.assertEquals(document.numberOfChildren(), 4);
		Assert.assertEquals(document.getChild(0).numberOfChildren(), 0);
		Assert.assertEquals(document.getChild(1).numberOfChildren(), 3);
		Assert.assertEquals(document.getChild(2).numberOfChildren(), 0);
		Assert.assertEquals(document.getChild(3).numberOfChildren(), 5);
	}
	@Ignore
	@Test
	public void testPrimjer() {
		String text = loader("primjer.txt");
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(text);
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
	
	
//	@Ignore
	@Test
	public void testPrimjer2() {
		String text = loader("primjer2.txt");
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(text);
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

	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}

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
