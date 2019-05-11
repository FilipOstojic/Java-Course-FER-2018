package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Predstavlja {@link IWebWorker}-a koji prikazuje internetsku stranicu s
 * linkovima na: {@link HelloWorker} i {@link CircleWorker} te na pametne skripte:
 * osnovni, brojPoziva te fibonaccih. Ima i dijalog za upis parametara za
 * {@link SumWorker} te padajuću listu s izborom boja pozadine.
 * 
 * @author Filip
 *
 */
public class HomeWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String color = "7f7f7f";
		for (String name : context.getPersistentParameterNames()) {
			if (name.equals("bgcolor")) {
				color = context.getPersistentParameter(name);
			}
		}

		context.setTemporaryParameter("background", color);
		context.getDispatcher().dispatchRequest("/private/home.smscr");
	}

}
