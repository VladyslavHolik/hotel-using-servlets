package holik.hotel.servlet.web.controller;

import holik.hotel.servlet.web.command.Command;
import holik.hotel.servlet.web.context.ApplicationContext;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller that is responsible for managing all requests.
 */
public class MainController extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(MainController.class);
	private static final int REDIRECT_OFFSET = 9;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Command command = ApplicationContext.get(request.getRequestURI());
		manage(request, response, command);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String commandName = request.getParameter("command");
		Command command = ApplicationContext.get(commandName);
		manage(request, response, command);
	}

	private void manage(HttpServletRequest request, HttpServletResponse response, Command command) throws IOException, ServletException {
		LOG.debug("Request with command " + command);
		String page = command.execute(request, response);
		if (page != null) {
			if (page.startsWith("redirect:")) {
				response.sendRedirect(page.substring(REDIRECT_OFFSET));
			} else {
				request.getRequestDispatcher(page).forward(request, response);
			}
		}
	}
}
