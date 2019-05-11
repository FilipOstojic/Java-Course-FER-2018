package hr.fer.zemris.java.custom.scripting.demo;

import static hr.fer.zemris.java.custom.scripting.demo.Util.readFromDisk;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Razred <code>BasicMain</code> testira rad parsera i rad
 * {@link SmartScriptEngine}-a. Testira rad s for petljom te funkcije sin i
 * decfmt. Ne očekuju se argumenti.
 * 
 * @author Filip
 *
 */
public class BasicMain {
	/**
	 * Metoda se poziva kada se pokrene program.
	 * 
	 * @param args
	 *            argumenti komandne linije
	 */
	public static void main(String[] args) {

		String documentBody = readFromDisk("src/main/resources/osnovni.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();

		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}
}
