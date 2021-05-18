package holik.hotel.servlet.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import holik.hotel.servlet.web.command.constant.Pages;

/**
 * Command that forward user to registration page.
 */
public class GetSignUpCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return Pages.PAGE_SIGNUP;
	}

	@Override
	public String toString() {
		return "GetSignUpCommand";
	}

}
