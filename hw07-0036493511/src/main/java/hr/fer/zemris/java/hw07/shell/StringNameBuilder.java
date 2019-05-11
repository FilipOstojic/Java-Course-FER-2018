package hr.fer.zemris.java.hw07.shell;

/**
 * Razred <code>StringNameBuilder</code> predstavlja implementaciju
 * <code>NameBuilder</code>-a koja dodaje StringBuilderu string konstantu.
 * 
 * @author Filip
 *
 */
public class StringNameBuilder implements NameBuilder {

	/**
	 * String konstanta.
	 */
	private String text;

	/**
	 * Konstruktor, prima string tekst.
	 * 
	 * @param text
	 *            string tekst
	 */
	public StringNameBuilder(String text) {
		this.text = text;
	}

	@Override
	public void execute(NameBuilderInfo info) {
		info.getStringBuilder().append(text);
	}

}
