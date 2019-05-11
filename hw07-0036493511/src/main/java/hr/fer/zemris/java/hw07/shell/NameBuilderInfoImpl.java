package hr.fer.zemris.java.hw07.shell;

import java.util.regex.Matcher;

/**
 * Razred <code>NameBuilderInfoImpl</code> predstavlja implementaiju razreda
 * <code>NameBuilderInfo</code>.
 * 
 * @author Filip
 *
 */
public class NameBuilderInfoImpl implements NameBuilderInfo {
	/**
	 * StringBuilder.
	 */
	StringBuilder sb;
	/**
	 * Matcher.
	 */
	Matcher matcher;

	/**
	 * Konstruktor, prima matcher.
	 * 
	 * @param matcher
	 */
	public NameBuilderInfoImpl(Matcher matcher) {
		sb = new StringBuilder();
		this.matcher = matcher;
	}

	@Override
	public StringBuilder getStringBuilder() {
		return sb;
	}

	@Override
	public String getGroup(int index) {
		if (index > matcher.groupCount()) {
			throw new IllegalArgumentException("Group index too big.");
		}
		return matcher.group(index);
	}

}
