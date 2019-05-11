package hr.fer.zemris.java.hw13.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Razred <code>TimeListener</code> je promatrač na kontekst Servleta. Kada se
 * Servlet stvori, promatrač pošalje trenutno vrijeme u milisekundama kao
 * vrijeme početaka rada Servleta. Vrijeme je poslano kao globalna varijabla
 * Servleta.
 * 
 * @author Filip
 *
 */
@WebListener
public class TimeListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("timeStarted", System.currentTimeMillis());

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
