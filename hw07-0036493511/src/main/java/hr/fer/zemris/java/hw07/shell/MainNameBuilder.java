package hr.fer.zemris.java.hw07.shell;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred <code>MainNameBuilder</code> predstavlja implementaciju
 * <code>NameBuilder</code>-a koja sadrži listu <code>GroupNameBulder</code>-a i
 * <code>StringNameBuilder</code>-a te nad njima zove njihove implementacije
 * metode execute.
 * 
 * @author Filip
 *
 */
public class MainNameBuilder implements NameBuilder {

	/**
	 * Lista refernci <code>NameBuilder</code>-a.
	 */
	private List<NameBuilder> builders = new ArrayList<>();

	/**
	 * Konstruktor, prima listu referenci <code>NameBuilder</code>-a.
	 * 
	 * @param builders
	 *            lista <code>NameBuilder</code>-a
	 */
	public MainNameBuilder(List<NameBuilder> builders) {
		this.builders = builders;
	}

	@Override
	public void execute(NameBuilderInfo info) {
		for (NameBuilder builder : builders) {
			builder.execute(info);
		}
	}

}
