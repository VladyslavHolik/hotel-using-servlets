package holik.hotel.servlet.web.command;

import holik.hotel.servlet.web.command.sort.SortMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Command that sets sorting method.
 */
public class SortingCommand implements Command {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String sortBy = request.getParameter("sortingMethod");
		
		if (!SortMethod.isValidMethod(sortBy)) {
			// Sorting method must be existing
			throw new IllegalArgumentException("Invalid sorting method");
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("sort", SortMethod.getMethod(sortBy));
		return "redirect:rooms";
	}
}
