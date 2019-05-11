package hr.fer.zemris.java.hw07.shell;

/**
 * Sučelje <code>NameBuilder</code> je funkcijsko sučelje s metodom
 * execute(NameBuilderInfo). Sve vrste NameBuilder-a moraju implementirati ovo
 * sučelje.
 * 
 * @author Filip
 *
 */
public interface NameBuilder {
	/**
	 * Metoda za dodavanje teksta u StringBUilder razreda
	 * <code>NameBuilderInfoImpl</code>.
	 * 
	 * @param info
	 *            {@linkplain NameBuilderInfo}
	 */
	void execute(NameBuilderInfo info);
}
