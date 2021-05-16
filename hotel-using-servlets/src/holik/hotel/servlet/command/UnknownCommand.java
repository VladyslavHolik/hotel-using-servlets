package holik.hotel.servlet.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import holik.hotel.servlet.path.Path;

/**
 * Commands that forwards user to error page due to unknown command.
 */
public class UnknownCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String errorMessage = "That command is unknown";
		request.setAttribute("errorMessage", errorMessage);
		return Path.PAGE__ERROR_PAGE;
	}

}
