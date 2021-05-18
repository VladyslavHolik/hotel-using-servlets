package holik.hotel.servlet.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import holik.hotel.servlet.command.constant.Pages;
import holik.hotel.servlet.command.sort.SortMethod;

/**
 * Command that sets sorting method.
 */
public class SortingCommand implements Command {
	private static final Logger LOG = Logger.getLogger(SortingCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String sortBy = request.getParameter("sortingMethod");
		
		String errorMessage = null;		
		String forward = Pages.PAGE__ERROR_PAGE;
		
		if (!SortMethod.isValidMethod(sortBy)) {
			errorMessage = "Invalid sort method";
			request.setAttribute("errorMessage", errorMessage);
			LOG.info(errorMessage);
			return forward;	
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("sort", SortMethod.getMethod(sortBy));
		return "redirect:rooms";

	}

}
