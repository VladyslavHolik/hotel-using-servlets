package holik.hotel.servlet.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import holik.hotel.servlet.command.constant.Pages;

/**
 * Command that forwards user to login page.
 */
public class GetLoginCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return Pages.PAGE__SIGNIN;
	}

	@Override
	public String toString() {
		return "GetLoginCommand";
	}
}
