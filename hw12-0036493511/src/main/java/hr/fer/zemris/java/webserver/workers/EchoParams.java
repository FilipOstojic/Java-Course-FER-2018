package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Predstavlja {@link IWebWorker}-a koji predstavlja s internetsku stranicu s
 * tablicom svih predanih parametara i njihovim s+vrijednostima.
 * 
 * @author Filip
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE html>");
		sb.append("<html><body>");
		sb.append("<table border=\"1\" style=\"width:50%\">");
		sb.append("<tr><th>parameter</th><th>value</th></tr>");

		for (String name : context.getParameterNames()) {
			sb.append("<tr>");
			sb.append(String.format("<td align=\"center\">%s</td>", name));
			sb.append(String.format("<td align=\"center\">%s</td>", context.getParameter(name)));
			sb.append("</tr>");
		}

		sb.append("</table></body></html>");

		try {
			context.write(sb.toString());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

}
