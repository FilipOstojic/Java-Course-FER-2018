package hr.fer.zemris.java.hw07.shell;

/**
 * Razred <code>GroupNameBuilder</code> se koristi u
 * <code>NameBuilderParser</code>-u kao posebna vrsta znakova koja započinje s
 * ${ i završava s }.
 * 
 * @author Filip
 *
 */
public class GroupNameBuilder implements NameBuilder {
	private String text;

	public GroupNameBuilder(String text) {
		this.text = text;
	}

	@Override
	public void execute(NameBuilderInfo info) {
		String word;
		String format;
		if (text.contains(",")) {
			String[] chunks = text.split(",");
			word = info.getGroup(Integer.parseInt(chunks[0]));
			format = chunks[1];
			info.getStringBuilder().append(String.format("%" + format + "d", Integer.parseInt(word)));
			return;
		}
		info.getStringBuilder().append(String.format("%s", info.getGroup(Integer.parseInt(text))));
	}
}
