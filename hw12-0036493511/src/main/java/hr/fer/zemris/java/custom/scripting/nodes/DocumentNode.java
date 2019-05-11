package hr.fer.zemris.java.custom.scripting.nodes;


/**
 * Razred <code>DocumentNode</code> predstavlja početni čvor
 * te skupa sa svojom djecom čini stablo ulaznog teksta.
 * @author Filip
 *
 */
public class DocumentNode extends Node {
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
}
