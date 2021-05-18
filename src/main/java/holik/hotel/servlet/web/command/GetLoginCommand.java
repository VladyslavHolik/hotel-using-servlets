package holik.hotel.servlet.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import holik.hotel.servlet.web.command.constant.Pages;

/**
 * Command that forwards user to login page.
 */
public class GetLoginCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return Pages.PAGE_SIGN_IN;
	}

	@Override
	public String toString() {
		return "GetLoginCommand";
	}
}
