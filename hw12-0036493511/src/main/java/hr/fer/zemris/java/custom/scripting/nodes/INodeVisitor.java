package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Sučelje <code>INodeVisitor</code> predstavlja oblikovni obrazac Visitor. Služi za 
 * implementaciju metoda koje se razlikuju od čvora do čvora.
 * @author Filip
 *
 */
public interface INodeVisitor {
	/**
	 * 
	 * @param node
	 */
	public void visitTextNode(TextNode node);
	/**
	 * 
	 * @param node
	 */
	public void visitForLoopNode(ForLoopNode node);
	/**
	 * 
	 * @param node
	 */
	public void visitEchoNode(EchoNode node);
	/**
	 * 
	 * @param node
	 */
	public void visitDocumentNode(DocumentNode node);
}
