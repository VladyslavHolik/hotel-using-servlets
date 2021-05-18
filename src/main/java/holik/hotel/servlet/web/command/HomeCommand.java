package holik.hotel.servlet.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import holik.hotel.servlet.web.command.constant.Pages;

/**
 * Command that forward user to home page.
 */
public final class HomeCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return Pages.PAGE_HOME;
	}

	@Override
	public String toString() {
		return "HomeCommand";
	}
}
