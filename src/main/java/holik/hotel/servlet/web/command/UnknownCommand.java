package holik.hotel.servlet.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import holik.hotel.servlet.web.command.constant.Pages;

/**
 * Commands that forwards user to error page due to unknown command.
 */
public class UnknownCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String errorMessage = "That command is unknown";
		request.setAttribute("errorMessage", errorMessage);
		return Pages.PAGE_ERROR_PAGE;
	}

}