package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Razred <code>TextNode</code> nasljeđuje razred <code>Element</code> te
 * predstavlja čvor koji sadrži String. CLOSINGTAG je tip tokena nakon kojeg
 * započinje ovaj čvor. Prazan je i ne može imati djecu.
 * 
 * @author Filip
 *
 */
public class TextNode extends Node {
	/**
	 * Varijabla čuva String vrijednost teksta.
	 */
	private String text;

	/**
	 * Konstruktor, prima tekst.
	 * 
	 * @param text
	 */
	public TextNode(String text) {
		super();
		this.text = text;
	}

	/**
	 * Metoda vraća vrijednost varijable text.
	 * 
	 * @return
	 */
	public String getText() {
		return text;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
}
