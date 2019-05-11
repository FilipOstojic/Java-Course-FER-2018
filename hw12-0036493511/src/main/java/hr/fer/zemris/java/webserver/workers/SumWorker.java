package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Predstavlja {@link IWebWorker}-a koji izračunava sumu dvaju brojeva. Rezultat
 * kao i parametre prikazuje u tablici.
 * 
 * @author Filip
 *
 */
public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {

		int a = getParam(context, "a");
		int b = getParam(context, "b");

		setTempParams(context, a, b);

		context.getDispatcher().dispatchRequest("/private/calc.smscr");
	}

	/**
	 * Postavlja string vrijednost paramatara a i b te njihovog zbroja u mapu
	 * privremenih parametara.
	 * 
	 * @param context
	 *            {@link RequestContext}
	 * @param a
	 *            prvi parametar
	 * @param b
	 *            drugi parametar
	 */
	private void setTempParams(RequestContext context, int a, int b) {
		context.setTemporaryParameter("a", String.valueOf(a));
		context.setTemporaryParameter("b", String.valueOf(b));
		context.setTemporaryParameter("zbroj", String.valueOf(a + b));
	}

	/**
	 * Izračunava vrijednost parametara ovisno o tome jesu li ispravno zadani. Ako
	 * nisu postavljaju se na a=1 i b=2. Ako jesu postavljaju se na zadane
	 * vrijednosti.
	 * 
	 * @param context
	 *            {@link RequestContext}
	 * @param a
	 *            prvi parametar
	 * @param b
	 *            drugi parametar
	 */
	private int getParam(RequestContext context, String name) {
		try {
			switch (name) {
			case "a":
				return context.getParameter("a") == null ? 1 : Integer.parseInt(context.getParameter("a"));
			default:
				return context.getParameter("b") == null ? 2 : Integer.parseInt(context.getParameter("b"));
			}
		} catch (NumberFormatException e) {
			switch (name) {
			case "a":
				return 1;
			default:
				return 2;
			}
		}
	}

}
