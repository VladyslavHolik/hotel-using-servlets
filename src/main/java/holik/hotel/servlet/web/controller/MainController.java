package holik.hotel.servlet.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import holik.hotel.servlet.web.command.Command;
import holik.hotel.servlet.web.command.CommandManager;
import holik.hotel.servlet.path.PathParser;

/**
 * Controller that is responsible for managing all requests.
 */
public class MainController extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(MainController.class);
	private static final int REDIRECT_OFFSET = 9;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO get command here
		manage(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO get command here
		String uri = request.getRequestURI();
		manage(request, response);
	}

	private void manage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String commandName;
		if ("POST".equals(request.getMethod())) {
			commandName = request.getParameter("command");
		} else {
			commandName = PathParser.getCommand(request.getRequestURI());
		}
		Command command = CommandManager.get(commandName);
		LOG.debug("Request with command " + command);
		String page = command.execute(request, response);
		if (page != null) {
			if (!page.startsWith("redirect:")) {
				request.getRequestDispatcher(page).forward(request, response);
			} else {
				response.sendRedirect(page.substring(REDIRECT_OFFSET));
			}
		}
	}
}