package hr.fer.zemris.java.webserver.workers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Predstavlja {@link IWebWorker}-a koji vrši promjenu boje pozadine. Početna boja je siva.
 * Boje na izbor: bijela, crvena, zelena, plava i narančasta. Poruka o uspiješnosti 
 * 
 * @author Filip
 *
 */
public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		for (String param : context.getParameterNames()) {
			if (param.equals("bgcolor")) {
				String color = context.getParameter(param);
				if (checkIfColor(color)) {
					context.setPersistentParameter("bgcolor", color);
					context.write("Color succesfully updated.");
				} else {
					context.write("Color unsuccesfully updated.");
				}
			}
		}
	}

	/**
	 * Provjerava je li boja ispravna, tj. sastoji li se od točno 6 heksadekadskih
	 * znamenaka.
	 * 
	 * @param color
	 *            string reprezentacija boje
	 * @return true, ako je boja ispravno zadana, inače false
	 */
	private boolean checkIfColor(String color) {
		Pattern pattern = Pattern.compile("[0-9A-F]{6}");
		Matcher matcher = pattern.matcher(color.toUpperCase());
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

}
